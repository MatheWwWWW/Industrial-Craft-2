/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerGamePacketListener;

public class ServerboundAcceptTeleportationPacket
implements Packet<ServerGamePacketListener> {
    private final int f_133785_;

    public ServerboundAcceptTeleportationPacket(int p_133788_) {
        this.f_133785_ = p_133788_;
    }

    public ServerboundAcceptTeleportationPacket(FriendlyByteBuf p_179538_) {
        this.f_133785_ = p_179538_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133797_) {
        p_133797_.m_130130_(this.f_133785_);
    }

    @Override
    public void m_5797_(ServerGamePacketListener p_133794_) {
        p_133794_.m_7376_(this);
    }

    public int m_133795_() {
        return this.f_133785_;
    }
}

