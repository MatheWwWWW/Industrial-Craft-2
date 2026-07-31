/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class RenderLayer<T extends Entity, M extends EntityModel<T>> {
    private final RenderLayerParent<T, M> f_117344_;

    public RenderLayer(RenderLayerParent<T, M> p_117346_) {
        this.f_117344_ = p_117346_;
    }

    protected static <T extends LivingEntity> void m_117359_(EntityModel<T> p_117360_, EntityModel<T> p_117361_, ResourceLocation p_117362_, PoseStack p_117363_, MultiBufferSource p_117364_, int p_117365_, T p_117366_, float p_117367_, float p_117368_, float p_117369_, float p_117370_, float p_117371_, float p_117372_, float p_117373_, float p_117374_, float p_117375_) {
        if (!p_117366_.m_20145_()) {
            p_117360_.m_102624_(p_117361_);
            p_117361_.m_6839_(p_117366_, p_117367_, p_117368_, p_117372_);
            p_117361_.m_6973_(p_117366_, p_117367_, p_117368_, p_117369_, p_117370_, p_117371_);
            RenderLayer.m_117376_(p_117361_, p_117362_, p_117363_, p_117364_, p_117365_, p_117366_, p_117373_, p_117374_, p_117375_);
        }
    }

    protected static <T extends LivingEntity> void m_117376_(EntityModel<T> p_117377_, ResourceLocation p_117378_, PoseStack p_117379_, MultiBufferSource p_117380_, int p_117381_, T p_117382_, float p_117383_, float p_117384_, float p_117385_) {
        VertexConsumer $$9 = p_117380_.m_6299_(RenderType.m_110458_(p_117378_));
        p_117377_.m_7695_(p_117379_, $$9, p_117381_, LivingEntityRenderer.m_115338_(p_117382_, 0.0f), p_117383_, p_117384_, p_117385_, 1.0f);
    }

    public M m_117386_() {
        return this.f_117344_.m_7200_();
    }

    protected ResourceLocation m_117347_(T p_117348_) {
        return this.f_117344_.m_5478_(p_117348_);
    }

    public abstract void m_6494_(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10);
}

