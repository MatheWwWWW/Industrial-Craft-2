/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class ReversePortalParticle
extends PortalParticle {
    ReversePortalParticle(ClientLevel p_107590_, double p_107591_, double p_107592_, double p_107593_, double p_107594_, double p_107595_, double p_107596_) {
        super(p_107590_, p_107591_, p_107592_, p_107593_, p_107594_, p_107595_, p_107596_);
        this.f_107663_ *= 1.5f;
        this.f_107225_ = (int)(Math.random() * 2.0) + 60;
    }

    @Override
    public float m_5902_(float p_107608_) {
        float $$1 = 1.0f - ((float)this.f_107224_ + p_107608_) / ((float)this.f_107225_ * 1.5f);
        return this.f_107663_ * $$1;
    }

    @Override
    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
            return;
        }
        float $$0 = (float)this.f_107224_ / (float)this.f_107225_;
        this.f_107212_ += this.f_107215_ * (double)$$0;
        this.f_107213_ += this.f_107216_ * (double)$$0;
        this.f_107214_ += this.f_107217_ * (double)$$0;
    }

    public static class ReversePortalProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107609_;

        public ReversePortalProvider(SpriteSet p_107611_) {
            this.f_107609_ = p_107611_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107622_, ClientLevel p_107623_, double p_107624_, double p_107625_, double p_107626_, double p_107627_, double p_107628_, double p_107629_) {
            ReversePortalParticle $$8 = new ReversePortalParticle(p_107623_, p_107624_, p_107625_, p_107626_, p_107627_, p_107628_, p_107629_);
            $$8.m_108335_(this.f_107609_);
            return $$8;
        }
    }
}

