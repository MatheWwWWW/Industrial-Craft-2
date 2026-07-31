/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.HoglinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

public class HoglinRenderer
extends MobRenderer<Hoglin, HoglinModel<Hoglin>> {
    private static final ResourceLocation f_114853_ = new ResourceLocation("textures/entity/hoglin/hoglin.png");

    public HoglinRenderer(EntityRendererProvider.Context p_174165_) {
        super(p_174165_, new HoglinModel(p_174165_.m_174023_(ModelLayers.f_171184_)), 0.7f);
    }

    @Override
    public ResourceLocation m_5478_(Hoglin p_114862_) {
        return f_114853_;
    }

    @Override
    protected boolean m_5936_(Hoglin p_114864_) {
        return super.m_5936_(p_114864_) || p_114864_.m_34554_();
    }

    @Override
    protected /* synthetic */ boolean m_5936_(LivingEntity livingEntity) {
        return this.m_5936_((Hoglin)livingEntity);
    }
}

