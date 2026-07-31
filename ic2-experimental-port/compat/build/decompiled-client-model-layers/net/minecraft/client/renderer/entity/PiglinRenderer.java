/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 */
package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;

public class PiglinRenderer
extends HumanoidMobRenderer<Mob, PiglinModel<Mob>> {
    private static final Map<EntityType<?>, ResourceLocation> f_174341_ = ImmutableMap.of(EntityType.f_20511_, (Object)new ResourceLocation("textures/entity/piglin/piglin.png"), EntityType.f_20531_, (Object)new ResourceLocation("textures/entity/piglin/zombified_piglin.png"), EntityType.f_20512_, (Object)new ResourceLocation("textures/entity/piglin/piglin_brute.png"));
    private static final float f_174342_ = 1.0019531f;

    public PiglinRenderer(EntityRendererProvider.Context p_174344_, ModelLayerLocation p_174345_, ModelLayerLocation p_174346_, ModelLayerLocation p_174347_, boolean p_174348_) {
        super(p_174344_, PiglinRenderer.m_174349_(p_174344_.m_174027_(), p_174345_, p_174348_), 0.5f, 1.0019531f, 1.0f, 1.0019531f);
        this.m_115326_(new HumanoidArmorLayer(this, new HumanoidModel(p_174344_.m_174023_(p_174346_)), new HumanoidModel(p_174344_.m_174023_(p_174347_))));
    }

    private static PiglinModel<Mob> m_174349_(EntityModelSet p_174350_, ModelLayerLocation p_174351_, boolean p_174352_) {
        PiglinModel<Mob> $$3 = new PiglinModel<Mob>(p_174350_.m_171103_(p_174351_));
        if (p_174352_) {
            $$3.f_170807_.f_104207_ = false;
        }
        return $$3;
    }

    @Override
    public ResourceLocation m_5478_(Mob p_115708_) {
        ResourceLocation $$1 = f_174341_.get(p_115708_.m_6095_());
        if ($$1 == null) {
            throw new IllegalArgumentException("I don't know what texture to use for " + p_115708_.m_6095_());
        }
        return $$1;
    }

    @Override
    protected boolean m_5936_(Mob p_115712_) {
        return super.m_5936_(p_115712_) || p_115712_ instanceof AbstractPiglin && ((AbstractPiglin)p_115712_).m_34666_();
    }

    @Override
    protected /* synthetic */ boolean m_5936_(LivingEntity livingEntity) {
        return this.m_5936_((Mob)livingEntity);
    }
}

