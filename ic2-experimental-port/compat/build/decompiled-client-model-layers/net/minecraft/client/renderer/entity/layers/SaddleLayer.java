/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Saddleable;

public class SaddleLayer<T extends Entity, M extends EntityModel<T>>
extends RenderLayer<T, M> {
    private final ResourceLocation f_117387_;
    private final M f_117388_;

    public SaddleLayer(RenderLayerParent<T, M> p_117390_, M p_117391_, ResourceLocation p_117392_) {
        super(p_117390_);
        this.f_117388_ = p_117391_;
        this.f_117387_ = p_117392_;
    }

    @Override
    public void m_6494_(PoseStack p_117394_, MultiBufferSource p_117395_, int p_117396_, T p_117397_, float p_117398_, float p_117399_, float p_117400_, float p_117401_, float p_117402_, float p_117403_) {
        if (!((Saddleable)p_117397_).m_6254_()) {
            return;
        }
        ((EntityModel)this.m_117386_()).m_102624_(this.f_117388_);
        ((EntityModel)this.f_117388_).m_6839_(p_117397_, p_117398_, p_117399_, p_117400_);
        ((EntityModel)this.f_117388_).m_6973_(p_117397_, p_117398_, p_117399_, p_117401_, p_117402_, p_117403_);
        VertexConsumer $$10 = p_117395_.m_6299_(RenderType.m_110458_(this.f_117387_));
        ((Model)this.f_117388_).m_7695_(p_117394_, $$10, p_117396_, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

