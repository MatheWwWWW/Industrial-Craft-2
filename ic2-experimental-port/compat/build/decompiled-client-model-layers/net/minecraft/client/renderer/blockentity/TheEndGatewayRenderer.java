/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;

public class TheEndGatewayRenderer
extends TheEndPortalRenderer<TheEndGatewayBlockEntity> {
    private static final ResourceLocation f_112598_ = new ResourceLocation("textures/entity/end_gateway_beam.png");

    public TheEndGatewayRenderer(BlockEntityRendererProvider.Context p_173683_) {
        super(p_173683_);
    }

    @Override
    public void m_6922_(TheEndGatewayBlockEntity p_112613_, float p_112614_, PoseStack p_112615_, MultiBufferSource p_112616_, int p_112617_, int p_112618_) {
        if (p_112613_.m_59971_() || p_112613_.m_59972_()) {
            float $$6 = p_112613_.m_59971_() ? p_112613_.m_59933_(p_112614_) : p_112613_.m_59967_(p_112614_);
            double $$7 = p_112613_.m_59971_() ? (double)p_112613_.m_58904_().m_151558_() : 50.0;
            $$6 = Mth.m_14031_($$6 * (float)Math.PI);
            int $$8 = Mth.m_14107_((double)$$6 * $$7);
            float[] $$9 = p_112613_.m_59971_() ? DyeColor.MAGENTA.m_41068_() : DyeColor.PURPLE.m_41068_();
            long $$10 = p_112613_.m_58904_().m_46467_();
            BeaconRenderer.m_112184_(p_112615_, p_112616_, f_112598_, p_112614_, $$6, $$10, -$$8, $$8 * 2, $$9, 0.15f, 0.175f);
        }
        super.m_6922_(p_112613_, p_112614_, p_112615_, p_112616_, p_112617_, p_112618_);
    }

    @Override
    protected float m_142491_() {
        return 1.0f;
    }

    @Override
    protected float m_142489_() {
        return 0.0f;
    }

    @Override
    protected RenderType m_142330_() {
        return RenderType.m_173242_();
    }

    @Override
    public int m_142163_() {
        return 256;
    }
}

