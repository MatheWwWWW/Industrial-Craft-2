/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SlimeBlock
extends HalfTransparentBlock {
    public SlimeBlock(BlockBehaviour.Properties p_56402_) {
        super(p_56402_);
    }

    @Override
    public void m_142072_(Level p_154567_, BlockState p_154568_, BlockPos p_154569_, Entity p_154570_, float p_154571_) {
        if (p_154570_.m_20162_()) {
            super.m_142072_(p_154567_, p_154568_, p_154569_, p_154570_, p_154571_);
        } else {
            p_154570_.m_142535_(p_154571_, 0.0f, DamageSource.f_19315_);
        }
    }

    @Override
    public void m_5548_(BlockGetter p_56406_, Entity p_56407_) {
        if (p_56407_.m_20162_()) {
            super.m_5548_(p_56406_, p_56407_);
        } else {
            this.m_56403_(p_56407_);
        }
    }

    private void m_56403_(Entity p_56404_) {
        Vec3 $$1 = p_56404_.m_20184_();
        if ($$1.f_82480_ < 0.0) {
            double $$2 = p_56404_ instanceof LivingEntity ? 1.0 : 0.8;
            p_56404_.m_20334_($$1.f_82479_, -$$1.f_82480_ * $$2, $$1.f_82481_);
        }
    }

    @Override
    public void m_141947_(Level p_154573_, BlockPos p_154574_, BlockState p_154575_, Entity p_154576_) {
        double $$4 = Math.abs(p_154576_.m_20184_().f_82480_);
        if ($$4 < 0.1 && !p_154576_.m_20161_()) {
            double $$5 = 0.4 + $$4 * 0.2;
            p_154576_.m_20256_(p_154576_.m_20184_().m_82542_($$5, 1.0, $$5));
        }
        super.m_141947_(p_154573_, p_154574_, p_154575_, p_154576_);
    }
}

