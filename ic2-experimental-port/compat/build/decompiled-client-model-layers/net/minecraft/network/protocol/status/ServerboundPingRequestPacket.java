/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.status;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.status.ServerStatusPacketListener;

public class ServerboundPingRequestPacket
implements Packet<ServerStatusPacketListener> {
    private final long f_134988_;

    public ServerboundPingRequestPacket(long p_134991_) {
        this.f_134988_ = p_134991_;
    }

    public ServerboundPingRequestPacket(FriendlyByteBuf p_179838_) {
        this.f_134988_ = p_179838_.readLong();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_135000_) {
        p_135000_.writeLong(this.f_134988_);
    }

    @Override
    public void m_5797_(ServerStatusPacketListener p_134997_) {
        p_134997_.m_7883_(this);
    }

    public long m_134998_() {
        return this.f_134988_;
    }
}

