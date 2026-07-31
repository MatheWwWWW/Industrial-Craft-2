/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix3f
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.block.beam;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import ic2.core.block.beam.ParticleEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BeamRenderer
extends EntityRenderer<ParticleEntity> {
    private static final ResourceLocation texture = new ResourceLocation("ic2", "textures/models/beam.png");

    public BeamRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(ParticleEntity particleEntity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        float f3 = 0.0f;
        float f4 = 1.0f;
        float f5 = 0.0f;
        float f6 = 1.0f;
        float f7 = 0.1f;
        int n2 = 255;
        int n3 = 255;
        int n4 = 255;
        int n5 = 255;
        poseStack.m_85836_();
        poseStack.m_85845_(this.f_114476_.m_114470_());
        VertexConsumer vertexConsumer = multiBufferSource.m_6299_(RenderType.m_110467_((ResourceLocation)texture));
        PoseStack.Pose pose = poseStack.m_85850_();
        Matrix4f matrix4f = pose.m_85861_();
        Matrix3f matrix3f = pose.m_85864_();
        for (int i = 0; i < 4; ++i) {
            float f8;
            float f9;
            float f10;
            float f11;
            if (i < 2) {
                f11 = -f7;
                f10 = f3;
            } else {
                f11 = f7;
                f10 = f4;
            }
            if (i == 0 || i == 3) {
                f9 = -f7;
                f8 = f5;
            } else {
                f9 = f7;
                f8 = f6;
            }
            vertexConsumer.m_85982_(matrix4f, f11, f9, 0.0f).m_6122_(n2, n3, n4, n5).m_7421_(f10, f8).m_86008_(OverlayTexture.f_118083_).m_85969_(n).m_85977_(matrix3f, 0.0f, 1.0f, 0.0f).m_5752_();
        }
        poseStack.m_85849_();
    }

    public ResourceLocation getTexture(ParticleEntity particleEntity) {
        return texture;
    }
}

