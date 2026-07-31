/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.network.protocol.game;

import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ClientboundEntityEventPacket
implements Packet<ClientGamePacketListener> {
    private final int f_132088_;
    private final byte f_132089_;

    public ClientboundEntityEventPacket(Entity p_132092_, byte p_132093_) {
        this.f_132088_ = p_132092_.m_19879_();
        this.f_132089_ = p_132093_;
    }

    public ClientboundEntityEventPacket(FriendlyByteBuf p_178843_) {
        this.f_132088_ = p_178843_.readInt();
        this.f_132089_ = p_178843_.readByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132104_) {
        p_132104_.writeInt(this.f_132088_);
        p_132104_.writeByte(this.f_132089_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132101_) {
        p_132101_.m_7628_(this);
    }

    @Nullable
    public Entity m_132094_(Level p_132095_) {
        return p_132095_.m_6815_(this.f_132088_);
    }

    public byte m_132102_() {
        return this.f_132089_;
    }
}

