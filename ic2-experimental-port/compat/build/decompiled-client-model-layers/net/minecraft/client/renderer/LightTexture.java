/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.dimension.DimensionType;

public class LightTexture
implements AutoCloseable {
    public static final int f_173040_ = 0xF000F0;
    public static final int f_173041_ = 0xF00000;
    public static final int f_173042_ = 240;
    private final DynamicTexture f_109870_;
    private final NativeImage f_109871_;
    private final ResourceLocation f_109872_;
    private boolean f_109873_;
    private float f_109874_;
    private final GameRenderer f_109875_;
    private final Minecraft f_109876_;

    public LightTexture(GameRenderer p_109878_, Minecraft p_109879_) {
        this.f_109875_ = p_109878_;
        this.f_109876_ = p_109879_;
        this.f_109870_ = new DynamicTexture(16, 16, false);
        this.f_109872_ = this.f_109876_.m_91097_().m_118490_("light_map", this.f_109870_);
        this.f_109871_ = this.f_109870_.m_117991_();
        for (int $$2 = 0; $$2 < 16; ++$$2) {
            for (int $$3 = 0; $$3 < 16; ++$$3) {
                this.f_109871_.m_84988_($$3, $$2, -1);
            }
        }
        this.f_109870_.m_117985_();
    }

    @Override
    public void close() {
        this.f_109870_.close();
    }

    public void m_109880_() {
        this.f_109874_ += (float)((Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
        this.f_109874_ *= 0.9f;
        this.f_109873_ = true;
    }

    public void m_109891_() {
        RenderSystem.m_157453_(2, 0);
    }

    public void m_109896_() {
        RenderSystem.m_157456_(2, this.f_109872_);
        this.f_109876_.m_91097_().m_174784_(this.f_109872_);
        RenderSystem.m_69937_(3553, 10241, 9729);
        RenderSystem.m_69937_(3553, 10240, 9729);
        RenderSystem.m_157429_(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private float m_234319_(float p_234320_) {
        MobEffectInstance $$1;
        if (this.f_109876_.f_91074_.m_21023_(MobEffects.f_216964_) && ($$1 = this.f_109876_.f_91074_.m_21124_(MobEffects.f_216964_)) != null && $$1.m_216895_().isPresent()) {
            return $$1.m_216895_().get().m_238413_(this.f_109876_.f_91074_, p_234320_);
        }
        return 0.0f;
    }

    private float m_234312_(LivingEntity p_234313_, float p_234314_, float p_234315_) {
        float $$3 = 0.45f * p_234314_;
        return Math.max(0.0f, Mth.m_14089_(((float)p_234313_.f_19797_ - p_234315_) * (float)Math.PI * 0.025f) * $$3);
    }

    public void m_109881_(float p_109882_) {
        float $$11;
        float $$4;
        if (!this.f_109873_) {
            return;
        }
        this.f_109873_ = false;
        this.f_109876_.m_91307_().m_6180_("lightTex");
        ClientLevel $$1 = this.f_109876_.f_91073_;
        if ($$1 == null) {
            return;
        }
        float $$2 = $$1.m_104805_(1.0f);
        if ($$1.m_104819_() > 0) {
            float $$3 = 1.0f;
        } else {
            $$4 = $$2 * 0.95f + 0.05f;
        }
        float $$5 = this.f_109876_.f_91066_.m_231926_().m_231551_().floatValue();
        float $$6 = this.m_234319_(p_109882_) * $$5;
        float $$7 = this.m_234312_(this.f_109876_.f_91074_, $$6, p_109882_) * $$5;
        float $$8 = this.f_109876_.f_91074_.m_108639_();
        if (this.f_109876_.f_91074_.m_21023_(MobEffects.f_19611_)) {
            float $$9 = GameRenderer.m_109108_(this.f_109876_.f_91074_, p_109882_);
        } else if ($$8 > 0.0f && this.f_109876_.f_91074_.m_21023_(MobEffects.f_19592_)) {
            float $$10 = $$8;
        } else {
            $$11 = 0.0f;
        }
        Vector3f $$12 = new Vector3f($$2, $$2, 1.0f);
        $$12.m_122255_(new Vector3f(1.0f, 1.0f, 1.0f), 0.35f);
        float $$13 = this.f_109874_ + 1.5f;
        Vector3f $$14 = new Vector3f();
        for (int $$15 = 0; $$15 < 16; ++$$15) {
            for (int $$16 = 0; $$16 < 16; ++$$16) {
                float $$26;
                float $$18;
                float $$17 = LightTexture.m_234316_($$1.m_6042_(), $$15) * $$4;
                float $$19 = $$18 = LightTexture.m_234316_($$1.m_6042_(), $$16) * $$13;
                float $$20 = $$18 * (($$18 * 0.6f + 0.4f) * 0.6f + 0.4f);
                float $$21 = $$18 * ($$18 * $$18 * 0.6f + 0.4f);
                $$14.m_122245_($$19, $$20, $$21);
                boolean $$22 = $$1.m_104583_().m_108884_();
                if ($$22) {
                    $$14.m_122255_(new Vector3f(0.99f, 1.12f, 1.0f), 0.25f);
                    $$14.m_122242_(0.0f, 1.0f);
                } else {
                    Vector3f $$23 = $$12.m_122281_();
                    $$23.m_122261_($$17);
                    $$14.m_122253_($$23);
                    $$14.m_122255_(new Vector3f(0.75f, 0.75f, 0.75f), 0.04f);
                    if (this.f_109875_.m_109131_(p_109882_) > 0.0f) {
                        float $$24 = this.f_109875_.m_109131_(p_109882_);
                        Vector3f $$25 = $$14.m_122281_();
                        $$25.m_122263_(0.7f, 0.6f, 0.6f);
                        $$14.m_122255_($$25, $$24);
                    }
                }
                if ($$11 > 0.0f && ($$26 = Math.max($$14.m_122239_(), Math.max($$14.m_122260_(), $$14.m_122269_()))) < 1.0f) {
                    float $$27 = 1.0f / $$26;
                    Vector3f $$28 = $$14.m_122281_();
                    $$28.m_122261_($$27);
                    $$14.m_122255_($$28, $$11);
                }
                if (!$$22) {
                    if ($$7 > 0.0f) {
                        $$14.m_122272_(-$$7, -$$7, -$$7);
                    }
                    $$14.m_122242_(0.0f, 1.0f);
                }
                float $$29 = this.f_109876_.f_91066_.m_231927_().m_231551_().floatValue();
                Vector3f $$30 = $$14.m_122281_();
                $$30.m_122258_(this::m_109892_);
                $$14.m_122255_($$30, Math.max(0.0f, $$29 - $$6));
                $$14.m_122255_(new Vector3f(0.75f, 0.75f, 0.75f), 0.04f);
                $$14.m_122242_(0.0f, 1.0f);
                $$14.m_122261_(255.0f);
                int $$31 = 255;
                int $$32 = (int)$$14.m_122239_();
                int $$33 = (int)$$14.m_122260_();
                int $$34 = (int)$$14.m_122269_();
                this.f_109871_.m_84988_($$16, $$15, 0xFF000000 | $$34 << 16 | $$33 << 8 | $$32);
            }
        }
        this.f_109870_.m_117985_();
        this.f_109876_.m_91307_().m_7238_();
    }

    private float m_109892_(float p_109893_) {
        float $$1 = 1.0f - p_109893_;
        return 1.0f - $$1 * $$1 * $$1 * $$1;
    }

    public static float m_234316_(DimensionType p_234317_, int p_234318_) {
        float $$2 = (float)p_234318_ / 15.0f;
        float $$3 = $$2 / (4.0f - 3.0f * $$2);
        return Mth.m_14179_(p_234317_.f_63838_(), $$3, 1.0f);
    }

    public static int m_109885_(int p_109886_, int p_109887_) {
        return p_109886_ << 4 | p_109887_ << 20;
    }

    public static int m_109883_(int p_109884_) {
        return p_109884_ >> 4 & 0xFFFF;
    }

    public static int m_109894_(int p_109895_) {
        return p_109895_ >> 20 & 0xFFFF;
    }
}

