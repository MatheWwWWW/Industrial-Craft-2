/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 */
package net.minecraft.network.protocol.login;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.login.ClientLoginPacketListener;

public class ClientboundGameProfilePacket
implements Packet<ClientLoginPacketListener> {
    private final GameProfile f_134764_;

    public ClientboundGameProfilePacket(GameProfile p_134767_) {
        this.f_134764_ = p_134767_;
    }

    public ClientboundGameProfilePacket(FriendlyByteBuf p_179814_) {
        this.f_134764_ = p_179814_.m_236875_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134776_) {
        p_134776_.m_236803_(this.f_134764_);
    }

    @Override
    public void m_5797_(ClientLoginPacketListener p_134773_) {
        p_134773_.m_7056_(this);
    }

    public GameProfile m_134774_() {
        return this.f_134764_;
    }
}

