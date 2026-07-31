/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.math.LongMath
 *  com.google.gson.JsonParser
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.logging.LogUtils
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 *  it.unimi.dsi.fastutil.objects.Object2BooleanFunction
 *  javax.annotation.Nullable
 *  org.slf4j.Logger
 */
package net.minecraft.client;

import com.google.common.collect.ImmutableMap;
import com.google.common.math.LongMath;
import com.google.gson.JsonParser;
import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2BooleanFunction;
import java.io.BufferedReader;
import java.io.Reader;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public class PeriodicNotificationManager
extends SimplePreparableReloadListener<Map<String, List<Notification>>>
implements AutoCloseable {
    private static final Codec<Map<String, List<Notification>>> f_205285_ = Codec.unboundedMap((Codec)Codec.STRING, (Codec)RecordCodecBuilder.create(p_205303_ -> p_205303_.group((App)Codec.LONG.optionalFieldOf("delay", (Object)0L).forGetter(Notification::f_205328_), (App)Codec.LONG.fieldOf("period").forGetter(Notification::f_205329_), (App)Codec.STRING.fieldOf("title").forGetter(Notification::f_205330_), (App)Codec.STRING.fieldOf("message").forGetter(Notification::f_205331_)).apply((Applicative)p_205303_, Notification::new)).listOf());
    private static final Logger f_205286_ = LogUtils.getLogger();
    private final ResourceLocation f_205287_;
    private final Object2BooleanFunction<String> f_205288_;
    @Nullable
    private Timer f_205289_;
    @Nullable
    private NotificationTask f_205290_;

    public PeriodicNotificationManager(ResourceLocation p_205293_, Object2BooleanFunction<String> p_205294_) {
        this.f_205287_ = p_205293_;
        this.f_205288_ = p_205294_;
    }

    @Override
    protected Map<String, List<Notification>> m_5944_(ResourceManager p_205300_, ProfilerFiller p_205301_) {
        Map map;
        block8: {
            BufferedReader $$2 = p_205300_.m_215597_(this.f_205287_);
            try {
                map = (Map)f_205285_.parse((DynamicOps)JsonOps.INSTANCE, (Object)JsonParser.parseReader((Reader)$$2)).result().orElseThrow();
                if ($$2 == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if ($$2 != null) {
                        try {
                            ((Reader)$$2).close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception $$3) {
                    f_205286_.warn("Failed to load {}", (Object)this.f_205287_, (Object)$$3);
                    return ImmutableMap.of();
                }
            }
            ((Reader)$$2).close();
        }
        return map;
    }

    @Override
    protected void m_5787_(Map<String, List<Notification>> p_205318_, ResourceManager p_205319_, ProfilerFiller p_205320_) {
        List<Notification> $$3 = p_205318_.entrySet().stream().filter(p_205316_ -> (Boolean)this.f_205288_.apply((Object)((String)p_205316_.getKey()))).map(Map.Entry::getValue).flatMap(Collection::stream).collect(Collectors.toList());
        if ($$3.isEmpty()) {
            this.m_205295_();
            return;
        }
        if ($$3.stream().anyMatch(p_205326_ -> p_205326_.f_205329_ == 0L)) {
            Util.m_143785_("A periodic notification in " + this.f_205287_ + " has a period of zero minutes");
            this.m_205295_();
            return;
        }
        long $$4 = this.m_205310_($$3);
        long $$5 = this.m_205312_($$3, $$4);
        if (this.f_205289_ == null) {
            this.f_205289_ = new Timer();
        }
        this.f_205290_ = this.f_205290_ == null ? new NotificationTask($$3, $$4, $$5) : this.f_205290_.m_205356_($$3, $$5);
        this.f_205289_.scheduleAtFixedRate((TimerTask)this.f_205290_, TimeUnit.MINUTES.toMillis($$4), TimeUnit.MINUTES.toMillis($$5));
    }

    @Override
    public void close() {
        this.m_205295_();
    }

    private void m_205295_() {
        if (this.f_205289_ != null) {
            this.f_205289_.cancel();
        }
    }

    private long m_205312_(List<Notification> p_205313_, long p_205314_) {
        return p_205313_.stream().mapToLong(p_205298_ -> {
            long $$2 = p_205298_.f_205328_ - p_205314_;
            return LongMath.gcd((long)$$2, (long)p_205298_.f_205329_);
        }).reduce(LongMath::gcd).orElseThrow(() -> new IllegalStateException("Empty notifications from: " + this.f_205287_));
    }

    private long m_205310_(List<Notification> p_205311_) {
        return p_205311_.stream().mapToLong(p_205305_ -> p_205305_.f_205328_).min().orElse(0L);
    }

    @Override
    protected /* synthetic */ Object m_5944_(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.m_5944_(resourceManager, profilerFiller);
    }

    static class NotificationTask
    extends TimerTask {
        private final Minecraft f_205345_ = Minecraft.m_91087_();
        private final List<Notification> f_205346_;
        private final long f_205347_;
        private final AtomicLong f_205348_;

        public NotificationTask(List<Notification> p_205350_, long p_205351_, long p_205352_) {
            this.f_205346_ = p_205350_;
            this.f_205347_ = p_205352_;
            this.f_205348_ = new AtomicLong(p_205351_);
        }

        public NotificationTask m_205356_(List<Notification> p_205357_, long p_205358_) {
            this.cancel();
            return new NotificationTask(p_205357_, this.f_205348_.get(), p_205358_);
        }

        @Override
        public void run() {
            long $$0 = this.f_205348_.getAndAdd(this.f_205347_);
            long $$1 = this.f_205348_.get();
            for (Notification $$2 : this.f_205346_) {
                long $$4;
                long $$3;
                if ($$0 < $$2.f_205328_ || ($$3 = $$0 / $$2.f_205329_) == ($$4 = $$1 / $$2.f_205329_)) continue;
                this.f_205345_.execute(() -> SystemToast.m_94855_(Minecraft.m_91087_().m_91300_(), SystemToast.SystemToastIds.PERIODIC_NOTIFICATION, Component.m_237110_(p_205354_.f_205330_, $$3), Component.m_237110_(p_205354_.f_205331_, $$3)));
                return;
            }
        }
    }

    public record Notification(long f_205328_, long f_205329_, String f_205330_, String f_205331_) {
        public Notification(long f_205328_, long f_205329_, String f_205330_, String f_205331_) {
            this.f_205328_ = f_205328_ != 0L ? f_205328_ : f_205329_;
            this.f_205329_ = f_205329_;
            this.f_205330_ = f_205330_;
            this.f_205331_ = f_205331_;
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{Notification.class, "delay;period;title;message", "f_205328_", "f_205329_", "f_205330_", "f_205331_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{Notification.class, "delay;period;title;message", "f_205328_", "f_205329_", "f_205330_", "f_205331_"}, this);
        }

        @Override
        public final boolean equals(Object p_205342_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{Notification.class, "delay;period;title;message", "f_205328_", "f_205329_", "f_205330_", "f_205331_"}, this, p_205342_);
        }
    }
}

