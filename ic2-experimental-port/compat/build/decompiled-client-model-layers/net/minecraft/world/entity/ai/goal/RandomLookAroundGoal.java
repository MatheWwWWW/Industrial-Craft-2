/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class RandomLookAroundGoal
extends Goal {
    private final Mob f_25715_;
    private double f_25716_;
    private double f_25717_;
    private int f_25718_;

    public RandomLookAroundGoal(Mob p_25720_) {
        this.f_25715_ = p_25720_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean m_8036_() {
        return this.f_25715_.m_217043_().m_188501_() < 0.02f;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25718_ >= 0;
    }

    @Override
    public void m_8056_() {
        double $$0 = Math.PI * 2 * this.f_25715_.m_217043_().m_188500_();
        this.f_25716_ = Math.cos($$0);
        this.f_25717_ = Math.sin($$0);
        this.f_25718_ = 20 + this.f_25715_.m_217043_().m_188503_(20);
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        --this.f_25718_;
        this.f_25715_.m_21563_().m_24946_(this.f_25715_.m_20185_() + this.f_25716_, this.f_25715_.m_20188_(), this.f_25715_.m_20189_() + this.f_25717_);
    }
}

