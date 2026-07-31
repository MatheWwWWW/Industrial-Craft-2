/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.layers.StrayClothingLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class StrayRenderer
extends SkeletonRenderer {
    private static final ResourceLocation f_116040_ = new ResourceLocation("textures/entity/skeleton/stray.png");

    public StrayRenderer(EntityRendererProvider.Context p_174409_) {
        super(p_174409_, ModelLayers.f_171247_, ModelLayers.f_171248_, ModelLayers.f_171249_);
        this.m_115326_(new StrayClothingLayer<AbstractSkeleton, SkeletonModel<AbstractSkeleton>>(this, p_174409_.m_174027_()));
    }

    @Override
    public ResourceLocation m_5478_(AbstractSkeleton p_116049_) {
        return f_116040_;
    }
}

