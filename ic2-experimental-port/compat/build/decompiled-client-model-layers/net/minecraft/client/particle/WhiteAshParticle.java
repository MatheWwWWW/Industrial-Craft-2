/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class WhiteAshParticle
extends BaseAshSmokeParticle {
    private static final int f_172509_ = 12235202;

    protected WhiteAshParticle(ClientLevel p_108512_, double p_108513_, double p_108514_, double p_108515_, double p_108516_, double p_108517_, double p_108518_, float p_108519_, SpriteSet p_108520_) {
        super(p_108512_, p_108513_, p_108514_, p_108515_, 0.1f, -0.1f, 0.1f, p_108516_, p_108517_, p_108518_, p_108519_, p_108520_, 0.0f, 20, 0.0125f, false);
        this.f_107227_ = 0.7294118f;
        this.f_107228_ = 0.69411767f;
        this.f_107229_ = 0.7607843f;
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_108521_;

        public Provider(SpriteSet p_108523_) {
            this.f_108521_ = p_108523_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108534_, ClientLevel p_108535_, double p_108536_, double p_108537_, double p_108538_, double p_108539_, double p_108540_, double p_108541_) {
            RandomSource $$8 = p_108535_.f_46441_;
            double $$9 = (double)$$8.m_188501_() * -1.9 * (double)$$8.m_188501_() * 0.1;
            double $$10 = (double)$$8.m_188501_() * -0.5 * (double)$$8.m_188501_() * 0.1 * 5.0;
            double $$11 = (double)$$8.m_188501_() * -1.9 * (double)$$8.m_188501_() * 0.1;
            return new WhiteAshParticle(p_108535_, p_108536_, p_108537_, p_108538_, $$9, $$10, $$11, 1.0f, this.f_108521_);
        }
    }
}

