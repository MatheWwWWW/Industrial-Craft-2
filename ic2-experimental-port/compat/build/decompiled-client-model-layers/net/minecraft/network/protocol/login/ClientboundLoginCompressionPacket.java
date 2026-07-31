/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;

public class ClientboundLoginCompressionPacket
implements Packet<ClientLoginPacketListener> {
    private final int f_134796_;

    public ClientboundLoginCompressionPacket(int p_134799_) {
        this.f_134796_ = p_134799_;
    }

    public ClientboundLoginCompressionPacket(FriendlyByteBuf p_179818_) {
        this.f_134796_ = p_179818_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134808_) {
        p_134808_.m_130130_(this.f_134796_);
    }

    @Override
    public void m_5797_(ClientLoginPacketListener p_134805_) {
        p_134805_.m_5693_(this);
    }

    public int m_134806_() {
        return this.f_134796_;
    }
}

