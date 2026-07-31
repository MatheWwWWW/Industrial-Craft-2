/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class AbsoptionMobEffect
extends MobEffect {
    protected AbsoptionMobEffect(MobEffectCategory p_19414_, int p_19415_) {
        super(p_19414_, p_19415_);
    }

    @Override
    public void m_6386_(LivingEntity p_19417_, AttributeMap p_19418_, int p_19419_) {
        p_19417_.m_7911_(p_19417_.m_6103_() - (float)(4 * (p_19419_ + 1)));
        super.m_6386_(p_19417_, p_19418_, p_19419_);
    }

    @Override
    public void m_6385_(LivingEntity p_19421_, AttributeMap p_19422_, int p_19423_) {
        p_19421_.m_7911_(p_19421_.m_6103_() + (float)(4 * (p_19423_ + 1)));
        super.m_6385_(p_19421_, p_19422_, p_19423_);
    }
}

