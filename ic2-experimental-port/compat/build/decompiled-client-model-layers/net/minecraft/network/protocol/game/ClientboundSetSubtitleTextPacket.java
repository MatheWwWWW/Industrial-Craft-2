/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetSubtitleTextPacket
implements Packet<ClientGamePacketListener> {
    private final Component f_179374_;

    public ClientboundSetSubtitleTextPacket(Component p_179378_) {
        this.f_179374_ = p_179378_;
    }

    public ClientboundSetSubtitleTextPacket(FriendlyByteBuf p_179376_) {
        this.f_179374_ = p_179376_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179380_) {
        p_179380_.m_130083_(this.f_179374_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179384_) {
        p_179384_.m_141913_(this);
    }

    public Component m_179385_() {
        return this.f_179374_;
    }
}

