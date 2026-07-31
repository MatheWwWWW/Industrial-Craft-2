/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class AttackDamageMobEffect
extends MobEffect {
    protected final double f_19424_;

    protected AttackDamageMobEffect(MobEffectCategory p_19426_, int p_19427_, double p_19428_) {
        super(p_19426_, p_19427_);
        this.f_19424_ = p_19428_;
    }

    @Override
    public double m_7048_(int p_19430_, AttributeModifier p_19431_) {
        return this.f_19424_ * (double)(p_19430_ + 1);
    }
}

