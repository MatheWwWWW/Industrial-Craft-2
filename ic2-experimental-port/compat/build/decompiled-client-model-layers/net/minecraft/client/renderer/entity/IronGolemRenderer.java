/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.IronGolemModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.IronGolemCrackinessLayer;
import net.minecraft.client.renderer.entity.layers.IronGolemFlowerLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.IronGolem;

public class IronGolemRenderer
extends MobRenderer<IronGolem, IronGolemModel<IronGolem>> {
    private static final ResourceLocation f_114999_ = new ResourceLocation("textures/entity/iron_golem/iron_golem.png");

    public IronGolemRenderer(EntityRendererProvider.Context p_174188_) {
        super(p_174188_, new IronGolemModel(p_174188_.m_174023_(ModelLayers.f_171192_)), 0.7f);
        this.m_115326_(new IronGolemCrackinessLayer(this));
        this.m_115326_(new IronGolemFlowerLayer(this, p_174188_.m_234597_()));
    }

    @Override
    public ResourceLocation m_5478_(IronGolem p_115012_) {
        return f_114999_;
    }

    @Override
    protected void m_7523_(IronGolem p_115014_, PoseStack p_115015_, float p_115016_, float p_115017_, float p_115018_) {
        super.m_7523_(p_115014_, p_115015_, p_115016_, p_115017_, p_115018_);
        if ((double)p_115014_.f_20924_ < 0.01) {
            return;
        }
        float $$5 = 13.0f;
        float $$6 = p_115014_.f_20925_ - p_115014_.f_20924_ * (1.0f - p_115018_) + 6.0f;
        float $$7 = (Math.abs($$6 % 13.0f - 6.5f) - 3.25f) / 3.25f;
        p_115015_.m_85845_(Vector3f.f_122227_.m_122240_(6.5f * $$7));
    }
}

