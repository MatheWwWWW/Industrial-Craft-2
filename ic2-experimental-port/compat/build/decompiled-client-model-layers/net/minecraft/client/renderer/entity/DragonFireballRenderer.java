/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.DragonFireball;

public class DragonFireballRenderer
extends EntityRenderer<DragonFireball> {
    private static final ResourceLocation f_114060_ = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
    private static final RenderType f_114061_ = RenderType.m_110458_(f_114060_);

    public DragonFireballRenderer(EntityRendererProvider.Context p_173962_) {
        super(p_173962_);
    }

    @Override
    protected int m_6086_(DragonFireball p_114087_, BlockPos p_114088_) {
        return 15;
    }

    @Override
    public void m_7392_(DragonFireball p_114080_, float p_114081_, float p_114082_, PoseStack p_114083_, MultiBufferSource p_114084_, int p_114085_) {
        p_114083_.m_85836_();
        p_114083_.m_85841_(2.0f, 2.0f, 2.0f);
        p_114083_.m_85845_(this.f_114476_.m_114470_());
        p_114083_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        PoseStack.Pose $$6 = p_114083_.m_85850_();
        Matrix4f $$7 = $$6.m_85861_();
        Matrix3f $$8 = $$6.m_85864_();
        VertexConsumer $$9 = p_114084_.m_6299_(f_114061_);
        DragonFireballRenderer.m_114089_($$9, $$7, $$8, p_114085_, 0.0f, 0, 0, 1);
        DragonFireballRenderer.m_114089_($$9, $$7, $$8, p_114085_, 1.0f, 0, 1, 1);
        DragonFireballRenderer.m_114089_($$9, $$7, $$8, p_114085_, 1.0f, 1, 1, 0);
        DragonFireballRenderer.m_114089_($$9, $$7, $$8, p_114085_, 0.0f, 1, 0, 0);
        p_114083_.m_85849_();
        super.m_7392_(p_114080_, p_114081_, p_114082_, p_114083_, p_114084_, p_114085_);
    }

    private static void m_114089_(VertexConsumer p_114090_, Matrix4f p_114091_, Matrix3f p_114092_, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_) {
        p_114090_.m_85982_(p_114091_, p_114094_ - 0.5f, (float)p_114095_ - 0.25f, 0.0f).m_6122_(255, 255, 255, 255).m_7421_(p_114096_, p_114097_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114093_).m_85977_(p_114092_, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    @Override
    public ResourceLocation m_5478_(DragonFireball p_114078_) {
        return f_114060_;
    }
}

