/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SpellParticle
extends TextureSheetParticle {
    private static final RandomSource f_107758_ = RandomSource.m_216327_();
    private final SpriteSet f_107759_;

    SpellParticle(ClientLevel p_107762_, double p_107763_, double p_107764_, double p_107765_, double p_107766_, double p_107767_, double p_107768_, SpriteSet p_107769_) {
        super(p_107762_, p_107763_, p_107764_, p_107765_, 0.5 - f_107758_.m_188500_(), p_107767_, 0.5 - f_107758_.m_188500_());
        this.f_172258_ = 0.96f;
        this.f_107226_ = -0.1f;
        this.f_172259_ = true;
        this.f_107759_ = p_107769_;
        this.f_107216_ *= (double)0.2f;
        if (p_107766_ == 0.0 && p_107768_ == 0.0) {
            this.f_107215_ *= (double)0.1f;
            this.f_107217_ *= (double)0.1f;
        }
        this.f_107663_ *= 0.75f;
        this.f_107225_ = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.f_107219_ = false;
        this.m_108339_(p_107769_);
        if (this.m_172323_()) {
            this.m_107271_(0.0f);
        }
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_107759_);
        if (this.m_172323_()) {
            this.m_107271_(0.0f);
        } else {
            this.m_107271_(Mth.m_14179_(0.05f, this.f_107230_, 1.0f));
        }
    }

    private boolean m_172323_() {
        Minecraft $$0 = Minecraft.m_91087_();
        LocalPlayer $$1 = $$0.f_91074_;
        return $$1 != null && $$1.m_146892_().m_82531_(this.f_107212_, this.f_107213_, this.f_107214_) <= 9.0 && $$0.f_91066_.m_92176_().m_90612_() && $$1.m_150108_();
    }

    public static class InstantProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107803_;

        public InstantProvider(SpriteSet p_107805_) {
            this.f_107803_ = p_107805_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107816_, ClientLevel p_107817_, double p_107818_, double p_107819_, double p_107820_, double p_107821_, double p_107822_, double p_107823_) {
            return new SpellParticle(p_107817_, p_107818_, p_107819_, p_107820_, p_107821_, p_107822_, p_107823_, this.f_107803_);
        }
    }

    public static class WitchProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107866_;

        public WitchProvider(SpriteSet p_107868_) {
            this.f_107866_ = p_107868_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107879_, ClientLevel p_107880_, double p_107881_, double p_107882_, double p_107883_, double p_107884_, double p_107885_, double p_107886_) {
            SpellParticle $$8 = new SpellParticle(p_107880_, p_107881_, p_107882_, p_107883_, p_107884_, p_107885_, p_107886_, this.f_107866_);
            float $$9 = p_107880_.f_46441_.m_188501_() * 0.5f + 0.35f;
            $$8.m_107253_(1.0f * $$9, 0.0f * $$9, 1.0f * $$9);
            return $$8;
        }
    }

    public static class AmbientMobProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107782_;

        public AmbientMobProvider(SpriteSet p_107784_) {
            this.f_107782_ = p_107784_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107795_, ClientLevel p_107796_, double p_107797_, double p_107798_, double p_107799_, double p_107800_, double p_107801_, double p_107802_) {
            SpellParticle $$8 = new SpellParticle(p_107796_, p_107797_, p_107798_, p_107799_, p_107800_, p_107801_, p_107802_, this.f_107782_);
            $$8.m_107271_(0.15f);
            $$8.m_107253_((float)p_107800_, (float)p_107801_, (float)p_107802_);
            return $$8;
        }
    }

    public static class MobProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107824_;

        public MobProvider(SpriteSet p_107826_) {
            this.f_107824_ = p_107826_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107837_, ClientLevel p_107838_, double p_107839_, double p_107840_, double p_107841_, double p_107842_, double p_107843_, double p_107844_) {
            SpellParticle $$8 = new SpellParticle(p_107838_, p_107839_, p_107840_, p_107841_, p_107842_, p_107843_, p_107844_, this.f_107824_);
            $$8.m_107253_((float)p_107842_, (float)p_107843_, (float)p_107844_);
            return $$8;
        }
    }

    public static class Provider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_107845_;

        public Provider(SpriteSet p_107847_) {
            this.f_107845_ = p_107847_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_107858_, ClientLevel p_107859_, double p_107860_, double p_107861_, double p_107862_, double p_107863_, double p_107864_, double p_107865_) {
            return new SpellParticle(p_107859_, p_107860_, p_107861_, p_107862_, p_107863_, p_107864_, p_107865_, this.f_107845_);
        }
    }
}

