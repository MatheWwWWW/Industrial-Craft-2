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
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonSittingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.phys.Vec3;

public class DragonSittingFlamingPhase
extends AbstractDragonSittingPhase {
    private static final int f_149579_ = 200;
    private static final int f_149580_ = 4;
    private static final int f_149581_ = 10;
    private int f_31326_;
    private int f_31327_;
    @Nullable
    private AreaEffectCloud f_31328_;

    public DragonSittingFlamingPhase(EnderDragon p_31330_) {
        super(p_31330_);
    }

    @Override
    public void m_6991_() {
        ++this.f_31326_;
        if (this.f_31326_ % 2 == 0 && this.f_31326_ < 10) {
            Vec3 $$0 = this.f_31176_.m_31174_(1.0f).m_82541_();
            $$0.m_82524_(-0.7853982f);
            double $$1 = this.f_31176_.f_31080_.m_20185_();
            double $$2 = this.f_31176_.f_31080_.m_20227_(0.5);
            double $$3 = this.f_31176_.f_31080_.m_20189_();
            for (int $$4 = 0; $$4 < 8; ++$$4) {
                double $$5 = $$1 + this.f_31176_.m_217043_().m_188583_() / 2.0;
                double $$6 = $$2 + this.f_31176_.m_217043_().m_188583_() / 2.0;
                double $$7 = $$3 + this.f_31176_.m_217043_().m_188583_() / 2.0;
                for (int $$8 = 0; $$8 < 6; ++$$8) {
                    this.f_31176_.f_19853_.m_7106_(ParticleTypes.f_123799_, $$5, $$6, $$7, -$$0.f_82479_ * (double)0.08f * (double)$$8, -$$0.f_82480_ * (double)0.6f, -$$0.f_82481_ * (double)0.08f * (double)$$8);
                }
                $$0.m_82524_(0.19634955f);
            }
        }
    }

    @Override
    public void m_6989_() {
        ++this.f_31326_;
        if (this.f_31326_ >= 200) {
            if (this.f_31327_ >= 4) {
                this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31381_);
            } else {
                this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31383_);
            }
        } else if (this.f_31326_ == 10) {
            double $$4;
            Vec3 $$0 = new Vec3(this.f_31176_.f_31080_.m_20185_() - this.f_31176_.m_20185_(), 0.0, this.f_31176_.f_31080_.m_20189_() - this.f_31176_.m_20189_()).m_82541_();
            float $$1 = 5.0f;
            double $$2 = this.f_31176_.f_31080_.m_20185_() + $$0.f_82479_ * 5.0 / 2.0;
            double $$3 = this.f_31176_.f_31080_.m_20189_() + $$0.f_82481_ * 5.0 / 2.0;
            double $$5 = $$4 = this.f_31176_.f_31080_.m_20227_(0.5);
            BlockPos.MutableBlockPos $$6 = new BlockPos.MutableBlockPos($$2, $$5, $$3);
            while (this.f_31176_.f_19853_.m_46859_($$6)) {
                if (($$5 -= 1.0) < 0.0) {
                    $$5 = $$4;
                    break;
                }
                $$6.m_122169_($$2, $$5, $$3);
            }
            $$5 = Mth.m_14107_($$5) + 1;
            this.f_31328_ = new AreaEffectCloud(this.f_31176_.f_19853_, $$2, $$5, $$3);
            this.f_31328_.m_19718_(this.f_31176_);
            this.f_31328_.m_19712_(5.0f);
            this.f_31328_.m_19734_(200);
            this.f_31328_.m_19724_(ParticleTypes.f_123799_);
            this.f_31328_.m_19716_(new MobEffectInstance(MobEffects.f_19602_));
            this.f_31176_.f_19853_.m_7967_(this.f_31328_);
        }
    }

    @Override
    public void m_7083_() {
        this.f_31326_ = 0;
        ++this.f_31327_;
    }

    @Override
    public void m_7081_() {
        if (this.f_31328_ != null) {
            this.f_31328_.m_146870_();
            this.f_31328_ = null;
        }
    }

    public EnderDragonPhase<DragonSittingFlamingPhase> m_7309_() {
        return EnderDragonPhase.f_31382_;
    }

    public void m_31336_() {
        this.f_31327_ = 0;
    }
}

