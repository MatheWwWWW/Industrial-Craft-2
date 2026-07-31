/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundContainerClosePacket
implements Packet<ServerGamePacketListener> {
    private final int f_133967_;

    public ServerboundContainerClosePacket(int p_133970_) {
        this.f_133967_ = p_133970_;
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133976_) {
        p_133976_.m_7951_(this);
    }

    public ServerboundContainerClosePacket(FriendlyByteBuf p_179584_) {
        this.f_133967_ = p_179584_.readByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133978_) {
        p_133978_.writeByte(this.f_133967_);
    }

    public int m_179585_() {
        return this.f_133967_;
    }
}

