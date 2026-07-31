/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import jdk.jfr.Configuration;
import jdk.jfr.Event;
import jdk.jfr.FlightRecorder;
import jdk.jfr.FlightRecorderListener;
import jdk.jfr.Recording;
import jdk.jfr.RecordingState;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.jfr.Environment;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import net.minecraft.util.profiling.jfr.SummaryReporter;
import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
import net.minecraft.util.profiling.jfr.event.ChunkGenerationEvent;
import net.minecraft.util.profiling.jfr.event.NetworkSummaryEvent;
import net.minecraft.util.profiling.jfr.event.PacketReceivedEvent;
import net.minecraft.util.profiling.jfr.event.PacketSentEvent;
import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;

public class JfrProfiler
implements JvmProfiler {
    private static final Logger f_185288_ = LogUtils.getLogger();
    public static final String f_185284_ = "Minecraft";
    public static final String f_185285_ = "World Generation";
    public static final String f_185286_ = "Ticking";
    public static final String f_185287_ = "Network";
    private static final List<Class<? extends Event>> f_185289_ = List.of(ChunkGenerationEvent.class, PacketReceivedEvent.class, PacketSentEvent.class, NetworkSummaryEvent.class, ServerTickTimeEvent.class, WorldLoadFinishedEvent.class);
    private static final String f_185290_ = "/flightrecorder-config.jfc";
    private static final DateTimeFormatter f_185291_ = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd-HHmmss").toFormatter().withZone(ZoneId.systemDefault());
    private static final JfrProfiler f_185292_ = new JfrProfiler();
    @Nullable
    Recording f_185293_;
    private float f_185294_;
    private final Map<String, NetworkSummaryEvent.SumAggregation> f_185295_ = new ConcurrentHashMap<String, NetworkSummaryEvent.SumAggregation>();

    private JfrProfiler() {
        f_185289_.forEach(FlightRecorder::register);
        FlightRecorder.addPeriodicEvent(ServerTickTimeEvent.class, () -> new ServerTickTimeEvent(this.f_185294_).commit());
        FlightRecorder.addPeriodicEvent(NetworkSummaryEvent.class, () -> {
            Iterator<NetworkSummaryEvent.SumAggregation> $$0 = this.f_185295_.values().iterator();
            while ($$0.hasNext()) {
                $$0.next().m_195576_();
                $$0.remove();
            }
        });
    }

    public static JfrProfiler m_185298_() {
        return f_185292_;
    }

    @Override
    public boolean m_183425_(Environment p_185307_) {
        boolean bl;
        URL $$1 = JfrProfiler.class.getResource(f_185290_);
        if ($$1 == null) {
            f_185288_.warn("Could not find default flight recorder config at {}", (Object)f_185290_);
            return false;
        }
        BufferedReader $$2 = new BufferedReader(new InputStreamReader($$1.openStream()));
        try {
            bl = this.m_185316_($$2, p_185307_);
        }
        catch (Throwable throwable) {
            try {
                try {
                    $$2.close();
                }
                catch (Throwable throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
            catch (IOException $$3) {
                f_185288_.warn("Failed to start flight recorder using configuration at {}", (Object)$$1, (Object)$$3);
                return false;
            }
        }
        $$2.close();
        return bl;
    }

    @Override
    public Path m_183243_() {
        if (this.f_185293_ == null) {
            throw new IllegalStateException("Not currently profiling");
        }
        this.f_185295_.clear();
        Path $$0 = this.f_185293_.getDestination();
        this.f_185293_.stop();
        return $$0;
    }

    @Override
    public boolean m_183608_() {
        return this.f_185293_ != null;
    }

    @Override
    public boolean m_183609_() {
        return FlightRecorder.isAvailable();
    }

    private boolean m_185316_(Reader p_185317_, Environment p_185318_) {
        if (this.m_183608_()) {
            f_185288_.warn("Profiling already in progress");
            return false;
        }
        try {
            Configuration $$2 = Configuration.create(p_185317_);
            String $$3 = f_185291_.format(Instant.now());
            this.f_185293_ = Util.m_137469_(new Recording($$2), p_185311_ -> {
                f_185289_.forEach(p_185311_::enable);
                p_185311_.setDumpOnExit(true);
                p_185311_.setToDisk(true);
                p_185311_.setName(String.format(Locale.ROOT, "%s-%s-%s", p_185318_.m_185277_(), SharedConstants.m_183709_().getName(), $$3));
            });
            Path $$4 = Paths.get(String.format(Locale.ROOT, "debug/%s-%s.jfr", p_185318_.m_185277_(), $$3), new String[0]);
            if (!Files.exists($$4.getParent(), new LinkOption[0])) {
                Files.createDirectories($$4.getParent(), new FileAttribute[0]);
            }
            this.f_185293_.setDestination($$4);
            this.f_185293_.start();
            this.m_185330_();
        }
        catch (IOException | ParseException $$5) {
            f_185288_.warn("Failed to start jfr profiling", (Throwable)$$5);
            return false;
        }
        f_185288_.info("Started flight recorder profiling id({}):name({}) - will dump to {} on exit or stop command", new Object[]{this.f_185293_.getId(), this.f_185293_.getName(), this.f_185293_.getDestination()});
        return true;
    }

    private void m_185330_() {
        FlightRecorder.addListener(new FlightRecorderListener(){
            final SummaryReporter f_185333_ = new SummaryReporter(() -> {
                JfrProfiler.this.f_185293_ = null;
            });

            @Override
            public void recordingStateChanged(Recording p_185339_) {
                if (p_185339_ != JfrProfiler.this.f_185293_ || p_185339_.getState() != RecordingState.STOPPED) {
                    return;
                }
                this.f_185333_.m_185400_(p_185339_.getDestination());
                FlightRecorder.removeListener(this);
            }
        });
    }

    @Override
    public void m_183597_(float p_185300_) {
        if (ServerTickTimeEvent.f_195598_.isEnabled()) {
            this.f_185294_ = p_185300_;
        }
    }

    @Override
    public void m_183510_(int p_185302_, int p_185303_, SocketAddress p_185304_, int p_185305_) {
        if (PacketReceivedEvent.f_195582_.isEnabled()) {
            new PacketReceivedEvent(p_185302_, p_185303_, p_185304_, p_185305_).commit();
        }
        if (NetworkSummaryEvent.f_195554_.isEnabled()) {
            this.m_185319_(p_185304_).m_195579_(p_185305_);
        }
    }

    @Override
    public void m_183508_(int p_185323_, int p_185324_, SocketAddress p_185325_, int p_185326_) {
        if (PacketSentEvent.f_195590_.isEnabled()) {
            new PacketSentEvent(p_185323_, p_185324_, p_185325_, p_185326_).commit();
        }
        if (NetworkSummaryEvent.f_195554_.isEnabled()) {
            this.m_185319_(p_185325_).m_195577_(p_185326_);
        }
    }

    private NetworkSummaryEvent.SumAggregation m_185319_(SocketAddress p_185320_) {
        return this.f_185295_.computeIfAbsent(p_185320_.toString(), NetworkSummaryEvent.SumAggregation::new);
    }

    @Override
    @Nullable
    public ProfiledDuration m_183494_() {
        if (!WorldLoadFinishedEvent.f_195606_.isEnabled()) {
            return null;
        }
        WorldLoadFinishedEvent $$0 = new WorldLoadFinishedEvent();
        $$0.begin();
        return $$0::commit;
    }

    @Override
    @Nullable
    public ProfiledDuration m_183559_(ChunkPos p_185313_, ResourceKey<Level> p_185314_, String p_185315_) {
        if (!ChunkGenerationEvent.f_195534_.isEnabled()) {
            return null;
        }
        ChunkGenerationEvent $$3 = new ChunkGenerationEvent(p_185313_, p_185314_, p_185315_);
        $$3.begin();
        return $$3::commit;
    }
}

