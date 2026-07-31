/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

public class LeapAtTargetGoal
extends Goal {
    private final Mob f_25488_;
    private LivingEntity f_25489_;
    private final float f_25490_;

    public LeapAtTargetGoal(Mob p_25492_, float p_25493_) {
        this.f_25488_ = p_25492_;
        this.f_25490_ = p_25493_;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25488_.m_20160_()) {
            return false;
        }
        this.f_25489_ = this.f_25488_.m_5448_();
        if (this.f_25489_ == null) {
            return false;
        }
        double $$0 = this.f_25488_.m_20280_(this.f_25489_);
        if ($$0 < 4.0 || $$0 > 16.0) {
            return false;
        }
        if (!this.f_25488_.m_20096_()) {
            return false;
        }
        return this.f_25488_.m_217043_().m_188503_(LeapAtTargetGoal.m_186073_(5)) == 0;
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25488_.m_20096_();
    }

    @Override
    public void m_8056_() {
        Vec3 $$0 = this.f_25488_.m_20184_();
        Vec3 $$1 = new Vec3(this.f_25489_.m_20185_() - this.f_25488_.m_20185_(), 0.0, this.f_25489_.m_20189_() - this.f_25488_.m_20189_());
        if ($$1.m_82556_() > 1.0E-7) {
            $$1 = $$1.m_82541_().m_82490_(0.4).m_82549_($$0.m_82490_(0.2));
        }
        this.f_25488_.m_20334_($$1.f_82479_, this.f_25490_, $$1.f_82481_);
    }
}

