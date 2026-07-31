/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.network;

import net.minecraft.SharedConstants;
import net.minecraft.network.Connection;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.protocol.handshake.ServerHandshakePacketListener;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraft.server.network.ServerStatusPacketListenerImpl;

public class ServerHandshakePacketListenerImpl
implements ServerHandshakePacketListener {
    private static final Component f_9964_ = Component.m_237113_("Ignoring status request");
    private final MinecraftServer f_9965_;
    private final Connection f_9966_;

    public ServerHandshakePacketListenerImpl(MinecraftServer p_9969_, Connection p_9970_) {
        this.f_9965_ = p_9969_;
        this.f_9966_ = p_9970_;
    }

    @Override
    public void m_7322_(ClientIntentionPacket p_9975_) {
        switch (p_9975_.m_134735_()) {
            case LOGIN: {
                this.f_9966_.m_129498_(ConnectionProtocol.LOGIN);
                if (p_9975_.m_134738_() != SharedConstants.m_183709_().getProtocolVersion()) {
                    MutableComponent $$2;
                    if (p_9975_.m_134738_() < 754) {
                        MutableComponent $$1 = Component.m_237110_("multiplayer.disconnect.outdated_client", SharedConstants.m_183709_().getName());
                    } else {
                        $$2 = Component.m_237110_("multiplayer.disconnect.incompatible", SharedConstants.m_183709_().getName());
                    }
                    this.f_9966_.m_129512_(new ClientboundLoginDisconnectPacket($$2));
                    this.f_9966_.m_129507_($$2);
                    break;
                }
                this.f_9966_.m_129505_(new ServerLoginPacketListenerImpl(this.f_9965_, this.f_9966_));
                break;
            }
            case STATUS: {
                if (this.f_9965_.m_6373_()) {
                    this.f_9966_.m_129498_(ConnectionProtocol.STATUS);
                    this.f_9966_.m_129505_(new ServerStatusPacketListenerImpl(this.f_9965_, this.f_9966_));
                    break;
                }
                this.f_9966_.m_129507_(f_9964_);
                break;
            }
            default: {
                throw new UnsupportedOperationException("Invalid intention " + p_9975_.m_134735_());
            }
        }
    }

    @Override
    public void m_7026_(Component p_9973_) {
    }

    @Override
    public Connection m_6198_() {
        return this.f_9966_;
    }
}

