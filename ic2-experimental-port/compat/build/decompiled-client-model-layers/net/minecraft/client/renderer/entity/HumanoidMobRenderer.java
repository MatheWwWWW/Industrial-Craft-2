/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class HumanoidMobRenderer<T extends Mob, M extends HumanoidModel<T>>
extends MobRenderer<T, M> {
    private static final ResourceLocation f_114875_ = new ResourceLocation("textures/entity/steve.png");

    public HumanoidMobRenderer(EntityRendererProvider.Context p_174169_, M p_174170_, float p_174171_) {
        this(p_174169_, p_174170_, p_174171_, 1.0f, 1.0f, 1.0f);
    }

    public HumanoidMobRenderer(EntityRendererProvider.Context p_174173_, M p_174174_, float p_174175_, float p_174176_, float p_174177_, float p_174178_) {
        super(p_174173_, p_174174_, p_174175_);
        this.m_115326_(new CustomHeadLayer(this, p_174173_.m_174027_(), p_174176_, p_174177_, p_174178_, p_174173_.m_234598_()));
        this.m_115326_(new ElytraLayer(this, p_174173_.m_174027_()));
        this.m_115326_(new ItemInHandLayer(this, p_174173_.m_234598_()));
    }

    @Override
    public ResourceLocation m_5478_(T p_114891_) {
        return f_114875_;
    }
}

