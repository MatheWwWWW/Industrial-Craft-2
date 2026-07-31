/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiling.jfr.stats;

import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import jdk.jfr.consumer.RecordedEvent;

public record CpuLoadStat(double f_185614_, double f_185615_, double f_185616_) {
    public static CpuLoadStat m_185622_(RecordedEvent p_185623_) {
        return new CpuLoadStat(p_185623_.getFloat("jvmSystem"), p_185623_.getFloat("jvmUser"), p_185623_.getFloat("machineTotal"));
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{CpuLoadStat.class, "jvm;userJvm;system", "f_185614_", "f_185615_", "f_185616_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{CpuLoadStat.class, "jvm;userJvm;system", "f_185614_", "f_185615_", "f_185616_"}, this);
    }

    @Override
    public final boolean equals(Object p_185627_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{CpuLoadStat.class, "jvm;userJvm;system", "f_185614_", "f_185615_", "f_185616_"}, this, p_185627_);
    }
}

