/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PolarBearModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;

public class PolarBearRenderer
extends MobRenderer<PolarBear, PolarBearModel<PolarBear>> {
    private static final ResourceLocation f_115721_ = new ResourceLocation("textures/entity/bear/polarbear.png");

    public PolarBearRenderer(EntityRendererProvider.Context p_174356_) {
        super(p_174356_, new PolarBearModel(p_174356_.m_174023_(ModelLayers.f_171170_)), 0.9f);
    }

    @Override
    public ResourceLocation m_5478_(PolarBear p_115732_) {
        return f_115721_;
    }

    @Override
    protected void m_7546_(PolarBear p_115734_, PoseStack p_115735_, float p_115736_) {
        p_115735_.m_85841_(1.2f, 1.2f, 1.2f);
        super.m_7546_(p_115734_, p_115735_, p_115736_);
    }
}

