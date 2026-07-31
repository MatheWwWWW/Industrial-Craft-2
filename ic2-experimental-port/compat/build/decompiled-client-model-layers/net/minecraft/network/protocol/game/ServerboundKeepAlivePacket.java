/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundKeepAlivePacket
implements Packet<ServerGamePacketListener> {
    private final long f_134092_;

    public ServerboundKeepAlivePacket(long p_134095_) {
        this.f_134092_ = p_134095_;
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134101_) {
        p_134101_.m_5683_(this);
    }

    public ServerboundKeepAlivePacket(FriendlyByteBuf p_179671_) {
        this.f_134092_ = p_179671_.readLong();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134104_) {
        p_134104_.writeLong(this.f_134092_);
    }

    public long m_134102_() {
        return this.f_134092_;
    }
}

