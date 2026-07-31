/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;

public class ClientboundMoveVehiclePacket
implements Packet<ClientGamePacketListener> {
    private final double f_132577_;
    private final double f_132578_;
    private final double f_132579_;
    private final float f_132580_;
    private final float f_132581_;

    public ClientboundMoveVehiclePacket(Entity p_132584_) {
        this.f_132577_ = p_132584_.m_20185_();
        this.f_132578_ = p_132584_.m_20186_();
        this.f_132579_ = p_132584_.m_20189_();
        this.f_132580_ = p_132584_.m_146908_();
        this.f_132581_ = p_132584_.m_146909_();
    }

    public ClientboundMoveVehiclePacket(FriendlyByteBuf p_179007_) {
        this.f_132577_ = p_179007_.readDouble();
        this.f_132578_ = p_179007_.readDouble();
        this.f_132579_ = p_179007_.readDouble();
        this.f_132580_ = p_179007_.readFloat();
        this.f_132581_ = p_179007_.readFloat();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132593_) {
        p_132593_.writeDouble(this.f_132577_);
        p_132593_.writeDouble(this.f_132578_);
        p_132593_.writeDouble(this.f_132579_);
        p_132593_.writeFloat(this.f_132580_);
        p_132593_.writeFloat(this.f_132581_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132590_) {
        p_132590_.m_7410_(this);
    }

    public double m_132591_() {
        return this.f_132577_;
    }

    public double m_132594_() {
        return this.f_132578_;
    }

    public double m_132595_() {
        return this.f_132579_;
    }

    public float m_132596_() {
        return this.f_132580_;
    }

    public float m_132597_() {
        return this.f_132581_;
    }
}

