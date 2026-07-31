/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class OwnerHurtByTargetGoal
extends TargetGoal {
    private final TamableAnimal f_26103_;
    private LivingEntity f_26104_;
    private int f_26105_;

    public OwnerHurtByTargetGoal(TamableAnimal p_26107_) {
        super(p_26107_, false);
        this.f_26103_ = p_26107_;
        this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
    }

    @Override
    public boolean m_8036_() {
        if (!this.f_26103_.m_21824_() || this.f_26103_.m_21827_()) {
            return false;
        }
        LivingEntity $$0 = this.f_26103_.m_21826_();
        if ($$0 == null) {
            return false;
        }
        this.f_26104_ = $$0.m_21188_();
        int $$1 = $$0.m_21213_();
        return $$1 != this.f_26105_ && this.m_26150_(this.f_26104_, TargetingConditions.f_26872_) && this.f_26103_.m_7757_(this.f_26104_, $$0);
    }

    @Override
    public void m_8056_() {
        this.f_26135_.m_6710_(this.f_26104_);
        LivingEntity $$0 = this.f_26103_.m_21826_();
        if ($$0 != null) {
            this.f_26105_ = $$0.m_21213_();
        }
        super.m_8056_();
    }
}

