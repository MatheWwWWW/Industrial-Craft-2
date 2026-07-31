/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling;

import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;

public class ContinuousProfiler {
    private final LongSupplier f_18430_;
    private final IntSupplier f_18431_;
    private ProfileCollector f_18432_ = InactiveProfiler.f_18554_;

    public ContinuousProfiler(LongSupplier p_18434_, IntSupplier p_18435_) {
        this.f_18430_ = p_18434_;
        this.f_18431_ = p_18435_;
    }

    public boolean m_18436_() {
        return this.f_18432_ != InactiveProfiler.f_18554_;
    }

    public void m_18437_() {
        this.f_18432_ = InactiveProfiler.f_18554_;
    }

    public void m_18438_() {
        this.f_18432_ = new ActiveProfiler(this.f_18430_, this.f_18431_, true);
    }

    public ProfilerFiller m_18439_() {
        return this.f_18432_;
    }

    public ProfileResults m_18440_() {
        return this.f_18432_.m_5948_();
    }
}

