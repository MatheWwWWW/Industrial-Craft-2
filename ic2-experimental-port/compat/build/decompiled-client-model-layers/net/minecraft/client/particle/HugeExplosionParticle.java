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

public class HugeExplosionParticle
extends TextureSheetParticle {
    private final SpriteSet f_106903_;

    protected HugeExplosionParticle(ClientLevel p_106905_, double p_106906_, double p_106907_, double p_106908_, double p_106909_, SpriteSet p_106910_) {
        super(p_106905_, p_106906_, p_106907_, p_106908_, 0.0, 0.0, 0.0);
        float $$6;
        this.f_107225_ = 6 + this.f_107223_.m_188503_(4);
        this.f_107227_ = $$6 = this.f_107223_.m_188501_() * 0.6f + 0.4f;
        this.f_107228_ = $$6;
        this.f_107229_ = $$6;
        this.f_107663_ = 2.0f * (1.0f - (float)p_106909_ * 0.5f);
        this.f_106903_ = p_106910_;
        this.m_108339_(p_106910_);
    }

    @Override
    public int m_6355_(float p_106921_) {
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
        this.m_108339_(this.f_106903_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107432_;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106923_;

        public Provider(SpriteSet p_106925_) {
            this.f_106923_ = p_106925_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106936_, ClientLevel p_106937_, double p_106938_, double p_106939_, double p_106940_, double p_106941_, double p_106942_, double p_106943_) {
            return new HugeExplosionParticle(p_106937_, p_106938_, p_106939_, p_106940_, p_106941_, this.f_106923_);
        }
    }
}

