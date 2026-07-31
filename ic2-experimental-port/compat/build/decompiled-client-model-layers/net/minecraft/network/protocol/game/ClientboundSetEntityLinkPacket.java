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

public class ClientboundSetEntityLinkPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133160_;
    private final int f_133161_;

    public ClientboundSetEntityLinkPacket(Entity p_133164_, @Nullable Entity p_133165_) {
        this.f_133160_ = p_133164_.m_19879_();
        this.f_133161_ = p_133165_ != null ? p_133165_.m_19879_() : 0;
    }

    public ClientboundSetEntityLinkPacket(FriendlyByteBuf p_179292_) {
        this.f_133160_ = p_179292_.readInt();
        this.f_133161_ = p_179292_.readInt();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133174_) {
        p_133174_.writeInt(this.f_133160_);
        p_133174_.writeInt(this.f_133161_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133171_) {
        p_133171_.m_5599_(this);
    }

    public int m_133172_() {
        return this.f_133160_;
    }

    public int m_133175_() {
        return this.f_133161_;
    }
}

