/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.world.InteractionHand;

public class ServerboundSwingPacket
implements Packet<ServerGamePacketListener> {
    private final InteractionHand f_134664_;

    public ServerboundSwingPacket(InteractionHand p_134667_) {
        this.f_134664_ = p_134667_;
    }

    public ServerboundSwingPacket(FriendlyByteBuf p_179792_) {
        this.f_134664_ = p_179792_.m_130066_(InteractionHand.class);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_134676_) {
        p_134676_.m_130068_(this.f_134664_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_134673_) {
        p_134673_.m_7953_(this);
    }

    public InteractionHand m_134674_() {
        return this.f_134664_;
    }
}

