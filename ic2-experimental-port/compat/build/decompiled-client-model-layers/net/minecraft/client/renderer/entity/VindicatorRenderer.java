/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vindicator;

public class VindicatorRenderer
extends IllagerRenderer<Vindicator> {
    private static final ResourceLocation f_116317_ = new ResourceLocation("textures/entity/illager/vindicator.png");

    public VindicatorRenderer(EntityRendererProvider.Context p_174439_) {
        super(p_174439_, new IllagerModel(p_174439_.m_174023_(ModelLayers.f_171211_)), 0.5f);
        this.m_115326_(new ItemInHandLayer<Vindicator, IllagerModel<Vindicator>>((RenderLayerParent)this, p_174439_.m_234598_()){

            @Override
            public void m_6494_(PoseStack p_116352_, MultiBufferSource p_116353_, int p_116354_, Vindicator p_116355_, float p_116356_, float p_116357_, float p_116358_, float p_116359_, float p_116360_, float p_116361_) {
                if (p_116355_.m_5912_()) {
                    super.m_6494_(p_116352_, p_116353_, p_116354_, p_116355_, p_116356_, p_116357_, p_116358_, p_116359_, p_116360_, p_116361_);
                }
            }
        });
    }

    @Override
    public ResourceLocation m_5478_(Vindicator p_116324_) {
        return f_116317_;
    }
}

