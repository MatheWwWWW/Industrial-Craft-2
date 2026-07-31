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
import net.minecraft.util.Mth;

public class HeartParticle
extends TextureSheetParticle {
    HeartParticle(ClientLevel p_106847_, double p_106848_, double p_106849_, double p_106850_) {
        super(p_106847_, p_106848_, p_106849_, p_106850_, 0.0, 0.0, 0.0);
        this.f_172259_ = true;
        this.f_172258_ = 0.86f;
        this.f_107215_ *= (double)0.01f;
        this.f_107216_ *= (double)0.01f;
        this.f_107217_ *= (double)0.01f;
        this.f_107216_ += 0.1;
        this.f_107663_ *= 1.5f;
        this.f_107225_ = 16;
        this.f_107219_ = false;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public float m_5902_(float p_106860_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_106860_) / (float)this.f_107225_ * 32.0f, 0.0f, 1.0f);
    }

    public static class AngryVillagerProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106861_;

        public AngryVillagerProvider(SpriteSet p_106863_) {
            this.f_106861_ = p_106863_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106874_, ClientLevel p_106875_, double p_106876_, double p_106877_, double p_106878_, double p_106879_, double p_106880_, double p_106881_) {
            HeartParticle $$8 = new HeartParticle(p_106875_, p_106876_, p_106877_ + 0.5, p_106878_);
            $$8.m_108335_(this.f_106861_);
            $$8.m_107253_(1.0f, 1.0f, 1.0f);
            return $$8;
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106882_;

        public Provider(SpriteSet p_106884_) {
            this.f_106882_ = p_106884_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106895_, ClientLevel p_106896_, double p_106897_, double p_106898_, double p_106899_, double p_106900_, double p_106901_, double p_106902_) {
            HeartParticle $$8 = new HeartParticle(p_106896_, p_106897_, p_106898_, p_106899_);
            $$8.m_108335_(this.f_106882_);
            return $$8;
        }
    }
}

