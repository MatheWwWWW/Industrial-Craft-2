/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.login;

import java.security.PublicKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;

public class ClientboundHelloPacket
implements Packet<ClientLoginPacketListener> {
    private final String f_134777_;
    private final byte[] f_134778_;
    private final byte[] f_134779_;

    public ClientboundHelloPacket(String p_134782_, byte[] p_134783_, byte[] p_134784_) {
        this.f_134777_ = p_134782_;
        this.f_134778_ = p_134783_;
        this.f_134779_ = p_134784_;
    }

    public ClientboundHelloPacket(FriendlyByteBuf p_179816_) {
        this.f_134777_ = p_179816_.m_130136_(20);
        this.f_134778_ = p_179816_.m_130052_();
        this.f_134779_ = p_179816_.m_130052_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134793_) {
        p_134793_.m_130070_(this.f_134777_);
        p_134793_.m_130087_(this.f_134778_);
        p_134793_.m_130087_(this.f_134779_);
    }

    @Override
    public void m_5797_(ClientLoginPacketListener p_134790_) {
        p_134790_.m_7318_(this);
    }

    public String m_134791_() {
        return this.f_134777_;
    }

    public PublicKey m_134794_() throws CryptException {
        return Crypt.m_13600_(this.f_134778_);
    }

    public byte[] m_134795_() {
        return this.f_134779_;
    }
}

