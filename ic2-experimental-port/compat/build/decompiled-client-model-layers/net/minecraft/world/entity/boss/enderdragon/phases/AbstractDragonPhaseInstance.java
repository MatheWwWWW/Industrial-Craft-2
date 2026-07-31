/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractDragonPhaseInstance
implements DragonPhaseInstance {
    protected final EnderDragon f_31176_;

    public AbstractDragonPhaseInstance(EnderDragon p_31178_) {
        this.f_31176_ = p_31178_;
    }

    @Override
    public boolean m_7080_() {
        return false;
    }

    @Override
    public void m_6991_() {
    }

    @Override
    public void m_6989_() {
    }

    @Override
    public void m_8059_(EndCrystal p_31184_, BlockPos p_31185_, DamageSource p_31186_, @Nullable Player p_31187_) {
    }

    @Override
    public void m_7083_() {
    }

    @Override
    public void m_7081_() {
    }

    @Override
    public float m_7072_() {
        return 0.6f;
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return null;
    }

    @Override
    public float m_7584_(DamageSource p_31181_, float p_31182_) {
        return p_31182_;
    }

    @Override
    public float m_7089_() {
        float $$0 = (float)this.f_31176_.m_20184_().m_165924_() + 1.0f;
        float $$1 = Math.min($$0, 40.0f);
        return 0.7f / $$1 / $$0;
    }
}

