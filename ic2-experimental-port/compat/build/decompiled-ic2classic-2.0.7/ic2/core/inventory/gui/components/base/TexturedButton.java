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
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.inventory.gui.components.base;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.utils.math.ColorUtils;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class TexturedButton
extends ToolTipButton {
    ResourceLocation texture;
    Box2i bounds;
    boolean full = false;

    public TexturedButton(int xPos, int yPos, int width, int height, ResourceLocation texture, Box2i bounds, Button.OnPress handler) {
        super(xPos, yPos, width, height, (Component)Component.m_237119_(), handler);
        this.texture = texture;
        this.bounds = bounds;
    }

    public TexturedButton setFull() {
        this.full = true;
        return this;
    }

    public void m_6303_(PoseStack mStack, int mouseX, int mouseY, float partial) {
        super.m_6303_(mStack, mouseX, mouseY, partial);
        if (this.f_93624_) {
            RenderSystem.m_157456_((int)0, (ResourceLocation)this.texture);
            this.drawTextureRegion(mStack, this.f_93620_ + (this.full ? 0 : 2), this.f_93621_ + (this.full ? 0 : 2), this.bounds.getX(), this.bounds.getY(), this.f_93618_ - (this.full ? 0 : 4), this.f_93619_ - (this.full ? 0 : 4), this.bounds.getWidth(), this.bounds.getHeight(), ColorUtils.darker(-1, this.f_93622_ ? 0.75f : (this.f_93623_ ? 1.0f : 0.5f)));
        }
    }

    public void drawTextureRegion(PoseStack stack, float x, float y, float texX, float texY, float width, float height, float texWidth, float texHeight, int color) {
        Matrix4f matrix = stack.m_85850_().m_85861_();
        float maxX = x + width;
        float maxY = y + height;
        float t_minX = texX / 256.0f;
        float t_minY = texY / 256.0f;
        float t_maxX = (texX + texWidth) / 256.0f;
        float t_maxY = (texY + texHeight) / 256.0f;
        Tesselator tessellator = Tesselator.m_85913_();
        BufferBuilder bufferbuilder = tessellator.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172814_);
        bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85818_);
        bufferbuilder.m_85982_(matrix, x, maxY, (float)this.m_93252_()).m_6122_(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >> 24 & 0xFF).m_7421_(t_minX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, maxY, (float)this.m_93252_()).m_6122_(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >> 24 & 0xFF).m_7421_(t_maxX, t_maxY).m_5752_();
        bufferbuilder.m_85982_(matrix, maxX, y, (float)this.m_93252_()).m_6122_(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >> 24 & 0xFF).m_7421_(t_maxX, t_minY).m_5752_();
        bufferbuilder.m_85982_(matrix, x, y, (float)this.m_93252_()).m_6122_(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, color >> 24 & 0xFF).m_7421_(t_minX, t_minY).m_5752_();
        tessellator.m_85914_();
    }
}

