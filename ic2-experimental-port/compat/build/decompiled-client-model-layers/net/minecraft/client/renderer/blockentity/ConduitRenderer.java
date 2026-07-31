/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;

public class ConduitRenderer
implements BlockEntityRenderer<ConduitBlockEntity> {
    public static final Material f_112378_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/base"));
    public static final Material f_112379_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/cage"));
    public static final Material f_112380_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/wind"));
    public static final Material f_112381_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/wind_vertical"));
    public static final Material f_112382_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/open_eye"));
    public static final Material f_112383_ = new Material(TextureAtlas.f_118259_, new ResourceLocation("entity/conduit/closed_eye"));
    private final ModelPart f_112384_;
    private final ModelPart f_112385_;
    private final ModelPart f_112386_;
    private final ModelPart f_112387_;
    private final BlockEntityRenderDispatcher f_173611_;

    public ConduitRenderer(BlockEntityRendererProvider.Context p_173613_) {
        this.f_173611_ = p_173613_.m_173581_();
        this.f_112384_ = p_173613_.m_173582_(ModelLayers.f_171281_);
        this.f_112385_ = p_173613_.m_173582_(ModelLayers.f_171283_);
        this.f_112386_ = p_173613_.m_173582_(ModelLayers.f_171282_);
        this.f_112387_ = p_173613_.m_173582_(ModelLayers.f_171280_);
    }

    public static LayerDefinition m_173614_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("eye", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171488_(-4.0f, -4.0f, 0.0f, 8.0f, 8.0f, 0.0f, new CubeDeformation(0.01f)), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 16, 16);
    }

    public static LayerDefinition m_173615_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("wind", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-8.0f, -8.0f, -8.0f, 16.0f, 16.0f, 16.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 64, 32);
    }

    public static LayerDefinition m_173616_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-3.0f, -3.0f, -3.0f, 6.0f, 6.0f, 6.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 32, 16);
    }

    public static LayerDefinition m_173617_() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.m_171576_();
        $$1.m_171599_("shell", CubeListBuilder.m_171558_().m_171514_(0, 0).m_171481_(-4.0f, -4.0f, -4.0f, 8.0f, 8.0f, 8.0f), PartPose.f_171404_);
        return LayerDefinition.m_171565_($$0, 32, 16);
    }

    @Override
    public void m_6922_(ConduitBlockEntity p_112399_, float p_112400_, PoseStack p_112401_, MultiBufferSource p_112402_, int p_112403_, int p_112404_) {
        float $$6 = (float)p_112399_.f_59183_ + p_112400_;
        if (!p_112399_.m_59216_()) {
            float $$7 = p_112399_.m_59197_(0.0f);
            VertexConsumer $$8 = f_112378_.m_119194_(p_112402_, RenderType::m_110446_);
            p_112401_.m_85836_();
            p_112401_.m_85837_(0.5, 0.5, 0.5);
            p_112401_.m_85845_(Vector3f.f_122225_.m_122240_($$7));
            this.f_112386_.m_104301_(p_112401_, $$8, p_112403_, p_112404_);
            p_112401_.m_85849_();
            return;
        }
        float $$9 = p_112399_.m_59197_(p_112400_) * 57.295776f;
        float $$10 = Mth.m_14031_($$6 * 0.1f) / 2.0f + 0.5f;
        $$10 = $$10 * $$10 + $$10;
        p_112401_.m_85836_();
        p_112401_.m_85837_(0.5, 0.3f + $$10 * 0.2f, 0.5);
        Vector3f $$11 = new Vector3f(0.5f, 1.0f, 0.5f);
        $$11.m_122278_();
        p_112401_.m_85845_($$11.m_122240_($$9));
        this.f_112387_.m_104301_(p_112401_, f_112379_.m_119194_(p_112402_, RenderType::m_110458_), p_112403_, p_112404_);
        p_112401_.m_85849_();
        int $$12 = p_112399_.f_59183_ / 66 % 3;
        p_112401_.m_85836_();
        p_112401_.m_85837_(0.5, 0.5, 0.5);
        if ($$12 == 1) {
            p_112401_.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
        } else if ($$12 == 2) {
            p_112401_.m_85845_(Vector3f.f_122227_.m_122240_(90.0f));
        }
        VertexConsumer $$13 = ($$12 == 1 ? f_112381_ : f_112380_).m_119194_(p_112402_, RenderType::m_110458_);
        this.f_112385_.m_104301_(p_112401_, $$13, p_112403_, p_112404_);
        p_112401_.m_85849_();
        p_112401_.m_85836_();
        p_112401_.m_85837_(0.5, 0.5, 0.5);
        p_112401_.m_85841_(0.875f, 0.875f, 0.875f);
        p_112401_.m_85845_(Vector3f.f_122223_.m_122240_(180.0f));
        p_112401_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
        this.f_112385_.m_104301_(p_112401_, $$13, p_112403_, p_112404_);
        p_112401_.m_85849_();
        Camera $$14 = this.f_173611_.f_112249_;
        p_112401_.m_85836_();
        p_112401_.m_85837_(0.5, 0.3f + $$10 * 0.2f, 0.5);
        p_112401_.m_85841_(0.5f, 0.5f, 0.5f);
        float $$15 = -$$14.m_90590_();
        p_112401_.m_85845_(Vector3f.f_122225_.m_122240_($$15));
        p_112401_.m_85845_(Vector3f.f_122223_.m_122240_($$14.m_90589_()));
        p_112401_.m_85845_(Vector3f.f_122227_.m_122240_(180.0f));
        float $$16 = 1.3333334f;
        p_112401_.m_85841_(1.3333334f, 1.3333334f, 1.3333334f);
        this.f_112384_.m_104301_(p_112401_, (p_112399_.m_59217_() ? f_112382_ : f_112383_).m_119194_(p_112402_, RenderType::m_110458_), p_112403_, p_112404_);
        p_112401_.m_85849_();
    }
}

