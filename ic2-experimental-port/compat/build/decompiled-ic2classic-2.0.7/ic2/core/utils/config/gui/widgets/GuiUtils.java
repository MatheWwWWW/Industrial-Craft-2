/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  com.mojang.math.Matrix4f
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.utils.config.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import ic2.core.utils.config.gui.widgets.Icon;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class GuiUtils {
    private static final FormattedText DOTS = FormattedText.m_130775_((String)"...");

    public static FormattedText ellipsizeStyled(Component text, int maxWidth, Font font) {
        MutableComponent dots = Component.m_237113_((String)"...").m_130948_(text.m_7383_());
        int strWidth = font.m_92852_((FormattedText)text);
        int ellipsisWidth = font.m_92852_((FormattedText)dots);
        if (strWidth > maxWidth) {
            if (ellipsisWidth >= maxWidth) {
                return font.m_92854_((FormattedText)text, maxWidth);
            }
            return FormattedText.m_130773_((FormattedText[])new FormattedText[]{font.m_92854_((FormattedText)text, maxWidth - ellipsisWidth), dots});
        }
        return text;
    }

    public static FormattedText ellipsize(FormattedText text, int maxWidth, Font font) {
        int strWidth = font.m_92852_(text);
        int ellipsisWidth = font.m_92852_(DOTS);
        if (strWidth > maxWidth) {
            if (ellipsisWidth >= maxWidth) {
                return font.m_92854_(text, maxWidth);
            }
            return FormattedText.m_130773_((FormattedText[])new FormattedText[]{font.m_92854_(text, maxWidth - ellipsisWidth), DOTS});
        }
        return text;
    }

    public static void drawTextureRegion(PoseStack stack, float x, float y, float width, float height, Icon icon, float texWidth, float texHeight) {
        RenderSystem.m_157179_((int)0, (ResourceLocation)icon.getTexture());
        GuiUtils.drawTextureRegion(stack, x, y, icon.getX(), icon.getY(), width, height, texWidth, texHeight, icon.getSheetWidth(), icon.getSheetHeight());
    }

    public static void drawTextureRegion(PoseStack stack, float x, float y, float texX, float texY, float width, float height, float texWidth, float texHeight, float textureWidth, float textureHeight) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        float t_minX = texX / textureWidth;
        float t_minY = texY / textureHeight;
        float t_maxX = (texX + texWidth) / textureWidth;
        float t_maxY = (texY + texHeight) / textureHeight;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85817_);
        bufferbuilder.m_85982_(matrix, x, maxY, 0.0f).m_7421_(t_minX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, 0.0f).m_7421_(t_maxX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, 0.0f).m_7421_(t_maxX, t_minY).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, 0.0f).m_7421_(t_minX, t_minY).m_5752_();
        tessellator.m_85914_();
    }
}

