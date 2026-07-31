/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class BlockRenderDispatcher
implements ResourceManagerReloadListener {
    private final BlockModelShaper f_110899_;
    private final ModelBlockRenderer f_110900_;
    private final BlockEntityWithoutLevelRenderer f_173397_;
    private final LiquidBlockRenderer f_110901_;
    private final RandomSource f_110902_ = RandomSource.m_216327_();
    private final BlockColors f_110903_;

    public BlockRenderDispatcher(BlockModelShaper p_173399_, BlockEntityWithoutLevelRenderer p_173400_, BlockColors p_173401_) {
        this.f_110899_ = p_173399_;
        this.f_173397_ = p_173400_;
        this.f_110903_ = p_173401_;
        this.f_110900_ = new ModelBlockRenderer(this.f_110903_);
        this.f_110901_ = new LiquidBlockRenderer();
    }

    public BlockModelShaper m_110907_() {
        return this.f_110899_;
    }

    public void m_110918_(BlockState p_110919_, BlockPos p_110920_, BlockAndTintGetter p_110921_, PoseStack p_110922_, VertexConsumer p_110923_) {
        if (p_110919_.m_60799_() != RenderShape.MODEL) {
            return;
        }
        BakedModel $$5 = this.f_110899_.m_110893_(p_110919_);
        long $$6 = p_110919_.m_60726_(p_110920_);
        this.f_110900_.m_234379_(p_110921_, $$5, p_110919_, p_110920_, p_110922_, p_110923_, true, this.f_110902_, $$6, OverlayTexture.f_118083_);
    }

    public void m_234355_(BlockState p_234356_, BlockPos p_234357_, BlockAndTintGetter p_234358_, PoseStack p_234359_, VertexConsumer p_234360_, boolean p_234361_, RandomSource p_234362_) {
        try {
            RenderShape $$7 = p_234356_.m_60799_();
            if ($$7 == RenderShape.MODEL) {
                this.f_110900_.m_234379_(p_234358_, this.m_110910_(p_234356_), p_234356_, p_234357_, p_234359_, p_234360_, p_234361_, p_234362_, p_234356_.m_60726_(p_234357_), OverlayTexture.f_118083_);
            }
        }
        catch (Throwable $$8) {
            CrashReport $$9 = CrashReport.m_127521_($$8, "Tesselating block in world");
            CrashReportCategory $$10 = $$9.m_127514_("Block being tesselated");
            CrashReportCategory.m_178950_($$10, p_234358_, p_234357_, p_234356_);
            throw new ReportedException($$9);
        }
    }

    public void m_234363_(BlockPos p_234364_, BlockAndTintGetter p_234365_, VertexConsumer p_234366_, BlockState p_234367_, FluidState p_234368_) {
        try {
            this.f_110901_.m_234369_(p_234365_, p_234364_, p_234366_, p_234367_, p_234368_);
        }
        catch (Throwable $$5) {
            CrashReport $$6 = CrashReport.m_127521_($$5, "Tesselating liquid in world");
            CrashReportCategory $$7 = $$6.m_127514_("Block being tesselated");
            CrashReportCategory.m_178950_($$7, p_234365_, p_234364_, null);
            throw new ReportedException($$6);
        }
    }

    public ModelBlockRenderer m_110937_() {
        return this.f_110900_;
    }

    public BakedModel m_110910_(BlockState p_110911_) {
        return this.f_110899_.m_110893_(p_110911_);
    }

    public void m_110912_(BlockState p_110913_, PoseStack p_110914_, MultiBufferSource p_110915_, int p_110916_, int p_110917_) {
        RenderShape $$5 = p_110913_.m_60799_();
        if ($$5 == RenderShape.INVISIBLE) {
            return;
        }
        switch ($$5) {
            case MODEL: {
                BakedModel $$6 = this.m_110910_(p_110913_);
                int $$7 = this.f_110903_.m_92577_(p_110913_, null, null, 0);
                float $$8 = (float)($$7 >> 16 & 0xFF) / 255.0f;
                float $$9 = (float)($$7 >> 8 & 0xFF) / 255.0f;
                float $$10 = (float)($$7 & 0xFF) / 255.0f;
                this.f_110900_.m_111067_(p_110914_.m_85850_(), p_110915_.m_6299_(ItemBlockRenderTypes.m_109284_(p_110913_, false)), p_110913_, $$6, $$8, $$9, $$10, p_110916_, p_110917_);
                break;
            }
            case ENTITYBLOCK_ANIMATED: {
                this.f_173397_.m_108829_(new ItemStack(p_110913_.m_60734_()), ItemTransforms.TransformType.NONE, p_110914_, p_110915_, p_110916_, p_110917_);
            }
        }
    }

    @Override
    public void m_6213_(ResourceManager p_110909_) {
        this.f_110901_.m_110944_();
    }
}

