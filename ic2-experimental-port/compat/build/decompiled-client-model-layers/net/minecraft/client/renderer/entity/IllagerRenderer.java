/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.world.entity.monster.AbstractIllager;

public abstract class IllagerRenderer<T extends AbstractIllager>
extends MobRenderer<T, IllagerModel<T>> {
    protected IllagerRenderer(EntityRendererProvider.Context p_174182_, IllagerModel<T> p_174183_, float p_174184_) {
        super(p_174182_, p_174183_, p_174184_);
        this.m_115326_(new CustomHeadLayer(this, p_174182_.m_174027_(), p_174182_.m_234598_()));
    }

    @Override
    protected void m_7546_(T p_114919_, PoseStack p_114920_, float p_114921_) {
        float $$3 = 0.9375f;
        p_114920_.m_85841_(0.9375f, 0.9375f, 0.9375f);
    }
}

