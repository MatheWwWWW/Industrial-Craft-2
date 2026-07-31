/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;

public class HealthBoostMobEffect
extends MobEffect {
    public HealthBoostMobEffect(MobEffectCategory p_19433_, int p_19434_) {
        super(p_19433_, p_19434_);
    }

    @Override
    public void m_6386_(LivingEntity p_19436_, AttributeMap p_19437_, int p_19438_) {
        super.m_6386_(p_19436_, p_19437_, p_19438_);
        if (p_19436_.m_21223_() > p_19436_.m_21233_()) {
            p_19436_.m_21153_(p_19436_.m_21233_());
        }
    }
}

