/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Quaternion
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.LevelRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.block.model.ItemTransforms$TransformType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.Mth
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.enchantment.Enchantments
 *  net.minecraft.world.level.BlockAndTintGetter
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import ic2.core.block.machines.tiles.mv.ChunkloaderTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;

public class ChunkloaderRenderer
implements BlockEntityRenderer<ChunkloaderTileEntity> {
    ItemStack pearl = new ItemStack((ItemLike)Items.f_42584_);
    ItemStack activePearl = new ItemStack((ItemLike)Items.f_42584_);

    public ChunkloaderRenderer(BlockEntityRendererProvider.Context context) {
        this.activePearl.m_41663_(Enchantments.f_44971_, 1);
    }

    public void render(ChunkloaderTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemRenderer render = Minecraft.m_91087_().m_91291_();
        matrixStackIn.m_85836_();
        if (tileEntityIn.animationProgress < 1.0f) {
            float yOffset = Mth.m_14179_((float)tileEntityIn.animationProgress, (float)1.0f, (float)1.5f);
            float hOffset = Mth.m_14179_((float)tileEntityIn.animationProgress, (float)0.03f, (float)0.0f);
            float rotation = Mth.m_14179_((float)tileEntityIn.animationProgress, (float)90.0f, (float)0.0f);
            matrixStackIn.m_85837_(0.5 - (double)hOffset, (double)yOffset, 0.5 + (double)hOffset);
            matrixStackIn.m_85841_(0.75f, 0.75f, 0.75f);
            matrixStackIn.m_85845_(new Quaternion(rotation, 0.0f, 0.0f, true));
        } else {
            matrixStackIn.m_85837_(0.5, 1.5, 0.5);
            matrixStackIn.m_85841_(0.75f, 0.75f, 0.75f);
            matrixStackIn.m_85845_(new Quaternion(0.0f, tileEntityIn.animationProgress * 360.0f, 0.0f, true));
        }
        render.m_174269_(tileEntityIn.doesChunkProcessing && tileEntityIn.isActive() ? this.activePearl : this.pearl, ItemTransforms.TransformType.FIXED, LevelRenderer.m_109541_((BlockAndTintGetter)tileEntityIn.m_58904_(), (BlockPos)tileEntityIn.m_58899_().m_7494_()), combinedOverlayIn, matrixStackIn, bufferIn, 0);
        matrixStackIn.m_85849_();
    }
}

