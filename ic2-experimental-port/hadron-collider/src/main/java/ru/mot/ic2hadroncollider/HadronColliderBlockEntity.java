package ru.mot.ic2hadroncollider;

import ic2.api.energy.tile.IExplosionPowerOverride;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.Recipes;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Fluids;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.GuiParser;
import ic2.core.init.MainConfig;
import ic2.core.network.GrowingBuffer;
import ic2.core.network.GuiSynced;
import ic2.core.ref.Ic2Fluids;
import ic2.core.ref.Ic2ScreenHandlers;
import ic2.core.ref.Ic2SoundEvents;
import ic2.core.util.ConfigUtil;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Liquid UU-matter producer for IC2 Experimental.
 *
 * <p>Compared with IC2's Matter Fabricator ({@code ic2:matter_generator}, 1 mB per
 * {@code 1 000 000 * uuEnergyFactor} EU at HV) the collider:
 * <ul>
 *   <li>accepts IV (tier 5) input and spends every EU it receives in the same tick;</li>
 *   <li>builds up beam stability while it runs continuously; the cost per mB drops
 *       linearly from 100% of the fabricator cost (cold beam) to 40% (stable beam),
 *       so a stable collider makes 2.5x the UU-matter per EU;</li>
 *   <li>pays a constant 512 EU/t magnetic-confinement upkeep while running, so it
 *       only outperforms the fabricator on high-power grids;</li>
 *   <li>loses beam stability four times faster than it gains it when stopped by
 *       redstone, lack of power or a full tank.</li>
 * </ul>
 * Scrap amplifiers follow the fabricator rule: each amplifier unit adds 5 EU of
 * synthesis work for 1 EU of received power.
 */
public final class HadronColliderBlockEntity extends TileEntityElectricMachine
        implements IHasGui, IUpgradableBlock, IExplosionPowerOverride {
    public static final int BASE_TIER = 5;
    public static final int ENERGY_BUFFER = 1_000_000;
    public static final int UPKEEP_EU_PER_TICK = 512;
    public static final int BEAM_MAX = 1_000;
    public static final int BEAM_RISE_PER_TICK = 1;
    public static final int BEAM_DECAY_PER_TICK = 4;
    /** Cost per mB at full beam stability, in permille of the Matter Fabricator cost. */
    public static final int STABLE_COST_PERMILLE = 400;
    public static final int TANK_CAPACITY_MB = 16_000;
    public static final int AMPLIFIER_BONUS = 5;
    private static final int AMPLIFIER_REFILL_BELOW = 10_000;
    private static final String GUI_DEFINITION = "/assets/ic2_hadron_collider/guidef/hadron_collider.xml";
    private static volatile GuiParser.GuiNode guiDefinition;

    public final InvSlotProcessable<IRecipeInput, Integer, ItemStack> amplifierSlot =
            new InvSlotProcessable<IRecipeInput, Integer, ItemStack>(
                    this, "scrap", 1, level -> Recipes.matterAmplifier) {
                @Override
                protected ItemStack getInput(ItemStack stack) {
                    return stack;
                }

                @Override
                protected void setInput(ItemStack stack) {
                    put(stack);
                }
            };
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "output", 1);
    public final InvSlotConsumableLiquid containerSlot;
    public final InvSlotUpgrade upgradeSlot;
    @GuiSynced
    public final Ic2FluidTank fluidTank;
    private final Redstone redstone;

    /** Beam stability in permille. */
    @GuiSynced
    public int beam;
    /** Stored scrap-amplifier units. */
    @GuiSynced
    public int amplifier;
    @GuiSynced
    public int synthesisPercent;
    /** EU-equivalent synthesis work accumulated towards the next mB. */
    private double synthesis;

    public HadronColliderBlockEntity(BlockPos position, BlockState state) {
        super(HadronColliderContent.HADRON_COLLIDER_BLOCK_ENTITY.get(), position, state,
                ENERGY_BUFFER, BASE_TIER);
        containerSlot = new InvSlotConsumableLiquidByList(this, "container", InvSlot.Access.I, 1,
                InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill, Ic2Fluids.UU_MATTER.still);
        upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4);
        redstone = addComponent(new Redstone(this));
        redstone.subscribe(level -> energy.setEnabled(level == 0));
        Fluids fluids = addComponent(new Fluids(this));
        fluidTank = fluids.addTank("fluidTank", TANK_CAPACITY_MB,
                Fluids.fluidPredicate(Ic2Fluids.UU_MATTER.still));
    }

    /** EU per mB on IC2's Matter Fabricator, honouring {@code balance/uuEnergyFactor}. */
    public static double fabricatorEnergyPerMb() {
        return Math.round(1_000_000.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor"));
    }

    /** EU-equivalent work for one mB at the given beam stability (permille). */
    public static double energyPerMb(int beamPermille) {
        int costPermille = 1_000 - (1_000 - STABLE_COST_PERMILLE) * beamPermille / BEAM_MAX;
        return fabricatorEnergyPerMb() * costPermille / 1_000.0;
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        boolean changed = upgradeSlot.tickNoMark();
        boolean running = !redstone.hasRedstoneInput()
                && fluidTank.getFluidAmount() < fluidTank.getCapacity()
                && energy.getEnergy() >= UPKEEP_EU_PER_TICK;
        if (running) {
            energy.useEnergy(UPKEEP_EU_PER_TICK);
            if (amplifier < AMPLIFIER_REFILL_BELOW) {
                MachineRecipeResult<IRecipeInput, Integer, ItemStack> result = amplifierSlot.process();
                if (result != null) {
                    amplifierSlot.consume(result);
                    amplifier += result.getOutput();
                    changed = true;
                }
            }
            double received = energy.getEnergy();
            if (received > 0.0) {
                energy.useEnergy(received);
                int amplified = (int) Math.min(amplifier, Math.floor(received));
                amplifier -= amplified;
                synthesis += received + (double) AMPLIFIER_BONUS * amplified;
            }
            double cost = energyPerMb(beam);
            int free = fluidTank.getCapacity() - fluidTank.getFluidAmount();
            int produced = (int) Math.min(free, Math.floor(synthesis / cost));
            if (produced > 0) {
                fluidTank.fillMbUnchecked(Ic2FluidStack.create(Ic2Fluids.UU_MATTER.still, produced), false);
                synthesis -= produced * cost;
                changed = true;
            }
            if (produced == free) {
                // Tank is full: excess work cannot be stored beyond the next mB.
                synthesis = Math.min(synthesis, cost);
            }
            beam = Math.min(BEAM_MAX, beam + BEAM_RISE_PER_TICK);
        } else {
            beam = Math.max(0, beam - BEAM_DECAY_PER_TICK);
        }
        synthesisPercent = (int) Math.min(100.0, 100.0 * synthesis / energyPerMb(beam));
        if (containerSlot.processFromTank(fluidTank, outputSlot)) {
            changed = true;
        }
        setActiveState(running);
        if (changed) {
            m_6596_();
        }
    }

    @Override
    protected void onLoaded() {
        super.onLoaded();
        if (!m_58904_().f_46443_) {
            updateUpgrades();
        }
    }

    @Override
    public void m_6596_() {
        super.m_6596_();
        if (m_58904_() != null && !m_58904_().f_46443_) {
            updateUpgrades();
        }
    }

    private void updateUpgrades() {
        upgradeSlot.onChanged();
        int tier = upgradeSlot.getTier(BASE_TIER);
        energy.setSinkTier(tier);
        dischargeSlot.setTier(tier);
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        beam = tag.m_128451_("beam");
        amplifier = tag.m_128451_("amplifier");
        synthesis = tag.m_128459_("synthesis");
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128405_("beam", beam);
        tag.m_128405_("amplifier", amplifier);
        tag.m_128347_("synthesis", synthesis);
    }

    @Override
    public SoundEvent getLoopingSoundEvent() {
        return Ic2SoundEvents.MACHINE_FABRICATOR_LOOP;
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(Ic2ScreenHandlers.DYNAMIC_BE, syncId, player.m_150109_(), this, guiDefinition());
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(int syncId, Inventory inventory, GrowingBuffer buffer) {
        return DynamicContainer.create(Ic2ScreenHandlers.DYNAMIC_BE, syncId, inventory, this, guiDefinition());
    }

    /**
     * IC2's {@code GuiParser.parse(ResourceLocation, Class)} reads guidef XML through the
     * IC2 module, which cannot see resources of other mod jars. Read the XML from this
     * addon's own module and hand it to IC2's stream parser instead.
     */
    private static GuiParser.GuiNode guiDefinition() {
        GuiParser.GuiNode node = guiDefinition;
        if (node == null) {
            try (InputStream stream = HadronColliderBlockEntity.class.getResourceAsStream(GUI_DEFINITION)) {
                if (stream == null) {
                    throw new IllegalStateException("Missing " + GUI_DEFINITION);
                }
                Method parse = GuiParser.class.getDeclaredMethod("parse", InputStream.class, Class.class);
                parse.setAccessible(true);
                node = (GuiParser.GuiNode) parse.invoke(null, stream, HadronColliderBlockEntity.class);
            } catch (ReflectiveOperationException | java.io.IOException exception) {
                throw new IllegalStateException("Unable to load " + GUI_DEFINITION, exception);
            }
            guiDefinition = node;
        }
        return node;
    }

    @Override
    public boolean getGuiState(String name) {
        if ("amplified".equals(name)) {
            return amplifier > 0;
        }
        return super.getGuiState(name);
    }

    public int getBeamPercent() {
        return beam / 10;
    }

    public int getSynthesisPercent() {
        return synthesisPercent;
    }

    /** Current price of one mB in kEU, as shown in the GUI. */
    public int getCostKiloEu() {
        return (int) Math.round(energyPerMb(beam) / 1_000.0);
    }

    public int getAmplifier() {
        return amplifier;
    }

    public int getStoredUu() {
        return fluidTank.getFluidAmount();
    }

    @Override
    public double getEnergy() {
        return energy.getEnergy();
    }

    @Override
    public boolean useEnergy(double amount) {
        return energy.useEnergy(amount);
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(
                UpgradableProperty.RedstoneSensitive,
                UpgradableProperty.Transformer,
                UpgradableProperty.ItemConsuming,
                UpgradableProperty.ItemProducing,
                UpgradableProperty.FluidProducing);
    }

    @Override
    public boolean shouldExplode() {
        return true;
    }

    @Override
    public float getExplosionPower(int tier, float defaultPower) {
        return 20.0F;
    }
}
