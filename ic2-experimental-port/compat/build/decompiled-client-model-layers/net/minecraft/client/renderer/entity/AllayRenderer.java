/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.allay.Allay;

public class AllayRenderer
extends MobRenderer<Allay, AllayModel> {
    private static final ResourceLocation f_234548_ = new ResourceLocation("textures/entity/allay/allay.png");

    public AllayRenderer(EntityRendererProvider.Context p_234551_) {
        super(p_234551_, new AllayModel(p_234551_.m_174023_(ModelLayers.f_233547_)), 0.4f);
        this.m_115326_(new ItemInHandLayer<Allay, AllayModel>(this, p_234551_.m_234598_()));
    }

    @Override
    public ResourceLocation m_5478_(Allay p_234558_) {
        return f_234548_;
    }

    @Override
    protected int m_6086_(Allay p_234560_, BlockPos p_234561_) {
        return 15;
    }
}

