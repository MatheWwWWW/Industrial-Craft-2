/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.ExperienceOrb;

public class ClientboundAddExperienceOrbPacket
implements Packet<ClientGamePacketListener> {
    private final int f_131510_;
    private final double f_131511_;
    private final double f_131512_;
    private final double f_131513_;
    private final int f_131514_;

    public ClientboundAddExperienceOrbPacket(ExperienceOrb p_131517_) {
        this.f_131510_ = p_131517_.m_19879_();
        this.f_131511_ = p_131517_.m_20185_();
        this.f_131512_ = p_131517_.m_20186_();
        this.f_131513_ = p_131517_.m_20189_();
        this.f_131514_ = p_131517_.m_20801_();
    }

    public ClientboundAddExperienceOrbPacket(FriendlyByteBuf p_178564_) {
        this.f_131510_ = p_178564_.m_130242_();
        this.f_131511_ = p_178564_.readDouble();
        this.f_131512_ = p_178564_.readDouble();
        this.f_131513_ = p_178564_.readDouble();
        this.f_131514_ = p_178564_.readShort();
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_131526_) {
        p_131526_.m_130130_(this.f_131510_);
        p_131526_.writeDouble(this.f_131511_);
        p_131526_.writeDouble(this.f_131512_);
        p_131526_.writeDouble(this.f_131513_);
        p_131526_.writeShort(this.f_131514_);
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_131523_) {
        p_131523_.m_7708_(this);
    }

    public int m_131524_() {
        return this.f_131510_;
    }

    public double m_131527_() {
        return this.f_131511_;
    }

    public double m_131528_() {
        return this.f_131512_;
    }

    public double m_131529_() {
        return this.f_131513_;
    }

    public int m_131530_() {
        return this.f_131514_;
    }
}

