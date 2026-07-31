/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class OcelotAttackGoal
extends Goal {
    private final Mob f_25654_;
    private LivingEntity f_25655_;
    private int f_25656_;

    public OcelotAttackGoal(Mob p_25658_) {
        this.f_25654_ = p_25658_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        LivingEntity $$0 = this.f_25654_.m_5448_();
        if ($$0 == null) {
            return false;
        }
        this.f_25655_ = $$0;
        return true;
    }

    @Override
    public boolean m_8045_() {
        if (!this.f_25655_.m_6084_()) {
            return false;
        }
        if (this.f_25654_.m_20280_(this.f_25655_) > 225.0) {
            return false;
        }
        return !this.f_25654_.m_21573_().m_26571_() || this.m_8036_();
    }

    @Override
    public void m_8041_() {
        this.f_25655_ = null;
        this.f_25654_.m_21573_().m_26573_();
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        this.f_25654_.m_21563_().m_24960_(this.f_25655_, 30.0f, 30.0f);
        double $$0 = this.f_25654_.m_20205_() * 2.0f * (this.f_25654_.m_20205_() * 2.0f);
        double $$1 = this.f_25654_.m_20275_(this.f_25655_.m_20185_(), this.f_25655_.m_20186_(), this.f_25655_.m_20189_());
        double $$2 = 0.8;
        if ($$1 > $$0 && $$1 < 16.0) {
            $$2 = 1.33;
        } else if ($$1 < 225.0) {
            $$2 = 0.6;
        }
        this.f_25654_.m_21573_().m_5624_(this.f_25655_, $$2);
        this.f_25656_ = Math.max(this.f_25656_ - 1, 0);
        if ($$1 > $$0) {
            return;
        }
        if (this.f_25656_ > 0) {
            return;
        }
        this.f_25656_ = 20;
        this.f_25654_.m_7327_(this.f_25655_);
    }
}

