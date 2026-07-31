/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.util.profiling.metrics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;

public class MetricsRegistry {
    public static final MetricsRegistry f_146067_ = new MetricsRegistry();
    private final WeakHashMap<ProfilerMeasured, Void> f_146068_ = new WeakHashMap();

    private MetricsRegistry() {
    }

    public void m_146072_(ProfilerMeasured p_146073_) {
        this.f_146068_.put(p_146073_, null);
    }

    public List<MetricSampler> m_146071_() {
        Map<String, List<MetricSampler>> $$0 = this.f_146068_.keySet().stream().flatMap(p_146079_ -> p_146079_.m_142754_().stream()).collect(Collectors.groupingBy(MetricSampler::m_146020_));
        return MetricsRegistry.m_146076_($$0);
    }

    private static List<MetricSampler> m_146076_(Map<String, List<MetricSampler>> p_146077_) {
        return p_146077_.entrySet().stream().map(p_146075_ -> {
            String $$1 = (String)p_146075_.getKey();
            List $$2 = (List)p_146075_.getValue();
            return $$2.size() > 1 ? new AggregatedMetricSampler($$1, $$2) : (MetricSampler)$$2.get(0);
        }).collect(Collectors.toList());
    }

    static class AggregatedMetricSampler
    extends MetricSampler {
        private final List<MetricSampler> f_146080_;

        AggregatedMetricSampler(String p_146082_, List<MetricSampler> p_146083_) {
            super(p_146082_, p_146083_.get(0).m_146021_(), () -> AggregatedMetricSampler.m_146094_(p_146083_), () -> AggregatedMetricSampler.m_146092_(p_146083_), AggregatedMetricSampler.m_146087_(p_146083_));
            this.f_146080_ = p_146083_;
        }

        private static MetricSampler.ThresholdTest m_146087_(List<MetricSampler> p_146088_) {
            return p_146091_ -> p_146088_.stream().anyMatch(p_146086_ -> {
                if (p_146086_.f_145986_ != null) {
                    return p_146086_.f_145986_.m_142488_(p_146091_);
                }
                return false;
            });
        }

        private static void m_146092_(List<MetricSampler> p_146093_) {
            for (MetricSampler $$1 : p_146093_) {
                $$1.m_146001_();
            }
        }

        private static double m_146094_(List<MetricSampler> p_146095_) {
            double $$1 = 0.0;
            for (MetricSampler $$2 : p_146095_) {
                $$1 += $$2.m_146019_().getAsDouble();
            }
            return $$1 / (double)p_146095_.size();
        }

        @Override
        public boolean equals(@Nullable Object p_146101_) {
            if (this == p_146101_) {
                return true;
            }
            if (p_146101_ == null || this.getClass() != p_146101_.getClass()) {
                return false;
            }
            if (!super.equals(p_146101_)) {
                return false;
            }
            AggregatedMetricSampler $$1 = (AggregatedMetricSampler)p_146101_;
            return this.f_146080_.equals($$1.f_146080_);
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), this.f_146080_);
        }
    }
}

