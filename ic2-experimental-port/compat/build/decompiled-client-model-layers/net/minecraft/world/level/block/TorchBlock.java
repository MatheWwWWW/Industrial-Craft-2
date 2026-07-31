/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorchBlock
extends Block {
    protected static final int f_154831_ = 2;
    protected static final VoxelShape f_57487_ = Block.m_49796_(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
    protected final ParticleOptions f_57488_;

    protected TorchBlock(BlockBehaviour.Properties p_57491_, ParticleOptions p_57492_) {
        super(p_57491_);
        this.f_57488_ = p_57492_;
    }

    @Override
    public VoxelShape m_5940_(BlockState p_57510_, BlockGetter p_57511_, BlockPos p_57512_, CollisionContext p_57513_) {
        return f_57487_;
    }

    @Override
    public BlockState m_7417_(BlockState p_57503_, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
        if (p_57504_ == Direction.DOWN && !this.m_7898_(p_57503_, p_57506_, p_57507_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_57503_, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
    }

    @Override
    public boolean m_7898_(BlockState p_57499_, LevelReader p_57500_, BlockPos p_57501_) {
        return TorchBlock.m_49863_(p_57500_, p_57501_.m_7495_(), Direction.UP);
    }

    @Override
    public void m_214162_(BlockState p_222593_, Level p_222594_, BlockPos p_222595_, RandomSource p_222596_) {
        double $$4 = (double)p_222595_.m_123341_() + 0.5;
        double $$5 = (double)p_222595_.m_123342_() + 0.7;
        double $$6 = (double)p_222595_.m_123343_() + 0.5;
        p_222594_.m_7106_(ParticleTypes.f_123762_, $$4, $$5, $$6, 0.0, 0.0, 0.0);
        p_222594_.m_7106_(this.f_57488_, $$4, $$5, $$6, 0.0, 0.0, 0.0);
    }
}

