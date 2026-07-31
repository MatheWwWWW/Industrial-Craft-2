/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WitherRoseBlock
extends FlowerBlock {
    public WitherRoseBlock(MobEffect p_58235_, BlockBehaviour.Properties p_58236_) {
        super(p_58235_, 8, p_58236_);
    }

    @Override
    protected boolean m_6266_(BlockState p_58248_, BlockGetter p_58249_, BlockPos p_58250_) {
        return super.m_6266_(p_58248_, p_58249_, p_58250_) || p_58248_.m_60713_(Blocks.f_50134_) || p_58248_.m_60713_(Blocks.f_50135_) || p_58248_.m_60713_(Blocks.f_50136_);
    }

    @Override
    public void m_214162_(BlockState p_222687_, Level p_222688_, BlockPos p_222689_, RandomSource p_222690_) {
        VoxelShape $$4 = this.m_5940_(p_222687_, p_222688_, p_222689_, CollisionContext.m_82749_());
        Vec3 $$5 = $$4.m_83215_().m_82399_();
        double $$6 = (double)p_222689_.m_123341_() + $$5.f_82479_;
        double $$7 = (double)p_222689_.m_123343_() + $$5.f_82481_;
        for (int $$8 = 0; $$8 < 3; ++$$8) {
            if (!p_222690_.m_188499_()) continue;
            p_222688_.m_7106_(ParticleTypes.f_123762_, $$6 + p_222690_.m_188500_() / 5.0, (double)p_222689_.m_123342_() + (0.5 - p_222690_.m_188500_()), $$7 + p_222690_.m_188500_() / 5.0, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void m_7892_(BlockState p_58238_, Level p_58239_, BlockPos p_58240_, Entity p_58241_) {
        LivingEntity $$4;
        if (p_58239_.f_46443_ || p_58239_.m_46791_() == Difficulty.PEACEFUL) {
            return;
        }
        if (p_58241_ instanceof LivingEntity && !($$4 = (LivingEntity)p_58241_).m_6673_(DamageSource.f_19320_)) {
            $$4.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 40));
        }
    }
}

