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

public class EnchantmentTableParticle
extends TextureSheetParticle {
    private final double f_106461_;
    private final double f_106462_;
    private final double f_106460_;

    EnchantmentTableParticle(ClientLevel p_106464_, double p_106465_, double p_106466_, double p_106467_, double p_106468_, double p_106469_, double p_106470_) {
        super(p_106464_, p_106465_, p_106466_, p_106467_);
        this.f_107215_ = p_106468_;
        this.f_107216_ = p_106469_;
        this.f_107217_ = p_106470_;
        this.f_106461_ = p_106465_;
        this.f_106462_ = p_106466_;
        this.f_106460_ = p_106467_;
        this.f_107209_ = p_106465_ + p_106468_;
        this.f_107210_ = p_106466_ + p_106469_;
        this.f_107211_ = p_106467_ + p_106470_;
        this.f_107212_ = this.f_107209_;
        this.f_107213_ = this.f_107210_;
        this.f_107214_ = this.f_107211_;
        this.f_107663_ = 0.1f * (this.f_107223_.m_188501_() * 0.5f + 0.2f);
        float $$7 = this.f_107223_.m_188501_() * 0.6f + 0.4f;
        this.f_107227_ = 0.9f * $$7;
        this.f_107228_ = 0.9f * $$7;
        this.f_107229_ = $$7;
        this.f_107219_ = false;
        this.f_107225_ = (int)(Math.random() * 10.0) + 30;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public void m_6257_(double p_106482_, double p_106483_, double p_106484_) {
        this.m_107259_(this.m_107277_().m_82386_(p_106482_, p_106483_, p_106484_));
        this.m_107275_();
    }

    @Override
    public int m_6355_(float p_106486_) {
        int $$1 = super.m_6355_(p_106486_);
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
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107224_++ >= this.f_107225_) {
            this.m_107274_();
            return;
        }
        float $$0 = (float)this.f_107224_ / (float)this.f_107225_;
        $$0 = 1.0f - $$0;
        float $$1 = 1.0f - $$0;
        $$1 *= $$1;
        $$1 *= $$1;
        this.f_107212_ = this.f_106461_ + this.f_107215_ * (double)$$0;
        this.f_107213_ = this.f_106462_ + this.f_107216_ * (double)$$0 - (double)($$1 * 1.2f);
        this.f_107214_ = this.f_106460_ + this.f_107217_ * (double)$$0;
    }

    public static class NautilusProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106488_;

        public NautilusProvider(SpriteSet p_106490_) {
            this.f_106488_ = p_106490_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106501_, ClientLevel p_106502_, double p_106503_, double p_106504_, double p_106505_, double p_106506_, double p_106507_, double p_106508_) {
            EnchantmentTableParticle $$8 = new EnchantmentTableParticle(p_106502_, p_106503_, p_106504_, p_106505_, p_106506_, p_106507_, p_106508_);
            $$8.m_108335_(this.f_106488_);
            return $$8;
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106509_;

        public Provider(SpriteSet p_106511_) {
            this.f_106509_ = p_106511_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106522_, ClientLevel p_106523_, double p_106524_, double p_106525_, double p_106526_, double p_106527_, double p_106528_, double p_106529_) {
            EnchantmentTableParticle $$8 = new EnchantmentTableParticle(p_106523_, p_106524_, p_106525_, p_106526_, p_106527_, p_106528_, p_106529_);
            $$8.m_108335_(this.f_106509_);
            return $$8;
        }
    }
}

