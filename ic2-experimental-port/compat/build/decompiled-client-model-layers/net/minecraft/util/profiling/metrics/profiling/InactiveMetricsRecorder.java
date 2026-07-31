/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.metrics.profiling;

import net.minecraft.util.profiling.InactiveProfiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;

public class InactiveMetricsRecorder
implements MetricsRecorder {
    public static final MetricsRecorder f_146153_ = new InactiveMetricsRecorder();

    @Override
    public void m_142760_() {
    }

    @Override
    public void m_213832_() {
    }

    @Override
    public void m_142759_() {
    }

    @Override
    public boolean m_142763_() {
        return false;
    }

    @Override
    public ProfilerFiller m_142610_() {
        return InactiveProfiler.f_18554_;
    }

    @Override
    public void m_142758_() {
    }
}

