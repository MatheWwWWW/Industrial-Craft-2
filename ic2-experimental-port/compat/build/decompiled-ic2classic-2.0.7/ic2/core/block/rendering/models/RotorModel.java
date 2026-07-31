/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Quaternion
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
import com.mojang.math.Quaternion;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class RotorModel
extends Model {
    ModelPart shaft;
    ModelPart blade;

    public RotorModel(int size) {
        super(RenderType::m_110452_);
        size *= 8;
        MeshDefinition mesh = RotorModel.create(size += 2);
        this.shaft = mesh.m_171576_().m_171597_("shaft").m_171583_(64, 128);
        this.blade = mesh.m_171576_().m_171597_("blade").m_171583_(64, 128);
    }

    private static MeshDefinition create(int size) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition part = mesh.m_171576_();
        PartDefinition shaft = part.m_171599_("shaft", CubeListBuilder.m_171558_(), PartPose.f_171404_);
        PartDefinition blade = part.m_171599_("blade", CubeListBuilder.m_171558_(), PartPose.f_171404_);
        shaft.m_171599_("shaft", CubeListBuilder.m_171558_().m_171514_(29, 0).m_171481_(0.0f, -2.0f, -2.0f, 10.0f, 4.0f, 4.0f), PartPose.m_171419_((float)-14.0f, (float)0.0f, (float)0.0f));
        blade.m_171599_("wing", CubeListBuilder.m_171558_().m_171514_(48, 14).m_171481_(-2.0f, 2.0f, -2.0f, 4.0f, (float)size + 12.0f, 4.0f), PartPose.m_171419_((float)-12.0f, (float)0.0f, (float)0.0f));
        blade.m_171599_("fan", CubeListBuilder.m_171558_().m_171481_(-1.0f, 14.0f, -18.0f, 2.0f, (float)size, 16.0f), PartPose.m_171419_((float)-12.0f, (float)0.0f, (float)0.0f));
        return mesh;
    }

    public void m_7695_(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.shaft.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        int max = 4;
        for (int i = 0; i < max; ++i) {
            float value = 360 / max * i;
            matrixStackIn.m_85845_(new Quaternion(value, 0.0f, 0.0f, true));
            this.blade.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
}

