package ru.mot.ic2exfidelity.legacy;

import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.tileentity.TileEntityInventory;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.gui.dynamic.DynamicContainer;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.network.GrowingBuffer;
import ic2.core.ref.Ic2Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import ru.mot.ic2exfidelity.integration.RestoredLegacyContent;

/** Exact 20-tick cadence and 3x3x3 structure logic of IC2 2.8.222's coke kiln. */
public final class LegacyCokeKilnBlockEntity extends TileEntityInventory
        implements IHasGui, IGuiValueProvider {
    private static final int TICK_RATE = 20;
    private static final int OPERATION_LENGTH = 1_800;
    private static final int LOG_CREOSOTE_MB = 250;
    private static final int COAL_CREOSOTE_MB = 500;

    public final InvSlotOutput outputSlot;
    private int updateTicker = ic2.core.IC2.random.m_188503_(TICK_RATE);
    private boolean formed;
    private int progress;
    private int operationLength;
    private KilnRecipe recipe;
    private float guiProgress;

    public LegacyCokeKilnBlockEntity(BlockPos pos, BlockState state) {
        super(RestoredLegacyContent.COKE_KILN_BLOCK_ENTITY.get(), pos, state);
        outputSlot = new InvSlotOutput(this, "output", 1, InvSlot.InvSide.ANY);
    }

    @Override
    public void m_142466_(CompoundTag tag) {
        super.m_142466_(tag);
        progress = tag.m_128451_("progress");
        operationLength = tag.m_128451_("operationLength");
    }

    @Override
    public void m_183515_(CompoundTag tag) {
        super.m_183515_(tag);
        tag.m_128405_("progress", progress);
        tag.m_128405_("operationLength", operationLength);
    }

    @Override
    protected void updateEntityServer() {
        super.updateEntityServer();
        if (++updateTicker % TICK_RATE != 0) {
            return;
        }

        formed = hasValidStructure();
        if (!formed) {
            progress = 0;
            guiProgress = 0.0F;
            setActive(false);
            return;
        }

        boolean changed = false;
        if (canWork()) {
            setActive(true);
            if (progress == 0) {
                changed = true;
            }
            if (progress < OPERATION_LENGTH) {
                progress += TICK_RATE;
            }
            if (progress >= OPERATION_LENGTH) {
                finishWork();
                changed = true;
            }
        } else {
            setActive(false);
        }

        guiProgress = progress == 0 || operationLength == 0
                ? 0.0F : (float) progress / (float) operationLength;
        if (changed) {
            m_6596_();
        }
    }

    public boolean hasValidStructure() {
        Level level = m_58904_();
        Direction facing = getFacing();
        if (level == null || facing.m_122434_().m_122478_()) {
            return false;
        }
        BlockPos center = m_58899_().m_121945_(facing.m_122424_());
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos bottom = center.m_7918_(dx, -1, dz);
                if (dx == 0 && dz == 0) {
                    if (!(level.m_7702_(bottom) instanceof LegacyCokeKilnGrateBlockEntity)) {
                        return false;
                    }
                } else if (level.m_8055_(bottom).m_60734_()
                        != RestoredLegacyContent.REFRACTORY_BRICKS_BLOCK.get()) {
                    return false;
                }

                BlockPos middle = center.m_7918_(dx, 0, dz);
                if (dx == 0 && dz == 0) {
                    if (!level.m_8055_(middle).m_60795_()) {
                        return false;
                    }
                } else if (middle.equals(m_58899_())) {
                    if (level.m_7702_(middle) != this) {
                        return false;
                    }
                } else if (level.m_8055_(middle).m_60734_()
                        != RestoredLegacyContent.REFRACTORY_BRICKS_BLOCK.get()) {
                    return false;
                }

                BlockPos top = center.m_7918_(dx, 1, dz);
                if (dx == 0 && dz == 0) {
                    if (!(level.m_7702_(top) instanceof LegacyCokeKilnHatchBlockEntity)) {
                        return false;
                    }
                } else if (level.m_8055_(top).m_60734_()
                        != RestoredLegacyContent.REFRACTORY_BRICKS_BLOCK.get()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canWork() {
        LegacyCokeKilnHatchBlockEntity hatch = getHatch();
        LegacyCokeKilnGrateBlockEntity grate = getGrate();
        if (hatch == null || grate == null) {
            return false;
        }
        ItemStack input = hatch.inventory.get();
        if (input.m_41619_()) {
            return false;
        }
        KilnRecipe found = findRecipe(input);
        if (recipe != null && !recipe.equals(found)) {
            resetRecipe();
        }
        if (recipe == null) {
            recipe = found;
            operationLength = found == null ? 0 : OPERATION_LENGTH;
        }
        if (recipe == null) {
            return false;
        }
        ItemStack output = new ItemStack(recipe.output());
        Ic2FluidStack creosote = Ic2FluidStack.create(Ic2Fluids.CREOSOTE.still, recipe.creosoteMb());
        return outputSlot.canAdd(output)
                && grate.fluidTank.fillMb(creosote, true) >= recipe.creosoteMb();
    }

    private void finishWork() {
        LegacyCokeKilnHatchBlockEntity hatch = getHatch();
        LegacyCokeKilnGrateBlockEntity grate = getGrate();
        if (hatch == null || grate == null || recipe == null) {
            return;
        }
        hatch.inventory.get().m_41774_(1);
        hatch.inventory.onChanged();
        outputSlot.add(new ItemStack(recipe.output()));
        grate.fluidTank.fillMb(
                Ic2FluidStack.create(Ic2Fluids.CREOSOTE.still, recipe.creosoteMb()), false);
        progress = 0;
    }

    private LegacyCokeKilnHatchBlockEntity getHatch() {
        Level level = m_58904_();
        return level != null && level.m_7702_(structureCenter().m_7494_())
                instanceof LegacyCokeKilnHatchBlockEntity hatch ? hatch : null;
    }

    private LegacyCokeKilnGrateBlockEntity getGrate() {
        Level level = m_58904_();
        return level != null && level.m_7702_(structureCenter().m_7495_())
                instanceof LegacyCokeKilnGrateBlockEntity grate ? grate : null;
    }

    private BlockPos structureCenter() {
        return m_58899_().m_121945_(getFacing().m_122424_());
    }

    private void resetRecipe() {
        progress = 0;
        operationLength = 0;
        recipe = null;
    }

    private static KilnRecipe findRecipe(ItemStack input) {
        Item coal = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "coal"));
        if (input.m_41720_() == coal) {
            return new KilnRecipe(RestoredLegacyContent.COKE.get(), COAL_CREOSOTE_MB);
        }
        if (input.m_204117_(ItemTags.f_13182_)) {
            Item charcoal = ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft", "charcoal"));
            return charcoal == null ? null : new KilnRecipe(charcoal, LOG_CREOSOTE_MB);
        }
        return null;
    }

    public boolean isFormed() {
        return formed;
    }

    public int getProgress() {
        return progress;
    }

    @Override
    public double getGuiValue(String name) {
        if ("progress".equals(name)) {
            return guiProgress;
        }
        throw new IllegalArgumentException("Unknown coke-kiln GUI value: " + name);
    }

    @Override
    public ContainerBase<?> createServerScreenHandler(int syncId, Player player) {
        return DynamicContainer.create(syncId, player.m_150109_(), this);
    }

    @Override
    public ContainerBase<?> createClientScreenHandler(
            int syncId, Inventory inventory, GrowingBuffer data) {
        return DynamicContainer.create(syncId, inventory, this);
    }

    private record KilnRecipe(Item output, int creosoteMb) {
    }
}
