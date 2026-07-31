/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ClientboundSetEntityMotionPacket
implements Packet<ClientGamePacketListener> {
    private final int f_133176_;
    private final int f_133177_;
    private final int f_133178_;
    private final int f_133179_;

    public ClientboundSetEntityMotionPacket(Entity p_133185_) {
        this(p_133185_.m_19879_(), p_133185_.m_20184_());
    }

    public ClientboundSetEntityMotionPacket(int p_133182_, Vec3 p_133183_) {
        this.f_133176_ = p_133182_;
        double $$2 = 3.9;
        double $$3 = Mth.m_14008_(p_133183_.f_82479_, -3.9, 3.9);
        double $$4 = Mth.m_14008_(p_133183_.f_82480_, -3.9, 3.9);
        double $$5 = Mth.m_14008_(p_133183_.f_82481_, -3.9, 3.9);
        this.f_133177_ = (int)($$3 * 8000.0);
        this.f_133178_ = (int)($$4 * 8000.0);
        this.f_133179_ = (int)($$5 * 8000.0);
    }

    public ClientboundSetEntityMotionPacket(FriendlyByteBuf p_179294_) {
        this.f_133176_ = p_179294_.m_130242_();
        this.f_133177_ = p_179294_.readShort();
        this.f_133178_ = p_179294_.readShort();
        this.f_133179_ = p_179294_.readShort();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_133194_) {
        p_133194_.m_130130_(this.f_133176_);
        p_133194_.writeShort(this.f_133177_);
        p_133194_.writeShort(this.f_133178_);
        p_133194_.writeShort(this.f_133179_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_133191_) {
        p_133191_.m_8048_(this);
    }

    public int m_133192_() {
        return this.f_133176_;
    }

    public int m_133195_() {
        return this.f_133177_;
    }

    public int m_133196_() {
        return this.f_133178_;
    }

    public int m_133197_() {
        return this.f_133179_;
    }
}

