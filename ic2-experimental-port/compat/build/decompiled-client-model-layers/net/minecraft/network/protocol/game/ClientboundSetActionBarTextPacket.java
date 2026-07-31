/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetActionBarTextPacket
implements Packet<ClientGamePacketListener> {
    private final Component f_179199_;

    public ClientboundSetActionBarTextPacket(Component p_179203_) {
        this.f_179199_ = p_179203_;
    }

    public ClientboundSetActionBarTextPacket(FriendlyByteBuf p_179201_) {
        this.f_179199_ = p_179201_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_179205_) {
        p_179205_.m_130083_(this.f_179199_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_179209_) {
        p_179209_.m_142456_(this);
    }

    public Component m_179210_() {
        return this.f_179199_;
    }
}

