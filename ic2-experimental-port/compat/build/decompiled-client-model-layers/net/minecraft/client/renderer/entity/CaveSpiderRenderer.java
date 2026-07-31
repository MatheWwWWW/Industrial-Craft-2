/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.CaveSpider;

public class CaveSpiderRenderer
extends SpiderRenderer<CaveSpider> {
    private static final ResourceLocation f_113961_ = new ResourceLocation("textures/entity/spider/cave_spider.png");
    private static final float f_173944_ = 0.7f;

    public CaveSpiderRenderer(EntityRendererProvider.Context p_173946_) {
        super(p_173946_, ModelLayers.f_171274_);
        this.f_114477_ *= 0.7f;
    }

    @Override
    protected void m_7546_(CaveSpider p_113974_, PoseStack p_113975_, float p_113976_) {
        p_113975_.m_85841_(0.7f, 0.7f, 0.7f);
    }

    @Override
    public ResourceLocation m_5478_(CaveSpider p_113972_) {
        return f_113961_;
    }
}

