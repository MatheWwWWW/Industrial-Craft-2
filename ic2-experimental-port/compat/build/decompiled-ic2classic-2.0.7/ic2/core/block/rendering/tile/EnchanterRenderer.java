/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Vector3f
 *  net.minecraft.client.model.BookModel
 *  net.minecraft.client.model.geom.ModelLayers
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.blockentity.EnchantTableRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.BlockAndTintGetter
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import ic2.core.block.machines.tiles.hv.ElectricEnchanterTileEntity;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;

public class EnchanterRenderer
implements BlockEntityRenderer<ElectricEnchanterTileEntity> {
    private BookModel modelBook;

    public EnchanterRenderer(BlockEntityRendererProvider.Context context) {
        this.modelBook = new BookModel(context.m_173582_(ModelLayers.f_171271_));
    }

    public void render(ElectricEnchanterTileEntity tile, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ElectricEnchanterTileEntity.BookInformation te = tile.renderData;
        te.tick();
        matrixStackIn.m_85836_();
        matrixStackIn.m_85837_(0.5, 0.775, 0.5);
        float f = (float)te.tickCount + partialTicks;
        matrixStackIn.m_85837_(0.0, (double)(0.1f + Mth.m_14031_((float)(f * 0.1f)) * 0.01f), 0.0);
        float f1 = te.bookRotation - te.bookRotationPrev;
        if ((double)f1 > Math.PI) {
            f1 = (float)((double)f1 - Math.PI * 2);
        }
        if ((double)f1 < -Math.PI) {
            f1 = (float)((double)f1 + Math.PI * 2);
        }
        float f2 = te.bookRotationPrev + f1 * partialTicks;
        matrixStackIn.m_85845_(Vector3f.f_122225_.m_122270_(-f2));
        matrixStackIn.m_85845_(Vector3f.f_122227_.m_122240_(80.0f));
        float f3 = Mth.m_14179_((float)partialTicks, (float)te.pageFlipPrev, (float)te.pageFlip);
        float f4 = Mth.m_14187_((float)(f3 + 0.25f)) * 1.6f - 0.3f;
        float f5 = Mth.m_14187_((float)(f3 + 0.75f)) * 1.6f - 0.3f;
        float f6 = Mth.m_14179_((float)partialTicks, (float)te.bookSpreadPrev, (float)te.bookSpread);
        this.modelBook.m_102292_(f, Mth.m_14036_((float)f4, (float)0.0f, (float)1.0f), Mth.m_14036_((float)f5, (float)0.0f, (float)1.0f), f6);
        VertexConsumer ivertexbuilder = EnchantTableRenderer.f_112405_.m_119194_(bufferIn, RenderType::m_110446_);
        this.modelBook.m_102316_(matrixStackIn, ivertexbuilder, LevelRenderer.m_109541_((BlockAndTintGetter)tile.m_58904_(), (BlockPos)tile.m_58899_().m_7494_()), combinedOverlayIn, 1.0f, 1.0f, 1.0f, 1.0f);
        matrixStackIn.m_85849_();
    }
}

