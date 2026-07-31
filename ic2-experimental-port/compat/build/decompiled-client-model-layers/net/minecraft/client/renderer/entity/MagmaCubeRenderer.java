/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.LavaSlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.MagmaCube;

public class MagmaCubeRenderer
extends MobRenderer<MagmaCube, LavaSlimeModel<MagmaCube>> {
    private static final ResourceLocation f_115379_ = new ResourceLocation("textures/entity/slime/magmacube.png");

    public MagmaCubeRenderer(EntityRendererProvider.Context p_174298_) {
        super(p_174298_, new LavaSlimeModel(p_174298_.m_174023_(ModelLayers.f_171197_)), 0.25f);
    }

    @Override
    protected int m_6086_(MagmaCube p_115399_, BlockPos p_115400_) {
        return 15;
    }

    @Override
    public ResourceLocation m_5478_(MagmaCube p_115393_) {
        return f_115379_;
    }

    @Override
    protected void m_7546_(MagmaCube p_115395_, PoseStack p_115396_, float p_115397_) {
        int $$3 = p_115395_.m_33632_();
        float $$4 = Mth.m_14179_(p_115397_, p_115395_.f_33585_, p_115395_.f_33584_) / ((float)$$3 * 0.5f + 1.0f);
        float $$5 = 1.0f / ($$4 + 1.0f);
        p_115396_.m_85841_($$5 * (float)$$3, 1.0f / $$5 * (float)$$3, $$5 * (float)$$3);
    }
}

