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
import net.minecraft.world.entity.player.Player;

public class PlayerCloudParticle
extends TextureSheetParticle {
    private final SpriteSet f_107481_;

    PlayerCloudParticle(ClientLevel p_107483_, double p_107484_, double p_107485_, double p_107486_, double p_107487_, double p_107488_, double p_107489_, SpriteSet p_107490_) {
        super(p_107483_, p_107484_, p_107485_, p_107486_, 0.0, 0.0, 0.0);
        float $$9;
        this.f_172258_ = 0.96f;
        this.f_107481_ = p_107490_;
        float $$8 = 2.5f;
        this.f_107215_ *= (double)0.1f;
        this.f_107216_ *= (double)0.1f;
        this.f_107217_ *= (double)0.1f;
        this.f_107215_ += p_107487_;
        this.f_107216_ += p_107488_;
        this.f_107217_ += p_107489_;
        this.f_107227_ = $$9 = 1.0f - (float)(Math.random() * (double)0.3f);
        this.f_107228_ = $$9;
        this.f_107229_ = $$9;
        this.f_107663_ *= 1.875f;
        int $$10 = (int)(8.0 / (Math.random() * 0.8 + 0.3));
        this.f_107225_ = (int)Math.max((float)$$10 * 2.5f, 1.0f);
        this.f_107219_ = false;
        this.m_108339_(p_107490_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public float m_5902_(float p_107504_) {
        return this.f_107663_ * Mth.m_14036_(((float)this.f_107224_ + p_107504_) / (float)this.f_107225_ * 32.0f, 0.0f, 1.0f);
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        if (!this.f_107220_) {
            double $$1;
            this.m_108339_(this.f_107481_);
            Player $$0 = this.f_107208_.m_45924_(this.f_107212_, this.f_107213_, this.f_107214_, 2.0, false);
            if ($$0 != null && this.f_107213_ > ($$1 = $$0.m_20186_())) {
                this.f_107213_ += ($$1 - this.f_107213_) * 0.2;
                this.f_107216_ += ($$0.m_20184_().f_82480_ - this.f_107216_) * 0.2;
                this.m_107264_(this.f_107212_, this.f_107213_, this.f_107214_);
            }
        }
    }

    public static class SneezeProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107526_;

        public SneezeProvider(SpriteSet p_107528_) {
            this.f_107526_ = p_107528_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107539_, ClientLevel p_107540_, double p_107541_, double p_107542_, double p_107543_, double p_107544_, double p_107545_, double p_107546_) {
            PlayerCloudParticle $$8 = new PlayerCloudParticle(p_107540_, p_107541_, p_107542_, p_107543_, p_107544_, p_107545_, p_107546_, this.f_107526_);
            $$8.m_107253_(200.0f, 50.0f, 120.0f);
            $$8.m_107271_(0.4f);
            return $$8;
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107505_;

        public Provider(SpriteSet p_107507_) {
            this.f_107505_ = p_107507_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107518_, ClientLevel p_107519_, double p_107520_, double p_107521_, double p_107522_, double p_107523_, double p_107524_, double p_107525_) {
            return new PlayerCloudParticle(p_107519_, p_107520_, p_107521_, p_107522_, p_107523_, p_107524_, p_107525_, this.f_107505_);
        }
    }
}

