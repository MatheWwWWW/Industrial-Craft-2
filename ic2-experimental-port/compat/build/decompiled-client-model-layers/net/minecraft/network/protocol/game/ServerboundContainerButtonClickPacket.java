/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundContainerButtonClickPacket
implements Packet<ServerGamePacketListener> {
    private final int f_133923_;
    private final int f_133924_;

    public ServerboundContainerButtonClickPacket(int p_133927_, int p_133928_) {
        this.f_133923_ = p_133927_;
        this.f_133924_ = p_133928_;
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133934_) {
        p_133934_.m_6557_(this);
    }

    public ServerboundContainerButtonClickPacket(FriendlyByteBuf p_179567_) {
        this.f_133923_ = p_179567_.readByte();
        this.f_133924_ = p_179567_.readByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133937_) {
        p_133937_.writeByte(this.f_133923_);
        p_133937_.writeByte(this.f_133924_);
    }

    public int m_133935_() {
        return this.f_133923_;
    }

    public int m_133938_() {
        return this.f_133924_;
    }
}

