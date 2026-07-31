/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Direction$Axis
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 */
package ic2.core.block.rendering.tile;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import ic2.api.tiles.display.IDisplayInfo;
import ic2.api.tiles.display.IMonitorRenderer;
import ic2.core.block.cables.mointor.MonitorBlock;
import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.platform.rendering.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class MonitorRenderer
implements BlockEntityRenderer<MonitorTileEntity> {
    public MonitorRenderer(BlockEntityRendererProvider.Context context) {
    }

    public void render(MonitorTileEntity tile, float partialTicks, PoseStack matrix, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = tile.m_58900_();
        if (!((Boolean)state.m_61143_((Property)MonitorBlock.MASTER)).booleanValue() || !((Boolean)state.m_61143_((Property)MonitorBlock.ACTIVE)).booleanValue() || tile.dataManager.isEmpty()) {
            return;
        }
        float scale = tile.scale;
        float multiplier = 256.0f / scale;
        float offset = 28.0f / scale;
        matrix.m_85836_();
        Direction dir = tile.getFacing();
        RenderUtils.prepareText(matrix, dir.m_122434_() == Direction.Axis.Z ? dir : dir.m_122424_(), scale, 16.0, 16.0, -0.001, 0.0, 0.0, 0.0);
        Helper helper = new Helper(MultiBufferSource.m_109898_((BufferBuilder)Tesselator.m_85913_().m_85915_()), Minecraft.m_91087_());
        int width = (int)((float)tile.width * multiplier - offset);
        int height = (int)((float)tile.height * multiplier - offset);
        int y = 0;
        int x = tile.align.getXOffset(width);
        int m = tile.dataManager.size();
        for (int i = 0; i < m && height > 0; ++i) {
            IDisplayInfo info = tile.dataManager.get(i);
            info.render(matrix, x, y, width, height, tile.align, helper);
            int size = info.getHeight(width, tile.align);
            helper.endBatch();
            height -= size;
            y += size;
        }
        matrix.m_85849_();
    }

    public boolean shouldRenderOffScreen(MonitorTileEntity p_188185_1_) {
        return true;
    }

    public static class Helper
    implements IMonitorRenderer {
        MultiBufferSource.BufferSource batcher;
        Minecraft mc;

        public Helper(MultiBufferSource.BufferSource batcher, Minecraft mc) {
            this.batcher = batcher;
            this.mc = mc;
        }

        public void endBatch() {
            this.batcher.m_109911_();
        }

        @Override
        public MultiBufferSource.BufferSource getBatcher() {
            return this.batcher;
        }

        @Override
        public Font getFont() {
            return this.mc.f_91062_;
        }

        @Override
        public ItemRenderer getItemRenderer() {
            return this.mc.m_91291_();
        }

        @Override
        public void renderGuiItems(PoseStack matrix, ItemStack stack, float x, float y) {
            RenderUtils.renderGuiItem(this.mc.m_91291_(), matrix, stack, x, y);
        }

        @Override
        public void renderGuiItemText(PoseStack matrixstack, Font font, ItemStack stack, float x, float y, String text) {
            RenderUtils.renderGuiItemDecorations(matrixstack, font, stack, x, y, text);
        }
    }
}

