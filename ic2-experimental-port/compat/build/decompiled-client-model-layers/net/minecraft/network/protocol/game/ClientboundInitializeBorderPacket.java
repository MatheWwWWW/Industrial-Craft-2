/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.level.border.WorldBorder;

public class ClientboundInitializeBorderPacket
implements Packet<ClientGamePacketListener> {
    private final double f_178868_;
    private final double f_178869_;
    private final double f_178870_;
    private final double f_178871_;
    private final long f_178872_;
    private final int f_178873_;
    private final int f_178874_;
    private final int f_178875_;

    public ClientboundInitializeBorderPacket(FriendlyByteBuf p_178879_) {
        this.f_178868_ = p_178879_.readDouble();
        this.f_178869_ = p_178879_.readDouble();
        this.f_178870_ = p_178879_.readDouble();
        this.f_178871_ = p_178879_.readDouble();
        this.f_178872_ = p_178879_.m_130258_();
        this.f_178873_ = p_178879_.m_130242_();
        this.f_178874_ = p_178879_.m_130242_();
        this.f_178875_ = p_178879_.m_130242_();
    }

    public ClientboundInitializeBorderPacket(WorldBorder p_178877_) {
        this.f_178868_ = p_178877_.m_6347_();
        this.f_178869_ = p_178877_.m_6345_();
        this.f_178870_ = p_178877_.m_61959_();
        this.f_178871_ = p_178877_.m_61961_();
        this.f_178872_ = p_178877_.m_61960_();
        this.f_178873_ = p_178877_.m_61963_();
        this.f_178874_ = p_178877_.m_61968_();
        this.f_178875_ = p_178877_.m_61967_();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_178881_) {
        p_178881_.writeDouble(this.f_178868_);
        p_178881_.writeDouble(this.f_178869_);
        p_178881_.writeDouble(this.f_178870_);
        p_178881_.writeDouble(this.f_178871_);
        p_178881_.m_130103_(this.f_178872_);
        p_178881_.m_130130_(this.f_178873_);
        p_178881_.m_130130_(this.f_178874_);
        p_178881_.m_130130_(this.f_178875_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_178885_) {
        p_178885_.m_142237_(this);
    }

    public double m_178886_() {
        return this.f_178868_;
    }

    public double m_178887_() {
        return this.f_178869_;
    }

    public double m_178888_() {
        return this.f_178871_;
    }

    public double m_178889_() {
        return this.f_178870_;
    }

    public long m_178890_() {
        return this.f_178872_;
    }

    public int m_178891_() {
        return this.f_178873_;
    }

    public int m_178892_() {
        return this.f_178875_;
    }

    public int m_178893_() {
        return this.f_178874_;
    }
}

