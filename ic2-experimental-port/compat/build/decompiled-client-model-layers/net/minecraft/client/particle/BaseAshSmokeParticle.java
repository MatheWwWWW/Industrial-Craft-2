/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;

public class BaseAshSmokeParticle
extends TextureSheetParticle {
    private final SpriteSet f_105620_;

    protected BaseAshSmokeParticle(ClientLevel p_171904_, double p_171905_, double p_171906_, double p_171907_, float p_171908_, float p_171909_, float p_171910_, double p_171911_, double p_171912_, double p_171913_, float p_171914_, SpriteSet p_171915_, float p_171916_, int p_171917_, float p_171918_, boolean p_171919_) {
        super(p_171904_, p_171905_, p_171906_, p_171907_, 0.0, 0.0, 0.0);
        float $$16;
        this.f_172258_ = 0.96f;
        this.f_107226_ = p_171918_;
        this.f_172259_ = true;
        this.f_105620_ = p_171915_;
        this.f_107215_ *= (double)p_171908_;
        this.f_107216_ *= (double)p_171909_;
        this.f_107217_ *= (double)p_171910_;
        this.f_107215_ += p_171911_;
        this.f_107216_ += p_171912_;
        this.f_107217_ += p_171913_;
        this.f_107227_ = $$16 = p_171904_.f_46441_.m_188501_() * p_171916_;
        this.f_107228_ = $$16;
        this.f_107229_ = $$16;
        this.f_107663_ *= 0.75f * p_171914_;
        this.f_107225_ = (int)((double)p_171917_ / ((double)p_171904_.f_46441_.m_188501_() * 0.8 + 0.2) * (double)p_171914_);
        this.f_107225_ = Math.max(this.f_107225_, 1);
        this.m_108339_(p_171915_);
        this.f_107219_ = p_171919_;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public float m_5902_(float p_105642_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_105642_) / (float)this.f_107225_ * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_105620_);
    }
}

