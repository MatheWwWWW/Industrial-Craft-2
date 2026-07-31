/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class FallingBlockRenderer
extends EntityRenderer<FallingBlockEntity> {
    private final BlockRenderDispatcher f_234617_;

    public FallingBlockRenderer(EntityRendererProvider.Context p_174112_) {
        super(p_174112_);
        this.f_114477_ = 0.5f;
        this.f_234617_ = p_174112_.m_234597_();
    }

    @Override
    public void m_7392_(FallingBlockEntity p_114634_, float p_114635_, float p_114636_, PoseStack p_114637_, MultiBufferSource p_114638_, int p_114639_) {
        BlockState $$6 = p_114634_.m_31980_();
        if ($$6.m_60799_() != RenderShape.MODEL) {
            return;
        }
        Level $$7 = p_114634_.m_9236_();
        if ($$6 == $$7.m_8055_(p_114634_.m_20183_()) || $$6.m_60799_() == RenderShape.INVISIBLE) {
            return;
        }
        p_114637_.m_85836_();
        BlockPos $$8 = new BlockPos(p_114634_.m_20185_(), p_114634_.m_20191_().f_82292_, p_114634_.m_20189_());
        p_114637_.m_85837_(-0.5, 0.0, -0.5);
        this.f_234617_.m_110937_().m_234379_($$7, this.f_234617_.m_110910_($$6), $$6, $$8, p_114637_, p_114638_.m_6299_(ItemBlockRenderTypes.m_109293_($$6)), false, RandomSource.m_216327_(), $$6.m_60726_(p_114634_.m_31978_()), OverlayTexture.f_118083_);
        p_114637_.m_85849_();
        super.m_7392_(p_114634_, p_114635_, p_114636_, p_114637_, p_114638_, p_114639_);
    }

    @Override
    public ResourceLocation m_5478_(FallingBlockEntity p_114632_) {
        return TextureAtlas.f_118259_;
    }
}

