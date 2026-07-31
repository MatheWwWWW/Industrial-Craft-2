/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.Difficulty;

public class ServerboundChangeDifficultyPacket
implements Packet<ServerGamePacketListener> {
    private final Difficulty f_133814_;

    public ServerboundChangeDifficultyPacket(Difficulty p_133817_) {
        this.f_133814_ = p_133817_;
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133823_) {
        p_133823_.m_7477_(this);
    }

    public ServerboundChangeDifficultyPacket(FriendlyByteBuf p_179542_) {
        this.f_133814_ = Difficulty.m_19029_(p_179542_.readUnsignedByte());
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133826_) {
        p_133826_.writeByte(this.f_133814_.m_19028_());
    }

    public Difficulty m_133824_() {
        return this.f_133814_;
    }
}

