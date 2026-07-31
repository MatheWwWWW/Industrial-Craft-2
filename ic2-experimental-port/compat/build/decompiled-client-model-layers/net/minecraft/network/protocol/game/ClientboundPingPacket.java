/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundPingPacket
implements Packet<ClientGamePacketListener> {
    private final int f_179014_;

    public ClientboundPingPacket(int p_179016_) {
        this.f_179014_ = p_179016_;
    }

    public ClientboundPingPacket(FriendlyByteBuf p_179018_) {
        this.f_179014_ = p_179018_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179020_) {
        p_179020_.writeInt(this.f_179014_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179024_) {
        p_179024_.m_141955_(this);
    }

    public int m_179025_() {
        return this.f_179014_;
    }
}

