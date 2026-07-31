/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.projectile.AbstractArrow;

public abstract class AbstractDragonSittingPhase
extends AbstractDragonPhaseInstance {
    public AbstractDragonSittingPhase(EnderDragon p_31196_) {
        super(p_31196_);
    }

    @Override
    public boolean m_7080_() {
        return true;
    }

    @Override
    public float m_7584_(DamageSource p_31199_, float p_31200_) {
        if (p_31199_.m_7640_() instanceof AbstractArrow) {
            p_31199_.m_7640_().m_20254_(1);
            return 0.0f;
        }
        return super.m_7584_(p_31199_, p_31200_);
    }
}

