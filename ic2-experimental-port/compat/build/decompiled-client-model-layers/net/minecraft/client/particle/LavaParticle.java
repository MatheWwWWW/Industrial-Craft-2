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
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class LavaParticle
extends TextureSheetParticle {
    LavaParticle(ClientLevel p_107074_, double p_107075_, double p_107076_, double p_107077_) {
        super(p_107074_, p_107075_, p_107076_, p_107077_, 0.0, 0.0, 0.0);
        this.f_107226_ = 0.75f;
        this.f_172258_ = 0.999f;
        this.f_107215_ *= (double)0.8f;
        this.f_107216_ *= (double)0.8f;
        this.f_107217_ *= (double)0.8f;
        this.f_107216_ = this.f_107223_.m_188501_() * 0.4f + 0.05f;
        this.f_107663_ *= this.f_107223_.m_188501_() * 2.0f + 0.2f;
        this.f_107225_ = (int)(16.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public int m_6355_(float p_107086_) {
        int $$1 = super.m_6355_(p_107086_);
        int $$2 = 240;
        int $$3 = $$1 >> 16 & 0xFF;
        return 0xF0 | $$3 << 16;
    }

    @Override
    public float m_5902_(float p_107089_) {
        float $$1 = ((float)this.f_107224_ + p_107089_) / (float)this.f_107225_;
        return this.f_107663_ * (1.0f - $$1 * $$1);
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        if (!this.f_107220_) {
            float $$0 = (float)this.f_107224_ / (float)this.f_107225_;
            if (this.f_107223_.m_188501_() > $$0) {
                this.f_107208_.m_7106_(ParticleTypes.f_123762_, this.f_107212_, this.f_107213_, this.f_107214_, this.f_107215_, this.f_107216_, this.f_107217_);
            }
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107090_;

        public Provider(SpriteSet p_107092_) {
            this.f_107090_ = p_107092_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107103_, ClientLevel p_107104_, double p_107105_, double p_107106_, double p_107107_, double p_107108_, double p_107109_, double p_107110_) {
            LavaParticle $$8 = new LavaParticle(p_107104_, p_107105_, p_107106_, p_107107_);
            $$8.m_108335_(this.f_107090_);
            return $$8;
        }
    }
}

