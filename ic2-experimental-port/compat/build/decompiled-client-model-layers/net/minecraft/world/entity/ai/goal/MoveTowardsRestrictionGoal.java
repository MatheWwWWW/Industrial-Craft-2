/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class MoveTowardsRestrictionGoal
extends Goal {
    private final PathfinderMob f_25627_;
    private double f_25628_;
    private double f_25629_;
    private double f_25630_;
    private final double f_25631_;

    public MoveTowardsRestrictionGoal(PathfinderMob p_25633_, double p_25634_) {
        this.f_25627_ = p_25633_;
        this.f_25631_ = p_25634_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25627_.m_21533_()) {
            return false;
        }
        Vec3 $$0 = DefaultRandomPos.m_148412_(this.f_25627_, 16, 7, Vec3.m_82539_(this.f_25627_.m_21534_()), 1.5707963705062866);
        if ($$0 == null) {
            return false;
        }
        this.f_25628_ = $$0.f_82479_;
        this.f_25629_ = $$0.f_82480_;
        this.f_25630_ = $$0.f_82481_;
        return true;
    }

    @Override
    public boolean m_8045_() {
        return !this.f_25627_.m_21573_().m_26571_();
    }

    @Override
    public void m_8056_() {
        this.f_25627_.m_21573_().m_26519_(this.f_25628_, this.f_25629_, this.f_25630_, this.f_25631_);
    }
}

