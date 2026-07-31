/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;

public class PotentialCalculator {
    private final List<PointCharge> f_47190_ = Lists.newArrayList();

    public void m_47192_(BlockPos p_47193_, double p_47194_) {
        if (p_47194_ != 0.0) {
            this.f_47190_.add(new PointCharge(p_47193_, p_47194_));
        }
    }

    public double m_47195_(BlockPos p_47196_, double p_47197_) {
        if (p_47197_ == 0.0) {
            return 0.0;
        }
        double $$2 = 0.0;
        for (PointCharge $$3 : this.f_47190_) {
            $$2 += $$3.m_47203_(p_47196_);
        }
        return $$2 * p_47197_;
    }

    static class PointCharge {
        private final BlockPos f_47198_;
        private final double f_47199_;

        public PointCharge(BlockPos p_47201_, double p_47202_) {
            this.f_47198_ = p_47201_;
            this.f_47199_ = p_47202_;
        }

        public double m_47203_(BlockPos p_47204_) {
            double $$1 = this.f_47198_.m_123331_(p_47204_);
            if ($$1 == 0.0) {
                return Double.POSITIVE_INFINITY;
            }
            return this.f_47199_ / Math.sqrt($$1);
        }
    }
}

