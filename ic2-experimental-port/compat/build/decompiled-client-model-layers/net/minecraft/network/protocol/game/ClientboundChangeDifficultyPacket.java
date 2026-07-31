/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.Difficulty;

public class ClientboundChangeDifficultyPacket
implements Packet<ClientGamePacketListener> {
    private final Difficulty f_131805_;
    private final boolean f_131806_;

    public ClientboundChangeDifficultyPacket(Difficulty p_131809_, boolean p_131810_) {
        this.f_131805_ = p_131809_;
        this.f_131806_ = p_131810_;
    }

    public ClientboundChangeDifficultyPacket(FriendlyByteBuf p_178774_) {
        this.f_131805_ = Difficulty.m_19029_(p_178774_.readUnsignedByte());
        this.f_131806_ = p_178774_.readBoolean();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131819_) {
        p_131819_.writeByte(this.f_131805_.m_19028_());
        p_131819_.writeBoolean(this.f_131806_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131816_) {
        p_131816_.m_6664_(this);
    }

    public boolean m_131817_() {
        return this.f_131806_;
    }

    public Difficulty m_131820_() {
        return this.f_131805_;
    }
}

