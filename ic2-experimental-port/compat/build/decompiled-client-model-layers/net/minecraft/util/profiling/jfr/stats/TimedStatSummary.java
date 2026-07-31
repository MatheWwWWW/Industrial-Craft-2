/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.jfr.Percentiles;
import net.minecraft.util.profiling.jfr.stats.TimedStat;

public record TimedStatSummary<T extends TimedStat>(T f_185833_, T f_185834_, @Nullable T f_185835_, int f_185836_, Map<Integer, Double> f_185837_, Duration f_185838_) {
    public static <T extends TimedStat> TimedStatSummary<T> m_185849_(List<T> p_185850_) {
        if (p_185850_.isEmpty()) {
            throw new IllegalArgumentException("No values");
        }
        List<TimedStat> $$1 = p_185850_.stream().sorted(Comparator.comparing(TimedStat::m_183571_)).toList();
        Duration $$2 = $$1.stream().map(TimedStat::m_183571_).reduce(Duration::plus).orElse(Duration.ZERO);
        TimedStat $$3 = $$1.get(0);
        TimedStat $$4 = $$1.get($$1.size() - 1);
        TimedStat $$5 = $$1.size() > 1 ? $$1.get($$1.size() - 2) : null;
        int $$6 = $$1.size();
        Map<Integer, Double> $$7 = Percentiles.m_185392_($$1.stream().mapToLong(p_185848_ -> p_185848_.m_183571_().toNanos()).toArray());
        return new TimedStatSummary<TimedStat>($$3, $$4, $$5, $$6, $$7, $$2);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{TimedStatSummary.class, "fastest;slowest;secondSlowest;count;percentilesNanos;totalDuration", "f_185833_", "f_185834_", "f_185835_", "f_185836_", "f_185837_", "f_185838_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TimedStatSummary.class, "fastest;slowest;secondSlowest;count;percentilesNanos;totalDuration", "f_185833_", "f_185834_", "f_185835_", "f_185836_", "f_185837_", "f_185838_"}, this);
    }

    @Override
    public final boolean equals(Object p_185856_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TimedStatSummary.class, "fastest;slowest;secondSlowest;count;percentilesNanos;totalDuration", "f_185833_", "f_185834_", "f_185835_", "f_185836_", "f_185837_", "f_185838_"}, this, p_185856_);
    }
}

