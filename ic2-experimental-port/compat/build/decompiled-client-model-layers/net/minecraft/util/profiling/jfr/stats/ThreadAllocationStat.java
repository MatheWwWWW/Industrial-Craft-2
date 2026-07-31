/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 */
package net.minecraft.util.profiling.jfr.stats;

import com.google.common.base.MoreObjects;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedThread;

public record ThreadAllocationStat(Instant f_185786_, String f_185787_, long f_185788_) {
    private static final String f_185789_ = "unknown";

    public static ThreadAllocationStat m_185803_(RecordedEvent p_185804_) {
        RecordedThread $$1 = p_185804_.getThread("thread");
        String $$2 = $$1 == null ? f_185789_ : (String)MoreObjects.firstNonNull((Object)$$1.getJavaName(), (Object)f_185789_);
        return new ThreadAllocationStat(p_185804_.getStartTime(), $$2, p_185804_.getLong("allocated"));
    }

    public static Summary m_185797_(List<ThreadAllocationStat> p_185798_) {
        TreeMap<String, Double> $$1 = new TreeMap<String, Double>();
        Map<String, List<ThreadAllocationStat>> $$2 = p_185798_.stream().collect(Collectors.groupingBy(p_185796_ -> p_185796_.f_185787_));
        $$2.forEach((p_185801_, p_185802_) -> {
            if (p_185802_.size() < 2) {
                return;
            }
            ThreadAllocationStat $$3 = (ThreadAllocationStat)p_185802_.get(0);
            ThreadAllocationStat $$4 = (ThreadAllocationStat)p_185802_.get(p_185802_.size() - 1);
            long $$5 = Duration.between($$3.f_185786_, $$4.f_185786_).getSeconds();
            long $$6 = $$4.f_185788_ - $$3.f_185788_;
            $$1.put((String)p_185801_, (double)$$6 / (double)$$5);
        });
        return new Summary($$1);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{ThreadAllocationStat.class, "timestamp;threadName;totalBytes", "f_185786_", "f_185787_", "f_185788_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{ThreadAllocationStat.class, "timestamp;threadName;totalBytes", "f_185786_", "f_185787_", "f_185788_"}, this);
    }

    @Override
    public final boolean equals(Object p_185808_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{ThreadAllocationStat.class, "timestamp;threadName;totalBytes", "f_185786_", "f_185787_", "f_185788_"}, this, p_185808_);
    }

    public record Summary(Map<String, Double> f_185811_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Summary.class, "allocationsPerSecondByThread", "f_185811_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Summary.class, "allocationsPerSecondByThread", "f_185811_"}, this);
        }

        @Override
        public final boolean equals(Object p_185816_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Summary.class, "allocationsPerSecondByThread", "f_185811_"}, this, p_185816_);
        }
    }
}

