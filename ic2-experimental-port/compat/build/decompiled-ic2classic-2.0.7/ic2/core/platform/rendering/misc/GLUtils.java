/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.platform.Window
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.entity.ItemRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.client.ForgeHooksClient
 */
package ic2.core.platform.rendering.misc;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;

@OnlyIn(value=Dist.CLIENT)
public class GLUtils {
    public static void enableHighlight(boolean depth) {
        RenderSystem.m_69472_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        if (depth) {
            RenderSystem.m_69465_();
        } else {
            RenderSystem.m_69482_();
        }
    }

    public static void disableHighlight(boolean depth) {
        RenderSystem.m_69493_();
        if (depth) {
            RenderSystem.m_69482_();
        }
    }

    public static void drawTooltip(PoseStack stack, List<Component> textLines, int x, int y) {
        Minecraft mc = Minecraft.m_91087_();
        Window main = mc.m_91268_();
        int width = main.m_85443_();
        int height = main.m_85444_();
        GLUtils.drawTooltipRaw(stack, ForgeHooksClient.gatherTooltipComponents((ItemStack)ItemStack.f_41583_, textLines, (int)x, (int)width, (int)height, (Font)mc.f_91062_, (Font)mc.f_91062_), x, y, width, height, mc.f_91062_);
    }

    private static void drawTooltipRaw(PoseStack stack, List<ClientTooltipComponent> textLines, int x, int y, int screenWidth, int screenHeight, Font font) {
        int width = 0;
        int height = textLines.size() == 1 ? -2 : 0;
        for (ClientTooltipComponent comp : textLines) {
            width = Math.max(width, comp.m_142069_(font));
            height += comp.m_142103_();
        }
        int minX = x + 12;
        int minY = y - 12;
        if (minX + width > screenWidth) {
            minX -= 28 + width;
        }
        if (minY + height + 6 > screenHeight) {
            minY = screenHeight - height - 6;
        }
        if (y - height - 8 < 0) {
            minY = y + 8;
        }
        GLUtils.drawArea(stack, x, y, minX + width + 5, minY + height);
        MultiBufferSource.BufferSource buffer = MultiBufferSource.m_109898_((BufferBuilder)Tesselator.m_85913_().m_85915_());
        stack.m_85836_();
        stack.m_85837_(0.0, 0.0, 400.0);
        Matrix4f matrix = stack.m_85850_().m_85861_();
        int tooltipTop = minY;
        for (int i2 = 0; i2 < textLines.size(); ++i2) {
            ClientTooltipComponent comp = textLines.get(i2);
            comp.m_142440_(font, minX, tooltipTop, matrix, buffer);
            tooltipTop += comp.m_142103_() + (i2 == 0 ? 2 : 0);
        }
        buffer.m_109911_();
        stack.m_85849_();
        stack.m_85836_();
        tooltipTop = minY;
        ItemRenderer item = Minecraft.m_91087_().m_91291_();
        for (int l2 = 0; l2 < textLines.size(); ++l2) {
            ClientTooltipComponent comp = textLines.get(l2);
            comp.m_183452_(font, minX, tooltipTop, stack, item, 400);
            tooltipTop += comp.m_142103_() + (l2 == 0 ? 2 : 0);
        }
        buffer.m_109911_();
        stack.m_85849_();
    }

    public static void drawArea(PoseStack stack, int x, int y, int widht, int height) {
        GlStateManager.m_84110_();
        GlStateManager.m_84525_();
        RenderSystem.m_69416_((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        Tesselator tes = Tesselator.m_85913_();
        BufferBuilder buffer = tes.m_85915_();
        buffer.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        int minX = 4 + x;
        int maxX = minX + widht;
        int minY = 4 + y;
        int maxY = minY + height;
        GLUtils.fillArea(stack, minX - 3, minY - 4, maxX + 3, minY - 3, -267386864, buffer);
        GLUtils.fillArea(stack, minX - 3, maxY + 3, maxX + 3, maxY + 4, -267386864, buffer);
        GLUtils.fillArea(stack, minX - 3, minY - 3, maxX + 3, maxY + 3, -267386864, buffer);
        GLUtils.fillArea(stack, minX - 4, minY - 3, minX - 3, maxY + 3, -267386864, buffer);
        GLUtils.fillArea(stack, maxX + 3, minY - 3, maxX + 4, maxY + 3, -267386864, buffer);
        GLUtils.fillArea(stack, minX - 3, minY - 3 + 1, minX - 3 + 1, maxY + 3 - 1, 0x505000FF, buffer);
        GLUtils.fillArea(stack, maxX + 2, minY - 3 + 1, maxX + 3, maxY + 3 - 1, 0x505000FF, buffer);
        GLUtils.fillArea(stack, minX - 3, minY - 3, maxX + 3, minY - 3 + 1, 0x505000FF, buffer);
        GLUtils.fillArea(stack, minX - 3, maxY + 2, maxX + 3, maxY + 3, 1344798847, buffer);
        GlStateManager.m_84513_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        tes.m_85914_();
        GlStateManager.m_84507_();
        GlStateManager.m_84519_();
        GlStateManager.m_84109_();
    }

    public static void fillArea(PoseStack stack, int left, int top, int right, int bottom, int color, BufferBuilder buffer) {
        float a = (float)(color >> 24 & 0xFF) / 255.0f;
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        Matrix4f matrix = stack.m_85850_().m_85861_();
        buffer.m_85982_(matrix, (float)right, (float)top, -10.0f).m_85950_(r, g, b, a).m_5752_();
        buffer.m_85982_(matrix, (float)left, (float)top, -10.0f).m_85950_(r, g, b, a).m_5752_();
        buffer.m_85982_(matrix, (float)left, (float)bottom, -10.0f).m_85950_(r, g, b, a).m_5752_();
        buffer.m_85982_(matrix, (float)right, (float)bottom, -10.0f).m_85950_(r, g, b, a).m_5752_();
    }
}

