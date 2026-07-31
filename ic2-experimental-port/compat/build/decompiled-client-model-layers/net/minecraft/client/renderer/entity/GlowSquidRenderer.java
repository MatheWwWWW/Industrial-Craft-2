/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.SquidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.GlowSquid;

public class GlowSquidRenderer
extends SquidRenderer<GlowSquid> {
    private static final ResourceLocation f_174133_ = new ResourceLocation("textures/entity/squid/glow_squid.png");

    public GlowSquidRenderer(EntityRendererProvider.Context p_174136_, SquidModel<GlowSquid> p_174137_) {
        super(p_174136_, p_174137_);
    }

    @Override
    public ResourceLocation m_5478_(GlowSquid p_174144_) {
        return f_174133_;
    }

    @Override
    protected int m_6086_(GlowSquid p_174146_, BlockPos p_174147_) {
        int $$2 = (int)Mth.m_144920_(0.0f, 15.0f, 1.0f - (float)p_174146_.m_147128_() / 10.0f);
        if ($$2 == 15) {
            return 15;
        }
        return Math.max($$2, super.m_6086_(p_174146_, p_174147_));
    }
}

