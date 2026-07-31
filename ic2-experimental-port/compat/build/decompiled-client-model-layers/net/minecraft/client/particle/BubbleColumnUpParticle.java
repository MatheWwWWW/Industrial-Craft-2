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

public class BubbleColumnUpParticle
extends TextureSheetParticle {
    BubbleColumnUpParticle(ClientLevel p_105733_, double p_105734_, double p_105735_, double p_105736_, double p_105737_, double p_105738_, double p_105739_) {
        super(p_105733_, p_105734_, p_105735_, p_105736_);
        this.f_107226_ = -0.125f;
        this.f_172258_ = 0.85f;
        this.m_107250_(0.02f, 0.02f);
        this.f_107663_ *= this.f_107223_.m_188501_() * 0.6f + 0.2f;
        this.f_107215_ = p_105737_ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.f_107216_ = p_105738_ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.f_107217_ = p_105739_ * (double)0.2f + (Math.random() * 2.0 - 1.0) * (double)0.02f;
        this.f_107225_ = (int)(40.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        if (!this.f_107220_ && !this.f_107208_.m_6425_(new BlockPos(this.f_107212_, this.f_107213_, this.f_107214_)).m_205070_(FluidTags.f_13131_)) {
            this.m_107274_();
        }
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_105751_;

        public Provider(SpriteSet p_105753_) {
            this.f_105751_ = p_105753_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_105764_, ClientLevel p_105765_, double p_105766_, double p_105767_, double p_105768_, double p_105769_, double p_105770_, double p_105771_) {
            BubbleColumnUpParticle $$8 = new BubbleColumnUpParticle(p_105765_, p_105766_, p_105767_, p_105768_, p_105769_, p_105770_, p_105771_);
            $$8.m_108335_(this.f_105751_);
            return $$8;
        }
    }
}

