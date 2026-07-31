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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;

public class BubbleParticle
extends TextureSheetParticle {
    BubbleParticle(ClientLevel p_105773_, double p_105774_, double p_105775_, double p_105776_, double p_105777_, double p_105778_, double p_105779_) {
        super(p_105773_, p_105774_, p_105775_, p_105776_);
        this.m_107250_(0.02f, 0.02f);
        this.f_107663_ *= this.f_107223_.m_188501_() * 0.6f + 0.2f;
        this.f_107215_ = p_105777_ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.f_107216_ = p_105778_ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.f_107217_ = p_105779_ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.f_107225_ = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void m_5989_() {
        this.f_107209_ = this.f_107212_;
        this.f_107210_ = this.f_107213_;
        this.f_107211_ = this.f_107214_;
        if (this.f_107225_-- <= 0) {
            this.m_107274_();
            return;
        }
        this.f_107216_ += 0.002;
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        this.f_107215_ *= (double)0.85f;
        this.f_107216_ *= (double)0.85f;
        this.f_107217_ *= (double)0.85f;
        if (!this.f_107208_.m_6425_(new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_)).m_205070_(FluidTags.f_13131_)) {
            this.m_107274_();
        }
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_105791_;

        public Provider(SpriteSet p_105793_) {
            this.f_105791_ = p_105793_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_105804_, ClientLevel p_105805_, double p_105806_, double p_105807_, double p_105808_, double p_105809_, double p_105810_, double p_105811_) {
            BubbleParticle $$8 = new BubbleParticle(p_105805_, p_105806_, p_105807_, p_105808_, p_105809_, p_105810_, p_105811_);
            $$8.m_108335_(this.f_105791_);
            return $$8;
        }
    }
}

