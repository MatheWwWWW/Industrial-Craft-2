/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Matrix3f
 *  com.mojang.math.Matrix4f
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 */
package trinsdar.gravisuit.util.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import trinsdar.gravisuit.entity.PlasmaBall;

public class PlasmaBallRenderer
extends EntityRenderer<PlasmaBall> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("gravisuit", "textures/entity/plasma_ball.png");
    private static final RenderType RENDER_TYPE = RenderType.m_110443_((ResourceLocation)TEXTURE, (boolean)false);

    public PlasmaBallRenderer(EntityRendererProvider.Context arg) {
        super(arg);
    }

    public void render(PlasmaBall entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.m_85836_();
        matrixStack.m_85841_(2.0f, 2.0f, 2.0f);
        matrixStack.m_85845_(this.f_114476_.m_114470_());
        matrixStack.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        PoseStack.Pose pose = matrixStack.m_85850_();
        Matrix4f matrix4f = pose.m_85861_();
        Matrix3f matrix3f = pose.m_85864_();
        VertexConsumer vertexConsumer = buffer.m_6299_(RENDER_TYPE);
        PlasmaBallRenderer.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0f, 0, 0, 1);
        PlasmaBallRenderer.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0f, 0, 1, 1);
        PlasmaBallRenderer.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 1.0f, 1, 1, 0);
        PlasmaBallRenderer.vertex(vertexConsumer, matrix4f, matrix3f, packedLight, 0.0f, 1, 0, 0);
        matrixStack.m_85849_();
        super.m_7392_((Entity)entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer arg, Matrix4f arg2, Matrix3f arg3, int i, float f, int j, int k, int l) {
        arg.m_85982_(arg2, f - 0.5f, (float)j - 0.25f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_((float)k, (float)l).m_86008_(OverlayTexture.f_118083_).m_85969_(i).m_85977_(arg3, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    public ResourceLocation getTextureLocation(PlasmaBall entity) {
        return TEXTURE;
    }
}

