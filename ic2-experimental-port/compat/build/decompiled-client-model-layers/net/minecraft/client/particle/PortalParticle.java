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

public class PortalParticle
extends TextureSheetParticle {
    private final double f_107548_;
    private final double f_107549_;
    private final double f_107547_;

    protected PortalParticle(ClientLevel p_107551_, double p_107552_, double p_107553_, double p_107554_, double p_107555_, double p_107556_, double p_107557_) {
        super(p_107551_, p_107552_, p_107553_, p_107554_);
        this.f_107215_ = p_107555_;
        this.f_107216_ = p_107556_;
        this.f_107217_ = p_107557_;
        this.f_107212_ = p_107552_;
        this.f_107213_ = p_107553_;
        this.f_107214_ = p_107554_;
        this.f_107548_ = this.f_107212_;
        this.f_107549_ = this.f_107213_;
        this.f_107547_ = this.f_107214_;
        this.f_107663_ = 0.1f * (this.f_107223_.m_188501_() * 0.2f + 0.5f);
        float $$7 = this.f_107223_.m_188501_() * 0.6f + 0.4f;
        this.f_107227_ = $$7 * 0.9f;
        this.f_107228_ = $$7 * 0.3f;
        this.f_107229_ = $$7;
        this.f_107225_ = (int)(Math.random() * 10.0) + 40;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public void m_6257_(double p_107560_, double p_107561_, double p_107562_) {
        this.m_107259_(this.m_107277_().m_82386_(p_107560_, p_107561_, p_107562_));
        this.m_107275_();
    }

    @Override
    public float m_5902_(float p_107567_) {
        float $$1 = ((float)this.f_107224_ + p_107567_) / (float)this.f_107225_;
        $$1 = 1.0f - $$1;
        $$1 *= $$1;
        $$1 = 1.0f - $$1;
        return this.f_107663_ * $$1;
    }

    @Override
    public int m_6355_(float p_107564_) {
        int $$1 = super.m_6355_(p_107564_);
        float $$2 = (float)this.f_107224_ / (float)this.f_107225_;
        $$2 *= $$2;
        $$2 *= $$2;
        int $$3 = $$1 & 0xFF;
        int $$4 = $$1 >> 16 & 0xFF;
        if (($$4 += (int)($$2 * 15.0f * 16.0f)) > 240) {
            $$4 = 240;
        }
        return $$3 | $$4 << 16;
    }

    @Override
    public void m_5989_() {
        float $$0;
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
            return;
        }
        float $$1 = $$0 = (float)this.f_107224_ / (float)this.f_107225_;
        $$0 = -$$0 + $$0 * $$0 * 2.0f;
        $$0 = 1.0f - $$0;
        this.f_107212_ = this.f_107548_ + this.f_107215_ * (double)$$0;
        this.f_107213_ = this.f_107549_ + this.f_107216_ * (double)$$0 + (double)(1.0f - $$1);
        this.f_107214_ = this.f_107547_ + this.f_107217_ * (double)$$0;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107568_;

        public Provider(SpriteSet p_107570_) {
            this.f_107568_ = p_107570_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107581_, ClientLevel p_107582_, double p_107583_, double p_107584_, double p_107585_, double p_107586_, double p_107587_, double p_107588_) {
            PortalParticle $$8 = new PortalParticle(p_107582_, p_107583_, p_107584_, p_107585_, p_107586_, p_107587_, p_107588_);
            $$8.m_108335_(this.f_107568_);
            return $$8;
        }
    }
}

