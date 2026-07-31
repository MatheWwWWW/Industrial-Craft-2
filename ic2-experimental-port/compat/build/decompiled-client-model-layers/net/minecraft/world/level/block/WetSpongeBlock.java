/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WetSpongeBlock
extends Block {
    protected WetSpongeBlock(BlockBehaviour.Properties p_58222_) {
        super(p_58222_);
    }

    @Override
    public void m_6807_(BlockState p_58229_, Level p_58230_, BlockPos p_58231_, BlockState p_58232_, boolean p_58233_) {
        if (p_58230_.m_6042_().f_63857_()) {
            p_58230_.m_7731_(p_58231_, Blocks.f_50056_.m_49966_(), 3);
            p_58230_.m_46796_(2009, p_58231_, 0);
            p_58230_.m_5594_(null, p_58231_, SoundEvents.f_11937_, SoundSource.BLOCKS, 1.0f, (1.0f + p_58230_.m_213780_().m_188501_() * 0.2f) * 0.7f);
        }
    }

    @Override
    public void m_214162_(BlockState p_222682_, Level p_222683_, BlockPos p_222684_, RandomSource p_222685_) {
        Direction $$4 = Direction.m_235672_(p_222685_);
        if ($$4 == Direction.UP) {
            return;
        }
        BlockPos $$5 = p_222684_.m_121945_($$4);
        BlockState $$6 = p_222683_.m_8055_($$5);
        if (p_222682_.m_60815_() && $$6.m_60783_(p_222683_, $$5, $$4.m_122424_())) {
            return;
        }
        double $$7 = p_222684_.m_123341_();
        double $$8 = p_222684_.m_123342_();
        double $$9 = p_222684_.m_123343_();
        if ($$4 == Direction.DOWN) {
            $$8 -= 0.05;
            $$7 += p_222685_.m_188500_();
            $$9 += p_222685_.m_188500_();
        } else {
            $$8 += p_222685_.m_188500_() * 0.8;
            if ($$4.m_122434_() == Direction.Axis.X) {
                $$9 += p_222685_.m_188500_();
                $$7 = $$4 == Direction.EAST ? ($$7 += 1.1) : ($$7 += 0.05);
            } else {
                $$7 += p_222685_.m_188500_();
                $$9 = $$4 == Direction.SOUTH ? ($$9 += 1.1) : ($$9 += 0.05);
            }
        }
        p_222683_.m_7106_(ParticleTypes.f_123803_, $$7, $$8, $$9, 0.0, 0.0, 0.0);
    }
}

