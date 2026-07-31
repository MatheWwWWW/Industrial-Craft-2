/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.cables;

import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedComparatorTileEntity
extends BlockEntity {
    public BlockState defaultState = null;
    public int output;

    public AdvancedComparatorTileEntity(BlockPos pos, BlockState state) {
        super(IC2Tiles.ADVANCED_COMPARATOR, pos, state);
    }

    public int getOutputSignal() {
        return this.output;
    }

    public void setOutputSignal(int value) {
        this.output = value;
    }

    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.output = compound.m_128451_("output");
    }

    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putByte(compound, "output", this.output, 0);
    }

    public BlockState m_58900_() {
        return this.defaultState != null ? this.defaultState : super.m_58900_();
    }
}

