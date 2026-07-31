/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.FireworkRocketItem;

public class FireworkParticles {

    public static class SparkProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106731_;

        public SparkProvider(SpriteSet p_106733_) {
            this.f_106731_ = p_106733_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106744_, ClientLevel p_106745_, double p_106746_, double p_106747_, double p_106748_, double p_106749_, double p_106750_, double p_106751_) {
            SparkParticle $$8 = new SparkParticle(p_106745_, p_106746_, p_106747_, p_106748_, p_106749_, p_106750_, p_106751_, Minecraft.m_91087_().f_91061_, this.f_106731_);
            $$8.m_107271_(0.99f);
            return $$8;
        }
    }

    public static class FlashProvider
    implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet f_106655_;

        public FlashProvider(SpriteSet p_106657_) {
            this.f_106655_ = p_106657_;
        }

        @Override
        public Particle m_6966_(SimpleParticleType p_106668_, ClientLevel p_106669_, double p_106670_, double p_106671_, double p_106672_, double p_106673_, double p_106674_, double p_106675_) {
            OverlayParticle $$8 = new OverlayParticle(p_106669_, p_106670_, p_106671_, p_106672_);
            $$8.m_108335_(this.f_106655_);
            return $$8;
        }
    }

    public static class OverlayParticle
    extends TextureSheetParticle {
        OverlayParticle(ClientLevel p_106677_, double p_106678_, double p_106679_, double p_106680_) {
            super(p_106677_, p_106678_, p_106679_, p_106680_);
            this.f_107225_ = 4;
        }

        @Override
        public ParticleRenderType m_7556_() {
            return ParticleRenderType.f_107431_;
        }

        @Override
        public void m_5744_(VertexConsumer p_106688_, Camera p_106689_, float p_106690_) {
            this.m_107271_(0.6f - ((float)this.f_107224_ + p_106690_ - 1.0f) * 0.25f * 0.5f);
            super.m_5744_(p_106688_, p_106689_, p_106690_);
        }

        @Override
        public float m_5902_(float p_106693_) {
            return 7.1f * Mth.m_14031_(((float)this.f_107224_ + p_106693_ - 1.0f) * 0.25f * (float)Math.PI);
        }
    }

    static class SparkParticle
    extends SimpleAnimatedParticle {
        private boolean f_106700_;
        private boolean f_106694_;
        private final ParticleEngine f_106695_;
        private float f_106696_;
        private float f_106697_;
        private float f_106698_;
        private boolean f_106699_;

        SparkParticle(ClientLevel p_106702_, double p_106703_, double p_106704_, double p_106705_, double p_106706_, double p_106707_, double p_106708_, ParticleEngine p_106709_, SpriteSet p_106710_) {
            super(p_106702_, p_106703_, p_106704_, p_106705_, p_106710_, 0.1f);
            this.f_107215_ = p_106706_;
            this.f_107216_ = p_106707_;
            this.f_107217_ = p_106708_;
            this.f_106695_ = p_106709_;
            this.f_107663_ *= 0.75f;
            this.f_107225_ = 48 + this.f_107223_.m_188503_(12);
            this.m_108339_(p_106710_);
        }

        public void m_106727_(boolean p_106728_) {
            this.f_106700_ = p_106728_;
        }

        public void m_106729_(boolean p_106730_) {
            this.f_106694_ = p_106730_;
        }

        @Override
        public void m_5744_(VertexConsumer p_106724_, Camera p_106725_, float p_106726_) {
            if (!this.f_106694_ || this.f_107224_ < this.f_107225_ / 3 || (this.f_107224_ + this.f_107225_) / 3 % 2 == 0) {
                super.m_5744_(p_106724_, p_106725_, p_106726_);
            }
        }

        @Override
        public void m_5989_() {
            super.m_5989_();
            if (this.f_106700_ && this.f_107224_ < this.f_107225_ / 2 && (this.f_107224_ + this.f_107225_) % 2 == 0) {
                SparkParticle $$0 = new SparkParticle(this.f_107208_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0, 0.0, 0.0, this.f_106695_, this.f_107644_);
                $$0.m_107271_(0.99f);
                $$0.m_107253_(this.f_107227_, this.f_107228_, this.f_107229_);
                $$0.f_107224_ = $$0.f_107225_ / 2;
                if (this.f_106699_) {
                    $$0.f_106699_ = true;
                    $$0.f_106696_ = this.f_106696_;
                    $$0.f_106697_ = this.f_106697_;
                    $$0.f_106698_ = this.f_106698_;
                }
                $$0.f_106694_ = this.f_106694_;
                this.f_106695_.m_107344_($$0);
            }
        }
    }

    public static class Starter
    extends NoRenderParticle {
        private int f_106754_;
        private final ParticleEngine f_106755_;
        private ListTag f_106752_;
        private boolean f_106753_;

        public Starter(ClientLevel p_106757_, double p_106758_, double p_106759_, double p_106760_, double p_106761_, double p_106762_, double p_106763_, ParticleEngine p_106764_, @Nullable CompoundTag p_106765_) {
            super(p_106757_, p_106758_, p_106759_, p_106760_);
            this.f_107215_ = p_106761_;
            this.f_107216_ = p_106762_;
            this.f_107217_ = p_106763_;
            this.f_106755_ = p_106764_;
            this.f_107225_ = 8;
            if (p_106765_ != null) {
                this.f_106752_ = p_106765_.m_128437_("Explosions", 10);
                if (this.f_106752_.isEmpty()) {
                    this.f_106752_ = null;
                } else {
                    this.f_107225_ = this.f_106752_.size() * 2 - 1;
                    for (int $$9 = 0; $$9 < this.f_106752_.size(); ++$$9) {
                        CompoundTag $$10 = this.f_106752_.m_128728_($$9);
                        if (!$$10.m_128471_("Flicker")) continue;
                        this.f_106753_ = true;
                        this.f_107225_ += 15;
                        break;
                    }
                }
            }
        }

        @Override
        public void m_5989_() {
            if (this.f_106754_ == 0 && this.f_106752_ != null) {
                SoundEvent $$5;
                boolean $$0 = this.m_106798_();
                boolean $$1 = false;
                if (this.f_106752_.size() >= 3) {
                    $$1 = true;
                } else {
                    for (int $$2 = 0; $$2 < this.f_106752_.size(); ++$$2) {
                        CompoundTag $$3 = this.f_106752_.m_128728_($$2);
                        if (FireworkRocketItem.Shape.m_41237_($$3.m_128445_("Type")) != FireworkRocketItem.Shape.LARGE_BALL) continue;
                        $$1 = true;
                        break;
                    }
                }
                if ($$1) {
                    SoundEvent $$4 = $$0 ? SoundEvents.f_11931_ : SoundEvents.f_11930_;
                } else {
                    $$5 = $$0 ? SoundEvents.f_11929_ : SoundEvents.f_11928_;
                }
                this.f_107208_.m_7785_(this.f_107212_, this.f_107213_, this.f_107214_, $$5, SoundSource.AMBIENT, 20.0f, 0.95f + this.f_107223_.m_188501_() * 0.1f, true);
            }
            if (this.f_106754_ % 2 == 0 && this.f_106752_ != null && this.f_106754_ / 2 < this.f_106752_.size()) {
                int $$6 = this.f_106754_ / 2;
                CompoundTag $$7 = this.f_106752_.m_128728_($$6);
                FireworkRocketItem.Shape $$8 = FireworkRocketItem.Shape.m_41237_($$7.m_128445_("Type"));
                boolean $$9 = $$7.m_128471_("Trail");
                boolean $$10 = $$7.m_128471_("Flicker");
                int[] $$11 = $$7.m_128465_("Colors");
                int[] $$12 = $$7.m_128465_("FadeColors");
                if ($$11.length == 0) {
                    $$11 = new int[]{DyeColor.BLACK.m_41070_()};
                }
                switch ($$8) {
                    default: {
                        this.m_106778_(0.25, 2, $$11, $$12, $$9, $$10);
                        break;
                    }
                    case LARGE_BALL: {
                        this.m_106778_(0.5, 4, $$11, $$12, $$9, $$10);
                        break;
                    }
                    case STAR: {
                        this.m_106785_(0.5, new double[][]{{0.0, 1.0}, {0.3455, 0.309}, {0.9511, 0.309}, {0.3795918367346939, -0.12653061224489795}, {0.6122448979591837, -0.8040816326530612}, {0.0, -0.35918367346938773}}, $$11, $$12, $$9, $$10, false);
                        break;
                    }
                    case CREEPER: {
                        this.m_106785_(0.5, new double[][]{{0.0, 0.2}, {0.2, 0.2}, {0.2, 0.6}, {0.6, 0.6}, {0.6, 0.2}, {0.2, 0.2}, {0.2, 0.0}, {0.4, 0.0}, {0.4, -0.6}, {0.2, -0.6}, {0.2, -0.4}, {0.0, -0.4}}, $$11, $$12, $$9, $$10, true);
                        break;
                    }
                    case BURST: {
                        this.m_106793_($$11, $$12, $$9, $$10);
                    }
                }
                int $$13 = $$11[0];
                float $$14 = (float)(($$13 & 0xFF0000) >> 16) / 255.0f;
                float $$15 = (float)(($$13 & 0xFF00) >> 8) / 255.0f;
                float $$16 = (float)(($$13 & 0xFF) >> 0) / 255.0f;
                Particle $$17 = this.f_106755_.m_107370_(ParticleTypes.f_123747_, this.f_107212_, this.f_107213_, this.f_107214_, 0.0, 0.0, 0.0);
                $$17.m_107253_($$14, $$15, $$16);
            }
            ++this.f_106754_;
            if (this.f_106754_ > this.f_107225_) {
                if (this.f_106753_) {
                    boolean $$18 = this.m_106798_();
                    SoundEvent $$19 = $$18 ? SoundEvents.f_11935_ : SoundEvents.f_11934_;
                    this.f_107208_.m_7785_(this.f_107212_, this.f_107213_, this.f_107214_, $$19, SoundSource.AMBIENT, 20.0f, 0.9f + this.f_107223_.m_188501_() * 0.15f, true);
                }
                this.m_107274_();
            }
        }

        private boolean m_106798_() {
            Minecraft $$0 = Minecraft.m_91087_();
            return $$0.f_91063_.m_109153_().m_90583_().m_82531_(this.f_107212_, this.f_107213_, this.f_107214_) >= 256.0;
        }

        private void m_106767_(double p_106768_, double p_106769_, double p_106770_, double p_106771_, double p_106772_, double p_106773_, int[] p_106774_, int[] p_106775_, boolean p_106776_, boolean p_106777_) {
            SparkParticle $$10 = (SparkParticle)this.f_106755_.m_107370_(ParticleTypes.f_123815_, p_106768_, p_106769_, p_106770_, p_106771_, p_106772_, p_106773_);
            $$10.m_106727_(p_106776_);
            $$10.m_106729_(p_106777_);
            $$10.m_107271_(0.99f);
            int $$11 = this.f_107223_.m_188503_(p_106774_.length);
            $$10.m_107657_(p_106774_[$$11]);
            if (p_106775_.length > 0) {
                $$10.m_107659_(Util.m_214667_(p_106775_, this.f_107223_));
            }
        }

        private void m_106778_(double p_106779_, int p_106780_, int[] p_106781_, int[] p_106782_, boolean p_106783_, boolean p_106784_) {
            double $$6 = this.f_107212_;
            double $$7 = this.f_107213_;
            double $$8 = this.f_107214_;
            for (int $$9 = -p_106780_; $$9 <= p_106780_; ++$$9) {
                for (int $$10 = -p_106780_; $$10 <= p_106780_; ++$$10) {
                    for (int $$11 = -p_106780_; $$11 <= p_106780_; ++$$11) {
                        double $$12 = (double)$$10 + (this.f_107223_.m_188500_() - this.f_107223_.m_188500_()) * 0.5;
                        double $$13 = (double)$$9 + (this.f_107223_.m_188500_() - this.f_107223_.m_188500_()) * 0.5;
                        double $$14 = (double)$$11 + (this.f_107223_.m_188500_() - this.f_107223_.m_188500_()) * 0.5;
                        double $$15 = Math.sqrt($$12 * $$12 + $$13 * $$13 + $$14 * $$14) / p_106779_ + this.f_107223_.m_188583_() * 0.05;
                        this.m_106767_($$6, $$7, $$8, $$12 / $$15, $$13 / $$15, $$14 / $$15, p_106781_, p_106782_, p_106783_, p_106784_);
                        if ($$9 == -p_106780_ || $$9 == p_106780_ || $$10 == -p_106780_ || $$10 == p_106780_) continue;
                        $$11 += p_106780_ * 2 - 1;
                    }
                }
            }
        }

        private void m_106785_(double p_106786_, double[][] p_106787_, int[] p_106788_, int[] p_106789_, boolean p_106790_, boolean p_106791_, boolean p_106792_) {
            double $$7 = p_106787_[0][0];
            double $$8 = p_106787_[0][1];
            this.m_106767_(this.f_107212_, this.f_107213_, this.f_107214_, $$7 * p_106786_, $$8 * p_106786_, 0.0, p_106788_, p_106789_, p_106790_, p_106791_);
            float $$9 = this.f_107223_.m_188501_() * (float)Math.PI;
            double $$10 = p_106792_ ? 0.034 : 0.34;
            for (int $$11 = 0; $$11 < 3; ++$$11) {
                double $$12 = (double)$$9 + (double)((float)$$11 * (float)Math.PI) * $$10;
                double $$13 = $$7;
                double $$14 = $$8;
                for (int $$15 = 1; $$15 < p_106787_.length; ++$$15) {
                    double $$16 = p_106787_[$$15][0];
                    double $$17 = p_106787_[$$15][1];
                    for (double $$18 = 0.25; $$18 <= 1.0; $$18 += 0.25) {
                        double $$19 = Mth.m_14139_($$18, $$13, $$16) * p_106786_;
                        double $$20 = Mth.m_14139_($$18, $$14, $$17) * p_106786_;
                        double $$21 = $$19 * Math.sin($$12);
                        $$19 *= Math.cos($$12);
                        for (double $$22 = -1.0; $$22 <= 1.0; $$22 += 2.0) {
                            this.m_106767_(this.f_107212_, this.f_107213_, this.f_107214_, $$19 * $$22, $$20, $$21 * $$22, p_106788_, p_106789_, p_106790_, p_106791_);
                        }
                    }
                    $$13 = $$16;
                    $$14 = $$17;
                }
            }
        }

        private void m_106793_(int[] p_106794_, int[] p_106795_, boolean p_106796_, boolean p_106797_) {
            double $$4 = this.f_107223_.m_188583_() * 0.05;
            double $$5 = this.f_107223_.m_188583_() * 0.05;
            for (int $$6 = 0; $$6 < 70; ++$$6) {
                double $$7 = this.f_107215_ * 0.5 + this.f_107223_.m_188583_() * 0.15 + $$4;
                double $$8 = this.f_107217_ * 0.5 + this.f_107223_.m_188583_() * 0.15 + $$5;
                double $$9 = this.f_107216_ * 0.5 + this.f_107223_.m_188500_() * 0.5;
                this.m_106767_(this.f_107212_, this.f_107213_, this.f_107214_, $$7, $$9, $$8, p_106794_, p_106795_, p_106796_, p_106797_);
            }
        }
    }
}

