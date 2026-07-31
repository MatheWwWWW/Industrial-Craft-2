/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;

public class SitWhenOrderedToGoal
extends Goal {
    private final TamableAnimal f_25896_;

    public SitWhenOrderedToGoal(TamableAnimal p_25898_) {
        this.f_25896_ = p_25898_;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8045_() {
        return this.f_25896_.m_21827_();
    }

    @Override
    public boolean m_8036_() {
        if (!this.f_25896_.m_21824_()) {
            return false;
        }
        if (this.f_25896_.m_20072_()) {
            return false;
        }
        if (!this.f_25896_.m_20096_()) {
            return false;
        }
        LivingEntity $$0 = this.f_25896_.m_21826_();
        if ($$0 == null) {
            return true;
        }
        if (this.f_25896_.m_20280_($$0) < 144.0 && $$0.m_21188_() != null) {
            return false;
        }
        return this.f_25896_.m_21827_();
    }

    @Override
    public void m_8056_() {
        this.f_25896_.m_21573_().m_26573_();
        this.f_25896_.m_21837_(true);
    }

    @Override
    public void m_8041_() {
        this.f_25896_.m_21837_(false);
    }
}

