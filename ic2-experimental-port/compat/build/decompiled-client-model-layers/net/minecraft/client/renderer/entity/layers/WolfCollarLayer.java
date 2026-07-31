/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class WolfCollarLayer
extends RenderLayer<Wolf, WolfModel<Wolf>> {
    private static final ResourceLocation f_117704_ = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

    public WolfCollarLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> p_117707_) {
        super(p_117707_);
    }

    @Override
    public void m_6494_(PoseStack p_117720_, MultiBufferSource p_117721_, int p_117722_, Wolf p_117723_, float p_117724_, float p_117725_, float p_117726_, float p_117727_, float p_117728_, float p_117729_) {
        if (!p_117723_.m_21824_() || p_117723_.m_20145_()) {
            return;
        }
        float[] $$10 = p_117723_.m_30428_().m_41068_();
        WolfCollarLayer.m_117376_(this.m_117386_(), f_117704_, p_117720_, p_117721_, p_117722_, p_117723_, $$10[0], $$10[1], $$10[2]);
    }
}

