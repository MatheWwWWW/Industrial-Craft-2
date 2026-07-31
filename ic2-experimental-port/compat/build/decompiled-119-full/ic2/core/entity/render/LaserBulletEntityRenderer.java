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
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 */
package ic2.core.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import ic2.core.IC2;
import ic2.core.entity.LaserBulletEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class LaserBulletEntityRenderer
extends EntityRenderer<LaserBulletEntity> {
    private static final ResourceLocation TEXTURE = IC2.getIdentifier("textures/models/laser.png");

    public LaserBulletEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(LaserBulletEntity laserBulletEntity, float f, float f2, PoseStack poseStack, MultiBufferSource multiBufferSource, int n) {
        poseStack.m_85836_();
        poseStack.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_((float)f2, (float)laserBulletEntity.f_19859_, (float)laserBulletEntity.m_146908_()) - 90.0f));
        poseStack.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_((float)f2, (float)laserBulletEntity.f_19860_, (float)laserBulletEntity.m_146909_())));
        poseStack.m_85845_(Vector3f.f_122227_.m_122240_(0.0f));
        poseStack.m_85845_(Vector3f.f_122223_.m_122240_(45.0f));
        poseStack.m_85841_(0.05625f, 0.05625f, 0.05625f);
        poseStack.m_85837_(-4.0, 0.0, 0.0);
        VertexConsumer vertexConsumer = multiBufferSource.m_6299_(RenderType.m_110452_((ResourceLocation)this.getTexture(laserBulletEntity)));
        PoseStack.Pose pose = poseStack.m_85850_();
        Matrix4f matrix4f = pose.m_85861_();
        Matrix3f matrix3f = pose.m_85864_();
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0f, 0.15625f, -1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625f, 0.15625f, -1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625f, 0.3125f, -1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0f, 0.3125f, -1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0f, 0.15625f, 1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625f, 0.15625f, 1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625f, 0.3125f, 1, 0, 0, n);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0f, 0.3125f, 1, 0, 0, n);
        for (int i = 0; i < 4; ++i) {
            poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0f));
            this.vertex(matrix4f, matrix3f, vertexConsumer, -8, -2, 0, 0.0f, 0.0f, 0, 1, 0, n);
            this.vertex(matrix4f, matrix3f, vertexConsumer, 8, -2, 0, 0.5f, 0.0f, 0, 1, 0, n);
            this.vertex(matrix4f, matrix3f, vertexConsumer, 8, 2, 0, 0.5f, 0.15625f, 0, 1, 0, n);
            this.vertex(matrix4f, matrix3f, vertexConsumer, -8, 2, 0, 0.0f, 0.15625f, 0, 1, 0, n);
        }
        poseStack.m_85849_();
        super.m_7392_((Entity)laserBulletEntity, f, f2, poseStack, multiBufferSource, n);
    }

    public void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int n, int n2, int n3, float f, float f2, int n4, int n5, int n6, int n7) {
        vertexConsumer.m_85982_(matrix4f, (float)n, (float)n2, (float)n3).m_6122_(255, 255, 255, 255).m_7421_(f, f2).m_86008_(OverlayTexture.f_118083_).m_85969_(n7).m_85977_(matrix3f, (float)n4, (float)n6, (float)n5).m_5752_();
    }

    public ResourceLocation getTexture(LaserBulletEntity laserBulletEntity) {
        return TEXTURE;
    }
}

