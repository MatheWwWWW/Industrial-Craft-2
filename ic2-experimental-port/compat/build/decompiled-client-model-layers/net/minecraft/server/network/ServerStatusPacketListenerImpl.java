/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.network;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.status.ClientboundPongResponsePacket;
import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatusPacketListener;
import net.minecraft.network.protocol.status.ServerboundPingRequestPacket;
import net.minecraft.network.protocol.status.ServerboundStatusRequestPacket;
import net.minecraft.server.MinecraftServer;

public class ServerStatusPacketListenerImpl
implements ServerStatusPacketListener {
    private static final Component f_10081_ = Component.m_237115_("multiplayer.status.request_handled");
    private final MinecraftServer f_10082_;
    private final Connection f_10083_;
    private boolean f_10084_;

    public ServerStatusPacketListenerImpl(MinecraftServer p_10087_, Connection p_10088_) {
        this.f_10082_ = p_10087_;
        this.f_10083_ = p_10088_;
    }

    @Override
    public void m_7026_(Component p_10091_) {
    }

    @Override
    public Connection m_6198_() {
        return this.f_10083_;
    }

    @Override
    public void m_6733_(ServerboundStatusRequestPacket p_10095_) {
        if (this.f_10084_) {
            this.f_10083_.m_129507_(f_10081_);
            return;
        }
        this.f_10084_ = true;
        this.f_10083_.m_129512_(new ClientboundStatusResponsePacket(this.f_10082_.m_129928_()));
    }

    @Override
    public void m_7883_(ServerboundPingRequestPacket p_10093_) {
        this.f_10083_.m_129512_(new ClientboundPongResponsePacket(p_10093_.m_134998_()));
        this.f_10083_.m_129507_(f_10081_);
    }
}

