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
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value=Dist.CLIENT)
public class PersonalChestModel
extends Model {
    ModelPart chest;
    ModelPart door;

    public PersonalChestModel() {
        super(RenderType::m_110446_);
        MeshDefinition mesh = PersonalChestModel.create();
        this.chest = mesh.m_171576_().m_171597_("chest").m_171583_(64, 64);
        this.door = mesh.m_171576_().m_171597_("door").m_171583_(64, 64);
    }

    private static MeshDefinition create() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition fullMesh = mesh.m_171576_();
        PartDefinition chest = fullMesh.m_171599_("chest", CubeListBuilder.m_171558_(), PartPose.f_171404_);
        PartDefinition door = fullMesh.m_171599_("door", CubeListBuilder.m_171558_(), PartPose.f_171404_);
        chest.m_171599_("Right-Wall", CubeListBuilder.m_171558_().m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 14.0f, 16.0f, 1.0f), PartPose.m_171423_((float)1.0f, (float)0.0f, (float)15.0f, (float)0.0f, (float)1.570796f, (float)0.0f));
        chest.m_171599_("Left-Wall", CubeListBuilder.m_171558_().m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 14.0f, 16.0f, 1.0f), PartPose.m_171423_((float)15.0f, (float)0.0f, (float)1.0f, (float)0.0f, (float)-1.570796f, (float)0.0f));
        chest.m_171599_("Back-Wall", CubeListBuilder.m_171558_().m_171514_(1, 1).m_171480_().m_171481_(1.0f, 1.0f, 0.0f, 12.0f, 14.0f, 1.0f), PartPose.m_171423_((float)15.0f, (float)0.0f, (float)15.0f, (float)0.0f, (float)3.141593f, (float)0.0f));
        chest.m_171599_("Up-Wall", CubeListBuilder.m_171558_().m_171514_(1, 17).m_171480_().m_171481_(1.0f, 0.0f, 0.0f, 12.0f, 14.0f, 1.0f), PartPose.m_171423_((float)1.0f, (float)0.0f, (float)15.0f, (float)-1.570796f, (float)0.0f, (float)0.0f));
        chest.m_171599_("Down-Wall", CubeListBuilder.m_171558_().m_171514_(1, 17).m_171480_().m_171481_(1.0f, 0.0f, 0.0f, 12.0f, 14.0f, 1.0f), PartPose.m_171423_((float)15.0f, (float)15.0f, (float)1.0f, (float)-1.570796f, (float)3.141593f, (float)0.0f));
        door.m_171599_("door", CubeListBuilder.m_171558_().m_171514_(30, 0).m_171480_().m_171481_(0.0f, 0.0f, 0.0f, 12.0f, 14.0f, 1.0f), PartPose.m_171419_((float)0.0f, (float)0.0f, (float)0.0f));
        return mesh;
    }

    public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.chest.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.door.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void render(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float rotation) {
        this.chest.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        this.door.f_104204_ = rotation;
        this.door.f_104200_ = 2.0f;
        this.door.f_104201_ = 1.0f;
        this.door.f_104202_ = 2.0f;
        this.door.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
    }
}

