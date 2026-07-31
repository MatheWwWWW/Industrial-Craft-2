/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundContainerClosePacket
implements Packet<ClientGamePacketListener> {
    private final int f_131930_;

    public ClientboundContainerClosePacket(int p_131933_) {
        this.f_131930_ = p_131933_;
    }

    public ClientboundContainerClosePacket(FriendlyByteBuf p_178820_) {
        this.f_131930_ = p_178820_.readUnsignedByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131941_) {
        p_131941_.writeByte(this.f_131930_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131939_) {
        p_131939_.m_7776_(this);
    }

    public int m_178821_() {
        return this.f_131930_;
    }
}

