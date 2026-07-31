/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetTitleTextPacket
implements Packet<ClientGamePacketListener> {
    private final Component f_179388_;

    public ClientboundSetTitleTextPacket(Component p_179392_) {
        this.f_179388_ = p_179392_;
    }

    public ClientboundSetTitleTextPacket(FriendlyByteBuf p_179390_) {
        this.f_179388_ = p_179390_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179394_) {
        p_179394_.m_130083_(this.f_179388_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179398_) {
        p_179398_.m_142442_(this);
    }

    public Component m_179399_() {
        return this.f_179388_;
    }
}

