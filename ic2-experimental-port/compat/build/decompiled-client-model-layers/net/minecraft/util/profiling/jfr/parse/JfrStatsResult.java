/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.util.profiling.jfr.parse;

import com.mojang.datafixers.util.Pair;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.jfr.serialize.JfrResultJsonSerializer;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;
import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
import net.minecraft.world.level.chunk.ChunkStatus;

public record JfrStatsResult(Instant f_185478_, Instant f_185479_, Duration f_185480_, @Nullable Duration f_185481_, List<TickTimeStat> f_185482_, List<CpuLoadStat> f_185483_, GcHeapStat.Summary f_185484_, ThreadAllocationStat.Summary f_185485_, NetworkPacketSummary f_185486_, NetworkPacketSummary f_185487_, FileIOStat.Summary f_185488_, FileIOStat.Summary f_185489_, List<ChunkGenStat> f_185490_) {
    public List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> m_185505_() {
        Map<ChunkStatus, List<ChunkGenStat>> $$0 = this.f_185490_.stream().collect(Collectors.groupingBy(ChunkGenStat::f_185595_));
        return $$0.entrySet().stream().map(p_185509_ -> Pair.of((Object)((ChunkStatus)p_185509_.getKey()), TimedStatSummary.m_185849_((List)p_185509_.getValue()))).sorted(Comparator.comparing(p_185507_ -> ((TimedStatSummary)p_185507_.getSecond()).f_185838_()).reversed()).toList();
    }

    public String m_185510_() {
        return new JfrResultJsonSerializer().m_185535_(this);
    }

    @Override
    public final String toString() {
        return ObjectMethods.bootstrap("toString", new MethodHandle[]{JfrStatsResult.class, "recordingStarted;recordingEnded;recordingDuration;worldCreationDuration;tickTimes;cpuLoadStats;heapSummary;threadAllocationSummary;receivedPacketsSummary;sentPacketsSummary;fileWrites;fileReads;chunkGenStats", "f_185478_", "f_185479_", "f_185480_", "f_185481_", "f_185482_", "f_185483_", "f_185484_", "f_185485_", "f_185486_", "f_185487_", "f_185488_", "f_185489_", "f_185490_"}, this);
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{JfrStatsResult.class, "recordingStarted;recordingEnded;recordingDuration;worldCreationDuration;tickTimes;cpuLoadStats;heapSummary;threadAllocationSummary;receivedPacketsSummary;sentPacketsSummary;fileWrites;fileReads;chunkGenStats", "f_185478_", "f_185479_", "f_185480_", "f_185481_", "f_185482_", "f_185483_", "f_185484_", "f_185485_", "f_185486_", "f_185487_", "f_185488_", "f_185489_", "f_185490_"}, this);
    }

    @Override
    public final boolean equals(Object p_185515_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{JfrStatsResult.class, "recordingStarted;recordingEnded;recordingDuration;worldCreationDuration;tickTimes;cpuLoadStats;heapSummary;threadAllocationSummary;receivedPacketsSummary;sentPacketsSummary;fileWrites;fileReads;chunkGenStats", "f_185478_", "f_185479_", "f_185480_", "f_185481_", "f_185482_", "f_185483_", "f_185484_", "f_185485_", "f_185486_", "f_185487_", "f_185488_", "f_185489_", "f_185490_"}, this, p_185515_);
    }
}

