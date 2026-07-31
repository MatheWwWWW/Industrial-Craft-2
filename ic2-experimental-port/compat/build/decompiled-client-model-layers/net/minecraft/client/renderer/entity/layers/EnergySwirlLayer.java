/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PowerableMob;

public abstract class EnergySwirlLayer<T extends Entity, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    public EnergySwirlLayer(RenderLayerParent<T, M> p_116967_) {
        super(p_116967_);
    }

    @Override
    public void m_6494_(PoseStack p_116970_, MultiBufferSource p_116971_, int p_116972_, T p_116973_, float p_116974_, float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
        if (!((PowerableMob)p_116973_).m_7090_()) {
            return;
        }
        float $$10 = (float)((Entity)p_116973_).f_19797_ + p_116976_;
        EntityModel<T> $$11 = this.m_7193_();
        $$11.m_6839_(p_116973_, p_116974_, p_116975_, p_116976_);
        ((EntityModel)this.m_117386_()).m_102624_($$11);
        VertexConsumer $$12 = p_116971_.m_6299_(RenderType.m_110436_(this.m_7029_(), this.m_7631_($$10) % 1.0f, $$10 * 0.01f % 1.0f));
        $$11.m_6973_(p_116973_, p_116974_, p_116975_, p_116977_, p_116978_, p_116979_);
        $$11.m_7695_(p_116970_, $$12, p_116972_, OverlayTexture.f_118083_, 0.5f, 0.5f, 0.5f, 1.0f);
    }

    protected abstract float m_7631_(float var1);

    protected abstract ResourceLocation m_7029_();

    protected abstract EntityModel<T> m_7193_();
}

