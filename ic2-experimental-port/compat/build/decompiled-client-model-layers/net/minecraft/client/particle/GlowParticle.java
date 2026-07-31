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
import net.minecraft.util.RandomSource;

public class GlowParticle
extends TextureSheetParticle {
    static final RandomSource f_172132_ = RandomSource.m_216327_();
    private final SpriteSet f_172133_;

    GlowParticle(ClientLevel p_172136_, double p_172137_, double p_172138_, double p_172139_, double p_172140_, double p_172141_, double p_172142_, SpriteSet p_172143_) {
        super(p_172136_, p_172137_, p_172138_, p_172139_, p_172140_, p_172141_, p_172142_);
        this.f_172258_ = 0.96f;
        this.f_172259_ = true;
        this.f_172133_ = p_172143_;
        this.f_107663_ *= 0.75f;
        this.f_107219_ = false;
        this.m_108339_(p_172143_);
    }

    @Override
    public ParticleRenderType m_7556_() {
        return ParticleRenderType.f_107431_;
    }

    @Override
    public int m_6355_(float p_172146_) {
        float $$1 = ((float)this.f_107224_ + p_172146_) / (float)this.f_107225_;
        $$1 = Mth.m_14036_($$1, 0.0f, 1.0f);
        int $$2 = super.m_6355_(p_172146_);
        int $$3 = $$2 & 0xFF;
        int $$4 = $$2 >> 16 & 0xFF;
        if (($$3 += (int)($$1 * 15.0f * 16.0f)) > 240) {
            $$3 = 240;
        }
        return $$3 | $$4 << 16;
    }

    @Override
    public void m_5989_() {
        super.m_5989_();
        this.m_108339_(this.f_172133_);
    }

    public static class ScrapeProvider
    implements ParticleProvider<SimpleParticleType> {
        private final double f_172191_ = 0.01;
        private final SpriteSet f_172192_;

        public ScrapeProvider(SpriteSet p_172194_) {
            this.f_172192_ = p_172194_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172205_, ClientLevel p_172206_, double p_172207_, double p_172208_, double p_172209_, double p_172210_, double p_172211_, double p_172212_) {
            GlowParticle $$8 = new GlowParticle(p_172206_, p_172207_, p_172208_, p_172209_, 0.0, 0.0, 0.0, this.f_172192_);
            if (p_172206_.f_46441_.m_188499_()) {
                $$8.m_107253_(0.29f, 0.58f, 0.51f);
            } else {
                $$8.m_107253_(0.43f, 0.77f, 0.62f);
            }
            $$8.m_172260_(p_172210_ * 0.01, p_172211_ * 0.01, p_172212_ * 0.01);
            int $$9 = 10;
            int $$10 = 40;
            $$8.m_107257_(p_172206_.f_46441_.m_188503_(30) + 10);
            return $$8;
        }
    }

    public static class ElectricSparkProvider
    implements ParticleProvider<SimpleParticleType> {
        private final double f_172148_ = 0.25;
        private final SpriteSet f_172149_;

        public ElectricSparkProvider(SpriteSet p_172151_) {
            this.f_172149_ = p_172151_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172162_, ClientLevel p_172163_, double p_172164_, double p_172165_, double p_172166_, double p_172167_, double p_172168_, double p_172169_) {
            GlowParticle $$8 = new GlowParticle(p_172163_, p_172164_, p_172165_, p_172166_, 0.0, 0.0, 0.0, this.f_172149_);
            $$8.m_107253_(1.0f, 0.9f, 1.0f);
            $$8.m_172260_(p_172167_ * 0.25, p_172168_ * 0.25, p_172169_ * 0.25);
            int $$9 = 2;
            int $$10 = 4;
            $$8.m_107257_(p_172163_.f_46441_.m_188503_(2) + 2);
            return $$8;
        }
    }

    public static class WaxOffProvider
    implements ParticleProvider<SimpleParticleType> {
        private final double f_172213_ = 0.01;
        private final SpriteSet f_172214_;

        public WaxOffProvider(SpriteSet p_172216_) {
            this.f_172214_ = p_172216_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172227_, ClientLevel p_172228_, double p_172229_, double p_172230_, double p_172231_, double p_172232_, double p_172233_, double p_172234_) {
            GlowParticle $$8 = new GlowParticle(p_172228_, p_172229_, p_172230_, p_172231_, 0.0, 0.0, 0.0, this.f_172214_);
            $$8.m_107253_(1.0f, 0.9f, 1.0f);
            $$8.m_172260_(p_172232_ * 0.01 / 2.0, p_172233_ * 0.01, p_172234_ * 0.01 / 2.0);
            int $$9 = 10;
            int $$10 = 40;
            $$8.m_107257_(p_172228_.f_46441_.m_188503_(30) + 10);
            return $$8;
        }
    }

    public static class WaxOnProvider
    implements ParticleProvider<SimpleParticleType> {
        private final double f_172235_ = 0.01;
        private final SpriteSet f_172236_;

        public WaxOnProvider(SpriteSet p_172238_) {
            this.f_172236_ = p_172238_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172249_, ClientLevel p_172250_, double p_172251_, double p_172252_, double p_172253_, double p_172254_, double p_172255_, double p_172256_) {
            GlowParticle $$8 = new GlowParticle(p_172250_, p_172251_, p_172252_, p_172253_, 0.0, 0.0, 0.0, this.f_172236_);
            $$8.m_107253_(0.91f, 0.55f, 0.08f);
            $$8.m_172260_(p_172254_ * 0.01 / 2.0, p_172255_ * 0.01, p_172256_ * 0.01 / 2.0);
            int $$9 = 10;
            int $$10 = 40;
            $$8.m_107257_(p_172250_.f_46441_.m_188503_(30) + 10);
            return $$8;
        }
    }

    public static class GlowSquidProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_172170_;

        public GlowSquidProvider(SpriteSet p_172172_) {
            this.f_172170_ = p_172172_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_172183_, ClientLevel p_172184_, double p_172185_, double p_172186_, double p_172187_, double p_172188_, double p_172189_, double p_172190_) {
            GlowParticle $$8 = new GlowParticle(p_172184_, p_172185_, p_172186_, p_172187_, 0.5 - f_172132_.m_188500_(), p_172189_, 0.5 - f_172132_.m_188500_(), this.f_172170_);
            if (p_172184_.f_46441_.m_188499_()) {
                $$8.m_107253_(0.6f, 1.0f, 0.8f);
            } else {
                $$8.m_107253_(0.08f, 0.4f, 0.4f);
            }
            $$8.f_107216_ *= (double)0.2f;
            if (p_172188_ == 0.0 && p_172190_ == 0.0) {
                $$8.f_107215_ *= (double)0.1f;
                $$8.f_107217_ *= (double)0.1f;
            }
            $$8.m_107257_((int)(8.0 / (p_172184_.f_46441_.m_188500_() * 0.8 + 0.2)));
            return $$8;
        }
    }
}

