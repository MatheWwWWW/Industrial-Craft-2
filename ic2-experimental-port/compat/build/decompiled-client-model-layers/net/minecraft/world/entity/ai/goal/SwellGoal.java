/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Creeper;

public class SwellGoal
extends Goal {
    private final Creeper f_25916_;
    @Nullable
    private LivingEntity f_25917_;

    public SwellGoal(Creeper p_25919_) {
        this.f_25916_ = p_25919_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        LivingEntity $$0 = this.f_25916_.m_5448_();
        return this.f_25916_.m_32310_() > 0 || $$0 != null && this.f_25916_.m_20280_($$0) < 9.0;
    }

    @Override
    public void m_8056_() {
        this.f_25916_.m_21573_().m_26573_();
        this.f_25917_ = this.f_25916_.m_5448_();
    }

    @Override
    public void m_8041_() {
        this.f_25917_ = null;
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        if (this.f_25917_ == null) {
            this.f_25916_.m_32283_(-1);
            return;
        }
        if (this.f_25916_.m_20280_(this.f_25917_) > 49.0) {
            this.f_25916_.m_32283_(-1);
            return;
        }
        if (!this.f_25916_.m_21574_().m_148306_(this.f_25917_)) {
            this.f_25916_.m_32283_(-1);
            return;
        }
        this.f_25916_.m_32283_(1);
    }
}

