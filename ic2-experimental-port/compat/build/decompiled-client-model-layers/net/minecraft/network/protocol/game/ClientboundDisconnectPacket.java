/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundDisconnectPacket
implements Packet<ClientGamePacketListener> {
    private final Component f_132075_;

    public ClientboundDisconnectPacket(Component p_132078_) {
        this.f_132075_ = p_132078_;
    }

    public ClientboundDisconnectPacket(FriendlyByteBuf p_178841_) {
        this.f_132075_ = p_178841_.m_130238_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132087_) {
        p_132087_.m_130083_(this.f_132075_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132084_) {
        p_132084_.m_6008_(this);
    }

    public Component m_132085_() {
        return this.f_132075_;
    }
}

