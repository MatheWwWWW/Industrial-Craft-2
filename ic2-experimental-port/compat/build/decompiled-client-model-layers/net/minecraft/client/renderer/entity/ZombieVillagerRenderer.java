/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ZombieVillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombieVillager;

public class ZombieVillagerRenderer
extends HumanoidMobRenderer<ZombieVillager, ZombieVillagerModel<ZombieVillager>> {
    private static final ResourceLocation f_116547_ = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");

    public ZombieVillagerRenderer(EntityRendererProvider.Context p_174463_) {
        super(p_174463_, new ZombieVillagerModel(p_174463_.m_174023_(ModelLayers.f_171228_)), 0.5f);
        this.m_115326_(new HumanoidArmorLayer(this, new ZombieVillagerModel(p_174463_.m_174023_(ModelLayers.f_171229_)), new ZombieVillagerModel(p_174463_.m_174023_(ModelLayers.f_171230_))));
        this.m_115326_(new VillagerProfessionLayer<ZombieVillager, ZombieVillagerModel<ZombieVillager>>(this, p_174463_.m_174026_(), "zombie_villager"));
    }

    @Override
    public ResourceLocation m_5478_(ZombieVillager p_116559_) {
        return f_116547_;
    }

    @Override
    protected boolean m_5936_(ZombieVillager p_116561_) {
        return super.m_5936_(p_116561_) || p_116561_.m_34408_();
    }

    @Override
    protected /* synthetic */ boolean m_5936_(LivingEntity livingEntity) {
        return this.m_5936_((ZombieVillager)livingEntity);
    }
}

