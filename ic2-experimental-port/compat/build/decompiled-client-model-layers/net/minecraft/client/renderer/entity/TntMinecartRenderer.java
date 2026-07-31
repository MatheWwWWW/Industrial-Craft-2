/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.block.state.BlockState;

public class TntMinecartRenderer
extends MinecartRenderer<MinecartTNT> {
    private final BlockRenderDispatcher f_234660_;

    public TntMinecartRenderer(EntityRendererProvider.Context p_174424_) {
        super(p_174424_, ModelLayers.f_171253_);
        this.f_234660_ = p_174424_.m_234597_();
    }

    @Override
    protected void m_7002_(MinecartTNT p_116151_, float p_116152_, BlockState p_116153_, PoseStack p_116154_, MultiBufferSource p_116155_, int p_116156_) {
        int $$6 = p_116151_.m_38694_();
        if ($$6 > -1 && (float)$$6 - p_116152_ + 1.0f < 10.0f) {
            float $$7 = 1.0f - ((float)$$6 - p_116152_ + 1.0f) / 10.0f;
            $$7 = Mth.m_14036_($$7, 0.0f, 1.0f);
            $$7 *= $$7;
            $$7 *= $$7;
            float $$8 = 1.0f + $$7 * 0.3f;
            p_116154_.m_85841_($$8, $$8, $$8);
        }
        TntMinecartRenderer.m_234661_(this.f_234660_, p_116153_, p_116154_, p_116155_, p_116156_, $$6 > -1 && $$6 / 5 % 2 == 0);
    }

    public static void m_234661_(BlockRenderDispatcher p_234662_, BlockState p_234663_, PoseStack p_234664_, MultiBufferSource p_234665_, int p_234666_, boolean p_234667_) {
        int $$7;
        if (p_234667_) {
            int $$6 = OverlayTexture.m_118093_(OverlayTexture.m_118088_(1.0f), 10);
        } else {
            $$7 = OverlayTexture.f_118083_;
        }
        p_234662_.m_110912_(p_234663_, p_234664_, p_234665_, p_234666_, $$7);
    }
}

