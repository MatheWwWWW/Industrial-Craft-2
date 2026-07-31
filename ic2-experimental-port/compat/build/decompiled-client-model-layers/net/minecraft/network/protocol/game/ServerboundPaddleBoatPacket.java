/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundPaddleBoatPacket
implements Packet<ServerGamePacketListener> {
    private final boolean f_134206_;
    private final boolean f_134207_;

    public ServerboundPaddleBoatPacket(boolean p_134210_, boolean p_134211_) {
        this.f_134206_ = p_134210_;
        this.f_134207_ = p_134211_;
    }

    public ServerboundPaddleBoatPacket(FriendlyByteBuf p_179702_) {
        this.f_134206_ = p_179702_.readBoolean();
        this.f_134207_ = p_179702_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134220_) {
        p_134220_.writeBoolean(this.f_134206_);
        p_134220_.writeBoolean(this.f_134207_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134217_) {
        p_134217_.m_5938_(this);
    }

    public boolean m_134218_() {
        return this.f_134206_;
    }

    public boolean m_134221_() {
        return this.f_134207_;
    }
}

