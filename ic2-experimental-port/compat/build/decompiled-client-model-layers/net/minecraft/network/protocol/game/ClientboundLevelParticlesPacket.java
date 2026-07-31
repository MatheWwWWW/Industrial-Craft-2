/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network.protocol.game;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public class ClientboundLevelParticlesPacket
implements Packet<ClientGamePacketListener> {
    private final double f_132280_;
    private final double f_132281_;
    private final double f_132282_;
    private final float f_132283_;
    private final float f_132284_;
    private final float f_132285_;
    private final float f_132286_;
    private final int f_132287_;
    private final boolean f_132288_;
    private final ParticleOptions f_132289_;

    public <T extends ParticleOptions> ClientboundLevelParticlesPacket(T p_132292_, boolean p_132293_, double p_132294_, double p_132295_, double p_132296_, float p_132297_, float p_132298_, float p_132299_, float p_132300_, int p_132301_) {
        this.f_132289_ = p_132292_;
        this.f_132288_ = p_132293_;
        this.f_132280_ = p_132294_;
        this.f_132281_ = p_132295_;
        this.f_132282_ = p_132296_;
        this.f_132283_ = p_132297_;
        this.f_132284_ = p_132298_;
        this.f_132285_ = p_132299_;
        this.f_132286_ = p_132300_;
        this.f_132287_ = p_132301_;
    }

    public ClientboundLevelParticlesPacket(FriendlyByteBuf p_178910_) {
        ParticleType<?> $$1 = p_178910_.m_236816_(Registry.f_122829_);
        this.f_132288_ = p_178910_.readBoolean();
        this.f_132280_ = p_178910_.readDouble();
        this.f_132281_ = p_178910_.readDouble();
        this.f_132282_ = p_178910_.readDouble();
        this.f_132283_ = p_178910_.readFloat();
        this.f_132284_ = p_178910_.readFloat();
        this.f_132285_ = p_178910_.readFloat();
        this.f_132286_ = p_178910_.readFloat();
        this.f_132287_ = p_178910_.readInt();
        this.f_132289_ = this.m_132304_(p_178910_, $$1);
    }

    private <T extends ParticleOptions> T m_132304_(FriendlyByteBuf p_132305_, ParticleType<T> p_132306_) {
        return p_132306_.m_123743_().m_6507_(p_132306_, p_132305_);
    }

    @Override
    public void m_5779_(FriendlyByteBuf p_132313_) {
        p_132313_.m_236818_(Registry.f_122829_, this.f_132289_.m_6012_());
        p_132313_.writeBoolean(this.f_132288_);
        p_132313_.writeDouble(this.f_132280_);
        p_132313_.writeDouble(this.f_132281_);
        p_132313_.writeDouble(this.f_132282_);
        p_132313_.writeFloat(this.f_132283_);
        p_132313_.writeFloat(this.f_132284_);
        p_132313_.writeFloat(this.f_132285_);
        p_132313_.writeFloat(this.f_132286_);
        p_132313_.writeInt(this.f_132287_);
        this.f_132289_.m_7711_(p_132313_);
    }

    public boolean m_132311_() {
        return this.f_132288_;
    }

    public double m_132314_() {
        return this.f_132280_;
    }

    public double m_132315_() {
        return this.f_132281_;
    }

    public double m_132316_() {
        return this.f_132282_;
    }

    public float m_132317_() {
        return this.f_132283_;
    }

    public float m_132318_() {
        return this.f_132284_;
    }

    public float m_132319_() {
        return this.f_132285_;
    }

    public float m_132320_() {
        return this.f_132286_;
    }

    public int m_132321_() {
        return this.f_132287_;
    }

    public ParticleOptions m_132322_() {
        return this.f_132289_;
    }

    @Override
    public void m_5797_(ClientGamePacketListener p_132310_) {
        p_132310_.m_7406_(this);
    }
}

