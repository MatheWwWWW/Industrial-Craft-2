/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Pillager;

public class PillagerRenderer
extends IllagerRenderer<Pillager> {
    private static final ResourceLocation f_115713_ = new ResourceLocation("textures/entity/illager/pillager.png");

    public PillagerRenderer(EntityRendererProvider.Context p_174354_) {
        super(p_174354_, new IllagerModel(p_174354_.m_174023_(ModelLayers.f_171161_)), 0.5f);
        this.m_115326_(new ItemInHandLayer<Pillager, IllagerModel<Pillager>>(this, p_174354_.m_234598_()));
    }

    @Override
    public ResourceLocation m_5478_(Pillager p_115720_) {
        return f_115713_;
    }
}

