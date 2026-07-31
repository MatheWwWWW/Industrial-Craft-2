/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class FloatGoal
extends Goal {
    private final Mob f_25228_;

    public FloatGoal(Mob p_25230_) {
        this.f_25228_ = p_25230_;
        this.m_7021_(EnumSet.of(Goal.Flag.JUMP));
        p_25230_.m_21573_().m_7008_(true);
    }

    @Override
    public boolean m_8036_() {
        return this.f_25228_.m_20069_() && this.f_25228_.m_204036_(FluidTags.f_13131_) > this.f_25228_.m_20204_() || this.f_25228_.m_20077_();
    }

    @Override
    public boolean m_183429_() {
        return true;
    }

    @Override
    public void m_8037_() {
        if (this.f_25228_.m_217043_().m_188501_() < 0.8f) {
            this.f_25228_.m_21569_().m_24901_();
        }
    }
}

