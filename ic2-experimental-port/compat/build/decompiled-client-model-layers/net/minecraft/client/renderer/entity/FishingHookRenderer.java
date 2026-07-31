/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class FishingHookRenderer
extends EntityRenderer<FishingHook> {
    private static final ResourceLocation f_114678_ = new ResourceLocation("textures/entity/fishing_hook.png");
    private static final RenderType f_114679_ = RenderType.m_110452_(f_114678_);
    private static final double f_174115_ = 960.0;

    public FishingHookRenderer(EntityRendererProvider.Context p_174117_) {
        super(p_174117_);
    }

    @Override
    public void m_7392_(FishingHook p_114705_, float p_114706_, float p_114707_, PoseStack p_114708_, MultiBufferSource p_114709_, int p_114710_) {
        float $$29;
        double $$28;
        double $$27;
        double $$26;
        Player $$6 = p_114705_.m_37168_();
        if ($$6 == null) {
            return;
        }
        p_114708_.m_85836_();
        p_114708_.m_85836_();
        p_114708_.m_85841_(0.5f, 0.5f, 0.5f);
        p_114708_.m_85845_(this.f_114476_.m_114470_());
        p_114708_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        PoseStack.Pose $$7 = p_114708_.m_85850_();
        Matrix4f $$8 = $$7.m_85861_();
        Matrix3f $$9 = $$7.m_85864_();
        VertexConsumer $$10 = p_114709_.m_6299_(f_114679_);
        FishingHookRenderer.m_114711_($$10, $$8, $$9, p_114710_, 0.0f, 0, 0, 1);
        FishingHookRenderer.m_114711_($$10, $$8, $$9, p_114710_, 1.0f, 0, 1, 1);
        FishingHookRenderer.m_114711_($$10, $$8, $$9, p_114710_, 1.0f, 1, 1, 0);
        FishingHookRenderer.m_114711_($$10, $$8, $$9, p_114710_, 0.0f, 1, 0, 0);
        p_114708_.m_85849_();
        int $$11 = $$6.m_5737_() == HumanoidArm.RIGHT ? 1 : -1;
        ItemStack $$12 = $$6.m_21205_();
        if (!$$12.m_150930_(Items.f_42523_)) {
            $$11 = -$$11;
        }
        float $$13 = $$6.m_21324_(p_114707_);
        float $$14 = Mth.m_14031_(Mth.m_14116_($$13) * (float)Math.PI);
        float $$15 = Mth.m_14179_(p_114707_, $$6.f_20884_, $$6.f_20883_) * ((float)Math.PI / 180);
        double $$16 = Mth.m_14031_($$15);
        double $$17 = Mth.m_14089_($$15);
        double $$18 = (double)$$11 * 0.35;
        double $$19 = 0.8;
        if (this.f_114476_.f_114360_ != null && !this.f_114476_.f_114360_.m_92176_().m_90612_() || $$6 != Minecraft.m_91087_().f_91074_) {
            double $$20 = Mth.m_14139_(p_114707_, $$6.f_19854_, $$6.m_20185_()) - $$17 * $$18 - $$16 * 0.8;
            double $$21 = $$6.f_19855_ + (double)$$6.m_20192_() + ($$6.m_20186_() - $$6.f_19855_) * (double)p_114707_ - 0.45;
            double $$22 = Mth.m_14139_(p_114707_, $$6.f_19856_, $$6.m_20189_()) - $$16 * $$18 + $$17 * 0.8;
            float $$23 = $$6.m_6047_() ? -0.1875f : 0.0f;
        } else {
            double $$24 = 960.0 / (double)this.f_114476_.f_114360_.m_231837_().m_231551_().intValue();
            Vec3 $$25 = this.f_114476_.f_114358_.m_167684_().m_167695_((float)$$11 * 0.525f, -0.1f);
            $$25 = $$25.m_82490_($$24);
            $$25 = $$25.m_82524_($$14 * 0.5f);
            $$25 = $$25.m_82496_(-$$14 * 0.7f);
            $$26 = Mth.m_14139_(p_114707_, $$6.f_19854_, $$6.m_20185_()) + $$25.f_82479_;
            $$27 = Mth.m_14139_(p_114707_, $$6.f_19855_, $$6.m_20186_()) + $$25.f_82480_;
            $$28 = Mth.m_14139_(p_114707_, $$6.f_19856_, $$6.m_20189_()) + $$25.f_82481_;
            $$29 = $$6.m_20192_();
        }
        double $$30 = Mth.m_14139_(p_114707_, p_114705_.f_19854_, p_114705_.m_20185_());
        double $$31 = Mth.m_14139_(p_114707_, p_114705_.f_19855_, p_114705_.m_20186_()) + 0.25;
        double $$32 = Mth.m_14139_(p_114707_, p_114705_.f_19856_, p_114705_.m_20189_());
        float $$33 = (float)($$26 - $$30);
        float $$34 = (float)($$27 - $$31) + $$29;
        float $$35 = (float)($$28 - $$32);
        VertexConsumer $$36 = p_114709_.m_6299_(RenderType.m_173247_());
        PoseStack.Pose $$37 = p_114708_.m_85850_();
        int $$38 = 16;
        for (int $$39 = 0; $$39 <= 16; ++$$39) {
            FishingHookRenderer.m_174118_($$33, $$34, $$35, $$36, $$37, FishingHookRenderer.m_114690_($$39, 16), FishingHookRenderer.m_114690_($$39 + 1, 16));
        }
        p_114708_.m_85849_();
        super.m_7392_(p_114705_, p_114706_, p_114707_, p_114708_, p_114709_, p_114710_);
    }

    private static float m_114690_(int p_114691_, int p_114692_) {
        return (float)p_114691_ / (float)p_114692_;
    }

    private static void m_114711_(VertexConsumer p_114712_, Matrix4f p_114713_, Matrix3f p_114714_, int p_114715_, float p_114716_, int p_114717_, int p_114718_, int p_114719_) {
        p_114712_.m_85982_(p_114713_, p_114716_ - 0.5f, (float)p_114717_ - 0.5f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(p_114718_, p_114719_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114715_).m_85977_(p_114714_, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    private static void m_174118_(float p_174119_, float p_174120_, float p_174121_, VertexConsumer p_174122_, PoseStack.Pose p_174123_, float p_174124_, float p_174125_) {
        float $$7 = p_174119_ * p_174124_;
        float $$8 = p_174120_ * (p_174124_ * p_174124_ + p_174124_) * 0.5f + 0.25f;
        float $$9 = p_174121_ * p_174124_;
        float $$10 = p_174119_ * p_174125_ - $$7;
        float $$11 = p_174120_ * (p_174125_ * p_174125_ + p_174125_) * 0.5f + 0.25f - $$8;
        float $$12 = p_174121_ * p_174125_ - $$9;
        float $$13 = Mth.m_14116_($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
        p_174122_.m_85982_(p_174123_.m_85861_(), $$7, $$8, $$9).m_6122_(0, 0, 0, 255).m_85977_(p_174123_.m_85864_(), $$10 /= $$13, $$11 /= $$13, $$12 /= $$13).m_5752_();
    }

    @Override
    public ResourceLocation m_5478_(FishingHook p_114703_) {
        return f_114678_;
    }
}

