/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundTabListPacket
implements Packet<ClientGamePacketListener> {
    private final Component f_133480_;
    private final Component f_133481_;

    public ClientboundTabListPacket(Component p_179430_, Component p_179431_) {
        this.f_133480_ = p_179430_;
        this.f_133481_ = p_179431_;
    }

    public ClientboundTabListPacket(FriendlyByteBuf p_179428_) {
        this.f_133480_ = p_179428_.m_130238_();
        this.f_133481_ = p_179428_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133491_) {
        p_133491_.m_130083_(this.f_133480_);
        p_133491_.m_130083_(this.f_133481_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133488_) {
        p_133488_.m_6235_(this);
    }

    public Component m_133489_() {
        return this.f_133480_;
    }

    public Component m_133492_() {
        return this.f_133481_;
    }
}

