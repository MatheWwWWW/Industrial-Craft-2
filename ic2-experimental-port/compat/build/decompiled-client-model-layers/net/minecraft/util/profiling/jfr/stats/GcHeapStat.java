/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jdk.jfr.consumer.RecordedEvent;

public record GcHeapStat(Instant f_185680_, long f_185681_, Timing f_185682_) {
    public static GcHeapStat m_185697_(RecordedEvent p_185698_) {
        return new GcHeapStat(p_185698_.getStartTime(), p_185698_.getLong("heapUsed"), p_185698_.getString("when").equalsIgnoreCase("before gc") ? Timing.BEFORE_GC : Timing.AFTER_GC);
    }

    public static Summary m_185690_(Duration p_185691_, List<GcHeapStat> p_185692_, Duration p_185693_, int p_185694_) {
        return new Summary(p_185691_, p_185693_, p_185694_, GcHeapStat.m_185695_(p_185692_));
    }

    private static double m_185695_(List<GcHeapStat> p_185696_) {
        long $$1 = 0L;
        Map<Timing, List<GcHeapStat>> $$2 = p_185696_.stream().collect(Collectors.groupingBy(p_185689_ -> p_185689_.f_185682_));
        List<GcHeapStat> $$3 = $$2.get((Object)Timing.BEFORE_GC);
        List<GcHeapStat> $$4 = $$2.get((Object)Timing.AFTER_GC);
        for (int $$5 = 1; $$5 < $$3.size(); ++$$5) {
            GcHeapStat $$6 = $$3.get($$5);
            GcHeapStat $$7 = $$4.get($$5 - 1);
            $$1 += $$6.f_185681_ - $$7.f_185681_;
        }
        Duration $$8 = Duration.between(p_185696_.get((int)1).f_185680_, p_185696_.get((int)(p_185696_.size() - 1)).f_185680_);
        return (double)$$1 / (double)$$8.getSeconds();
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{GcHeapStat.class, "timestamp;heapUsed;timing", "f_185680_", "f_185681_", "f_185682_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{GcHeapStat.class, "timestamp;heapUsed;timing", "f_185680_", "f_185681_", "f_185682_"}, this);
    }

    @Override
    public final boolean equals(Object p_185702_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{GcHeapStat.class, "timestamp;heapUsed;timing", "f_185680_", "f_185681_", "f_185682_"}, this, p_185702_);
    }

    static final class Timing
    extends Enum<Timing> {
        public static final /* enum */ Timing BEFORE_GC = new Timing();
        public static final /* enum */ Timing AFTER_GC = new Timing();
        private static final /* synthetic */ Timing[] $VALUES;

        public static Timing[] values() {
            return (Timing[])$VALUES.clone();
        }

        public static Timing valueOf(String p_185732_) {
            return Enum.valueOf(Timing.class, p_185732_);
        }

        private static /* synthetic */ Timing[] m_185730_() {
            return new Timing[]{BEFORE_GC, AFTER_GC};
        }

        static {
            $VALUES = Timing.m_185730_();
        }
    }

    public record Summary(Duration f_185705_, Duration f_185706_, int f_185707_, double f_185708_) {
        public float m_185714_() {
            return (float)this.f_185706_.toMillis() / (float)this.f_185705_.toMillis();
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Summary.class, "duration;gcTotalDuration;totalGCs;allocationRateBytesPerSecond", "f_185705_", "f_185706_", "f_185707_", "f_185708_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Summary.class, "duration;gcTotalDuration;totalGCs;allocationRateBytesPerSecond", "f_185705_", "f_185706_", "f_185707_", "f_185708_"}, this);
        }

        @Override
        public final boolean equals(Object p_185720_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Summary.class, "duration;gcTotalDuration;totalGCs;allocationRateBytesPerSecond", "f_185705_", "f_185706_", "f_185707_", "f_185708_"}, this, p_185720_);
        }
    }
}

