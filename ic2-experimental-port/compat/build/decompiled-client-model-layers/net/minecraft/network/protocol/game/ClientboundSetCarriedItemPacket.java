/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetCarriedItemPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133069_;

    public ClientboundSetCarriedItemPacket(int p_133072_) {
        this.f_133069_ = p_133072_;
    }

    public ClientboundSetCarriedItemPacket(FriendlyByteBuf p_179280_) {
        this.f_133069_ = p_179280_.readByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133081_) {
        p_133081_.writeByte(this.f_133069_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133078_) {
        p_133078_.m_5612_(this);
    }

    public int m_133079_() {
        return this.f_133069_;
    }
}

