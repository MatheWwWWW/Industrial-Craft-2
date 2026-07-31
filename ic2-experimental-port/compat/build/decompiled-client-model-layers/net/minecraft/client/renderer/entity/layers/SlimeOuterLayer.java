/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SlimeOuterLayer<T extends LivingEntity>
extends RenderLayer<T, SlimeModel<T>> {
    private final EntityModel<T> f_117455_;

    public SlimeOuterLayer(RenderLayerParent<T, SlimeModel<T>> p_174536_, EntityModelSet p_174537_) {
        super(p_174536_);
        this.f_117455_ = new SlimeModel(p_174537_.m_171103_(ModelLayers.f_171242_));
    }

    @Override
    public void m_6494_(PoseStack p_117470_, MultiBufferSource p_117471_, int p_117472_, T p_117473_, float p_117474_, float p_117475_, float p_117476_, float p_117477_, float p_117478_, float p_117479_) {
        VertexConsumer $$13;
        boolean $$11;
        Minecraft $$10 = Minecraft.m_91087_();
        boolean bl = $$11 = $$10.m_91314_((Entity)p_117473_) && ((Entity)p_117473_).m_20145_();
        if (((Entity)p_117473_).m_20145_() && !$$11) {
            return;
        }
        if ($$11) {
            VertexConsumer $$12 = p_117471_.m_6299_(RenderType.m_110491_(this.m_117347_(p_117473_)));
        } else {
            $$13 = p_117471_.m_6299_(RenderType.m_110473_(this.m_117347_(p_117473_)));
        }
        ((SlimeModel)this.m_117386_()).m_102624_(this.f_117455_);
        this.f_117455_.m_6839_(p_117473_, p_117474_, p_117475_, p_117476_);
        this.f_117455_.m_6973_(p_117473_, p_117474_, p_117475_, p_117477_, p_117478_, p_117479_);
        this.f_117455_.m_7695_(p_117470_, $$13, p_117472_, LivingEntityRenderer.m_115338_(p_117473_, 0.0f), 1.0f, 1.0f, 1.0f, 1.0f);
    }
}

