/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;

public class RestrictSunGoal
extends Goal {
    private final PathfinderMob f_25859_;

    public RestrictSunGoal(PathfinderMob p_25861_) {
        this.f_25859_ = p_25861_;
    }

    @Override
    public boolean m_8036_() {
        return this.f_25859_.f_19853_.m_46461_() && this.f_25859_.m_6844_(EquipmentSlot.HEAD).m_41619_() && GoalUtils.m_26894_(this.f_25859_);
    }

    @Override
    public void m_8056_() {
        ((GroundPathNavigation)this.f_25859_.m_21573_()).m_26490_(true);
    }

    @Override
    public void m_8041_() {
        if (GoalUtils.m_26894_(this.f_25859_)) {
            ((GroundPathNavigation)this.f_25859_.m_21573_()).m_26490_(false);
        }
    }
}

