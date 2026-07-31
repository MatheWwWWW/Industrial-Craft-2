/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundSetCarriedItemPacket
implements Packet<ServerGamePacketListener> {
    private final int f_134488_;

    public ServerboundSetCarriedItemPacket(int p_134491_) {
        this.f_134488_ = p_134491_;
    }

    public ServerboundSetCarriedItemPacket(FriendlyByteBuf p_179751_) {
        this.f_134488_ = p_179751_.readShort();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134500_) {
        p_134500_.writeShort(this.f_134488_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134497_) {
        p_134497_.m_7798_(this);
    }

    public int m_134498_() {
        return this.f_134488_;
    }
}

