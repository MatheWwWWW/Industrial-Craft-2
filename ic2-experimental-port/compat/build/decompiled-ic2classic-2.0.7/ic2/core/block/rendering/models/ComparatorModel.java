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
package ic2.core.block.rendering.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ComparatorModel
extends Model {
    ModelPart model = ComparatorModel.createPart();

    public ComparatorModel() {
        super(RenderType::m_110446_);
    }

    private static ModelPart createPart() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.m_171576_();
        part.m_171599_("base", CubeListBuilder.m_171558_().m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 16.0f, 2.0f, 16.0f), PartPose.m_171423_((float)0.0f, (float)2.0f, (float)0.0f, (float)3.15f, (float)0.0f, (float)0.0f));
        part.m_171599_("left", CubeListBuilder.m_171558_().m_171514_(0, 20).m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 5.0f, 2.0f), PartPose.m_171423_((float)4.0f, (float)6.0f, (float)-3.0f, (float)3.15f, (float)0.0f, (float)0.0f));
        part.m_171599_("right", CubeListBuilder.m_171558_().m_171514_(10, 20).m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 5.0f, 2.0f), PartPose.m_171423_((float)10.0f, (float)6.0f, (float)-3.0f, (float)-3.15f, (float)0.0f, (float)0.0f));
        part.m_171599_("front", CubeListBuilder.m_171558_().m_171514_(20, 20).m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 2.0f, 2.0f, 2.0f), PartPose.m_171423_((float)7.0f, (float)4.0f, (float)-12.0f, (float)-3.15f, (float)0.0f, (float)0.0f));
        return part.m_171583_(64, 32);
    }

    public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.model.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn) {
        this.model.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}

