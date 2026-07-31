/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonPhaseInstance;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class DragonLandingPhase
extends AbstractDragonPhaseInstance {
    @Nullable
    private Vec3 f_31303_;

    public DragonLandingPhase(EnderDragon p_31305_) {
        super(p_31305_);
    }

    @Override
    public void m_6991_() {
        Vec3 $$0 = this.f_31176_.m_31174_(1.0f).m_82541_();
        $$0.m_82524_(-0.7853982f);
        double $$1 = this.f_31176_.f_31080_.m_20185_();
        double $$2 = this.f_31176_.f_31080_.m_20227_(0.5);
        double $$3 = this.f_31176_.f_31080_.m_20189_();
        for (int $$4 = 0; $$4 < 8; ++$$4) {
            RandomSource $$5 = this.f_31176_.m_217043_();
            double $$6 = $$1 + $$5.m_188583_() / 2.0;
            double $$7 = $$2 + $$5.m_188583_() / 2.0;
            double $$8 = $$3 + $$5.m_188583_() / 2.0;
            Vec3 $$9 = this.f_31176_.m_20184_();
            this.f_31176_.f_19853_.m_7106_(ParticleTypes.f_123799_, $$6, $$7, $$8, -$$0.f_82479_ * (double)0.08f + $$9.f_82479_, -$$0.f_82480_ * (double)0.3f + $$9.f_82480_, -$$0.f_82481_ * (double)0.08f + $$9.f_82481_);
            $$0.m_82524_(0.19634955f);
        }
    }

    @Override
    public void m_6989_() {
        if (this.f_31303_ == null) {
            this.f_31303_ = Vec3.m_82539_(this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_));
        }
        if (this.f_31303_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_()) < 1.0) {
            this.f_31176_.m_31157_().m_31418_(EnderDragonPhase.f_31382_).m_31336_();
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31383_);
        }
    }

    @Override
    public float m_7072_() {
        return 1.5f;
    }

    @Override
    public float m_7089_() {
        float $$0 = (float)this.f_31176_.m_20184_().m_165924_() + 1.0f;
        float $$1 = Math.min($$0, 40.0f);
        return $$1 / $$0;
    }

    @Override
    public void m_7083_() {
        this.f_31303_ = null;
    }

    @Override
    @Nullable
    public Vec3 m_5535_() {
        return this.f_31303_;
    }

    public EnderDragonPhase<DragonLandingPhase> m_7309_() {
        return EnderDragonPhase.f_31380_;
    }
}

