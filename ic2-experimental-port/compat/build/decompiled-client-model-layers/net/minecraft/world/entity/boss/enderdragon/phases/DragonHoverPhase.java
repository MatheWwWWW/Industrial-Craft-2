/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.phys.Vec3;

public class DragonHoverPhase
extends AbstractDragonPhaseInstance {
    @Nullable
    private Vec3 f_31244_;

    public DragonHoverPhase(EnderDragon p_31246_) {
        super(p_31246_);
    }

    @Override
    public void m_6989_() {
        if (this.f_31244_ == null) {
            this.f_31244_ = this.f_31176_.m_20182_();
        }
    }

    @Override
    public boolean m_7080_() {
        return true;
    }

    @Override
    public void m_7083_() {
        this.f_31244_ = null;
    }

    @Override
    public float m_7072_() {
        return 1.0f;
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31244_;
    }

    public EnderDragonPhase<DragonHoverPhase> m_7309_() {
        return EnderDragonPhase.f_31387_;
    }
}

