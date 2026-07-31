/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls.machine.multi;

import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.features.multiblock.ITexturedMultiBlock;
import ic2.core.block.base.tiles.BaseLinkingTileEntity;
import ic2.core.block.base.tiles.impls.machine.multi.BaseColossalMachineTileEntity;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.utils.helpers.NBTUtils;
import ic2.core.utils.math.StructureBuilder;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DynamicColossalMachineTileEntity
extends BaseColossalMachineTileEntity
implements ITexturedMultiBlock,
IWrenchRemovable {
    protected int structureSize;

    public DynamicColossalMachineTileEntity(BlockPos pos, BlockState state, int energyPerTick, int progressPerTick, int maxProgress, int maxInput) {
        this(pos, state, 17, 8, energyPerTick, progressPerTick, maxProgress, maxInput);
    }

    public DynamicColossalMachineTileEntity(BlockPos pos, BlockState state, int size, int recipeSlots, int energyPerTick, int progressPerTick, int maxProgress, int maxInput) {
        super(pos, state, size, recipeSlots, energyPerTick, maxProgress, 100000, maxInput);
        this.defaultProgressPerTick = progressPerTick;
        this.progressPerTick = progressPerTick;
        this.addNetworkFields("children");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "structure_size", this.structureSize, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.structureSize = compound.m_128451_("structure_size");
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.65;
    }

    @Override
    public int[] getAllSlots(boolean input) {
        int[] nArray;
        if (input) {
            int[] nArray2 = new int[8];
            nArray2[0] = 1;
            nArray2[1] = 2;
            nArray2[2] = 3;
            nArray2[3] = 4;
            nArray2[4] = 5;
            nArray2[5] = 6;
            nArray2[6] = 7;
            nArray = nArray2;
            nArray2[7] = 8;
        } else {
            int[] nArray3 = new int[8];
            nArray3[0] = 9;
            nArray3[1] = 10;
            nArray3[2] = 11;
            nArray3[3] = 12;
            nArray3[4] = 13;
            nArray3[5] = 14;
            nArray3[6] = 15;
            nArray = nArray3;
            nArray3[7] = 16;
        }
        return nArray;
    }

    @Override
    public int[] getOutputSlots(int recipeSlot) {
        return new int[]{recipeSlot + 9};
    }

    @Override
    public int getRecipeSlots(int slot, boolean input) {
        return input ? -1 + (slot >= 1 && slot < 9 ? slot : 0) : (slot >= 9 && slot <= 16 ? slot - 9 : -1);
    }

    @Override
    public int getInputSlot(int recipeSlot) {
        return recipeSlot + 1;
    }

    @Override
    public int getStructureSize() {
        return this.structureSize;
    }

    @Override
    public void onStructureValidated(boolean revalidation) {
        super.onStructureValidated(revalidation);
        this.updateTileField("children");
    }

    @Override
    protected void onInitialValidation() {
        super.onInitialValidation();
        List<BaseLinkingTileEntity> tiles = this.children.getTiles();
        int m = tiles.size();
        for (int i = 0; i < m; ++i) {
            BaseLinkingTileEntity tile = tiles.get(i);
            tile.lock();
            tile.setState(this.structureSize, i + 1);
            tile.setFacing(this.getFacing());
            tile.unlock();
            tile.onStateChanged();
        }
        this.onStateChanged();
        switch (this.structureSize) {
            case 0: {
                this.setSlotsInUse(2);
                break;
            }
            case 1: {
                this.setSlotsInUse(4);
                break;
            }
            case 2: {
                this.setSlotsInUse(8);
            }
        }
    }

    @Override
    public boolean isStateStillValid(BlockPos actualPos, BlockPos structurePos, BlockState newState) {
        return newState.m_60734_() == IC2Blocks.COLOSSAL_BASE;
    }

    @Override
    public boolean testStructure(StructureBuilder builder) {
        int radius = builder.findRadius(3, true, this::valid);
        if (radius <= 0) {
            return false;
        }
        int i = 1;
        while (builder.cubicStructure(i, radius + 1, radius + 1, radius + 1)) {
            if (!builder.isBlock(IC2Blocks.COLOSSAL_BASE)) {
                return false;
            }
            this.children.add(builder.getTile(BaseLinkingTileEntity.class));
            ++i;
        }
        this.structureSize = radius - 1;
        return true;
    }

    protected boolean valid(StructureBuilder builder) {
        if (!builder.isBlock(IC2Blocks.COLOSSAL_BASE)) {
            return false;
        }
        BaseLinkingTileEntity tile = builder.getTile(BaseLinkingTileEntity.class);
        return tile != null && tile.getMaster() == null;
    }
}

