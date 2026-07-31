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

public class BubblePopParticle
extends TextureSheetParticle {
    private final SpriteSet f_105812_;

    BubblePopParticle(ClientLevel p_105814_, double p_105815_, double p_105816_, double p_105817_, double p_105818_, double p_105819_, double p_105820_, SpriteSet p_105821_) {
        super(p_105814_, p_105815_, p_105816_, p_105817_);
        this.f_105812_ = p_105821_;
        this.f_107225_ = 4;
        this.f_107226_ = 0.008f;
        this.f_107215_ = p_105818_;
        this.f_107216_ = p_105819_;
        this.f_107217_ = p_105820_;
        this.m_108339_(p_105821_);
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
        this.f_107216_ -= (double)this.f_107226_;
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        this.m_108339_(this.f_105812_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_105834_;

        public Provider(SpriteSet p_105836_) {
            this.f_105834_ = p_105836_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_105847_, ClientLevel p_105848_, double p_105849_, double p_105850_, double p_105851_, double p_105852_, double p_105853_, double p_105854_) {
            return new BubblePopParticle(p_105848_, p_105849_, p_105850_, p_105851_, p_105852_, p_105853_, p_105854_, this.f_105834_);
        }
    }
}

