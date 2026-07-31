/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import java.util.Optional;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SuspendedParticle
extends TextureSheetParticle {
    SuspendedParticle(ClientLevel p_172403_, SpriteSet p_172404_, double p_172405_, double p_172406_, double p_172407_) {
        super(p_172403_, p_172405_, p_172406_ - 0.125, p_172407_);
        this.m_107250_(0.01f, 0.01f);
        this.m_108335_(p_172404_);
        this.f_107663_ *= this.f_107223_.m_188501_() * 0.6f + 0.2f;
        this.f_107225_ = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.f_107219_ = false;
        this.f_172258_ = 1.0f;
        this.f_107226_ = 0.0f;
    }

    SuspendedParticle(ClientLevel p_172409_, SpriteSet p_172410_, double p_172411_, double p_172412_, double p_172413_, double p_172414_, double p_172415_, double p_172416_) {
        super(p_172409_, p_172411_, p_172412_ - 0.125, p_172413_, p_172414_, p_172415_, p_172416_);
        this.m_107250_(0.01f, 0.01f);
        this.m_108335_(p_172410_);
        this.f_107663_ *= this.f_107223_.m_188501_() * 0.6f + 0.6f;
        this.f_107225_ = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.f_107219_ = false;
        this.f_172258_ = 1.0f;
        this.f_107226_ = 0.0f;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    public static class WarpedSporeProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_108082_;

        public WarpedSporeProvider(SpriteSet p_108084_) {
            this.f_108082_ = p_108084_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108095_, ClientLevel p_108096_, double p_108097_, double p_108098_, double p_108099_, double p_108100_, double p_108101_, double p_108102_) {
            double $$8 = (double)p_108096_.f_46441_.m_188501_() * -1.9 * (double)p_108096_.f_46441_.m_188501_() * 0.1;
            SuspendedParticle $$9 = new SuspendedParticle(p_108096_, this.f_108082_, p_108097_, p_108098_, p_108099_, 0.0, $$8, 0.0);
            $$9.m_107253_(0.1f, 0.1f, 0.3f);
            $$9.m_107250_(0.001f, 0.001f);
            return $$9;
        }
    }

    public static class CrimsonSporeProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_108040_;

        public CrimsonSporeProvider(SpriteSet p_108042_) {
            this.f_108040_ = p_108042_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108053_, ClientLevel p_108054_, double p_108055_, double p_108056_, double p_108057_, double p_108058_, double p_108059_, double p_108060_) {
            RandomSource $$8 = p_108054_.f_46441_;
            double $$9 = $$8.m_188583_() * (double)1.0E-6f;
            double $$10 = $$8.m_188583_() * (double)1.0E-4f;
            double $$11 = $$8.m_188583_() * (double)1.0E-6f;
            SuspendedParticle $$12 = new SuspendedParticle(p_108054_, this.f_108040_, p_108055_, p_108056_, p_108057_, $$9, $$10, $$11);
            $$12.m_107253_(0.9f, 0.4f, 0.5f);
            return $$12;
        }
    }

    public static class SporeBlossomAirProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_172417_;

        public SporeBlossomAirProvider(SpriteSet p_172419_) {
            this.f_172417_ = p_172419_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172430_, ClientLevel p_172431_, double p_172432_, double p_172433_, double p_172434_, double p_172435_, double p_172436_, double p_172437_) {
            SuspendedParticle $$8 = new SuspendedParticle(p_172431_, this.f_172417_, p_172432_, p_172433_, p_172434_, 0.0, -0.8f, 0.0){

                @Override
                public Optional<ParticleGroup> m_142654_() {
                    return Optional.of(ParticleGroup.f_175814_);
                }
            };
            $$8.f_107225_ = Mth.m_216287_(p_172431_.f_46441_, 500, 1000);
            $$8.f_107226_ = 0.01f;
            $$8.m_107253_(0.32f, 0.5f, 0.22f);
            return $$8;
        }
    }

    public static class UnderwaterProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_108061_;

        public UnderwaterProvider(SpriteSet p_108063_) {
            this.f_108061_ = p_108063_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_108074_, ClientLevel p_108075_, double p_108076_, double p_108077_, double p_108078_, double p_108079_, double p_108080_, double p_108081_) {
            SuspendedParticle $$8 = new SuspendedParticle(p_108075_, this.f_108061_, p_108076_, p_108077_, p_108078_);
            $$8.m_107253_(0.4f, 0.4f, 0.7f);
            return $$8;
        }
    }
}

