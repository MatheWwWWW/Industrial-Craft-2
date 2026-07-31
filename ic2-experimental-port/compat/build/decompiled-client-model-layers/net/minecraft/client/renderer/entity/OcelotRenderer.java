/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Ocelot;

public class OcelotRenderer
extends MobRenderer<Ocelot, OcelotModel<Ocelot>> {
    private static final ResourceLocation f_115517_ = new ResourceLocation("textures/entity/cat/ocelot.png");

    public OcelotRenderer(EntityRendererProvider.Context p_174330_) {
        super(p_174330_, new OcelotModel(p_174330_.m_174023_(ModelLayers.f_171201_)), 0.4f);
    }

    @Override
    public ResourceLocation m_5478_(Ocelot p_115524_) {
        return f_115517_;
    }
}

