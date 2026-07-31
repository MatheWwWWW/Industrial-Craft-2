/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.util.Mth;

public class DustParticleBase<T extends DustParticleOptionsBase>
extends TextureSheetParticle {
    private final SpriteSet f_172092_;

    protected DustParticleBase(ClientLevel p_172094_, double p_172095_, double p_172096_, double p_172097_, double p_172098_, double p_172099_, double p_172100_, T p_172101_, SpriteSet p_172102_) {
        super(p_172094_, p_172095_, p_172096_, p_172097_, p_172098_, p_172099_, p_172100_);
        this.f_172258_ = 0.96f;
        this.f_172259_ = true;
        this.f_172092_ = p_172102_;
        this.f_107215_ *= (double)0.1f;
        this.f_107216_ *= (double)0.1f;
        this.f_107217_ *= (double)0.1f;
        float $$9 = this.f_107223_.m_188501_() * 0.4f + 0.6f;
        this.f_107227_ = this.m_172104_(((DustParticleOptionsBase)p_172101_).m_175812_().m_122239_(), $$9);
        this.f_107228_ = this.m_172104_(((DustParticleOptionsBase)p_172101_).m_175812_().m_122260_(), $$9);
        this.f_107229_ = this.m_172104_(((DustParticleOptionsBase)p_172101_).m_175812_().m_122269_(), $$9);
        this.f_107663_ *= 0.75f * ((DustParticleOptionsBase)p_172101_).m_175813_();
        int $$10 = (int)(8.0 / (this.f_107223_.m_188500_() * 0.8 + 0.2));
        this.f_107225_ = (int)Math.max((float)$$10 * ((DustParticleOptionsBase)p_172101_).m_175813_(), 1.0f);
        this.m_108339_(p_172102_);
    }

    protected float m_172104_(float p_172105_, float p_172106_) {
        return (this.f_107223_.m_188501_() * 0.2f + 0.8f) * p_172105_ * p_172106_;
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107430_;
    }

    @Override
    public float m_5902_(float p_172109_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_172109_) / (float)this.f_107225_ * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_172092_);
    }
}

