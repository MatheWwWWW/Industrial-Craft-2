/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zoglin;

public class ZoglinRenderer
extends MobRenderer<Zoglin, HoglinModel<Zoglin>> {
    private static final ResourceLocation f_116537_ = new ResourceLocation("textures/entity/hoglin/zoglin.png");

    public ZoglinRenderer(EntityRendererProvider.Context p_174454_) {
        super(p_174454_, new HoglinModel(p_174454_.m_174023_(ModelLayers.f_171222_)), 0.7f);
    }

    @Override
    public ResourceLocation m_5478_(Zoglin p_116544_) {
        return f_116537_;
    }
}

