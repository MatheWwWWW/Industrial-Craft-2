/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 */
package net.minecraft.network.protocol.game;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;

public class ClientboundAwardStatsPacket
implements Packet<ClientGamePacketListener> {
    private final Object2IntMap<Stat<?>> f_131628_;

    public ClientboundAwardStatsPacket(Object2IntMap<Stat<?>> p_131631_) {
        this.f_131628_ = p_131631_;
    }

    public ClientboundAwardStatsPacket(FriendlyByteBuf p_178592_) {
        this.f_131628_ = (Object2IntMap)p_178592_.m_236841_(Object2IntOpenHashMap::new, p_237577_ -> {
            StatType<?> $$2 = p_237577_.m_236816_(Registry.f_122867_);
            return ClientboundAwardStatsPacket.m_237572_(p_178592_, $$2);
        }, FriendlyByteBuf::m_130242_);
    }

    private static <T> Stat<T> m_237572_(FriendlyByteBuf p_237573_, StatType<T> p_237574_) {
        return p_237574_.m_12902_(p_237573_.m_236816_(p_237574_.m_12893_()));
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131642_) {
        p_131642_.m_7271_(this);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131645_) {
        p_131645_.m_236831_(this.f_131628_, ClientboundAwardStatsPacket::m_237569_, FriendlyByteBuf::m_130130_);
    }

    private static <T> void m_237569_(FriendlyByteBuf p_237570_, Stat<T> p_237571_) {
        p_237570_.m_236818_(Registry.f_122867_, p_237571_.m_12859_());
        p_237570_.m_236818_(p_237571_.m_12859_().m_12893_(), p_237571_.m_12867_());
    }

    public Map<Stat<?>, Integer> m_131643_() {
        return this.f_131628_;
    }
}

