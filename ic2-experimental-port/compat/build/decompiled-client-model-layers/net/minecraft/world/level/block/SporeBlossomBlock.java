/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
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

public class SporeBlossomBlock
extends Block {
    private static final VoxelShape f_154691_ = Block.m_49796_(2.0, 13.0, 2.0, 14.0, 16.0, 14.0);
    private static final int f_154692_ = 14;
    private static final int f_154693_ = 10;
    private static final int f_154694_ = 10;

    public SporeBlossomBlock(BlockBehaviour.Properties p_154697_) {
        super(p_154697_);
    }

    @Override
    public boolean m_7898_(BlockState p_154709_, LevelReader p_154710_, BlockPos p_154711_) {
        return Block.m_49863_(p_154710_, p_154711_.m_7494_(), Direction.DOWN) && !p_154710_.m_46801_(p_154711_);
    }

    @Override
    public BlockState m_7417_(BlockState p_154713_, Direction p_154714_, BlockState p_154715_, LevelAccessor p_154716_, BlockPos p_154717_, BlockPos p_154718_) {
        if (p_154714_ == Direction.UP && !this.m_7898_(p_154713_, p_154716_, p_154717_)) {
            return Blocks.f_50016_.m_49966_();
        }
        return super.m_7417_(p_154713_, p_154714_, p_154715_, p_154716_, p_154717_, p_154718_);
    }

    @Override
    public void m_214162_(BlockState p_222503_, Level p_222504_, BlockPos p_222505_, RandomSource p_222506_) {
        int $$4 = p_222505_.m_123341_();
        int $$5 = p_222505_.m_123342_();
        int $$6 = p_222505_.m_123343_();
        double $$7 = (double)$$4 + p_222506_.m_188500_();
        double $$8 = (double)$$5 + 0.7;
        double $$9 = (double)$$6 + p_222506_.m_188500_();
        p_222504_.m_7106_(ParticleTypes.f_175832_, $$7, $$8, $$9, 0.0, 0.0, 0.0);
        BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
        for (int $$11 = 0; $$11 < 14; ++$$11) {
            $$10.m_122178_($$4 + Mth.m_216271_(p_222506_, -10, 10), $$5 - p_222506_.m_188503_(10), $$6 + Mth.m_216271_(p_222506_, -10, 10));
            BlockState $$12 = p_222504_.m_8055_($$10);
            if ($$12.m_60838_(p_222504_, $$10)) continue;
            p_222504_.m_7106_(ParticleTypes.f_175833_, (double)$$10.m_123341_() + p_222506_.m_188500_(), (double)$$10.m_123342_() + p_222506_.m_188500_(), (double)$$10.m_123343_() + p_222506_.m_188500_(), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public VoxelShape m_5940_(BlockState p_154699_, BlockGetter p_154700_, BlockPos p_154701_, CollisionContext p_154702_) {
        return f_154691_;
    }
}

