/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.util.profiling.jfr.stats;

import com.mojang.datafixers.util.Pair;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public record FileIOStat(Duration f_185630_, @Nullable String f_185631_, long f_185632_) {
    public static Summary m_185640_(Duration p_185641_, List<FileIOStat> p_185642_) {
        long $$2 = p_185642_.stream().mapToLong(p_185652_ -> p_185652_.f_185632_).sum();
        return new Summary($$2, (double)$$2 / (double)p_185641_.getSeconds(), p_185642_.size(), (double)p_185642_.size() / (double)p_185641_.getSeconds(), p_185642_.stream().map(FileIOStat::f_185630_).reduce(Duration.ZERO, Duration::plus), p_185642_.stream().filter(p_185650_ -> p_185650_.f_185631_ != null).collect(Collectors.groupingBy(p_185647_ -> p_185647_.f_185631_, Collectors.summingLong(p_185639_ -> p_185639_.f_185632_))).entrySet().stream().sorted(Map.Entry.comparingByValue().reversed()).map(p_185644_ -> Pair.of((Object)((String)p_185644_.getKey()), (Object)((Long)p_185644_.getValue()))).limit(10L).toList());
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{FileIOStat.class, "duration;path;bytes", "f_185630_", "f_185631_", "f_185632_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{FileIOStat.class, "duration;path;bytes", "f_185630_", "f_185631_", "f_185632_"}, this);
    }

    @Override
    public final boolean equals(Object p_185654_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{FileIOStat.class, "duration;path;bytes", "f_185630_", "f_185631_", "f_185632_"}, this, p_185654_);
    }

    public record Summary(long f_185657_, double f_185658_, long f_185659_, double f_185660_, Duration f_185661_, List<Pair<String, Long>> f_185662_) {
        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Summary.class, "totalBytes;bytesPerSecond;counts;countsPerSecond;timeSpentInIO;topTenContributorsByTotalBytes", "f_185657_", "f_185658_", "f_185659_", "f_185660_", "f_185661_", "f_185662_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Summary.class, "totalBytes;bytesPerSecond;counts;countsPerSecond;timeSpentInIO;topTenContributorsByTotalBytes", "f_185657_", "f_185658_", "f_185659_", "f_185660_", "f_185661_", "f_185662_"}, this);
        }

        @Override
        public final boolean equals(Object p_185676_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Summary.class, "totalBytes;bytesPerSecond;counts;countsPerSecond;timeSpentInIO;topTenContributorsByTotalBytes", "f_185657_", "f_185658_", "f_185659_", "f_185660_", "f_185661_", "f_185662_"}, this, p_185676_);
        }
    }
}

