/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Stopwatch
 *  com.google.common.base.Ticker
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.ImmutableSet$Builder
 *  com.mojang.logging.LogUtils
 *  it.unimi.dsi.fastutil.objects.ObjectOpenHashSet
 *  org.slf4j.Logger
 *  oshi.SystemInfo
 *  oshi.hardware.CentralProcessor
 */
package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import net.minecraft.util.profiling.ProfileCollector;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import net.minecraft.util.profiling.metrics.profiling.ProfilerSamplerAdapter;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class ServerMetricsSamplersProvider
implements MetricsSamplerProvider {
    private static final Logger f_181117_ = LogUtils.getLogger();
    private final Set<MetricSampler> f_146177_ = new ObjectOpenHashSet();
    private final ProfilerSamplerAdapter f_146178_ = new ProfilerSamplerAdapter();

    public ServerMetricsSamplersProvider(LongSupplier p_146180_, boolean p_146181_) {
        this.f_146177_.add(ServerMetricsSamplersProvider.m_146188_(p_146180_));
        if (p_146181_) {
            this.f_146177_.addAll(ServerMetricsSamplersProvider.m_146182_());
        }
    }

    public static Set<MetricSampler> m_146182_() {
        ImmutableSet.Builder $$0 = ImmutableSet.builder();
        try {
            CpuStats $$1 = new CpuStats();
            IntStream.range(0, $$1.f_146200_).mapToObj(p_146185_ -> MetricSampler.m_146009_("cpu#" + p_146185_, MetricCategory.CPU, () -> $$1.m_146207_(p_146185_))).forEach(arg_0 -> ((ImmutableSet.Builder)$$0).add(arg_0));
        }
        catch (Throwable $$2) {
            f_181117_.warn("Failed to query cpu, no cpu stats will be recorded", $$2);
        }
        $$0.add((Object)MetricSampler.m_146009_("heap MiB", MetricCategory.JVM, () -> (float)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576.0f));
        $$0.addAll(MetricsRegistry.f_146067_.m_146071_());
        return $$0.build();
    }

    @Override
    public Set<MetricSampler> m_142531_(Supplier<ProfileCollector> p_146191_) {
        this.f_146177_.addAll(this.f_146178_.m_146163_(p_146191_));
        return this.f_146177_;
    }

    public static MetricSampler m_146188_(final LongSupplier p_146189_) {
        Stopwatch $$1 = Stopwatch.createUnstarted((Ticker)new Ticker(){

            public long read() {
                return p_146189_.getAsLong();
            }
        });
        ToDoubleFunction<Stopwatch> $$2 = p_146187_ -> {
            if (p_146187_.isRunning()) {
                p_146187_.stop();
            }
            long $$1 = p_146187_.elapsed(TimeUnit.NANOSECONDS);
            p_146187_.reset();
            return $$1;
        };
        MetricSampler.ValueIncreasedByPercentage $$3 = new MetricSampler.ValueIncreasedByPercentage(2.0f);
        return MetricSampler.m_146013_("ticktime", MetricCategory.TICK_LOOP, $$2, $$1).m_146042_(Stopwatch::start).m_146040_($$3).m_146039_();
    }

    static class CpuStats {
        private final SystemInfo f_146201_ = new SystemInfo();
        private final CentralProcessor f_146202_ = this.f_146201_.getHardware().getProcessor();
        public final int f_146200_ = this.f_146202_.getLogicalProcessorCount();
        private long[][] f_146203_ = this.f_146202_.getProcessorCpuLoadTicks();
        private double[] f_146204_ = this.f_146202_.getProcessorCpuLoadBetweenTicks(this.f_146203_);
        private long f_146205_;

        CpuStats() {
        }

        public double m_146207_(int p_146208_) {
            long $$1 = System.currentTimeMillis();
            if (this.f_146205_ == 0L || this.f_146205_ + 501L < $$1) {
                this.f_146204_ = this.f_146202_.getProcessorCpuLoadBetweenTicks(this.f_146203_);
                this.f_146203_ = this.f_146202_.getProcessorCpuLoadTicks();
                this.f_146205_ = $$1;
            }
            return this.f_146204_[p_146208_] * 100.0;
        }
    }
}

