/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CryingObsidianBlock
extends Block {
    public CryingObsidianBlock(BlockBehaviour.Properties p_52371_) {
        super(p_52371_);
    }

    @Override
    public void m_214162_(BlockState p_221055_, Level p_221056_, BlockPos p_221057_, RandomSource p_221058_) {
        if (p_221058_.m_188503_(5) != 0) {
            return;
        }
        Direction $$4 = Direction.m_235672_(p_221058_);
        if ($$4 == Direction.UP) {
            return;
        }
        BlockPos $$5 = p_221057_.m_121945_($$4);
        BlockState $$6 = p_221056_.m_8055_($$5);
        if (p_221055_.m_60815_() && $$6.m_60783_(p_221056_, $$5, $$4.m_122424_())) {
            return;
        }
        double $$7 = $$4.m_122429_() == 0 ? p_221058_.m_188500_() : 0.5 + (double)$$4.m_122429_() * 0.6;
        double $$8 = $$4.m_122430_() == 0 ? p_221058_.m_188500_() : 0.5 + (double)$$4.m_122430_() * 0.6;
        double $$9 = $$4.m_122431_() == 0 ? p_221058_.m_188500_() : 0.5 + (double)$$4.m_122431_() * 0.6;
        p_221056_.m_7106_(ParticleTypes.f_123786_, (double)p_221057_.m_123341_() + $$7, (double)p_221057_.m_123342_() + $$8, (double)p_221057_.m_123343_() + $$9, 0.0, 0.0, 0.0);
    }
}

