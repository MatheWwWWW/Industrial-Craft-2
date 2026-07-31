/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundSetBorderSizePacket
implements Packet<ClientGamePacketListener> {
    private final double f_179241_;

    public ClientboundSetBorderSizePacket(WorldBorder p_179243_) {
        this.f_179241_ = p_179243_.m_61961_();
    }

    public ClientboundSetBorderSizePacket(FriendlyByteBuf p_179245_) {
        this.f_179241_ = p_179245_.readDouble();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179247_) {
        p_179247_.writeDouble(this.f_179241_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179251_) {
        p_179251_.m_142238_(this);
    }

    public double m_179252_() {
        return this.f_179241_;
    }
}

