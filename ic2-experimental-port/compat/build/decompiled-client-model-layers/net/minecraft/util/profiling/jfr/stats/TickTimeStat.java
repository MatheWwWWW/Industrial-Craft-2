/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.time.Instant;
import jdk.jfr.consumer.RecordedEvent;

public record TickTimeStat(Instant f_185819_, Duration f_185820_) {
    public static TickTimeStat m_185825_(RecordedEvent p_185826_) {
        return new TickTimeStat(p_185826_.getStartTime(), p_185826_.getDuration("averageTickDuration"));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{TickTimeStat.class, "timestamp;currentAverage", "f_185819_", "f_185820_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{TickTimeStat.class, "timestamp;currentAverage", "f_185819_", "f_185820_"}, this);
    }

    @Override
    public final boolean equals(Object p_185829_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{TickTimeStat.class, "timestamp;currentAverage", "f_185819_", "f_185820_"}, this, p_185829_);
    }
}

