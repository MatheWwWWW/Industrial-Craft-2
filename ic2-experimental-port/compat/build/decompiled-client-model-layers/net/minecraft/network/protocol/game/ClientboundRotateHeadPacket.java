/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundRotateHeadPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132963_;
    private final byte f_132964_;

    public ClientboundRotateHeadPacket(Entity p_132967_, byte p_132968_) {
        this.f_132963_ = p_132967_.m_19879_();
        this.f_132964_ = p_132968_;
    }

    public ClientboundRotateHeadPacket(FriendlyByteBuf p_179193_) {
        this.f_132963_ = p_179193_.m_130242_();
        this.f_132964_ = p_179193_.readByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132979_) {
        p_132979_.m_130130_(this.f_132963_);
        p_132979_.writeByte(this.f_132964_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132976_) {
        p_132976_.m_6176_(this);
    }

    public Entity m_132969_(Level p_132970_) {
        return p_132970_.m_6815_(this.f_132963_);
    }

    public byte m_132977_() {
        return this.f_132964_;
    }
}

