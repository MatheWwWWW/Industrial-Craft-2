/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class DragonDeathPhase
extends AbstractDragonPhaseInstance {
    @Nullable
    private Vec3 f_31214_;
    private int f_31215_;

    public DragonDeathPhase(EnderDragon p_31217_) {
        super(p_31217_);
    }

    @Override
    public void m_6991_() {
        if (this.f_31215_++ % 10 == 0) {
            float $$0 = (this.f_31176_.m_217043_().m_188501_() - 0.5f) * 8.0f;
            float $$1 = (this.f_31176_.m_217043_().m_188501_() - 0.5f) * 4.0f;
            float $$2 = (this.f_31176_.m_217043_().m_188501_() - 0.5f) * 8.0f;
            this.f_31176_.f_19853_.m_7106_(ParticleTypes.f_123812_, this.f_31176_.m_20185_() + (double)$$0, this.f_31176_.m_20186_() + 2.0 + (double)$$1, this.f_31176_.m_20189_() + (double)$$2, 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void m_6989_() {
        double $$1;
        ++this.f_31215_;
        if (this.f_31214_ == null) {
            BlockPos $$0 = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.f_65714_);
            this.f_31214_ = Vec3.m_82539_($$0);
        }
        if (($$1 = this.f_31214_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_())) < 100.0 || $$1 > 22500.0 || this.f_31176_.f_19862_ || this.f_31176_.f_19863_) {
            this.f_31176_.m_21153_(0.0f);
        } else {
            this.f_31176_.m_21153_(1.0f);
        }
    }

    @Override
    public void m_7083_() {
        this.f_31214_ = null;
        this.f_31215_ = 0;
    }

    @Override
    public float m_7072_() {
        return 3.0f;
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31214_;
    }

    public EnderDragonPhase<DragonDeathPhase> m_7309_() {
        return EnderDragonPhase.f_31386_;
    }
}

