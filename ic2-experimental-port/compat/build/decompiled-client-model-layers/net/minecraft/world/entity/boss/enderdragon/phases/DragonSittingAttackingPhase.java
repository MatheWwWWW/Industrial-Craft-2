/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonSittingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;

public class DragonSittingAttackingPhase
extends AbstractDragonSittingPhase {
    private static final int f_149578_ = 40;
    private int f_31319_;

    public DragonSittingAttackingPhase(EnderDragon p_31321_) {
        super(p_31321_);
    }

    @Override
    public void m_6991_() {
        this.f_31176_.f_19853_.m_7785_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_(), SoundEvents.f_11894_, this.f_31176_.m_5720_(), 2.5f, 0.8f + this.f_31176_.m_217043_().m_188501_() * 0.3f, false);
    }

    @Override
    public void m_6989_() {
        if (this.f_31319_++ >= 40) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31382_);
        }
    }

    @Override
    public void m_7083_() {
        this.f_31319_ = 0;
    }

    public EnderDragonPhase<DragonSittingAttackingPhase> m_7309_() {
        return EnderDragonPhase.f_31384_;
    }
}

