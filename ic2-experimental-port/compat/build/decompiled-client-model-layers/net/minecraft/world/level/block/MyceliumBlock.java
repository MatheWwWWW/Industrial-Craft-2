/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MyceliumBlock
extends SpreadingSnowyDirtBlock {
    public MyceliumBlock(BlockBehaviour.Properties p_54898_) {
        super(p_54898_);
    }

    @Override
    public void m_214162_(BlockState p_221789_, Level p_221790_, BlockPos p_221791_, RandomSource p_221792_) {
        super.m_214162_(p_221789_, p_221790_, p_221791_, p_221792_);
        if (p_221792_.m_188503_(10) == 0) {
            p_221790_.m_7106_(ParticleTypes.f_123757_, (double)p_221791_.m_123341_() + p_221792_.m_188500_(), (double)p_221791_.m_123342_() + 1.1, (double)p_221791_.m_123343_() + p_221792_.m_188500_(), 0.0, 0.0, 0.0);
        }
    }
}

