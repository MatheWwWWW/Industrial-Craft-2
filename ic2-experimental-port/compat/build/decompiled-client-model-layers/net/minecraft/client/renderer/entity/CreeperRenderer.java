/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CreeperModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CreeperPowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

public class CreeperRenderer
extends MobRenderer<Creeper, CreeperModel<Creeper>> {
    private static final ResourceLocation f_114030_ = new ResourceLocation("textures/entity/creeper/creeper.png");

    public CreeperRenderer(EntityRendererProvider.Context p_173958_) {
        super(p_173958_, new CreeperModel(p_173958_.m_174023_(ModelLayers.f_171285_)), 0.5f);
        this.m_115326_(new CreeperPowerLayer(this, p_173958_.m_174027_()));
    }

    @Override
    protected void m_7546_(Creeper p_114046_, PoseStack p_114047_, float p_114048_) {
        float $$3 = p_114046_.m_32320_(p_114048_);
        float $$4 = 1.0f + Mth.m_14031_($$3 * 100.0f) * $$3 * 0.01f;
        $$3 = Mth.m_14036_($$3, 0.0f, 1.0f);
        $$3 *= $$3;
        $$3 *= $$3;
        float $$5 = (1.0f + $$3 * 0.4f) * $$4;
        float $$6 = (1.0f + $$3 * 0.1f) / $$4;
        p_114047_.m_85841_($$5, $$6, $$5);
    }

    @Override
    protected float m_6931_(Creeper p_114043_, float p_114044_) {
        float $$2 = p_114043_.m_32320_(p_114044_);
        if ((int)($$2 * 10.0f) % 2 == 0) {
            return 0.0f;
        }
        return Mth.m_14036_($$2, 0.5f, 1.0f);
    }

    @Override
    public ResourceLocation m_5478_(Creeper p_114041_) {
        return f_114030_;
    }

    @Override
    protected /* synthetic */ float m_6931_(LivingEntity livingEntity, float f) {
        return this.m_6931_((Creeper)livingEntity, f);
    }
}

