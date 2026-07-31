/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Silverfish;

public class SilverfishRenderer
extends MobRenderer<Silverfish, SilverfishModel<Silverfish>> {
    private static final ResourceLocation f_115920_ = new ResourceLocation("textures/entity/silverfish.png");

    public SilverfishRenderer(EntityRendererProvider.Context p_174378_) {
        super(p_174378_, new SilverfishModel(p_174378_.m_174023_(ModelLayers.f_171235_)), 0.3f);
    }

    @Override
    protected float m_6441_(Silverfish p_115927_) {
        return 180.0f;
    }

    @Override
    public ResourceLocation m_5478_(Silverfish p_115929_) {
        return f_115920_;
    }

    @Override
    protected /* synthetic */ float m_6441_(LivingEntity livingEntity) {
        return this.m_6441_((Silverfish)livingEntity);
    }

    @Override
    public /* synthetic */ ResourceLocation m_5478_(Entity entity) {
        return this.m_5478_((Silverfish)entity);
    }
}

