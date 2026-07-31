/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundForgetLevelChunkPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132137_;
    private final int f_132138_;

    public ClientboundForgetLevelChunkPacket(int p_132141_, int p_132142_) {
        this.f_132137_ = p_132141_;
        this.f_132138_ = p_132142_;
    }

    public ClientboundForgetLevelChunkPacket(FriendlyByteBuf p_178858_) {
        this.f_132137_ = p_178858_.readInt();
        this.f_132138_ = p_178858_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132151_) {
        p_132151_.writeInt(this.f_132137_);
        p_132151_.writeInt(this.f_132138_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132148_) {
        p_132148_.m_5729_(this);
    }

    public int m_132149_() {
        return this.f_132137_;
    }

    public int m_132152_() {
        return this.f_132138_;
    }
}

