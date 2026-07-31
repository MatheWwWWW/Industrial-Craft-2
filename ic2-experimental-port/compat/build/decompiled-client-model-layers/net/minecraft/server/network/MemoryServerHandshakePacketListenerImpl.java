/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.network;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;

public class MemoryServerHandshakePacketListenerImpl
implements ServerHandshakePacketListener {
    private final MinecraftServer f_9688_;
    private final Connection f_9689_;

    public MemoryServerHandshakePacketListenerImpl(MinecraftServer p_9691_, Connection p_9692_) {
        this.f_9688_ = p_9691_;
        this.f_9689_ = p_9692_;
    }

    @Override
    public void m_7322_(ClientIntentionPacket p_9697_) {
        this.f_9689_.m_129498_(p_9697_.m_134735_());
        this.f_9689_.m_129505_(new ServerLoginPacketListenerImpl(this.f_9688_, this.f_9689_));
    }

    @Override
    public void m_7026_(Component p_9695_) {
    }

    @Override
    public Connection m_6198_() {
        return this.f_9689_;
    }
}

