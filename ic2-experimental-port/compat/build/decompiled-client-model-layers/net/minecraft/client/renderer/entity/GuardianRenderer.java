/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.GuardianModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GuardianRenderer
extends MobRenderer<Guardian, GuardianModel> {
    private static final ResourceLocation f_114778_ = new ResourceLocation("textures/entity/guardian.png");
    private static final ResourceLocation f_114779_ = new ResourceLocation("textures/entity/guardian_beam.png");
    private static final RenderType f_114780_ = RenderType.m_110458_(f_114779_);

    public GuardianRenderer(EntityRendererProvider.Context p_174159_) {
        this(p_174159_, 0.5f, ModelLayers.f_171183_);
    }

    protected GuardianRenderer(EntityRendererProvider.Context p_174161_, float p_174162_, ModelLayerLocation p_174163_) {
        super(p_174161_, new GuardianModel(p_174161_.m_174023_(p_174163_)), p_174162_);
    }

    @Override
    public boolean m_5523_(Guardian p_114836_, Frustum p_114837_, double p_114838_, double p_114839_, double p_114840_) {
        LivingEntity $$5;
        if (super.m_5523_(p_114836_, p_114837_, p_114838_, p_114839_, p_114840_)) {
            return true;
        }
        if (p_114836_.m_32855_() && ($$5 = p_114836_.m_32856_()) != null) {
            Vec3 $$6 = this.m_114802_($$5, (double)$$5.m_20206_() * 0.5, 1.0f);
            Vec3 $$7 = this.m_114802_(p_114836_, p_114836_.m_20192_(), 1.0f);
            return p_114837_.m_113029_(new AABB($$7.f_82479_, $$7.f_82480_, $$7.f_82481_, $$6.f_82479_, $$6.f_82480_, $$6.f_82481_));
        }
        return false;
    }

    private Vec3 m_114802_(LivingEntity p_114803_, double p_114804_, float p_114805_) {
        double $$3 = Mth.m_14139_(p_114805_, p_114803_.f_19790_, p_114803_.m_20185_());
        double $$4 = Mth.m_14139_(p_114805_, p_114803_.f_19791_, p_114803_.m_20186_()) + p_114804_;
        double $$5 = Mth.m_14139_(p_114805_, p_114803_.f_19792_, p_114803_.m_20189_());
        return new Vec3($$3, $$4, $$5);
    }

    @Override
    public void m_7392_(Guardian p_114829_, float p_114830_, float p_114831_, PoseStack p_114832_, MultiBufferSource p_114833_, int p_114834_) {
        super.m_7392_(p_114829_, p_114830_, p_114831_, p_114832_, p_114833_, p_114834_);
        LivingEntity $$6 = p_114829_.m_32856_();
        if ($$6 != null) {
            float $$7 = p_114829_.m_32812_(p_114831_);
            float $$8 = (float)p_114829_.f_19853_.m_46467_() + p_114831_;
            float $$9 = $$8 * 0.5f % 1.0f;
            float $$10 = p_114829_.m_20192_();
            p_114832_.m_85836_();
            p_114832_.m_85837_(0.0, $$10, 0.0);
            Vec3 $$11 = this.m_114802_($$6, (double)$$6.m_20206_() * 0.5, p_114831_);
            Vec3 $$12 = this.m_114802_(p_114829_, $$10, p_114831_);
            Vec3 $$13 = $$11.m_82546_($$12);
            float $$14 = (float)($$13.m_82553_() + 1.0);
            $$13 = $$13.m_82541_();
            float $$15 = (float)Math.acos($$13.f_82480_);
            float $$16 = (float)Math.atan2($$13.f_82481_, $$13.f_82479_);
            p_114832_.m_85845_(Vector3f.f_122225_.m_122240_((1.5707964f - $$16) * 57.295776f));
            p_114832_.m_85845_(Vector3f.f_122223_.m_122240_($$15 * 57.295776f));
            boolean $$17 = true;
            float $$18 = $$8 * 0.05f * -1.5f;
            float $$19 = $$7 * $$7;
            int $$20 = 64 + (int)($$19 * 191.0f);
            int $$21 = 32 + (int)($$19 * 191.0f);
            int $$22 = 128 - (int)($$19 * 64.0f);
            float $$23 = 0.2f;
            float $$24 = 0.282f;
            float $$25 = Mth.m_14089_($$18 + 2.3561945f) * 0.282f;
            float $$26 = Mth.m_14031_($$18 + 2.3561945f) * 0.282f;
            float $$27 = Mth.m_14089_($$18 + 0.7853982f) * 0.282f;
            float $$28 = Mth.m_14031_($$18 + 0.7853982f) * 0.282f;
            float $$29 = Mth.m_14089_($$18 + 3.926991f) * 0.282f;
            float $$30 = Mth.m_14031_($$18 + 3.926991f) * 0.282f;
            float $$31 = Mth.m_14089_($$18 + 5.4977875f) * 0.282f;
            float $$32 = Mth.m_14031_($$18 + 5.4977875f) * 0.282f;
            float $$33 = Mth.m_14089_($$18 + (float)Math.PI) * 0.2f;
            float $$34 = Mth.m_14031_($$18 + (float)Math.PI) * 0.2f;
            float $$35 = Mth.m_14089_($$18 + 0.0f) * 0.2f;
            float $$36 = Mth.m_14031_($$18 + 0.0f) * 0.2f;
            float $$37 = Mth.m_14089_($$18 + 1.5707964f) * 0.2f;
            float $$38 = Mth.m_14031_($$18 + 1.5707964f) * 0.2f;
            float $$39 = Mth.m_14089_($$18 + 4.712389f) * 0.2f;
            float $$40 = Mth.m_14031_($$18 + 4.712389f) * 0.2f;
            float $$41 = $$14;
            float $$42 = 0.0f;
            float $$43 = 0.4999f;
            float $$44 = -1.0f + $$9;
            float $$45 = $$14 * 2.5f + $$44;
            VertexConsumer $$46 = p_114833_.m_6299_(f_114780_);
            PoseStack.Pose $$47 = p_114832_.m_85850_();
            Matrix4f $$48 = $$47.m_85861_();
            Matrix3f $$49 = $$47.m_85864_();
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$33, $$41, $$34, $$20, $$21, $$22, 0.4999f, $$45);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$33, 0.0f, $$34, $$20, $$21, $$22, 0.4999f, $$44);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$35, 0.0f, $$36, $$20, $$21, $$22, 0.0f, $$44);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$35, $$41, $$36, $$20, $$21, $$22, 0.0f, $$45);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$37, $$41, $$38, $$20, $$21, $$22, 0.4999f, $$45);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$37, 0.0f, $$38, $$20, $$21, $$22, 0.4999f, $$44);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$39, 0.0f, $$40, $$20, $$21, $$22, 0.0f, $$44);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$39, $$41, $$40, $$20, $$21, $$22, 0.0f, $$45);
            float $$50 = 0.0f;
            if (p_114829_.f_19797_ % 2 == 0) {
                $$50 = 0.5f;
            }
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$25, $$41, $$26, $$20, $$21, $$22, 0.5f, $$50 + 0.5f);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$27, $$41, $$28, $$20, $$21, $$22, 1.0f, $$50 + 0.5f);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$31, $$41, $$32, $$20, $$21, $$22, 1.0f, $$50);
            GuardianRenderer.m_114841_($$46, $$48, $$49, $$29, $$41, $$30, $$20, $$21, $$22, 0.5f, $$50);
            p_114832_.m_85849_();
        }
    }

    private static void m_114841_(VertexConsumer p_114842_, Matrix4f p_114843_, Matrix3f p_114844_, float p_114845_, float p_114846_, float p_114847_, int p_114848_, int p_114849_, int p_114850_, float p_114851_, float p_114852_) {
        p_114842_.m_85982_(p_114843_, p_114845_, p_114846_, p_114847_).m_6122_(p_114848_, p_114849_, p_114850_, 255).m_7421_(p_114851_, p_114852_).m_86008_(OverlayTexture.f_118083_).m_85969_(0xF000F0).m_85977_(p_114844_, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    @Override
    public ResourceLocation m_5478_(Guardian p_114827_) {
        return f_114778_;
    }
}

