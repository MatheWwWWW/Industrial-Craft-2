/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;

public class AttackSweepParticle
extends TextureSheetParticle {
    private final SpriteSet f_105544_;

    AttackSweepParticle(ClientLevel p_105546_, double p_105547_, double p_105548_, double p_105549_, double p_105550_, SpriteSet p_105551_) {
        super(p_105546_, p_105547_, p_105548_, p_105549_, 0.0, 0.0, 0.0);
        float $$6;
        this.f_105544_ = p_105551_;
        this.f_107225_ = 4;
        this.f_107227_ = $$6 = this.f_107223_.m_188501_() * 0.6f + 0.4f;
        this.f_107228_ = $$6;
        this.f_107229_ = $$6;
        this.f_107663_ = 1.0f - (float)p_105550_ * 0.5f;
        this.m_108339_(p_105551_);
    }

    @Override
    public int m_6355_(float p_105562_) {
        return 0xF000F0;
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
        this.m_108339_(this.f_105544_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107432_;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_105564_;

        public Provider(SpriteSet p_105566_) {
            this.f_105564_ = p_105566_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_105577_, ClientLevel p_105578_, double p_105579_, double p_105580_, double p_105581_, double p_105582_, double p_105583_, double p_105584_) {
            return new AttackSweepParticle(p_105578_, p_105579_, p_105580_, p_105581_, p_105582_, this.f_105564_);
        }
    }
}

