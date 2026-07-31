/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.tuple.Pair
 */
package net.minecraft.util.profiling;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;

public class InactiveProfiler
implements ProfileCollector {
    public static final InactiveProfiler f_18554_ = new InactiveProfiler();

    private InactiveProfiler() {
    }

    @Override
    public void m_7242_() {
    }

    @Override
    public void m_7241_() {
    }

    @Override
    public void m_6180_(String p_18559_) {
    }

    @Override
    public void m_6521_(Supplier<String> p_18561_) {
    }

    @Override
    public void m_142259_(MetricCategory p_145951_) {
    }

    @Override
    public void m_7238_() {
    }

    @Override
    public void m_6182_(String p_18564_) {
    }

    @Override
    public void m_6523_(Supplier<String> p_18566_) {
    }

    @Override
    public void m_183275_(String p_185253_, int p_185254_) {
    }

    @Override
    public void m_183536_(Supplier<String> p_185256_, int p_185257_) {
    }

    @Override
    public ProfileResults m_5948_() {
        return EmptyProfileResults.f_18441_;
    }

    @Override
    @Nullable
    public ActiveProfiler.PathEntry m_142431_(String p_145953_) {
        return null;
    }

    @Override
    public Set<Pair<String, MetricCategory>> m_142579_() {
        return ImmutableSet.of();
    }
}

