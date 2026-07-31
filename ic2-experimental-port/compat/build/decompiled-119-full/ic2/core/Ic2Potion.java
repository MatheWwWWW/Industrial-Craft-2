/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.effect.MobEffect
 *  net.minecraft.world.effect.MobEffectCategory
 *  net.minecraft.world.effect.MobEffectInstance
 *  net.minecraft.world.entity.LivingEntity
 */
package ic2.core;

import ic2.core.Ic2DamageSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class Ic2Potion
extends MobEffect {
    public static Ic2Potion radiation;

    public Ic2Potion(MobEffectCategory mobEffectCategory, int n) {
        super(mobEffectCategory, n);
    }

    public void m_6742_(LivingEntity livingEntity, int n) {
        if (this == radiation) {
            livingEntity.m_6469_((DamageSource)Ic2DamageSource.radiation, (float)(n / 100) + 0.5f);
        }
    }

    public boolean m_6584_(int n, int n2) {
        if (this == radiation) {
            int n3 = 25 >> n2;
            return n3 > 0 ? n % n3 == 0 : true;
        }
        return false;
    }

    public void applyTo(LivingEntity livingEntity, int n, int n2) {
        MobEffectInstance mobEffectInstance = new MobEffectInstance((MobEffect)radiation, n, n2);
        livingEntity.m_7292_(mobEffectInstance);
    }
}

