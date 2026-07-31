/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.util.profiling.jfr.parse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;

public class JfrStatsParser {
    private Instant f_185428_ = Instant.EPOCH;
    private Instant f_185429_ = Instant.EPOCH;
    private final List<ChunkGenStat> f_185430_ = Lists.newArrayList();
    private final List<CpuLoadStat> f_185431_ = Lists.newArrayList();
    private final Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> f_185432_ = Maps.newHashMap();
    private final Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> f_185433_ = Maps.newHashMap();
    private final List<FileIOStat> f_185434_ = Lists.newArrayList();
    private final List<FileIOStat> f_185435_ = Lists.newArrayList();
    private int f_185436_;
    private Duration f_185437_ = Duration.ZERO;
    private final List<GcHeapStat> f_185438_ = Lists.newArrayList();
    private final List<ThreadAllocationStat> f_185439_ = Lists.newArrayList();
    private final List<TickTimeStat> f_185440_ = Lists.newArrayList();
    @Nullable
    private Duration f_185441_ = null;

    private JfrStatsParser(Stream<RecordedEvent> p_185443_) {
        this.m_185454_(p_185443_);
    }

    public static JfrStatsResult m_185447_(Path p_185448_) {
        JfrStatsResult jfrStatsResult;
        final RecordingFile $$1 = new RecordingFile(p_185448_);
        try {
            Iterator<RecordedEvent> $$2 = new Iterator<RecordedEvent>(){

                @Override
                public boolean hasNext() {
                    return $$1.hasMoreEvents();
                }

                @Override
                public RecordedEvent next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    try {
                        return $$1.readEvent();
                    }
                    catch (IOException $$0) {
                        throw new UncheckedIOException($$0);
                    }
                }

                @Override
                public /* synthetic */ Object next() {
                    return this.next();
                }
            };
            Stream<RecordedEvent> $$3 = StreamSupport.stream(Spliterators.spliteratorUnknownSize($$2, 1297), false);
            jfrStatsResult = new JfrStatsParser($$3).m_185444_();
        }
        catch (Throwable throwable) {
            try {
                try {
                    $$1.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException $$4) {
                throw new UncheckedIOException($$4);
            }
        }
        $$1.close();
        return jfrStatsResult;
    }

    private JfrStatsResult m_185444_() {
        Duration $$0 = Duration.between(this.f_185428_, this.f_185429_);
        return new JfrStatsResult(this.f_185428_, this.f_185429_, $$0, this.f_185441_, this.f_185440_, this.f_185431_, GcHeapStat.m_185690_($$0, this.f_185438_, this.f_185437_, this.f_185436_), ThreadAllocationStat.m_185797_(this.f_185439_), JfrStatsParser.m_185449_($$0, this.f_185432_), JfrStatsParser.m_185449_($$0, this.f_185433_), FileIOStat.m_185640_($$0, this.f_185434_), FileIOStat.m_185640_($$0, this.f_185435_), this.f_185430_);
    }

    private void m_185454_(Stream<RecordedEvent> p_185455_) {
        p_185455_.forEach(p_185457_ -> {
            if (p_185457_.getEndTime().isAfter(this.f_185429_) || this.f_185429_.equals(Instant.EPOCH)) {
                this.f_185429_ = p_185457_.getEndTime();
            }
            if (p_185457_.getStartTime().isBefore(this.f_185428_) || this.f_185428_.equals(Instant.EPOCH)) {
                this.f_185428_ = p_185457_.getStartTime();
            }
            switch (p_185457_.getEventType().getName()) {
                case "minecraft.ChunkGeneration": {
                    this.f_185430_.add(ChunkGenStat.m_185604_(p_185457_));
                    break;
                }
                case "minecraft.LoadWorld": {
                    this.f_185441_ = p_185457_.getDuration();
                    break;
                }
                case "minecraft.ServerTickTime": {
                    this.f_185440_.add(TickTimeStat.m_185825_(p_185457_));
                    break;
                }
                case "minecraft.PacketReceived": {
                    this.m_185458_((RecordedEvent)p_185457_, p_185457_.getInt("bytes"), this.f_185432_);
                    break;
                }
                case "minecraft.PacketSent": {
                    this.m_185458_((RecordedEvent)p_185457_, p_185457_.getInt("bytes"), this.f_185433_);
                    break;
                }
                case "jdk.ThreadAllocationStatistics": {
                    this.f_185439_.add(ThreadAllocationStat.m_185803_(p_185457_));
                    break;
                }
                case "jdk.GCHeapSummary": {
                    this.f_185438_.add(GcHeapStat.m_185697_(p_185457_));
                    break;
                }
                case "jdk.CPULoad": {
                    this.f_185431_.add(CpuLoadStat.m_185622_(p_185457_));
                    break;
                }
                case "jdk.FileWrite": {
                    this.m_185462_((RecordedEvent)p_185457_, this.f_185434_, "bytesWritten");
                    break;
                }
                case "jdk.FileRead": {
                    this.m_185462_((RecordedEvent)p_185457_, this.f_185435_, "bytesRead");
                    break;
                }
                case "jdk.GarbageCollection": {
                    ++this.f_185436_;
                    this.f_185437_ = this.f_185437_.plus(p_185457_.getDuration());
                    break;
                }
            }
        });
    }

    private void m_185458_(RecordedEvent p_185459_, int p_185460_, Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> p_185461_) {
        p_185461_.computeIfAbsent(NetworkPacketSummary.PacketIdentification.m_185777_(p_185459_), p_185446_ -> new MutableCountAndSize()).m_185476_(p_185460_);
    }

    private void m_185462_(RecordedEvent p_185463_, List<FileIOStat> p_185464_, String p_185465_) {
        p_185464_.add(new FileIOStat(p_185463_.getDuration(), p_185463_.getString("path"), p_185463_.getLong(p_185465_)));
    }

    private static NetworkPacketSummary m_185449_(Duration p_185450_, Map<NetworkPacketSummary.PacketIdentification, MutableCountAndSize> p_185451_) {
        List<Pair<NetworkPacketSummary.PacketIdentification, NetworkPacketSummary.PacketCountAndSize>> $$2 = p_185451_.entrySet().stream().map(p_185453_ -> Pair.of((Object)((NetworkPacketSummary.PacketIdentification)p_185453_.getKey()), (Object)((MutableCountAndSize)p_185453_.getValue()).m_185475_())).toList();
        return new NetworkPacketSummary(p_185450_, $$2);
    }

    public static final class MutableCountAndSize {
        private long f_185472_;
        private long f_185473_;

        public void m_185476_(int p_185477_) {
            this.f_185473_ += (long)p_185477_;
            ++this.f_185472_;
        }

        public NetworkPacketSummary.PacketCountAndSize m_185475_() {
            return new NetworkPacketSummary.PacketCountAndSize(this.f_185472_, this.f_185473_);
        }
    }
}

