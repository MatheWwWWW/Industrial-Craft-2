/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.EndermiteModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Endermite;

public class EndermiteRenderer
extends MobRenderer<Endermite, EndermiteModel<Endermite>> {
    private static final ResourceLocation f_114345_ = new ResourceLocation("textures/entity/endermite.png");

    public EndermiteRenderer(EntityRendererProvider.Context p_173994_) {
        super(p_173994_, new EndermiteModel(p_173994_.m_174023_(ModelLayers.f_171143_)), 0.3f);
    }

    @Override
    protected float m_6441_(Endermite p_114352_) {
        return 180.0f;
    }

    @Override
    public ResourceLocation m_5478_(Endermite p_114354_) {
        return f_114345_;
    }

    @Override
    protected /* synthetic */ float m_6441_(LivingEntity livingEntity) {
        return this.m_6441_((Endermite)livingEntity);
    }

    @Override
    public /* synthetic */ ResourceLocation m_5478_(Entity entity) {
        return this.m_5478_((Endermite)entity);
    }
}

