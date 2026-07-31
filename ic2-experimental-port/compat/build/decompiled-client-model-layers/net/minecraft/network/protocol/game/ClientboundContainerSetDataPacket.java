/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundContainerSetDataPacket
implements Packet<ClientGamePacketListener> {
    private final int f_131958_;
    private final int f_131959_;
    private final int f_131960_;

    public ClientboundContainerSetDataPacket(int p_131963_, int p_131964_, int p_131965_) {
        this.f_131958_ = p_131963_;
        this.f_131959_ = p_131964_;
        this.f_131960_ = p_131965_;
    }

    public ClientboundContainerSetDataPacket(FriendlyByteBuf p_178825_) {
        this.f_131958_ = p_178825_.readUnsignedByte();
        this.f_131959_ = p_178825_.readShort();
        this.f_131960_ = p_178825_.readShort();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131974_) {
        p_131974_.writeByte(this.f_131958_);
        p_131974_.writeShort(this.f_131959_);
        p_131974_.writeShort(this.f_131960_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131971_) {
        p_131971_.m_7257_(this);
    }

    public int m_131972_() {
        return this.f_131958_;
    }

    public int m_131975_() {
        return this.f_131959_;
    }

    public int m_131976_() {
        return this.f_131960_;
    }
}

