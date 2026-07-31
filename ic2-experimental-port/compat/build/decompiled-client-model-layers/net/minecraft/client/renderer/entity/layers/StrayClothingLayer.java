/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class StrayClothingLayer<T extends Mob, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    private static final ResourceLocation f_117536_ = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
    private final SkeletonModel<T> f_117537_;

    public StrayClothingLayer(RenderLayerParent<T, M> p_174544_, EntityModelSet p_174545_) {
        super(p_174544_);
        this.f_117537_ = new SkeletonModel(p_174545_.m_171103_(ModelLayers.f_171250_));
    }

    @Override
    public void m_6494_(PoseStack p_117553_, MultiBufferSource p_117554_, int p_117555_, T p_117556_, float p_117557_, float p_117558_, float p_117559_, float p_117560_, float p_117561_, float p_117562_) {
        StrayClothingLayer.m_117359_(this.m_117386_(), this.f_117537_, f_117536_, p_117553_, p_117554_, p_117555_, p_117556_, p_117557_, p_117558_, p_117560_, p_117561_, p_117562_, p_117559_, 1.0f, 1.0f, 1.0f);
    }
}

