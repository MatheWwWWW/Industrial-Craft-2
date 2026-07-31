/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;

public class ClientboundAnimatePacket
implements Packet<ClientGamePacketListener> {
    public static final int f_178583_ = 0;
    public static final int f_178584_ = 1;
    public static final int f_178585_ = 2;
    public static final int f_178586_ = 3;
    public static final int f_178587_ = 4;
    public static final int f_178588_ = 5;
    private final int f_131612_;
    private final int f_131613_;

    public ClientboundAnimatePacket(Entity p_131616_, int p_131617_) {
        this.f_131612_ = p_131616_.m_19879_();
        this.f_131613_ = p_131617_;
    }

    public ClientboundAnimatePacket(FriendlyByteBuf p_178590_) {
        this.f_131612_ = p_178590_.m_130242_();
        this.f_131613_ = p_178590_.readUnsignedByte();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131626_) {
        p_131626_.m_130130_(this.f_131612_);
        p_131626_.writeByte(this.f_131613_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131623_) {
        p_131623_.m_7791_(this);
    }

    public int m_131624_() {
        return this.f_131612_;
    }

    public int m_131627_() {
        return this.f_131613_;
    }
}

