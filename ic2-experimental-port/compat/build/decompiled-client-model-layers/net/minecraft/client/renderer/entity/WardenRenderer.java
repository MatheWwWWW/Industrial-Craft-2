/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.warden.Warden;

public class WardenRenderer
extends MobRenderer<Warden, WardenModel<Warden>> {
    private static final ResourceLocation f_234780_ = new ResourceLocation("textures/entity/warden/warden.png");
    private static final ResourceLocation f_234781_ = new ResourceLocation("textures/entity/warden/warden_bioluminescent_layer.png");
    private static final ResourceLocation f_234782_ = new ResourceLocation("textures/entity/warden/warden_heart.png");
    private static final ResourceLocation f_234783_ = new ResourceLocation("textures/entity/warden/warden_pulsating_spots_1.png");
    private static final ResourceLocation f_234784_ = new ResourceLocation("textures/entity/warden/warden_pulsating_spots_2.png");

    public WardenRenderer(EntityRendererProvider.Context p_234787_) {
        super(p_234787_, new WardenModel(p_234787_.m_174023_(ModelLayers.f_233548_)), 0.9f);
        this.m_115326_(new WardenEmissiveLayer<Warden, WardenModel>(this, f_234781_, (p_234809_, p_234810_, p_234811_) -> 1.0f, WardenModel::m_233543_));
        this.m_115326_(new WardenEmissiveLayer<Warden, WardenModel>(this, f_234783_, (p_234805_, p_234806_, p_234807_) -> Math.max(0.0f, Mth.m_14089_(p_234807_ * 0.045f) * 0.25f), WardenModel::m_233544_));
        this.m_115326_(new WardenEmissiveLayer<Warden, WardenModel>(this, f_234784_, (p_234801_, p_234802_, p_234803_) -> Math.max(0.0f, Mth.m_14089_(p_234803_ * 0.045f + (float)Math.PI) * 0.25f), WardenModel::m_233544_));
        this.m_115326_(new WardenEmissiveLayer<Warden, WardenModel>(this, f_234780_, (p_234797_, p_234798_, p_234799_) -> p_234797_.m_219467_(p_234798_), WardenModel::m_233541_));
        this.m_115326_(new WardenEmissiveLayer<Warden, WardenModel>(this, f_234782_, (p_234793_, p_234794_, p_234795_) -> p_234793_.m_219469_(p_234794_), WardenModel::m_233542_));
    }

    @Override
    public ResourceLocation m_5478_(Warden p_234791_) {
        return f_234780_;
    }
}

