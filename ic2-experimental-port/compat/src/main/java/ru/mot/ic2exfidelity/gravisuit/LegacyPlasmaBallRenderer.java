package ru.mot.ic2exfidelity.gravisuit;

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

public final class LegacyPlasmaBallRenderer
        extends EntityRenderer<LegacyPlasmaBallEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "gravisuit", "textures/entity/plasma_ball.png");
    private static final RenderType RENDER_TYPE =
            RenderType.m_110443_(TEXTURE, false);

    public LegacyPlasmaBallRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void m_7392_(
            LegacyPlasmaBallEntity entity, float yaw, float partialTicks,
            PoseStack pose, MultiBufferSource buffers, int light) {
        pose.m_85836_();
        pose.m_85841_(2.0F, 2.0F, 2.0F);
        pose.m_85845_(f_114476_.m_114470_());
        pose.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
        PoseStack.Pose current = pose.m_85850_();
        Matrix4f position = current.m_85861_();
        Matrix3f normal = current.m_85864_();
        VertexConsumer vertices = buffers.m_6299_(RENDER_TYPE);
        vertex(vertices, position, normal, light, 0.0F, 0, 0, 1);
        vertex(vertices, position, normal, light, 1.0F, 0, 1, 1);
        vertex(vertices, position, normal, light, 1.0F, 1, 1, 0);
        vertex(vertices, position, normal, light, 0.0F, 1, 0, 0);
        pose.m_85849_();
        super.m_7392_(entity, yaw, partialTicks, pose, buffers, light);
    }

    private static void vertex(
            VertexConsumer vertex, Matrix4f position, Matrix3f normal,
            int light, float x, int y, int u, int v) {
        vertex.m_85982_(position, x - 0.5F, y - 0.25F, 0.0F)
                .m_6122_(255, 255, 255, 255)
                .m_7421_(u, v)
                .m_86008_(OverlayTexture.f_118083_)
                .m_85969_(light)
                .m_85977_(normal, 0.0F, 1.0F, 0.0F)
                .m_5752_();
    }

    @Override
    public ResourceLocation m_5478_(LegacyPlasmaBallEntity entity) {
        return TEXTURE;
    }
}
