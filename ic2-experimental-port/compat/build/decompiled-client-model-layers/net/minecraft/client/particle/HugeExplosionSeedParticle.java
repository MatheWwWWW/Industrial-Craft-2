/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class HugeExplosionSeedParticle
extends NoRenderParticle {
    private int f_106944_;
    private final int f_106945_;

    HugeExplosionSeedParticle(ClientLevel p_106947_, double p_106948_, double p_106949_, double p_106950_) {
        super(p_106947_, p_106948_, p_106949_, p_106950_, 0.0, 0.0, 0.0);
        this.f_106945_ = 8;
    }

    @Override
    public void m_5989_() {
        for (int $$0 = 0; $$0 < 6; ++$$0) {
            double $$1 = this.f_107212_ + (this.f_107223_.m_188500_() - this.f_107223_.m_188500_()) * 4.0;
            double $$2 = this.f_107213_ + (this.f_107223_.m_188500_() - this.f_107223_.m_188500_()) * 4.0;
            double $$3 = this.f_107214_ + (this.f_107223_.m_188500_() - this.f_107223_.m_188500_()) * 4.0;
            this.f_107208_.m_7106_(ParticleTypes.f_123813_, $$1, $$2, $$3, (float)this.f_106944_ / (float)this.f_106945_, 0.0, 0.0);
        }
        ++this.f_106944_;
        if (this.f_106944_ == this.f_106945_) {
            this.m_107274_();
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        @Override
        public Particle m_6966_(SimpleParticleType p_106969_, ClientLevel p_106970_, double p_106971_, double p_106972_, double p_106973_, double p_106974_, double p_106975_, double p_106976_) {
            return new HugeExplosionSeedParticle(p_106970_, p_106971_, p_106972_, p_106973_);
        }
    }
}

