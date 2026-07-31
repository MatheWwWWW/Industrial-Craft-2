/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundPickItemPacket
implements Packet<ServerGamePacketListener> {
    private final int f_134222_;

    public ServerboundPickItemPacket(int p_134225_) {
        this.f_134222_ = p_134225_;
    }

    public ServerboundPickItemPacket(FriendlyByteBuf p_179704_) {
        this.f_134222_ = p_179704_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134234_) {
        p_134234_.m_130130_(this.f_134222_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134231_) {
        p_134231_.m_7965_(this);
    }

    public int m_134232_() {
        return this.f_134222_;
    }
}

