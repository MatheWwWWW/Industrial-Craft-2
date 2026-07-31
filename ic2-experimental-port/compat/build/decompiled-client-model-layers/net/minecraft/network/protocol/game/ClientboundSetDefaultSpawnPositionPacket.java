/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundSetDefaultSpawnPositionPacket
implements Packet<ClientGamePacketListener> {
    private final BlockPos f_133111_;
    private final float f_133112_;

    public ClientboundSetDefaultSpawnPositionPacket(BlockPos p_133115_, float p_133116_) {
        this.f_133111_ = p_133115_;
        this.f_133112_ = p_133116_;
    }

    public ClientboundSetDefaultSpawnPositionPacket(FriendlyByteBuf p_179286_) {
        this.f_133111_ = p_179286_.m_130135_();
        this.f_133112_ = p_179286_.readFloat();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133125_) {
        p_133125_.m_130064_(this.f_133111_);
        p_133125_.writeFloat(this.f_133112_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133122_) {
        p_133122_.m_6571_(this);
    }

    public BlockPos m_133123_() {
        return this.f_133111_;
    }

    public float m_133126_() {
        return this.f_133112_;
    }
}

