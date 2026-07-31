/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CrossedArmsItemLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.VillagerProfessionLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

public class VillagerRenderer
extends MobRenderer<Villager, VillagerModel<Villager>> {
    private static final ResourceLocation f_116300_ = new ResourceLocation("textures/entity/villager/villager.png");

    public VillagerRenderer(EntityRendererProvider.Context p_174437_) {
        super(p_174437_, new VillagerModel(p_174437_.m_174023_(ModelLayers.f_171210_)), 0.5f);
        this.m_115326_(new CustomHeadLayer<Villager, VillagerModel<Villager>>(this, p_174437_.m_174027_(), p_174437_.m_234598_()));
        this.m_115326_(new VillagerProfessionLayer<Villager, VillagerModel<Villager>>(this, p_174437_.m_174026_(), "villager"));
        this.m_115326_(new CrossedArmsItemLayer<Villager, VillagerModel<Villager>>(this, p_174437_.m_234598_()));
    }

    @Override
    public ResourceLocation m_5478_(Villager p_116312_) {
        return f_116300_;
    }

    @Override
    protected void m_7546_(Villager p_116314_, PoseStack p_116315_, float p_116316_) {
        float $$3 = 0.9375f;
        if (p_116314_.m_6162_()) {
            $$3 *= 0.5f;
            this.f_114477_ = 0.25f;
        } else {
            this.f_114477_ = 0.5f;
        }
        p_116315_.m_85841_($$3, $$3, $$3);
    }
}

