/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.google.common.collect.ImmutableMap$Builder
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 */
package net.minecraft.util.profiling.jfr.stats;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;

public final class NetworkPacketSummary {
    private final PacketCountAndSize f_185734_;
    private final List<Pair<PacketIdentification, PacketCountAndSize>> f_185735_;
    private final Duration f_185736_;

    public NetworkPacketSummary(Duration p_185738_, List<Pair<PacketIdentification, PacketCountAndSize>> p_185739_) {
        this.f_185736_ = p_185738_;
        this.f_185734_ = p_185739_.stream().map(Pair::getSecond).reduce(PacketCountAndSize::m_185754_).orElseGet(() -> new PacketCountAndSize(0L, 0L));
        this.f_185735_ = p_185739_.stream().sorted(Comparator.comparing(Pair::getSecond, PacketCountAndSize.f_185748_)).limit(10L).toList();
    }

    public double m_185740_() {
        return (double)this.f_185734_.f_185746_ / (double)this.f_185736_.getSeconds();
    }

    public double m_185741_() {
        return (double)this.f_185734_.f_185747_ / (double)this.f_185736_.getSeconds();
    }

    public long m_185742_() {
        return this.f_185734_.f_185746_;
    }

    public long m_185743_() {
        return this.f_185734_.f_185747_;
    }

    public List<Pair<PacketIdentification, PacketCountAndSize>> m_185744_() {
        return this.f_185735_;
    }

    public record PacketCountAndSize(long f_185746_, long f_185747_) {
        static final Comparator<PacketCountAndSize> f_185748_ = Comparator.comparing(PacketCountAndSize::f_185747_).thenComparing(PacketCountAndSize::f_185746_).reversed();

        PacketCountAndSize m_185754_(PacketCountAndSize p_185755_) {
            return new PacketCountAndSize(this.f_185746_ + p_185755_.f_185746_, this.f_185747_ + p_185755_.f_185747_);
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PacketCountAndSize.class, "totalCount;totalSize", "f_185746_", "f_185747_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PacketCountAndSize.class, "totalCount;totalSize", "f_185746_", "f_185747_"}, this);
        }

        @Override
        public final boolean equals(Object p_185758_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PacketCountAndSize.class, "totalCount;totalSize", "f_185746_", "f_185747_"}, this, p_185758_);
        }
    }

    public record PacketIdentification(PacketFlow f_185761_, int f_185762_, int f_185763_) {
        private static final Map<PacketIdentification, String> f_185764_;

        public String m_185770_() {
            return f_185764_.getOrDefault(this, "unknown");
        }

        public static PacketIdentification m_185777_(RecordedEvent p_185778_) {
            return new PacketIdentification(p_185778_.getEventType().getName().equals("minecraft.PacketSent") ? PacketFlow.CLIENTBOUND : PacketFlow.SERVERBOUND, p_185778_.getInt("protocolId"), p_185778_.getInt("packetId"));
        }

        @Override
        public final String toString() {
            return ObjectMethods.bootstrap("toString", new MethodHandle[]{PacketIdentification.class, "direction;protocolId;packetId", "f_185761_", "f_185762_", "f_185763_"}, this);
        }

        @Override
        public final int hashCode() {
            return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{PacketIdentification.class, "direction;protocolId;packetId", "f_185761_", "f_185762_", "f_185763_"}, this);
        }

        @Override
        public final boolean equals(Object p_185783_) {
            return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{PacketIdentification.class, "direction;protocolId;packetId", "f_185761_", "f_185762_", "f_185763_"}, this, p_185783_);
        }

        static {
            ImmutableMap.Builder $$0 = ImmutableMap.builder();
            for (ConnectionProtocol $$1 : ConnectionProtocol.values()) {
                for (PacketFlow $$2 : PacketFlow.values()) {
                    Int2ObjectMap<Class<? extends Packet<?>>> $$3 = $$1.m_195620_($$2);
                    $$3.forEach((p_185775_, p_185776_) -> $$0.put((Object)new PacketIdentification($$2, $$1.m_129582_(), (int)p_185775_), (Object)p_185776_.getSimpleName()));
                }
            }
            f_185764_ = $$0.build();
        }
    }
}

