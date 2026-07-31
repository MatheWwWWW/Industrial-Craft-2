/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LlamaDecorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;

public class LlamaRenderer
extends MobRenderer<Llama, LlamaModel<Llama>> {
    private static final ResourceLocation[] f_115348_ = new ResourceLocation[]{new ResourceLocation("textures/entity/llama/creamy.png"), new ResourceLocation("textures/entity/llama/white.png"), new ResourceLocation("textures/entity/llama/brown.png"), new ResourceLocation("textures/entity/llama/gray.png")};

    public LlamaRenderer(EntityRendererProvider.Context p_174293_, ModelLayerLocation p_174294_) {
        super(p_174293_, new LlamaModel(p_174293_.m_174023_(p_174294_)), 0.7f);
        this.m_115326_(new LlamaDecorLayer(this, p_174293_.m_174027_()));
    }

    @Override
    public ResourceLocation m_5478_(Llama p_115355_) {
        return f_115348_[p_115355_.m_30825_()];
    }
}

