/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SnowGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SnowGolemHeadLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.SnowGolem;

public class SnowGolemRenderer
extends MobRenderer<SnowGolem, SnowGolemModel<SnowGolem>> {
    private static final ResourceLocation f_115986_ = new ResourceLocation("textures/entity/snow_golem.png");

    public SnowGolemRenderer(EntityRendererProvider.Context p_174393_) {
        super(p_174393_, new SnowGolemModel(p_174393_.m_174023_(ModelLayers.f_171243_)), 0.5f);
        this.m_115326_(new SnowGolemHeadLayer(this, p_174393_.m_234597_(), p_174393_.m_174025_()));
    }

    @Override
    public ResourceLocation m_5478_(SnowGolem p_115993_) {
        return f_115986_;
    }
}

