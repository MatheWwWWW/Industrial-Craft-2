/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RodBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;

public class EndRodBlock
extends RodBlock {
    protected EndRodBlock(BlockBehaviour.Properties p_53085_) {
        super(p_53085_);
        this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(f_52588_, Direction.UP));
    }

    @Override
    public BlockState m_5573_(BlockPlaceContext p_53087_) {
        Direction $$1 = p_53087_.m_43719_();
        BlockState $$2 = p_53087_.m_43725_().m_8055_(p_53087_.m_8083_().m_121945_($$1.m_122424_()));
        if ($$2.m_60713_(this) && $$2.m_61143_(f_52588_) == $$1) {
            return (BlockState)this.m_49966_().m_61124_(f_52588_, $$1.m_122424_());
        }
        return (BlockState)this.m_49966_().m_61124_(f_52588_, $$1);
    }

    @Override
    public void m_214162_(BlockState p_221107_, Level p_221108_, BlockPos p_221109_, RandomSource p_221110_) {
        Direction $$4 = p_221107_.m_61143_(f_52588_);
        double $$5 = (double)p_221109_.m_123341_() + 0.55 - (double)(p_221110_.m_188501_() * 0.1f);
        double $$6 = (double)p_221109_.m_123342_() + 0.55 - (double)(p_221110_.m_188501_() * 0.1f);
        double $$7 = (double)p_221109_.m_123343_() + 0.55 - (double)(p_221110_.m_188501_() * 0.1f);
        double $$8 = 0.4f - (p_221110_.m_188501_() + p_221110_.m_188501_()) * 0.4f;
        if (p_221110_.m_188503_(5) == 0) {
            p_221108_.m_7106_(ParticleTypes.f_123810_, $$5 + (double)$$4.m_122429_() * $$8, $$6 + (double)$$4.m_122430_() * $$8, $$7 + (double)$$4.m_122431_() * $$8, p_221110_.m_188583_() * 0.005, p_221110_.m_188583_() * 0.005, p_221110_.m_188583_() * 0.005);
        }
    }

    @Override
    protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53105_) {
        p_53105_.m_61104_(f_52588_);
    }

    @Override
    public PushReaction m_5537_(BlockState p_53112_) {
        return PushReaction.NORMAL;
    }
}

