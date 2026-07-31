/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.FastColor;

public class SquidInkParticle
extends SimpleAnimatedParticle {
    SquidInkParticle(ClientLevel p_172325_, double p_172326_, double p_172327_, double p_172328_, double p_172329_, double p_172330_, double p_172331_, int p_172332_, SpriteSet p_172333_) {
        super(p_172325_, p_172326_, p_172327_, p_172328_, p_172333_, 0.0f);
        this.f_172258_ = 0.92f;
        this.f_107663_ = 0.5f;
        this.m_107271_(1.0f);
        this.m_107253_(FastColor.ARGB32.m_13665_(p_172332_), FastColor.ARGB32.m_13667_(p_172332_), FastColor.ARGB32.m_13669_(p_172332_));
        this.f_107225_ = (int)((double)(this.f_107663_ * 12.0f) / (Math.random() * (double)0.8f + (double)0.2f));
        this.m_108339_(p_172333_);
        this.f_107219_ = false;
        this.f_107215_ = p_172329_;
        this.f_107216_ = p_172330_;
        this.f_107217_ = p_172331_;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        if (!this.f_107220_) {
            this.m_108339_(this.f_107644_);
            if (this.f_107224_ > this.f_107225_ / 2) {
                this.m_107271_(1.0f - ((float)this.f_107224_ - (float)(this.f_107225_ / 2)) / (float)this.f_107225_);
            }
            if (this.f_107208_.m_8055_(new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_)).m_60795_()) {
                this.f_107216_ -= (double)0.0074f;
            }
        }
    }

    public static class GlowInkProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_172334_;

        public GlowInkProvider(SpriteSet p_172336_) {
            this.f_172334_ = p_172336_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172347_, ClientLevel p_172348_, double p_172349_, double p_172350_, double p_172351_, double p_172352_, double p_172353_, double p_172354_) {
            return new SquidInkParticle(p_172348_, p_172349_, p_172350_, p_172351_, p_172352_, p_172353_, p_172354_, FastColor.ARGB32.m_13660_(255, 204, 31, 102), this.f_172334_);
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107989_;

        public Provider(SpriteSet p_107991_) {
            this.f_107989_ = p_107991_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108002_, ClientLevel p_108003_, double p_108004_, double p_108005_, double p_108006_, double p_108007_, double p_108008_, double p_108009_) {
            return new SquidInkParticle(p_108003_, p_108004_, p_108005_, p_108006_, p_108007_, p_108008_, p_108009_, FastColor.ARGB32.m_13660_(255, 255, 255, 255), this.f_107989_);
        }
    }
}

