/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class BushBlock
extends Block {
    protected BushBlock(BlockBehaviour.Properties p_51021_) {
        super(p_51021_);
    }

    protected boolean m_6266_(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return p_51042_.m_204336_(BlockTags.f_144274_) || p_51042_.m_60713_(Blocks.f_50093_);
    }

    @Override
    public BlockState m_7417_(BlockState p_51032_, Direction p_51033_, BlockState p_51034_, LevelAccessor p_51035_, BlockPos p_51036_, BlockPos p_51037_) {
        if (!p_51032_.m_60710_(p_51035_, p_51036_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_51032_, p_51033_, p_51034_, p_51035_, p_51036_, p_51037_);
    }

    @Override
    public boolean m_7898_(BlockState p_51028_, LevelReader p_51029_, BlockPos p_51030_) {
        BlockPos $$3 = p_51030_.m_7495_();
        return this.m_6266_(p_51029_.m_8055_($$3), p_51029_, $$3);
    }

    @Override
    public boolean m_7420_(BlockState p_51039_, BlockGetter p_51040_, BlockPos p_51041_) {
        return p_51039_.m_60819_().m_76178_();
    }

    @Override
    public boolean m_7357_(BlockState p_51023_, BlockGetter p_51024_, BlockPos p_51025_, PathComputationType p_51026_) {
        if (p_51026_ == PathComputationType.AIR && !this.f_60443_) {
            return true;
        }
        return super.m_7357_(p_51023_, p_51024_, p_51025_, p_51026_);
    }
}

