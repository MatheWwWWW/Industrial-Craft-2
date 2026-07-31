/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  net.minecraft.client.model.Model
 *  net.minecraft.client.model.geom.ModelPart
 *  net.minecraft.client.model.geom.PartPose
 *  net.minecraft.client.model.geom.builders.CubeListBuilder
 *  net.minecraft.client.model.geom.builders.MeshDefinition
 *  net.minecraft.client.model.geom.builders.PartDefinition
 *  net.minecraft.client.renderer.RenderType
 */
package ic2.core.item.renders.entitymodel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ModelShield
extends Model {
    ModelPart shield = ModelShield.create();

    private static ModelPart create() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.m_171576_();
        part.m_171599_("Shield1", CubeListBuilder.m_171558_().m_171514_(4, 0).m_171480_().m_171481_(-5.0f, -12.0f, -2.0f, 10.0f, 1.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield2", CubeListBuilder.m_171558_().m_171514_(2, 2).m_171480_().m_171481_(-6.0f, -11.0f, -2.0f, 12.0f, 1.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield3", CubeListBuilder.m_171558_().m_171514_(0, 4).m_171480_().m_171481_(-7.0f, -10.0f, -2.0f, 14.0f, 10.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield4", CubeListBuilder.m_171558_().m_171514_(2, 15).m_171480_().m_171481_(-6.0f, 0.0f, -2.0f, 12.0f, 6.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield5", CubeListBuilder.m_171558_().m_171514_(4, 22).m_171480_().m_171481_(-5.0f, 6.0f, -2.0f, 10.0f, 4.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield6", CubeListBuilder.m_171558_().m_171514_(6, 27).m_171480_().m_171481_(-4.0f, 10.0f, -2.0f, 8.0f, 2.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield7", CubeListBuilder.m_171558_().m_171514_(8, 30).m_171480_().m_171481_(-3.0f, 12.0f, -2.0f, 6.0f, 2.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield8", CubeListBuilder.m_171558_().m_171514_(10, 33).m_171480_().m_171481_(-2.0f, 14.0f, -2.0f, 4.0f, 1.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Shield9", CubeListBuilder.m_171558_().m_171514_(12, 35).m_171480_().m_171481_(-1.0f, 15.0f, -2.0f, 2.0f, 1.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Handle1", CubeListBuilder.m_171558_().m_171514_(34, 0).m_171480_().m_171481_(-1.0f, -3.0f, 0.0f, 2.0f, 1.0f, 4.0f), PartPose.f_171404_);
        part.m_171599_("Handle2", CubeListBuilder.m_171558_().m_171514_(34, 12).m_171480_().m_171481_(-1.0f, 2.0f, 0.0f, 2.0f, 1.0f, 4.0f), PartPose.f_171404_);
        part.m_171599_("Handle3", CubeListBuilder.m_171558_().m_171514_(34, 5).m_171480_().m_171481_(-1.0f, -3.0f, 4.0f, 2.0f, 6.0f, 1.0f), PartPose.f_171404_);
        part.m_171599_("Handle4", CubeListBuilder.m_171558_().m_171514_(34, 5).m_171480_().m_171481_(-1.0f, -3.0f, -1.0f, 2.0f, 6.0f, 1.0f), PartPose.f_171404_);
        return part.m_171583_(64, 64);
    }

    public ModelShield() {
        super(RenderType::m_110446_);
    }

    public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.shield.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
        this.shield.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}

