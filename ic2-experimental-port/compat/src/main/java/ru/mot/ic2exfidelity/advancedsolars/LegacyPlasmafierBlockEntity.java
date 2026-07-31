package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.comp.Energy;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.tileentity.TileEntityBase;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.network.GrowingBuffer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** Exact IC2 Classic Plasmafier material and energy cycle. */
public final class LegacyPlasmafierBlockEntity extends TileEntityBase implements IHasGui {
    public static final int CAPACITY = 5_120_000;
    public static final int INPUT_TIER = 4;
    public static final int ENERGY_PER_PLASMA = 10_240;
    public static final int PLASMA_PER_CELL = 1_000;
    public static final int MAX_PLASMA = 10_000;

    public final InvSlot matterInput;
    public final InvSlot emptyCellInput;
    public final InvSlotOutput output;
    private final Energy energy;
    private int plasma;
    private int uuMatter;
    private long ticks;

    public LegacyPlasmafierBlockEntity(BlockPos position, BlockState state) {
        super(LegacyAdvancedSolarsPrerequisites.PLASMAFIER_BLOCK_ENTITY.get(),
                position, state);
        matterInput = new InvSlot(this, "input", InvSlot.Access.I, 1, InvSlot.InvSide.ANY) {
            @Override
            public boolean accepts(ItemStack stack) {
                return stack.m_41720_()
                        == net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(
                                new net.minecraft.resources.ResourceLocation("ic2", "uu_matter"));
            }
        };
        emptyCellInput = new InvSlot(
                this, "container", InvSlot.Access.I, 1, InvSlot.InvSide.ANY) {
            @Override
            public boolean accepts(ItemStack stack) {
                return stack.m_41720_()
                        == net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(
                                new net.minecraft.resources.ResourceLocation("ic2", "empty_cell"));
            }
        };
        output = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.ANY);
        energy = addComponent(Energy.asBasicSink(this, CAPACITY, INPUT_TIER));
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        processTick();
    }

    public void processTick() {
        ticks++;
        if (uuMatter < 50 && ticks % 40L == 0L && isItem(matterInput.get(), "uu_matter")) {
            uuMatter += 100;
            consumeOne(matterInput);
        }
        if (energy.getEnergy() >= ENERGY_PER_PLASMA && uuMatter > 0 && plasma < MAX_PLASMA) {
            energy.useEnergy(ENERGY_PER_PLASMA);
            uuMatter--;
            plasma++;
        }
        if (plasma >= PLASMA_PER_CELL
                && ticks % 50L == 0L
                && isItem(emptyCellInput.get(), "empty_cell")) {
            // Classic subtracts plasma before checking whether the output slot
            // can accept a cell; preserve that observable edge case.
            plasma -= PLASMA_PER_CELL;
            ItemStack existing = output.get();
            net.minecraft.world.item.Item plasmaCell =
                    LegacyAdvancedSolarsPrerequisites.PLASMA_CELL.get();
            if (existing.m_41619_()) {
                output.put(new ItemStack(plasmaCell));
                consumeOne(emptyCellInput);
            } else if (existing.m_41720_() == plasmaCell
                    && existing.m_41613_() < existing.m_41741_()) {
                existing.m_41769_(1);
                output.put(existing);
                consumeOne(emptyCellInput);
            }
        }
        setActiveState(energy.getEnergy() >= ENERGY_PER_PLASMA
                && uuMatter > 0 && plasma < MAX_PLASMA);
        m_6596_();
    }

    private static boolean isItem(ItemStack stack, String id) {
        return !stack.m_41619_()
                && stack.m_41720_()
                        == net.minecraftforge.registries.ForgeRegistries.ITEMS.getValue(
                                new net.minecraft.resources.ResourceLocation("ic2", id));
    }

    private static void consumeOne(InvSlot slot) {
        ItemStack stack = slot.get();
        stack.m_41774_(1);
        if (stack.m_41619_()) {
            slot.clear();
        } else {
            slot.put(stack);
        }
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        plasma = tag.m_128451_("plasma");
        uuMatter = tag.m_128451_("uuMatter");
        ticks = tag.m_128454_("ticks");
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128405_("plasma", plasma);
        tag.m_128405_("uuMatter", uuMatter);
        tag.m_128356_("ticks", ticks);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer buffer) {
        return DynamicContainer.create(syncId, inventory, this);
    }

    public int getPlasma() {
        return plasma;
    }

    public int getUuMatter() {
        return uuMatter;
    }

    public int getStoredEU() {
        return (int) energy.getEnergy();
    }

    public void forceAddEnergyForTest(double amount) {
        energy.addEnergy(amount);
    }

    public void forceSetTicksForTest(long value) {
        ticks = value;
    }

    public void forceSetPlasmaForTest(int value) {
        plasma = value;
    }
}
