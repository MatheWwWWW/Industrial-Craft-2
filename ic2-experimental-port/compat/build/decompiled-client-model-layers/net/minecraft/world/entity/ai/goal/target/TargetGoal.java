/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal.target;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.scores.Team;

public abstract class TargetGoal
extends Goal {
    private static final int f_148155_ = 0;
    private static final int f_148156_ = 1;
    private static final int f_148157_ = 2;
    protected final Mob f_26135_;
    protected final boolean f_26136_;
    private final boolean f_26131_;
    private int f_26132_;
    private int f_26133_;
    private int f_26134_;
    @Nullable
    protected LivingEntity f_26137_;
    protected int f_26138_ = 60;

    public TargetGoal(Mob p_26140_, boolean p_26141_) {
        this(p_26140_, p_26141_, false);
    }

    public TargetGoal(Mob p_26143_, boolean p_26144_, boolean p_26145_) {
        this.f_26135_ = p_26143_;
        this.f_26136_ = p_26144_;
        this.f_26131_ = p_26145_;
    }

    @Override
    public boolean m_8045_() {
        LivingEntity $$0 = this.f_26135_.m_5448_();
        if ($$0 == null) {
            $$0 = this.f_26137_;
        }
        if ($$0 == null) {
            return false;
        }
        if (!this.f_26135_.m_6779_($$0)) {
            return false;
        }
        Team $$1 = this.f_26135_.m_5647_();
        Team $$2 = $$0.m_5647_();
        if ($$1 != null && $$2 == $$1) {
            return false;
        }
        double $$3 = this.m_7623_();
        if (this.f_26135_.m_20280_($$0) > $$3 * $$3) {
            return false;
        }
        if (this.f_26136_) {
            if (this.f_26135_.m_21574_().m_148306_($$0)) {
                this.f_26134_ = 0;
            } else if (++this.f_26134_ > TargetGoal.m_186073_(this.f_26138_)) {
                return false;
            }
        }
        this.f_26135_.m_6710_($$0);
        return true;
    }

    protected double m_7623_() {
        return this.f_26135_.m_21133_(Attributes.f_22277_);
    }

    @Override
    public void m_8056_() {
        this.f_26132_ = 0;
        this.f_26133_ = 0;
        this.f_26134_ = 0;
    }

    @Override
    public void m_8041_() {
        this.f_26135_.m_6710_(null);
        this.f_26137_ = null;
    }

    protected boolean m_26150_(@Nullable LivingEntity p_26151_, TargetingConditions p_26152_) {
        if (p_26151_ == null) {
            return false;
        }
        if (!p_26152_.m_26885_(this.f_26135_, p_26151_)) {
            return false;
        }
        if (!this.f_26135_.m_21444_(p_26151_.m_20183_())) {
            return false;
        }
        if (this.f_26131_) {
            if (--this.f_26133_ <= 0) {
                this.f_26132_ = 0;
            }
            if (this.f_26132_ == 0) {
                int n = this.f_26132_ = this.m_26148_(p_26151_) ? 1 : 2;
            }
            if (this.f_26132_ == 2) {
                return false;
            }
        }
        return true;
    }

    private boolean m_26148_(LivingEntity p_26149_) {
        int $$4;
        this.f_26133_ = TargetGoal.m_186073_(10 + this.f_26135_.m_217043_().m_188503_(5));
        Path $$1 = this.f_26135_.m_21573_().m_6570_(p_26149_, 0);
        if ($$1 == null) {
            return false;
        }
        Node $$2 = $$1.m_77395_();
        if ($$2 == null) {
            return false;
        }
        int $$3 = $$2.f_77271_ - p_26149_.m_146903_();
        return (double)($$3 * $$3 + ($$4 = $$2.f_77273_ - p_26149_.m_146907_()) * $$4) <= 2.25;
    }

    public TargetGoal m_26146_(int p_26147_) {
        this.f_26138_ = p_26147_;
        return this;
    }
}

