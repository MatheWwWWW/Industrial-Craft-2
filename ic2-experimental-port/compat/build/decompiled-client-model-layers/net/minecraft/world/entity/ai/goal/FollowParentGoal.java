/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.entity.ai.goal;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

public class FollowParentGoal
extends Goal {
    public static final int f_148091_ = 8;
    public static final int f_148092_ = 4;
    public static final int f_148093_ = 3;
    private final Animal f_25314_;
    @Nullable
    private Animal f_25315_;
    private final double f_25316_;
    private int f_25317_;

    public FollowParentGoal(Animal p_25319_, double p_25320_) {
        this.f_25314_ = p_25319_;
        this.f_25316_ = p_25320_;
    }

    @Override
    public boolean m_8036_() {
        if (this.f_25314_.m_146764_() >= 0) {
            return false;
        }
        List<?> $$0 = this.f_25314_.f_19853_.m_45976_(this.f_25314_.getClass(), this.f_25314_.m_20191_().m_82377_(8.0, 4.0, 8.0));
        Animal $$1 = null;
        double $$2 = Double.MAX_VALUE;
        for (Animal $$3 : $$0) {
            double $$4;
            if ($$3.m_146764_() < 0 || ($$4 = this.f_25314_.m_20280_($$3)) > $$2) continue;
            $$2 = $$4;
            $$1 = $$3;
        }
        if ($$1 == null) {
            return false;
        }
        if ($$2 < 9.0) {
            return false;
        }
        this.f_25315_ = $$1;
        return true;
    }

    @Override
    public boolean m_8045_() {
        if (this.f_25314_.m_146764_() >= 0) {
            return false;
        }
        if (!this.f_25315_.m_6084_()) {
            return false;
        }
        double $$0 = this.f_25314_.m_20280_(this.f_25315_);
        return !($$0 < 9.0) && !($$0 > 256.0);
    }

    @Override
    public void m_8056_() {
        this.f_25317_ = 0;
    }

    @Override
    public void m_8041_() {
        this.f_25315_ = null;
    }

    @Override
    public void m_8037_() {
        if (--this.f_25317_ > 0) {
            return;
        }
        this.f_25317_ = this.m_183277_(10);
        this.f_25314_.m_21573_().m_5624_(this.f_25315_, this.f_25316_);
    }
}

