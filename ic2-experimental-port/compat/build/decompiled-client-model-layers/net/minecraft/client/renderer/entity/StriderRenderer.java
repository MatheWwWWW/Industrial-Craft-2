/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Strider;

public class StriderRenderer
extends MobRenderer<Strider, StriderModel<Strider>> {
    private static final ResourceLocation f_116050_ = new ResourceLocation("textures/entity/strider/strider.png");
    private static final ResourceLocation f_116051_ = new ResourceLocation("textures/entity/strider/strider_cold.png");

    public StriderRenderer(EntityRendererProvider.Context p_174411_) {
        super(p_174411_, new StriderModel(p_174411_.m_174023_(ModelLayers.f_171251_)), 0.5f);
        this.m_115326_(new SaddleLayer(this, new StriderModel(p_174411_.m_174023_(ModelLayers.f_171252_)), new ResourceLocation("textures/entity/strider/strider_saddle.png")));
    }

    @Override
    public ResourceLocation m_5478_(Strider p_116064_) {
        return p_116064_.m_33935_() ? f_116051_ : f_116050_;
    }

    @Override
    protected void m_7546_(Strider p_116066_, PoseStack p_116067_, float p_116068_) {
        if (p_116066_.m_6162_()) {
            p_116067_.m_85841_(0.5f, 0.5f, 0.5f);
            this.f_114477_ = 0.25f;
        } else {
            this.f_114477_ = 0.5f;
        }
    }

    @Override
    protected boolean m_5936_(Strider p_116070_) {
        return super.m_5936_(p_116070_) || p_116070_.m_33935_();
    }

    @Override
    protected /* synthetic */ boolean m_5936_(LivingEntity livingEntity) {
        return this.m_5936_((Strider)livingEntity);
    }
}

