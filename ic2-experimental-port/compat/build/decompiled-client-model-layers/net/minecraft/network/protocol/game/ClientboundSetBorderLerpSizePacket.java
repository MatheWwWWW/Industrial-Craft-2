/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderLerpSizePacket
implements Packet<ClientGamePacketListener> {
    private final double f_179225_;
    private final double f_179226_;
    private final long f_179227_;

    public ClientboundSetBorderLerpSizePacket(WorldBorder p_179229_) {
        this.f_179225_ = p_179229_.m_61959_();
        this.f_179226_ = p_179229_.m_61961_();
        this.f_179227_ = p_179229_.m_61960_();
    }

    public ClientboundSetBorderLerpSizePacket(FriendlyByteBuf p_179231_) {
        this.f_179225_ = p_179231_.readDouble();
        this.f_179226_ = p_179231_.readDouble();
        this.f_179227_ = p_179231_.m_130258_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179233_) {
        p_179233_.writeDouble(this.f_179225_);
        p_179233_.writeDouble(this.f_179226_);
        p_179233_.m_130103_(this.f_179227_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179237_) {
        p_179237_.m_142686_(this);
    }

    public double m_179238_() {
        return this.f_179225_;
    }

    public double m_179239_() {
        return this.f_179226_;
    }

    public long m_179240_() {
        return this.f_179227_;
    }
}

