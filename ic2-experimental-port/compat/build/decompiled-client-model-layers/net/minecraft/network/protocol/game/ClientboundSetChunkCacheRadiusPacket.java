/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetChunkCacheRadiusPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133098_;

    public ClientboundSetChunkCacheRadiusPacket(int p_133101_) {
        this.f_133098_ = p_133101_;
    }

    public ClientboundSetChunkCacheRadiusPacket(FriendlyByteBuf p_179284_) {
        this.f_133098_ = p_179284_.m_130242_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133110_) {
        p_133110_.m_130130_(this.f_133098_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133107_) {
        p_133107_.m_7299_(this);
    }

    public int m_133108_() {
        return this.f_133098_;
    }
}

