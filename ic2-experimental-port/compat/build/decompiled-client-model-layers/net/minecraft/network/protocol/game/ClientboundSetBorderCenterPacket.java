/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderCenterPacket
implements Packet<ClientGamePacketListener> {
    private final double f_179211_;
    private final double f_179212_;

    public ClientboundSetBorderCenterPacket(WorldBorder p_179214_) {
        this.f_179211_ = p_179214_.m_6347_();
        this.f_179212_ = p_179214_.m_6345_();
    }

    public ClientboundSetBorderCenterPacket(FriendlyByteBuf p_179216_) {
        this.f_179211_ = p_179216_.readDouble();
        this.f_179212_ = p_179216_.readDouble();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179218_) {
        p_179218_.writeDouble(this.f_179211_);
        p_179218_.writeDouble(this.f_179212_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179222_) {
        p_179222_.m_142612_(this);
    }

    public double m_179223_() {
        return this.f_179212_;
    }

    public double m_179224_() {
        return this.f_179211_;
    }
}

