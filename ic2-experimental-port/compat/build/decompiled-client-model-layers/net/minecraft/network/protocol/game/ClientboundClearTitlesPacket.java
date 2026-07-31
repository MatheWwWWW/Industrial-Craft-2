/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundClearTitlesPacket
implements Packet<ClientGamePacketListener> {
    private final boolean f_178777_;

    public ClientboundClearTitlesPacket(boolean p_178781_) {
        this.f_178777_ = p_178781_;
    }

    public ClientboundClearTitlesPacket(FriendlyByteBuf p_178779_) {
        this.f_178777_ = p_178779_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_178783_) {
        p_178783_.writeBoolean(this.f_178777_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_178787_) {
        p_178787_.m_142766_(this);
    }

    public boolean m_178788_() {
        return this.f_178777_;
    }
}

