/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 */
package net.minecraft.util.profiling.metrics.profiling;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.profiling.ActiveProfiler;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;

public class ProfilerSamplerAdapter {
    private final Set<String> f_146161_ = new ObjectOpenHashSet();

    public Set<MetricSampler> m_146163_(Supplier<ProfileCollector> p_146164_) {
        Set<MetricSampler> $$1 = p_146164_.get().m_142579_().stream().filter(p_146176_ -> !this.f_146161_.contains(p_146176_.getLeft())).map(p_146174_ -> ProfilerSamplerAdapter.m_146168_(p_146164_, (String)p_146174_.getLeft(), (MetricCategory)((Object)((Object)p_146174_.getRight())))).collect(Collectors.toSet());
        for (MetricSampler $$2 : $$1) {
            this.f_146161_.add($$2.m_146020_());
        }
        return $$1;
    }

    private static MetricSampler m_146168_(Supplier<ProfileCollector> p_146169_, String p_146170_, MetricCategory p_146171_) {
        return MetricSampler.m_146009_(p_146170_, p_146171_, () -> {
            ActiveProfiler.PathEntry $$2 = ((ProfileCollector)p_146169_.get()).m_142431_(p_146170_);
            return $$2 == null ? 0.0 : (double)$$2.m_142752_() / (double)TimeUtil.f_145017_;
        });
    }
}

