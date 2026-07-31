/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;

public class DrownedOuterLayer<T extends Drowned>
extends RenderLayer<T, DrownedModel<T>> {
    private static final ResourceLocation f_116907_ = new ResourceLocation("textures/entity/zombie/drowned_outer_layer.png");
    private final DrownedModel<T> f_116908_;

    public DrownedOuterLayer(RenderLayerParent<T, DrownedModel<T>> p_174490_, EntityModelSet p_174491_) {
        super(p_174490_);
        this.f_116908_ = new DrownedModel(p_174491_.m_171103_(ModelLayers.f_171139_));
    }

    @Override
    public void m_6494_(PoseStack p_116924_, MultiBufferSource p_116925_, int p_116926_, T p_116927_, float p_116928_, float p_116929_, float p_116930_, float p_116931_, float p_116932_, float p_116933_) {
        DrownedOuterLayer.m_117359_(this.m_117386_(), this.f_116908_, f_116907_, p_116924_, p_116925_, p_116926_, p_116927_, p_116928_, p_116929_, p_116931_, p_116932_, p_116933_, p_116930_, 1.0f, 1.0f, 1.0f);
    }
}

