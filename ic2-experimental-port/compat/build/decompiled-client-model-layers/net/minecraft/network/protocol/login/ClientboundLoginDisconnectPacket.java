/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;

public class ClientboundLoginDisconnectPacket
implements Packet<ClientLoginPacketListener> {
    private final Component f_134809_;

    public ClientboundLoginDisconnectPacket(Component p_134812_) {
        this.f_134809_ = p_134812_;
    }

    public ClientboundLoginDisconnectPacket(FriendlyByteBuf p_179820_) {
        this.f_134809_ = Component.Serializer.m_130714_(p_179820_.m_130136_(262144));
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134821_) {
        p_134821_.m_130083_(this.f_134809_);
    }

    @Override
    public void m_5797_(ClientLoginPacketListener p_134818_) {
        p_134818_.m_5800_(this);
    }

    public Component m_134819_() {
        return this.f_134809_;
    }
}

