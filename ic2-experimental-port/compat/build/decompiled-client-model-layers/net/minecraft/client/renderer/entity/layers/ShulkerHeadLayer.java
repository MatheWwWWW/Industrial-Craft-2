/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ShulkerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Shulker;

public class ShulkerHeadLayer
extends RenderLayer<Shulker, ShulkerModel<Shulker>> {
    public ShulkerHeadLayer(RenderLayerParent<Shulker, ShulkerModel<Shulker>> p_117432_) {
        super(p_117432_);
    }

    @Override
    public void m_6494_(PoseStack p_117445_, MultiBufferSource p_117446_, int p_117447_, Shulker p_117448_, float p_117449_, float p_117450_, float p_117451_, float p_117452_, float p_117453_, float p_117454_) {
        ResourceLocation $$10 = ShulkerRenderer.m_174375_(p_117448_.m_33467_());
        VertexConsumer $$11 = p_117446_.m_6299_(RenderType.m_110446_($$10));
        ((ShulkerModel)this.m_117386_()).m_103743_().m_104301_(p_117445_, $$11, p_117447_, LivingEntityRenderer.m_115338_(p_117448_, 0.0f));
    }
}

