/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFixUtils
 */
package net.minecraft.world.entity.ai.goal;

import com.mojang.datafixers.DataFixUtils;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;

public class FollowFlockLeaderGoal
extends Goal {
    private static final int f_148086_ = 200;
    private final AbstractSchoolingFish f_25245_;
    private int f_25246_;
    private int f_25247_;

    public FollowFlockLeaderGoal(AbstractSchoolingFish p_25249_) {
        this.f_25245_ = p_25249_;
        this.f_25247_ = this.m_25251_(p_25249_);
    }

    protected int m_25251_(AbstractSchoolingFish p_25252_) {
        return FollowFlockLeaderGoal.m_186073_(200 + p_25252_.m_217043_().m_188503_(200) % 20);
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25245_.m_27543_()) {
            return false;
        }
        if (this.f_25245_.m_27540_()) {
            return true;
        }
        if (this.f_25247_ > 0) {
            --this.f_25247_;
            return false;
        }
        this.f_25247_ = this.m_25251_(this.f_25245_);
        Predicate<AbstractSchoolingFish> $$0 = p_25258_ -> p_25258_.m_27542_() || !p_25258_.m_27540_();
        List<AbstractSchoolingFish> $$1 = this.f_25245_.f_19853_.m_6443_(this.f_25245_.getClass(), this.f_25245_.m_20191_().m_82377_(8.0, 8.0, 8.0), $$0);
        AbstractSchoolingFish $$2 = (AbstractSchoolingFish)DataFixUtils.orElse($$1.stream().filter(AbstractSchoolingFish::m_27542_).findAny(), (Object)this.f_25245_);
        $$2.m_27533_($$1.stream().filter(p_25255_ -> !p_25255_.m_27540_()));
        return this.f_25245_.m_27540_();
    }

    @Override
    public boolean m_8045_() {
        return this.f_25245_.m_27540_() && this.f_25245_.m_27544_();
    }

    @Override
    public void m_8056_() {
        this.f_25246_ = 0;
    }

    @Override
    public void m_8041_() {
        this.f_25245_.m_27541_();
    }

    @Override
    public void m_8037_() {
        if (--this.f_25246_ > 0) {
            return;
        }
        this.f_25246_ = this.m_183277_(10);
        this.f_25245_.m_27545_();
    }
}

