/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DirtPathBlock
extends Block {
    protected static final VoxelShape f_153126_ = FarmBlock.f_53244_;

    protected DirtPathBlock(BlockBehaviour.Properties p_153129_) {
        super(p_153129_);
    }

    @Override
    public boolean m_7923_(BlockState p_153159_) {
        return true;
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_153131_) {
        if (!this.m_49966_().m_60710_(p_153131_.m_43725_(), p_153131_.m_8083_())) {
            return Block.m_49897_(this.m_49966_(), Blocks.f_50493_.m_49966_(), p_153131_.m_43725_(), p_153131_.m_8083_());
        }
        return super.m_5573_(p_153131_);
    }

    @Override
    public BlockState m_7417_(BlockState p_153152_, Direction p_153153_, BlockState p_153154_, LevelAccessor p_153155_, BlockPos p_153156_, BlockPos p_153157_) {
        if (p_153153_ == Direction.UP && !p_153152_.m_60710_(p_153155_, p_153156_)) {
            p_153155_.m_186460_(p_153156_, this, 1);
        }
        return super.m_7417_(p_153152_, p_153153_, p_153154_, p_153155_, p_153156_, p_153157_);
    }

    @Override
    public void m_213897_(BlockState p_221070_, ServerLevel p_221071_, BlockPos p_221072_, RandomSource p_221073_) {
        FarmBlock.m_53296_(p_221070_, p_221071_, p_221072_);
    }

    @Override
    public boolean m_7898_(BlockState p_153148_, LevelReader p_153149_, BlockPos p_153150_) {
        BlockState $$3 = p_153149_.m_8055_(p_153150_.m_7494_());
        return !$$3.m_60767_().m_76333_() || $$3.m_60734_() instanceof FenceGateBlock;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_153143_, BlockGetter p_153144_, BlockPos p_153145_, CollisionContext p_153146_) {
        return f_153126_;
    }

    @Override
    public boolean m_7357_(BlockState p_153138_, BlockGetter p_153139_, BlockPos p_153140_, PathComputationType p_153141_) {
        return false;
    }
}

