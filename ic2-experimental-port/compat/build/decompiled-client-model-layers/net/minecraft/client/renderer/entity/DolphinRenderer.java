/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.DolphinCarryingItemLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Dolphin;

public class DolphinRenderer
extends MobRenderer<Dolphin, DolphinModel<Dolphin>> {
    private static final ResourceLocation f_114052_ = new ResourceLocation("textures/entity/dolphin.png");

    public DolphinRenderer(EntityRendererProvider.Context p_173960_) {
        super(p_173960_, new DolphinModel(p_173960_.m_174023_(ModelLayers.f_171131_)), 0.7f);
        this.m_115326_(new DolphinCarryingItemLayer(this, p_173960_.m_234598_()));
    }

    @Override
    public ResourceLocation m_5478_(Dolphin p_114059_) {
        return f_114052_;
    }
}

