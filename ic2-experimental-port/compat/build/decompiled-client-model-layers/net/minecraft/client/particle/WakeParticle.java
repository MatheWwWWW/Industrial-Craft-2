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

public class WakeParticle
extends TextureSheetParticle {
    private final SpriteSet f_108405_;

    WakeParticle(ClientLevel p_108407_, double p_108408_, double p_108409_, double p_108410_, double p_108411_, double p_108412_, double p_108413_, SpriteSet p_108414_) {
        super(p_108407_, p_108408_, p_108409_, p_108410_, 0.0, 0.0, 0.0);
        this.f_108405_ = p_108414_;
        this.f_107215_ *= (double)0.3f;
        this.f_107216_ = Math.random() * (double)0.2f + (double)0.1f;
        this.f_107217_ *= (double)0.3f;
        this.m_107250_(0.01f, 0.01f);
        this.f_107225_ = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.m_108339_(p_108414_);
        this.f_107226_ = 0.0f;
        this.f_107215_ = p_108411_;
        this.f_107216_ = p_108412_;
        this.f_107217_ = p_108413_;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        int $$0 = 60 - this.f_107225_;
        if (this.f_107225_-- <= 0) {
            this.m_107274_();
            return;
        }
        this.f_107216_ -= (double)this.f_107226_;
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        this.f_107215_ *= (double)0.98f;
        this.f_107216_ *= (double)0.98f;
        this.f_107217_ *= (double)0.98f;
        float $$1 = (float)$$0 * 0.001f;
        this.m_107250_($$1, $$1);
        this.m_108337_(this.f_108405_.m_5819_($$0 % 4, 4));
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_108427_;

        public Provider(SpriteSet p_108429_) {
            this.f_108427_ = p_108429_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108440_, ClientLevel p_108441_, double p_108442_, double p_108443_, double p_108444_, double p_108445_, double p_108446_, double p_108447_) {
            return new WakeParticle(p_108441_, p_108442_, p_108443_, p_108444_, p_108445_, p_108446_, p_108447_, this.f_108427_);
        }
    }
}

