/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;

public class PigRenderer
extends MobRenderer<Pig, PigModel<Pig>> {
    private static final ResourceLocation f_115690_ = new ResourceLocation("textures/entity/pig/pig.png");

    public PigRenderer(EntityRendererProvider.Context p_174340_) {
        super(p_174340_, new PigModel(p_174340_.m_174023_(ModelLayers.f_171205_)), 0.7f);
        this.m_115326_(new SaddleLayer(this, new PigModel(p_174340_.m_174023_(ModelLayers.f_171160_)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
    }

    @Override
    public ResourceLocation m_5478_(Pig p_115697_) {
        return f_115690_;
    }
}

