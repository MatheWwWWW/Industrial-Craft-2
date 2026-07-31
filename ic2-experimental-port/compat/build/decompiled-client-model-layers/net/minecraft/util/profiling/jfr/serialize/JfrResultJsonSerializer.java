/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonNull
 *  com.google.gson.JsonObject
 *  com.google.gson.LongSerializationPolicy
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.util.profiling.jfr.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.LongSerializationPolicy;
import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import net.minecraft.Util;
import net.minecraft.util.profiling.jfr.Percentiles;
import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;
import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
import net.minecraft.world.level.chunk.ChunkStatus;

public class JfrResultJsonSerializer {
    private static final String f_185529_ = "bytesPerSecond";
    private static final String f_185530_ = "count";
    private static final String f_185531_ = "durationNanosTotal";
    private static final String f_185532_ = "totalBytes";
    private static final String f_185533_ = "countPerSecond";
    final Gson f_185528_ = new GsonBuilder().setPrettyPrinting().setLongSerializationPolicy(LongSerializationPolicy.DEFAULT).create();

    public String m_185535_(JfrStatsResult p_185536_) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty("startedEpoch", (Number)p_185536_.f_185478_().toEpochMilli());
        $$1.addProperty("endedEpoch", (Number)p_185536_.f_185479_().toEpochMilli());
        $$1.addProperty("durationMs", (Number)p_185536_.f_185480_().toMillis());
        Duration $$2 = p_185536_.f_185481_();
        if ($$2 != null) {
            $$1.addProperty("worldGenDurationMs", (Number)$$2.toMillis());
        }
        $$1.add("heap", this.m_185541_(p_185536_.f_185484_()));
        $$1.add("cpuPercent", this.m_185590_(p_185536_.f_185483_()));
        $$1.add("network", this.m_185588_(p_185536_));
        $$1.add("fileIO", this.m_185577_(p_185536_));
        $$1.add("serverTick", this.m_185586_(p_185536_.f_185482_()));
        $$1.add("threadAllocation", this.m_185545_(p_185536_.f_185485_()));
        $$1.add("chunkGen", this.m_185572_(p_185536_.m_185505_()));
        return this.f_185528_.toJson((JsonElement)$$1);
    }

    private JsonElement m_185541_(GcHeapStat.Summary p_185542_) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty("allocationRateBytesPerSecond", (Number)p_185542_.f_185708_());
        $$1.addProperty("gcCount", (Number)p_185542_.f_185707_());
        $$1.addProperty("gcOverHeadPercent", (Number)Float.valueOf(p_185542_.m_185714_()));
        $$1.addProperty("gcTotalDurationMs", (Number)p_185542_.f_185706_().toMillis());
        return $$1;
    }

    private JsonElement m_185572_(List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> p_185573_) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty(f_185531_, (Number)p_185573_.stream().mapToDouble(p_185567_ -> ((TimedStatSummary)p_185567_.getSecond()).f_185838_().toNanos()).sum());
        JsonArray $$2 = Util.m_137469_(new JsonArray(), p_185558_ -> $$1.add("status", (JsonElement)p_185558_));
        for (Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>> $$3 : p_185573_) {
            TimedStatSummary $$4 = (TimedStatSummary)$$3.getSecond();
            JsonObject $$5 = Util.m_137469_(new JsonObject(), arg_0 -> ((JsonArray)$$2).add(arg_0));
            $$5.addProperty("state", ((ChunkStatus)$$3.getFirst()).m_62467_());
            $$5.addProperty(f_185530_, (Number)$$4.f_185836_());
            $$5.addProperty(f_185531_, (Number)$$4.f_185838_().toNanos());
            $$5.addProperty("durationNanosAvg", (Number)($$4.f_185838_().toNanos() / (long)$$4.f_185836_()));
            JsonObject $$6 = Util.m_137469_(new JsonObject(), p_185561_ -> $$5.add("durationNanosPercentiles", (JsonElement)p_185561_));
            $$4.f_185837_().forEach((p_185584_, p_185585_) -> $$6.addProperty("p" + p_185584_, (Number)p_185585_));
            Function<ChunkGenStat, JsonElement> $$7 = p_185538_ -> {
                JsonObject $$1 = new JsonObject();
                $$1.addProperty("durationNanos", (Number)p_185538_.m_183571_().toNanos());
                $$1.addProperty("level", p_185538_.f_185596_());
                $$1.addProperty("chunkPosX", (Number)p_185538_.f_185593_().f_45578_);
                $$1.addProperty("chunkPosZ", (Number)p_185538_.f_185593_().f_45579_);
                $$1.addProperty("worldPosX", (Number)p_185538_.f_185594_().f_140723_());
                $$1.addProperty("worldPosZ", (Number)p_185538_.f_185594_().f_140724_());
                return $$1;
            };
            $$5.add("fastest", $$7.apply((ChunkGenStat)$$4.f_185833_()));
            $$5.add("slowest", $$7.apply((ChunkGenStat)$$4.f_185834_()));
            $$5.add("secondSlowest", (JsonElement)($$4.f_185835_() != null ? $$7.apply((ChunkGenStat)$$4.f_185835_()) : JsonNull.INSTANCE));
        }
        return $$1;
    }

    private JsonElement m_185545_(ThreadAllocationStat.Summary p_185546_) {
        JsonArray $$1 = new JsonArray();
        p_185546_.f_185811_().forEach((p_185554_, p_185555_) -> $$1.add((JsonElement)Util.m_137469_(new JsonObject(), p_185571_ -> {
            p_185571_.addProperty("thread", p_185554_);
            p_185571_.addProperty(f_185529_, (Number)p_185555_);
        })));
        return $$1;
    }

    private JsonElement m_185586_(List<TickTimeStat> p_185587_) {
        if (p_185587_.isEmpty()) {
            return JsonNull.INSTANCE;
        }
        JsonObject $$1 = new JsonObject();
        double[] $$2 = p_185587_.stream().mapToDouble(p_185548_ -> (double)p_185548_.f_185820_().toNanos() / 1000000.0).toArray();
        DoubleSummaryStatistics $$3 = DoubleStream.of($$2).summaryStatistics();
        $$1.addProperty("minMs", (Number)$$3.getMin());
        $$1.addProperty("averageMs", (Number)$$3.getAverage());
        $$1.addProperty("maxMs", (Number)$$3.getMax());
        Map<Integer, Double> $$4 = Percentiles.m_185390_($$2);
        $$4.forEach((p_185564_, p_185565_) -> $$1.addProperty("p" + p_185564_, (Number)p_185565_));
        return $$1;
    }

    private JsonElement m_185577_(JfrStatsResult p_185578_) {
        JsonObject $$1 = new JsonObject();
        $$1.add("write", this.m_185539_(p_185578_.f_185488_()));
        $$1.add("read", this.m_185539_(p_185578_.f_185489_()));
        return $$1;
    }

    private JsonElement m_185539_(FileIOStat.Summary p_185540_) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty(f_185532_, (Number)p_185540_.f_185657_());
        $$1.addProperty(f_185530_, (Number)p_185540_.f_185659_());
        $$1.addProperty(f_185529_, (Number)p_185540_.f_185658_());
        $$1.addProperty(f_185533_, (Number)p_185540_.f_185660_());
        JsonArray $$2 = new JsonArray();
        $$1.add("topContributors", (JsonElement)$$2);
        p_185540_.f_185662_().forEach(p_185581_ -> {
            JsonObject $$2 = new JsonObject();
            $$2.add((JsonElement)$$2);
            $$2.addProperty("path", (String)p_185581_.getFirst());
            $$2.addProperty(f_185532_, (Number)p_185581_.getSecond());
        });
        return $$1;
    }

    private JsonElement m_185588_(JfrStatsResult p_185589_) {
        JsonObject $$1 = new JsonObject();
        $$1.add("sent", this.m_185543_(p_185589_.f_185487_()));
        $$1.add("received", this.m_185543_(p_185589_.f_185486_()));
        return $$1;
    }

    private JsonElement m_185543_(NetworkPacketSummary p_185544_) {
        JsonObject $$1 = new JsonObject();
        $$1.addProperty(f_185532_, (Number)p_185544_.m_185743_());
        $$1.addProperty(f_185530_, (Number)p_185544_.m_185742_());
        $$1.addProperty(f_185529_, (Number)p_185544_.m_185741_());
        $$1.addProperty(f_185533_, (Number)p_185544_.m_185740_());
        JsonArray $$2 = new JsonArray();
        $$1.add("topContributors", (JsonElement)$$2);
        p_185544_.m_185744_().forEach(p_185551_ -> {
            JsonObject $$2 = new JsonObject();
            $$2.add((JsonElement)$$2);
            NetworkPacketSummary.PacketIdentification $$3 = (NetworkPacketSummary.PacketIdentification)p_185551_.getFirst();
            NetworkPacketSummary.PacketCountAndSize $$4 = (NetworkPacketSummary.PacketCountAndSize)p_185551_.getSecond();
            $$2.addProperty("protocolId", (Number)$$3.f_185762_());
            $$2.addProperty("packetId", (Number)$$3.f_185763_());
            $$2.addProperty("packetName", $$3.m_185770_());
            $$2.addProperty(f_185532_, (Number)$$4.f_185747_());
            $$2.addProperty(f_185530_, (Number)$$4.f_185746_());
        });
        return $$1;
    }

    private JsonElement m_185590_(List<CpuLoadStat> p_185591_) {
        JsonObject $$1 = new JsonObject();
        BiFunction<List, ToDoubleFunction, JsonObject> $$2 = (p_185575_, p_185576_) -> {
            JsonObject $$2 = new JsonObject();
            DoubleSummaryStatistics $$3 = p_185575_.stream().mapToDouble(p_185576_).summaryStatistics();
            $$2.addProperty("min", (Number)$$3.getMin());
            $$2.addProperty("average", (Number)$$3.getAverage());
            $$2.addProperty("max", (Number)$$3.getMax());
            return $$2;
        };
        $$1.add("jvm", (JsonElement)$$2.apply(p_185591_, CpuLoadStat::f_185614_));
        $$1.add("userJvm", (JsonElement)$$2.apply(p_185591_, CpuLoadStat::f_185615_));
        $$1.add("system", (JsonElement)$$2.apply(p_185591_, CpuLoadStat::f_185616_));
        return $$1;
    }
}

