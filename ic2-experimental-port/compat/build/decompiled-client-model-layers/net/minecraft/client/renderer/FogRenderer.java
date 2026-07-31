/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;

public class FogRenderer {
    private static final int f_172575_ = 96;
    private static final List<MobEffectFogFunction> f_234164_ = Lists.newArrayList((Object[])new MobEffectFogFunction[]{new BlindnessFogFunction(), new DarknessFogFunction()});
    public static final float f_172574_ = 5000.0f;
    private static float f_109010_;
    private static float f_109011_;
    private static float f_109012_;
    private static int f_109013_;
    private static int f_109014_;
    private static long f_109015_;

    public static void m_109018_(Camera p_109019_, float p_109020_, ClientLevel p_109021_, int p_109022_, float p_109023_) {
        float $$44;
        LivingEntity $$42;
        FogType $$5 = p_109019_.m_167685_();
        Entity $$6 = p_109019_.m_90592_();
        if ($$5 == FogType.WATER) {
            long $$7 = Util.m_137550_();
            int $$8 = p_109021_.m_204166_(new BlockPos(p_109019_.m_90583_())).m_203334_().m_47561_();
            if (f_109015_ < 0L) {
                f_109013_ = $$8;
                f_109014_ = $$8;
                f_109015_ = $$7;
            }
            int $$9 = f_109013_ >> 16 & 0xFF;
            int $$10 = f_109013_ >> 8 & 0xFF;
            int $$11 = f_109013_ & 0xFF;
            int $$12 = f_109014_ >> 16 & 0xFF;
            int $$13 = f_109014_ >> 8 & 0xFF;
            int $$14 = f_109014_ & 0xFF;
            float $$15 = Mth.m_14036_((float)($$7 - f_109015_) / 5000.0f, 0.0f, 1.0f);
            float $$16 = Mth.m_14179_($$15, $$12, $$9);
            float $$17 = Mth.m_14179_($$15, $$13, $$10);
            float $$18 = Mth.m_14179_($$15, $$14, $$11);
            f_109010_ = $$16 / 255.0f;
            f_109011_ = $$17 / 255.0f;
            f_109012_ = $$18 / 255.0f;
            if (f_109013_ != $$8) {
                f_109013_ = $$8;
                f_109014_ = Mth.m_14143_($$16) << 16 | Mth.m_14143_($$17) << 8 | Mth.m_14143_($$18);
                f_109015_ = $$7;
            }
        } else if ($$5 == FogType.LAVA) {
            f_109010_ = 0.6f;
            f_109011_ = 0.1f;
            f_109012_ = 0.0f;
            f_109015_ = -1L;
        } else if ($$5 == FogType.POWDER_SNOW) {
            f_109010_ = 0.623f;
            f_109011_ = 0.734f;
            f_109012_ = 0.785f;
            f_109015_ = -1L;
            RenderSystem.m_69424_(f_109010_, f_109011_, f_109012_, 0.0f);
        } else {
            float $$35;
            float $$19 = 0.25f + 0.75f * (float)p_109022_ / 32.0f;
            $$19 = 1.0f - (float)Math.pow($$19, 0.25);
            Vec3 $$20 = p_109021_.m_171660_(p_109019_.m_90583_(), p_109020_);
            float $$21 = (float)$$20.f_82479_;
            float $$22 = (float)$$20.f_82480_;
            float $$23 = (float)$$20.f_82481_;
            float $$24 = Mth.m_14036_(Mth.m_14089_(p_109021_.m_46942_(p_109020_) * ((float)Math.PI * 2)) * 2.0f + 0.5f, 0.0f, 1.0f);
            BiomeManager $$25 = p_109021_.m_7062_();
            Vec3 $$26 = p_109019_.m_90583_().m_82492_(2.0, 2.0, 2.0).m_82490_(0.25);
            Vec3 $$27 = CubicSampler.m_130038_($$26, (p_109033_, p_109034_, p_109035_) -> p_109021_.m_104583_().m_5927_(Vec3.m_82501_($$25.m_204210_(p_109033_, p_109034_, p_109035_).m_203334_().m_47539_()), $$24));
            f_109010_ = (float)$$27.m_7096_();
            f_109011_ = (float)$$27.m_7098_();
            f_109012_ = (float)$$27.m_7094_();
            if (p_109022_ >= 4) {
                float[] $$31;
                float $$28 = Mth.m_14031_(p_109021_.m_46490_(p_109020_)) > 0.0f ? -1.0f : 1.0f;
                Vector3f $$29 = new Vector3f($$28, 0.0f, 0.0f);
                float $$30 = p_109019_.m_90596_().m_122276_($$29);
                if ($$30 < 0.0f) {
                    $$30 = 0.0f;
                }
                if ($$30 > 0.0f && ($$31 = p_109021_.m_104583_().m_7518_(p_109021_.m_46942_(p_109020_), p_109020_)) != null) {
                    f_109010_ = f_109010_ * (1.0f - ($$30 *= $$31[3])) + $$31[0] * $$30;
                    f_109011_ = f_109011_ * (1.0f - $$30) + $$31[1] * $$30;
                    f_109012_ = f_109012_ * (1.0f - $$30) + $$31[2] * $$30;
                }
            }
            f_109010_ += ($$21 - f_109010_) * $$19;
            f_109011_ += ($$22 - f_109011_) * $$19;
            f_109012_ += ($$23 - f_109012_) * $$19;
            float $$32 = p_109021_.m_46722_(p_109020_);
            if ($$32 > 0.0f) {
                float $$33 = 1.0f - $$32 * 0.5f;
                float $$34 = 1.0f - $$32 * 0.4f;
                f_109010_ *= $$33;
                f_109011_ *= $$33;
                f_109012_ *= $$34;
            }
            if (($$35 = p_109021_.m_46661_(p_109020_)) > 0.0f) {
                float $$36 = 1.0f - $$35 * 0.5f;
                f_109010_ *= $$36;
                f_109011_ *= $$36;
                f_109012_ *= $$36;
            }
            f_109015_ = -1L;
        }
        float $$37 = ((float)p_109019_.m_90583_().f_82480_ - (float)p_109021_.m_141937_()) * p_109021_.m_6106_().m_205519_();
        MobEffectFogFunction $$38 = FogRenderer.m_234165_($$6, p_109020_);
        if ($$38 != null) {
            LivingEntity $$39 = (LivingEntity)$$6;
            $$37 = $$38.m_213936_($$39, $$39.m_21124_($$38.m_213948_()), $$37, p_109020_);
        }
        if ($$37 < 1.0f && $$5 != FogType.LAVA && $$5 != FogType.POWDER_SNOW) {
            if ($$37 < 0.0f) {
                $$37 = 0.0f;
            }
            $$37 *= $$37;
            f_109010_ *= $$37;
            f_109011_ *= $$37;
            f_109012_ *= $$37;
        }
        if (p_109023_ > 0.0f) {
            f_109010_ = f_109010_ * (1.0f - p_109023_) + f_109010_ * 0.7f * p_109023_;
            f_109011_ = f_109011_ * (1.0f - p_109023_) + f_109011_ * 0.6f * p_109023_;
            f_109012_ = f_109012_ * (1.0f - p_109023_) + f_109012_ * 0.6f * p_109023_;
        }
        if ($$5 == FogType.WATER) {
            if ($$6 instanceof LocalPlayer) {
                float $$40 = ((LocalPlayer)$$6).m_108639_();
            } else {
                float $$41 = 1.0f;
            }
        } else if ($$6 instanceof LivingEntity && ($$42 = (LivingEntity)$$6).m_21023_(MobEffects.f_19611_) && !$$42.m_21023_(MobEffects.f_216964_)) {
            float $$43 = GameRenderer.m_109108_($$42, p_109020_);
        } else {
            $$44 = 0.0f;
        }
        if (f_109010_ != 0.0f && f_109011_ != 0.0f && f_109012_ != 0.0f) {
            float $$45 = Math.min(1.0f / f_109010_, Math.min(1.0f / f_109011_, 1.0f / f_109012_));
            f_109010_ = f_109010_ * (1.0f - $$44) + f_109010_ * $$45 * $$44;
            f_109011_ = f_109011_ * (1.0f - $$44) + f_109011_ * $$45 * $$44;
            f_109012_ = f_109012_ * (1.0f - $$44) + f_109012_ * $$45 * $$44;
        }
        RenderSystem.m_69424_(f_109010_, f_109011_, f_109012_, 0.0f);
    }

    public static void m_109017_() {
        RenderSystem.m_157445_(Float.MAX_VALUE);
    }

    @Nullable
    private static MobEffectFogFunction m_234165_(Entity p_234166_, float p_234167_) {
        if (p_234166_ instanceof LivingEntity) {
            LivingEntity $$2 = (LivingEntity)p_234166_;
            return f_234164_.stream().filter(p_234171_ -> p_234171_.m_234205_($$2, p_234167_)).findFirst().orElse(null);
        }
        return null;
    }

    public static void m_234172_(Camera p_234173_, FogMode p_234174_, float p_234175_, boolean p_234176_, float p_234177_) {
        FogType $$5 = p_234173_.m_167685_();
        Entity $$6 = p_234173_.m_90592_();
        FogData $$7 = new FogData(p_234174_);
        MobEffectFogFunction $$8 = FogRenderer.m_234165_($$6, p_234177_);
        if ($$5 == FogType.LAVA) {
            if ($$6.m_5833_()) {
                $$7.f_234200_ = -8.0f;
                $$7.f_234201_ = p_234175_ * 0.5f;
            } else if ($$6 instanceof LivingEntity && ((LivingEntity)$$6).m_21023_(MobEffects.f_19607_)) {
                $$7.f_234200_ = 0.0f;
                $$7.f_234201_ = 3.0f;
            } else {
                $$7.f_234200_ = 0.25f;
                $$7.f_234201_ = 1.0f;
            }
        } else if ($$5 == FogType.POWDER_SNOW) {
            if ($$6.m_5833_()) {
                $$7.f_234200_ = -8.0f;
                $$7.f_234201_ = p_234175_ * 0.5f;
            } else {
                $$7.f_234200_ = 0.0f;
                $$7.f_234201_ = 2.0f;
            }
        } else if ($$8 != null) {
            LivingEntity $$9 = (LivingEntity)$$6;
            MobEffectInstance $$10 = $$9.m_21124_($$8.m_213948_());
            if ($$10 != null) {
                $$8.m_213725_($$7, $$9, $$10, p_234175_, p_234177_);
            }
        } else if ($$5 == FogType.WATER) {
            $$7.f_234200_ = -8.0f;
            $$7.f_234201_ = 96.0f;
            if ($$6 instanceof LocalPlayer) {
                LocalPlayer $$11 = (LocalPlayer)$$6;
                $$7.f_234201_ *= Math.max(0.25f, $$11.m_108639_());
                Holder<Biome> $$12 = $$11.f_19853_.m_204166_($$11.m_20183_());
                if ($$12.m_203656_(BiomeTags.f_215802_)) {
                    $$7.f_234201_ *= 0.85f;
                }
            }
            if ($$7.f_234201_ > p_234175_) {
                $$7.f_234201_ = p_234175_;
                $$7.f_234202_ = FogShape.CYLINDER;
            }
        } else if (p_234176_) {
            $$7.f_234200_ = p_234175_ * 0.05f;
            $$7.f_234201_ = Math.min(p_234175_, 192.0f) * 0.5f;
        } else if (p_234174_ == FogMode.FOG_SKY) {
            $$7.f_234200_ = 0.0f;
            $$7.f_234201_ = p_234175_;
            $$7.f_234202_ = FogShape.CYLINDER;
        } else {
            float $$13 = Mth.m_14036_(p_234175_ / 10.0f, 4.0f, 64.0f);
            $$7.f_234200_ = p_234175_ - $$13;
            $$7.f_234201_ = p_234175_;
            $$7.f_234202_ = FogShape.CYLINDER;
        }
        RenderSystem.m_157445_($$7.f_234200_);
        RenderSystem.m_157443_($$7.f_234201_);
        RenderSystem.m_202160_($$7.f_234202_);
    }

    public static void m_109036_() {
        RenderSystem.m_157434_(f_109010_, f_109011_, f_109012_);
    }

    static {
        f_109013_ = -1;
        f_109014_ = -1;
        f_109015_ = -1L;
    }

    static interface MobEffectFogFunction {
        public MobEffect m_213948_();

        public void m_213725_(FogData var1, LivingEntity var2, MobEffectInstance var3, float var4, float var5);

        default public boolean m_234205_(LivingEntity p_234206_, float p_234207_) {
            return p_234206_.m_21023_(this.m_213948_());
        }

        default public float m_213936_(LivingEntity p_234208_, MobEffectInstance p_234209_, float p_234210_, float p_234211_) {
            MobEffectInstance $$4 = p_234208_.m_21124_(this.m_213948_());
            if ($$4 != null) {
                p_234210_ = $$4.m_19557_() < 20 ? 1.0f - (float)$$4.m_19557_() / 20.0f : 0.0f;
            }
            return p_234210_;
        }
    }

    static class FogData {
        public final FogMode f_234199_;
        public float f_234200_;
        public float f_234201_;
        public FogShape f_234202_ = FogShape.SPHERE;

        public FogData(FogMode p_234204_) {
            this.f_234199_ = p_234204_;
        }
    }

    public static final class FogMode
    extends Enum<FogMode> {
        public static final /* enum */ FogMode FOG_SKY = new FogMode();
        public static final /* enum */ FogMode FOG_TERRAIN = new FogMode();
        private static final /* synthetic */ FogMode[] $VALUES;

        public static FogMode[] values() {
            return (FogMode[])$VALUES.clone();
        }

        public static FogMode valueOf(String p_109045_) {
            return Enum.valueOf(FogMode.class, p_109045_);
        }

        private static /* synthetic */ FogMode[] m_172577_() {
            return new FogMode[]{FOG_SKY, FOG_TERRAIN};
        }

        static {
            $VALUES = FogMode.m_172577_();
        }
    }

    static class BlindnessFogFunction
    implements MobEffectFogFunction {
        BlindnessFogFunction() {
        }

        @Override
        public MobEffect m_213948_() {
            return MobEffects.f_19610_;
        }

        @Override
        public void m_213725_(FogData p_234181_, LivingEntity p_234182_, MobEffectInstance p_234183_, float p_234184_, float p_234185_) {
            float $$5 = Mth.m_14179_(Math.min(1.0f, (float)p_234183_.m_19557_() / 20.0f), p_234184_, 5.0f);
            if (p_234181_.f_234199_ == FogMode.FOG_SKY) {
                p_234181_.f_234200_ = 0.0f;
                p_234181_.f_234201_ = $$5 * 0.8f;
            } else {
                p_234181_.f_234200_ = $$5 * 0.25f;
                p_234181_.f_234201_ = $$5;
            }
        }
    }

    static class DarknessFogFunction
    implements MobEffectFogFunction {
        DarknessFogFunction() {
        }

        @Override
        public MobEffect m_213948_() {
            return MobEffects.f_216964_;
        }

        @Override
        public void m_213725_(FogData p_234194_, LivingEntity p_234195_, MobEffectInstance p_234196_, float p_234197_, float p_234198_) {
            if (p_234196_.m_216895_().isEmpty()) {
                return;
            }
            float $$5 = Mth.m_14179_(p_234196_.m_216895_().get().m_238413_(p_234195_, p_234198_), p_234197_, 15.0f);
            p_234194_.f_234200_ = p_234194_.f_234199_ == FogMode.FOG_SKY ? 0.0f : $$5 * 0.75f;
            p_234194_.f_234201_ = $$5;
        }

        @Override
        public float m_213936_(LivingEntity p_234189_, MobEffectInstance p_234190_, float p_234191_, float p_234192_) {
            if (p_234190_.m_216895_().isEmpty()) {
                return 0.0f;
            }
            return 1.0f - p_234190_.m_216895_().get().m_238413_(p_234189_, p_234192_);
        }
    }
}

