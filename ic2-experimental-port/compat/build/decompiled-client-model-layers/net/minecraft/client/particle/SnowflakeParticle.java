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

public class SnowflakeParticle
extends TextureSheetParticle {
    private final SpriteSet f_172290_;

    protected SnowflakeParticle(ClientLevel p_172292_, double p_172293_, double p_172294_, double p_172295_, double p_172296_, double p_172297_, double p_172298_, SpriteSet p_172299_) {
        super(p_172292_, p_172293_, p_172294_, p_172295_);
        this.f_107226_ = 0.225f;
        this.f_172258_ = 1.0f;
        this.f_172290_ = p_172299_;
        this.f_107215_ = p_172296_ + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.f_107216_ = p_172297_ + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.f_107217_ = p_172298_ + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.f_107663_ = 0.1f * (this.f_107223_.m_188501_() * this.f_107223_.m_188501_() * 1.0f + 1.0f);
        this.f_107225_ = (int)(16.0 / ((double)this.f_107223_.m_188501_() * 0.8 + 0.2)) + 2;
        this.m_108339_(p_172299_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_172290_);
        this.f_107215_ *= (double)0.95f;
        this.f_107216_ *= (double)0.9f;
        this.f_107217_ *= (double)0.95f;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_172302_;

        public Provider(SpriteSet p_172304_) {
            this.f_172302_ = p_172304_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172315_, ClientLevel p_172316_, double p_172317_, double p_172318_, double p_172319_, double p_172320_, double p_172321_, double p_172322_) {
            SnowflakeParticle $$8 = new SnowflakeParticle(p_172316_, p_172317_, p_172318_, p_172319_, p_172320_, p_172321_, p_172322_, this.f_172302_);
            $$8.m_107253_(0.923f, 0.964f, 0.999f);
            return $$8;
        }
    }
}

