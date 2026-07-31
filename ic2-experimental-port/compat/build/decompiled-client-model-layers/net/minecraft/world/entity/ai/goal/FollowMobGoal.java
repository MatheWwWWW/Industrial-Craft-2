/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FollowMobGoal
extends Goal {
    private final Mob f_25261_;
    private final Predicate<Mob> f_25262_;
    @Nullable
    private Mob f_25263_;
    private final double f_25264_;
    private final PathNavigation f_25265_;
    private int f_25266_;
    private final float f_25267_;
    private float f_25268_;
    private final float f_25269_;

    public FollowMobGoal(Mob p_25271_, double p_25272_, float p_25273_, float p_25274_) {
        this.f_25261_ = p_25271_;
        this.f_25262_ = p_25278_ -> p_25278_ != null && p_25271_.getClass() != p_25278_.getClass();
        this.f_25264_ = p_25272_;
        this.f_25265_ = p_25271_.m_21573_();
        this.f_25267_ = p_25273_;
        this.f_25269_ = p_25274_;
        this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(p_25271_.m_21573_() instanceof GroundPathNavigation) && !(p_25271_.m_21573_() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    @Override
    public boolean m_8036_() {
        List<Mob> $$0 = this.f_25261_.f_19853_.m_6443_(Mob.class, this.f_25261_.m_20191_().m_82400_(this.f_25269_), this.f_25262_);
        if (!$$0.isEmpty()) {
            for (Mob $$1 : $$0) {
                if ($$1.m_20145_()) continue;
                this.f_25263_ = $$1;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean m_8045_() {
        return this.f_25263_ != null && !this.f_25265_.m_26571_() && this.f_25261_.m_20280_(this.f_25263_) > (double)(this.f_25267_ * this.f_25267_);
    }

    @Override
    public void m_8056_() {
        this.f_25266_ = 0;
        this.f_25268_ = this.f_25261_.m_21439_(BlockPathTypes.WATER);
        this.f_25261_.m_21441_(BlockPathTypes.WATER, 0.0f);
    }

    @Override
    public void m_8041_() {
        this.f_25263_ = null;
        this.f_25265_.m_26573_();
        this.f_25261_.m_21441_(BlockPathTypes.WATER, this.f_25268_);
    }

    @Override
    public void m_8037_() {
        double $$2;
        double $$1;
        if (this.f_25263_ == null || this.f_25261_.m_21523_()) {
            return;
        }
        this.f_25261_.m_21563_().m_24960_(this.f_25263_, 10.0f, this.f_25261_.m_8132_());
        if (--this.f_25266_ > 0) {
            return;
        }
        this.f_25266_ = this.m_183277_(10);
        double $$0 = this.f_25261_.m_20185_() - this.f_25263_.m_20185_();
        double $$3 = $$0 * $$0 + ($$1 = this.f_25261_.m_20186_() - this.f_25263_.m_20186_()) * $$1 + ($$2 = this.f_25261_.m_20189_() - this.f_25263_.m_20189_()) * $$2;
        if ($$3 <= (double)(this.f_25267_ * this.f_25267_)) {
            this.f_25265_.m_26573_();
            LookControl $$4 = this.f_25263_.m_21563_();
            if ($$3 <= (double)this.f_25267_ || $$4.m_24969_() == this.f_25261_.m_20185_() && $$4.m_24970_() == this.f_25261_.m_20186_() && $$4.m_24971_() == this.f_25261_.m_20189_()) {
                double $$5 = this.f_25263_.m_20185_() - this.f_25261_.m_20185_();
                double $$6 = this.f_25263_.m_20189_() - this.f_25261_.m_20189_();
                this.f_25265_.m_26519_(this.f_25261_.m_20185_() - $$5, this.f_25261_.m_20186_(), this.f_25261_.m_20189_() - $$6, this.f_25264_);
            }
            return;
        }
        this.f_25265_.m_5624_(this.f_25263_, this.f_25264_);
    }
}

