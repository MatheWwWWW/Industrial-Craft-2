/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundSelectTradePacket
implements Packet<ServerGamePacketListener> {
    private final int f_134459_;

    public ServerboundSelectTradePacket(int p_134462_) {
        this.f_134459_ = p_134462_;
    }

    public ServerboundSelectTradePacket(FriendlyByteBuf p_179747_) {
        this.f_134459_ = p_179747_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134471_) {
        p_134471_.m_130130_(this.f_134459_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134468_) {
        p_134468_.m_6321_(this);
    }

    public int m_134469_() {
        return this.f_134459_;
    }
}

