/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.InteractionHand;

public class ClientboundOpenBookPacket
implements Packet<ClientGamePacketListener> {
    private final InteractionHand f_132598_;

    public ClientboundOpenBookPacket(InteractionHand p_132601_) {
        this.f_132598_ = p_132601_;
    }

    public ClientboundOpenBookPacket(FriendlyByteBuf p_179009_) {
        this.f_132598_ = p_179009_.m_130066_(InteractionHand.class);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132610_) {
        p_132610_.m_130068_(this.f_132598_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132607_) {
        p_132607_.m_6503_(this);
    }

    public InteractionHand m_132608_() {
        return this.f_132598_;
    }
}

