/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundPongPacket
implements Packet<ServerGamePacketListener> {
    private final int f_179721_;

    public ServerboundPongPacket(int p_179723_) {
        this.f_179721_ = p_179723_;
    }

    public ServerboundPongPacket(FriendlyByteBuf p_179725_) {
        this.f_179721_ = p_179725_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179727_) {
        p_179727_.writeInt(this.f_179721_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_179731_) {
        p_179731_.m_142110_(this);
    }

    public int m_179732_() {
        return this.f_179721_;
    }
}

