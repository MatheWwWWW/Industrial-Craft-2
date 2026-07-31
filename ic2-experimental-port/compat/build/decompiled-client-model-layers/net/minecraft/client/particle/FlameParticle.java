/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.RisingParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class FlameParticle
extends RisingParticle {
    FlameParticle(ClientLevel p_106800_, double p_106801_, double p_106802_, double p_106803_, double p_106804_, double p_106805_, double p_106806_) {
        super(p_106800_, p_106801_, p_106802_, p_106803_, p_106804_, p_106805_, p_106806_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public void m_6257_(double p_106817_, double p_106818_, double p_106819_) {
        this.m_107259_(this.m_107277_().m_82386_(p_106817_, p_106818_, p_106819_));
        this.m_107275_();
    }

    @Override
    public float m_5902_(float p_106824_) {
        float $$1 = ((float)this.f_107224_ + p_106824_) / (float)this.f_107225_;
        return this.f_107663_ * (1.0f - $$1 * $$1 * 0.5f);
    }

    @Override
    public int m_6355_(float p_106821_) {
        float $$1 = ((float)this.f_107224_ + p_106821_) / (float)this.f_107225_;
        $$1 = Mth.m_14036_($$1, 0.0f, 1.0f);
        int $$2 = super.m_6355_(p_106821_);
        int $$3 = $$2 & 0xFF;
        int $$4 = $$2 >> 16 & 0xFF;
        if (($$3 += (int)($$1 * 15.0f * 16.0f)) > 240) {
            $$3 = 240;
        }
        return $$3 | $$4 << 16;
    }

    public static class SmallFlameProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_172111_;

        public SmallFlameProvider(SpriteSet p_172113_) {
            this.f_172111_ = p_172113_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172124_, ClientLevel p_172125_, double p_172126_, double p_172127_, double p_172128_, double p_172129_, double p_172130_, double p_172131_) {
            FlameParticle $$8 = new FlameParticle(p_172125_, p_172126_, p_172127_, p_172128_, p_172129_, p_172130_, p_172131_);
            $$8.m_108335_(this.f_172111_);
            $$8.m_6569_(0.5f);
            return $$8;
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106825_;

        public Provider(SpriteSet p_106827_) {
            this.f_106825_ = p_106827_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106838_, ClientLevel p_106839_, double p_106840_, double p_106841_, double p_106842_, double p_106843_, double p_106844_, double p_106845_) {
            FlameParticle $$8 = new FlameParticle(p_106839_, p_106840_, p_106841_, p_106842_, p_106843_, p_106844_, p_106845_);
            $$8.m_108335_(this.f_106825_);
            return $$8;
        }
    }
}

