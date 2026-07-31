/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.piston.PistonHeadBlock;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.PistonType;

public class PistonHeadRenderer
implements BlockEntityRenderer<PistonMovingBlockEntity> {
    private final BlockRenderDispatcher f_112441_;

    public PistonHeadRenderer(BlockEntityRendererProvider.Context p_173623_) {
        this.f_112441_ = p_173623_.m_173584_();
    }

    @Override
    public void m_6922_(PistonMovingBlockEntity p_112452_, float p_112453_, PoseStack p_112454_, MultiBufferSource p_112455_, int p_112456_, int p_112457_) {
        Level $$6 = p_112452_.m_58904_();
        if ($$6 == null) {
            return;
        }
        BlockPos $$7 = p_112452_.m_58899_().m_121945_(p_112452_.m_60399_().m_122424_());
        BlockState $$8 = p_112452_.m_60400_();
        if ($$8.m_60795_()) {
            return;
        }
        ModelBlockRenderer.m_111000_();
        p_112454_.m_85836_();
        p_112454_.m_85837_(p_112452_.m_60380_(p_112453_), p_112452_.m_60385_(p_112453_), p_112452_.m_60388_(p_112453_));
        if ($$8.m_60713_(Blocks.f_50040_) && p_112452_.m_60350_(p_112453_) <= 4.0f) {
            $$8 = (BlockState)$$8.m_61124_(PistonHeadBlock.f_60236_, p_112452_.m_60350_(p_112453_) <= 0.5f);
            this.m_112458_($$7, $$8, p_112454_, p_112455_, $$6, false, p_112457_);
        } else if (p_112452_.m_60397_() && !p_112452_.m_60387_()) {
            PistonType $$9 = $$8.m_60713_(Blocks.f_50032_) ? PistonType.STICKY : PistonType.DEFAULT;
            BlockState $$10 = (BlockState)((BlockState)Blocks.f_50040_.m_49966_().m_61124_(PistonHeadBlock.f_60235_, $$9)).m_61124_(PistonHeadBlock.f_52588_, $$8.m_61143_(PistonBaseBlock.f_52588_));
            $$10 = (BlockState)$$10.m_61124_(PistonHeadBlock.f_60236_, p_112452_.m_60350_(p_112453_) >= 0.5f);
            this.m_112458_($$7, $$10, p_112454_, p_112455_, $$6, false, p_112457_);
            BlockPos $$11 = $$7.m_121945_(p_112452_.m_60399_());
            p_112454_.m_85849_();
            p_112454_.m_85836_();
            $$8 = (BlockState)$$8.m_61124_(PistonBaseBlock.f_60153_, true);
            this.m_112458_($$11, $$8, p_112454_, p_112455_, $$6, true, p_112457_);
        } else {
            this.m_112458_($$7, $$8, p_112454_, p_112455_, $$6, false, p_112457_);
        }
        p_112454_.m_85849_();
        ModelBlockRenderer.m_111077_();
    }

    private void m_112458_(BlockPos p_112459_, BlockState p_112460_, PoseStack p_112461_, MultiBufferSource p_112462_, Level p_112463_, boolean p_112464_, int p_112465_) {
        RenderType $$7 = ItemBlockRenderTypes.m_109293_(p_112460_);
        VertexConsumer $$8 = p_112462_.m_6299_($$7);
        this.f_112441_.m_110937_().m_234379_(p_112463_, this.f_112441_.m_110910_(p_112460_), p_112460_, p_112459_, p_112461_, $$8, p_112464_, RandomSource.m_216327_(), p_112460_.m_60726_(p_112459_), p_112465_);
    }

    @Override
    public int m_142163_() {
        return 68;
    }
}

