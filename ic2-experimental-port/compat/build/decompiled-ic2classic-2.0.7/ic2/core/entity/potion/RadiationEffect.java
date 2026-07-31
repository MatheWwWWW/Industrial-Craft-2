/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core.entity.potion;

import ic2.api.util.IC2DamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RadiationEffect
extends MobEffect {
    public RadiationEffect() {
        super(MobEffectCategory.HARMFUL, 5149489);
    }

    public void m_6742_(LivingEntity entityLivingBaseIn, int amplifier) {
        entityLivingBaseIn.m_6469_((DamageSource)IC2DamageSource.RADIATION, (float)(amplifier + 1));
    }

    public boolean m_6584_(int duration, int amplifier) {
        if (amplifier >= 4) {
            return true;
        }
        return duration % (25 >> amplifier) == 0;
    }
}

