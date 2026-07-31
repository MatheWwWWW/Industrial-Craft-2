/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.AbstractDragonSittingPhase;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DragonSittingScanningPhase
extends AbstractDragonSittingPhase {
    private static final int f_149582_ = 100;
    private static final int f_149583_ = 10;
    private static final int f_149584_ = 20;
    private static final int f_149585_ = 150;
    private static final TargetingConditions f_31337_ = TargetingConditions.m_148352_().m_26883_(150.0);
    private final TargetingConditions f_31338_ = TargetingConditions.m_148352_().m_26883_(20.0).m_26888_(p_31345_ -> Math.abs(p_31345_.m_20186_() - p_31342_.m_20186_()) <= 10.0);
    private int f_31339_;

    public DragonSittingScanningPhase(EnderDragon p_31342_) {
        super(p_31342_);
    }

    @Override
    public void m_6989_() {
        ++this.f_31339_;
        Player $$0 = this.f_31176_.f_19853_.m_45949_(this.f_31338_, this.f_31176_, this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
        if ($$0 != null) {
            if (this.f_31339_ > 25) {
                this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31384_);
            } else {
                Vec3 $$1 = new Vec3($$0.m_20185_() - this.f_31176_.m_20185_(), 0.0, $$0.m_20189_() - this.f_31176_.m_20189_()).m_82541_();
                Vec3 $$2 = new Vec3(Mth.m_14031_(this.f_31176_.m_146908_() * ((float)Math.PI / 180)), 0.0, -Mth.m_14089_(this.f_31176_.m_146908_() * ((float)Math.PI / 180))).m_82541_();
                float $$3 = (float)$$2.m_82526_($$1);
                float $$4 = (float)(Math.acos($$3) * 57.2957763671875) + 0.5f;
                if ($$4 < 0.0f || $$4 > 10.0f) {
                    float $$8;
                    double $$5 = $$0.m_20185_() - this.f_31176_.f_31080_.m_20185_();
                    double $$6 = $$0.m_20189_() - this.f_31176_.f_31080_.m_20189_();
                    double $$7 = Mth.m_14008_(Mth.m_14175_(180.0 - Mth.m_14136_($$5, $$6) * 57.2957763671875 - (double)this.f_31176_.m_146908_()), -100.0, 100.0);
                    this.f_31176_.f_31085_ *= 0.8f;
                    float $$9 = $$8 = (float)Math.sqrt($$5 * $$5 + $$6 * $$6) + 1.0f;
                    if ($$8 > 40.0f) {
                        $$8 = 40.0f;
                    }
                    this.f_31176_.f_31085_ += (float)$$7 * (0.7f / $$8 / $$9);
                    this.f_31176_.m_146922_(this.f_31176_.m_146908_() + this.f_31176_.f_31085_);
                }
            }
        } else if (this.f_31339_ >= 100) {
            $$0 = this.f_31176_.f_19853_.m_45949_(f_31337_, this.f_31176_, this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31381_);
            if ($$0 != null) {
                this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31385_);
                this.f_31176_.m_31157_().m_31418_(EnderDragonPhase.f_31385_).m_31207_(new Vec3($$0.m_20185_(), $$0.m_20186_(), $$0.m_20189_()));
            }
        }
    }

    @Override
    public void m_7083_() {
        this.f_31339_ = 0;
    }

    public EnderDragonPhase<DragonSittingScanningPhase> m_7309_() {
        return EnderDragonPhase.f_31383_;
    }
}

