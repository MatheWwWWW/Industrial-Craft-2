/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;

public class EndCrystalRenderer
extends EntityRenderer<EndCrystal> {
    private static final ResourceLocation f_114132_ = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
    private static final RenderType f_114133_ = RenderType.m_110458_(f_114132_);
    private static final float f_114134_ = (float)Math.sin(0.7853981633974483);
    private static final String f_173967_ = "glass";
    private static final String f_173968_ = "base";
    private final ModelPart f_114135_;
    private final ModelPart f_114136_;
    private final ModelPart f_114137_;

    public EndCrystalRenderer(EntityRendererProvider.Context p_173970_) {
        super(p_173970_);
        this.f_114477_ = 0.5f;
        ModelPart $$1 = p_173970_.m_174023_(ModelLayers.f_171145_);
        this.f_114136_ = $$1.m_171324_(f_173967_);
        this.f_114135_ = $$1.m_171324_("cube");
        this.f_114137_ = $$1.m_171324_(f_173968_);
    }

    public static LayerDefinition m_173971_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_(f_173967_, CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        $$1.m_171599_("cube", CubeListBuilder.m_171558_().m_171514_(32, 0).m_171481_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        $$1.m_171599_(f_173968_, CubeListBuilder.m_171558_().m_171514_(0, 16).m_171481_(-6.0f, 0.0f, -6.0f, 12.0f, 4.0f, 12.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    @Override
    public void m_7392_(EndCrystal p_114162_, float p_114163_, float p_114164_, PoseStack p_114165_, MultiBufferSource p_114166_, int p_114167_) {
        p_114165_.m_85836_();
        float $$6 = EndCrystalRenderer.m_114158_(p_114162_, p_114164_);
        float $$7 = ((float)p_114162_.f_31032_ + p_114164_) * 3.0f;
        VertexConsumer $$8 = p_114166_.m_6299_(f_114133_);
        p_114165_.m_85836_();
        p_114165_.m_85841_(2.0f, 2.0f, 2.0f);
        p_114165_.m_85837_(0.0, -0.5, 0.0);
        int $$9 = OverlayTexture.f_118083_;
        if (p_114162_.m_31065_()) {
            this.f_114137_.m_104301_(p_114165_, $$8, p_114167_, $$9);
        }
        p_114165_.m_85845_(Vector3f.f_122225_.m_122240_($$7));
        p_114165_.m_85837_(0.0, 1.5f + $$6 / 2.0f, 0.0);
        p_114165_.m_85845_(new Quaternion(new Vector3f(f_114134_, 0.0f, f_114134_), 60.0f, true));
        this.f_114136_.m_104301_(p_114165_, $$8, p_114167_, $$9);
        float $$10 = 0.875f;
        p_114165_.m_85841_(0.875f, 0.875f, 0.875f);
        p_114165_.m_85845_(new Quaternion(new Vector3f(f_114134_, 0.0f, f_114134_), 60.0f, true));
        p_114165_.m_85845_(Vector3f.f_122225_.m_122240_($$7));
        this.f_114136_.m_104301_(p_114165_, $$8, p_114167_, $$9);
        p_114165_.m_85841_(0.875f, 0.875f, 0.875f);
        p_114165_.m_85845_(new Quaternion(new Vector3f(f_114134_, 0.0f, f_114134_), 60.0f, true));
        p_114165_.m_85845_(Vector3f.f_122225_.m_122240_($$7));
        this.f_114135_.m_104301_(p_114165_, $$8, p_114167_, $$9);
        p_114165_.m_85849_();
        p_114165_.m_85849_();
        BlockPos $$11 = p_114162_.m_31064_();
        if ($$11 != null) {
            float $$12 = (float)$$11.m_123341_() + 0.5f;
            float $$13 = (float)$$11.m_123342_() + 0.5f;
            float $$14 = (float)$$11.m_123343_() + 0.5f;
            float $$15 = (float)((double)$$12 - p_114162_.m_20185_());
            float $$16 = (float)((double)$$13 - p_114162_.m_20186_());
            float $$17 = (float)((double)$$14 - p_114162_.m_20189_());
            p_114165_.m_85837_($$15, $$16, $$17);
            EnderDragonRenderer.m_114187_(-$$15, -$$16 + $$6, -$$17, p_114164_, p_114162_.f_31032_, p_114165_, p_114166_, p_114167_);
        }
        super.m_7392_(p_114162_, p_114163_, p_114164_, p_114165_, p_114166_, p_114167_);
    }

    public static float m_114158_(EndCrystal p_114159_, float p_114160_) {
        float $$2 = (float)p_114159_.f_31032_ + p_114160_;
        float $$3 = Mth.m_14031_($$2 * 0.2f) / 2.0f + 0.5f;
        $$3 = ($$3 * $$3 + $$3) * 0.4f;
        return $$3 - 1.4f;
    }

    @Override
    public ResourceLocation m_5478_(EndCrystal p_114157_) {
        return f_114132_;
    }

    @Override
    public boolean m_5523_(EndCrystal p_114169_, Frustum p_114170_, double p_114171_, double p_114172_, double p_114173_) {
        return super.m_5523_(p_114169_, p_114170_, p_114171_, p_114172_, p_114173_) || p_114169_.m_31064_() != null;
    }
}

