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
import net.minecraft.util.Mth;

public class DragonBreathParticle
extends TextureSheetParticle {
    private static final int f_171926_ = 11993298;
    private static final int f_171927_ = 14614777;
    private static final float f_171920_ = 0.7176471f;
    private static final float f_171921_ = 0.0f;
    private static final float f_171922_ = 0.8235294f;
    private static final float f_171923_ = 0.8745098f;
    private static final float f_171924_ = 0.0f;
    private static final float f_171925_ = 0.9764706f;
    private boolean f_106002_;
    private final SpriteSet f_106003_;

    DragonBreathParticle(ClientLevel p_106005_, double p_106006_, double p_106007_, double p_106008_, double p_106009_, double p_106010_, double p_106011_, SpriteSet p_106012_) {
        super(p_106005_, p_106006_, p_106007_, p_106008_);
        this.f_172258_ = 0.96f;
        this.f_107215_ = p_106009_;
        this.f_107216_ = p_106010_;
        this.f_107217_ = p_106011_;
        this.f_107227_ = Mth.m_216267_(this.f_107223_, 0.7176471f, 0.8745098f);
        this.f_107228_ = Mth.m_216267_(this.f_107223_, 0.0f, 0.0f);
        this.f_107229_ = Mth.m_216267_(this.f_107223_, 0.8235294f, 0.9764706f);
        this.f_107663_ *= 0.75f;
        this.f_107225_ = (int)(20.0 / ((double)this.f_107223_.m_188501_() * 0.8 + 0.2));
        this.f_106002_ = false;
        this.f_107219_ = false;
        this.f_106003_ = p_106012_;
        this.m_108339_(p_106012_);
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
        this.m_108339_(this.f_106003_);
        if (this.f_107218_) {
            this.f_107216_ = 0.0;
            this.f_106002_ = true;
        }
        if (this.f_106002_) {
            this.f_107216_ += 0.002;
        }
        this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
        if (this.f_107213_ == this.f_107210_) {
            this.f_107215_ *= 1.1;
            this.f_107217_ *= 1.1;
        }
        this.f_107215_ *= (double)this.f_172258_;
        this.f_107217_ *= (double)this.f_172258_;
        if (this.f_106002_) {
            this.f_107216_ *= (double)this.f_172258_;
        }
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public float m_5902_(float p_106026_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_106026_) / (float)this.f_107225_ * 32.0f, 0.0f, 1.0f);
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106027_;

        public Provider(SpriteSet p_106029_) {
            this.f_106027_ = p_106029_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106040_, ClientLevel p_106041_, double p_106042_, double p_106043_, double p_106044_, double p_106045_, double p_106046_, double p_106047_) {
            return new DragonBreathParticle(p_106041_, p_106042_, p_106043_, p_106044_, p_106045_, p_106046_, p_106047_, this.f_106027_);
        }
    }
}

