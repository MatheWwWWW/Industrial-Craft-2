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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;

public class ExperienceOrbRenderer
extends EntityRenderer<ExperienceOrb> {
    private static final ResourceLocation f_114579_ = new ResourceLocation("textures/entity/experience_orb.png");
    private static final RenderType f_114580_ = RenderType.m_110467_(f_114579_);

    public ExperienceOrbRenderer(EntityRendererProvider.Context p_174110_) {
        super(p_174110_);
        this.f_114477_ = 0.15f;
        this.f_114478_ = 0.75f;
    }

    @Override
    protected int m_6086_(ExperienceOrb p_114606_, BlockPos p_114607_) {
        return Mth.m_14045_(super.m_6086_(p_114606_, p_114607_) + 7, 0, 15);
    }

    @Override
    public void m_7392_(ExperienceOrb p_114599_, float p_114600_, float p_114601_, PoseStack p_114602_, MultiBufferSource p_114603_, int p_114604_) {
        p_114602_.m_85836_();
        int $$6 = p_114599_.m_20802_();
        float $$7 = (float)($$6 % 4 * 16 + 0) / 64.0f;
        float $$8 = (float)($$6 % 4 * 16 + 16) / 64.0f;
        float $$9 = (float)($$6 / 4 * 16 + 0) / 64.0f;
        float $$10 = (float)($$6 / 4 * 16 + 16) / 64.0f;
        float $$11 = 1.0f;
        float $$12 = 0.5f;
        float $$13 = 0.25f;
        float $$14 = 255.0f;
        float $$15 = ((float)p_114599_.f_19797_ + p_114601_) / 2.0f;
        int $$16 = (int)((Mth.m_14031_($$15 + 0.0f) + 1.0f) * 0.5f * 255.0f);
        int $$17 = 255;
        int $$18 = (int)((Mth.m_14031_($$15 + 4.1887903f) + 1.0f) * 0.1f * 255.0f);
        p_114602_.m_85837_(0.0, 0.1f, 0.0);
        p_114602_.m_85845_(this.f_114476_.m_114470_());
        p_114602_.m_85845_(Vector3f.f_122225_.m_122240_(180.0f));
        float $$19 = 0.3f;
        p_114602_.m_85841_(0.3f, 0.3f, 0.3f);
        VertexConsumer $$20 = p_114603_.m_6299_(f_114580_);
        PoseStack.Pose $$21 = p_114602_.m_85850_();
        Matrix4f $$22 = $$21.m_85861_();
        Matrix3f $$23 = $$21.m_85864_();
        ExperienceOrbRenderer.m_114608_($$20, $$22, $$23, -0.5f, -0.25f, $$16, 255, $$18, $$7, $$10, p_114604_);
        ExperienceOrbRenderer.m_114608_($$20, $$22, $$23, 0.5f, -0.25f, $$16, 255, $$18, $$8, $$10, p_114604_);
        ExperienceOrbRenderer.m_114608_($$20, $$22, $$23, 0.5f, 0.75f, $$16, 255, $$18, $$8, $$9, p_114604_);
        ExperienceOrbRenderer.m_114608_($$20, $$22, $$23, -0.5f, 0.75f, $$16, 255, $$18, $$7, $$9, p_114604_);
        p_114602_.m_85849_();
        super.m_7392_(p_114599_, p_114600_, p_114601_, p_114602_, p_114603_, p_114604_);
    }

    private static void m_114608_(VertexConsumer p_114609_, Matrix4f p_114610_, Matrix3f p_114611_, float p_114612_, float p_114613_, int p_114614_, int p_114615_, int p_114616_, float p_114617_, float p_114618_, int p_114619_) {
        p_114609_.m_85982_(p_114610_, p_114612_, p_114613_, 0.0f).m_6122_(p_114614_, p_114615_, p_114616_, 128).m_7421_(p_114617_, p_114618_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114619_).m_85977_(p_114611_, 0.0f, 1.0f, 0.0f).m_5752_();
    }

    @Override
    public ResourceLocation m_5478_(ExperienceOrb p_114597_) {
        return f_114579_;
    }
}

