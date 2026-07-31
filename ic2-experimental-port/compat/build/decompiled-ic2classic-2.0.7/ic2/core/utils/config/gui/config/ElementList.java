/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.BufferBuilder
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.Tesselator
 *  com.mojang.blaze3d.vertex.VertexFormat$Mode
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.ContainerObjectSelectionList
 *  net.minecraft.client.renderer.GameRenderer
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 */
package ic2.core.utils.config.gui.config;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import ic2.core.utils.config.gui.api.BackgroundTexture;
import ic2.core.utils.config.gui.config.Element;
import ic2.core.utils.config.gui.screen.SmoothFloat;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ElementList
extends ContainerObjectSelectionList<Element> {
    BackgroundTexture customBackground;
    int listWidth = 220;
    int scrollPadding = 124;
    Consumer<Element> callback;
    int lastTick = 0;
    boolean isScrolling;
    SmoothFloat value = new SmoothFloat(0.8f);

    public ElementList(int width, int height, int screenY, int listY, int itemHeight) {
        super(Minecraft.m_91087_(), width, height, screenY, listY, itemHeight);
        this.m_93471_(false);
    }

    protected boolean m_7987_(int index) {
        return Objects.equals(this.m_93511_(), this.m_6702_().get(index));
    }

    public void setCallback(Consumer<Element> callback) {
        this.callback = callback;
    }

    public void addElement(Element element) {
        this.m_7085_((AbstractSelectionList.Entry)element);
    }

    public void addElements(List<Element> elements) {
        elements.forEach(x$0 -> this.m_7085_((AbstractSelectionList.Entry)x$0));
    }

    public void updateList(List<Element> elements) {
        super.m_5988_(elements);
    }

    public void removeElement(Element element) {
        this.m_93502_((AbstractSelectionList.Entry)element);
    }

    public int size() {
        return this.m_6702_().size();
    }

    public void setSelected(Element p_93462_) {
        super.m_6987_((AbstractSelectionList.Entry)p_93462_);
        if (this.callback != null && this.m_93511_() != null) {
            this.callback.accept((Element)this.m_93511_());
        }
    }

    public void scrollToElement(Element element, boolean center) {
        int index = this.m_6702_().indexOf((Object)element);
        if (index == -1) {
            return;
        }
        this.scrollToElement(index, center);
    }

    public void scrollToSelected(boolean center) {
        if (this.m_93511_() == null) {
            return;
        }
        this.scrollToElement((Element)this.m_93511_(), center);
    }

    public void scrollToElement(int index, boolean center) {
        if (center) {
            index -= this.f_93389_ / this.f_93387_ / 3;
        }
        this.m_93410_(Math.max(0, index) * this.f_93387_ + this.f_93395_);
    }

    public void setListWidth(int listWidth) {
        this.listWidth = listWidth;
    }

    public void setScrollPadding(int scrollPadding) {
        this.scrollPadding = scrollPadding;
    }

    public int m_5759_() {
        return this.listWidth;
    }

    public int getLastTick() {
        return this.lastTick;
    }

    protected int m_5756_() {
        return this.f_93388_ / 2 + this.scrollPadding;
    }

    protected void m_93481_(double mouseX, double mouseY, int button) {
        this.isScrolling = button == 0 && mouseX >= (double)this.m_5756_() && mouseX < (double)(this.m_5756_() + 6);
        super.m_93481_(mouseX, mouseY, button);
    }

    public void m_93410_(double value) {
        this.setScrollAmount(value, this.isScrolling);
    }

    public void setScrollAmount(double value, boolean force) {
        float actualValue = (float)Mth.m_14008_((double)value, (double)0.0, (double)this.m_93518_());
        this.value.setTarget(actualValue);
        if (force) {
            this.value.forceFinish();
        }
    }

    public double m_93517_() {
        return this.isScrolling ? (double)this.value.getTarget() : (double)this.value.getValue();
    }

    public boolean m_6348_(double mouseX, double mouseY, int button) {
        this.isScrolling = false;
        return super.m_6348_(mouseX, mouseY, button);
    }

    public boolean m_6050_(double mouseX, double mouseY, double scroll) {
        this.m_93410_(this.m_93517_() - scroll * (double)this.f_93387_ * 2.0);
        return true;
    }

    public void tick() {
        ++this.lastTick;
        int max = this.m_5773_();
        for (int i = 0; i < max; ++i) {
            int j1 = this.m_7610_(i);
            if (j1 + this.f_93387_ < this.f_93390_ || j1 > this.f_93391_) continue;
            ((Element)this.m_93500_(i)).tick();
        }
    }

    public void m_6305_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        this.value.update(partialTicks);
        super.m_93410_((double)this.value.getValue());
        super.m_6305_(stack, mouseX, mouseY, partialTicks);
    }

    public void setCustomBackground(BackgroundTexture customBackground) {
        this.customBackground = customBackground;
        this.m_93488_(this.customBackground == null);
        this.m_93496_(this.customBackground == null);
    }

    protected void m_7733_(PoseStack stack) {
        if (this.customBackground == null || this.f_93386_.f_91073_ != null && this.customBackground.shouldDisableInLevel()) {
            return;
        }
        ElementList.renderBackground(this.f_93393_, this.f_93392_, this.f_93390_, this.f_93391_, (float)this.m_93517_(), this.customBackground);
    }

    protected void m_239227_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        super.m_239227_(stack, mouseX, mouseY, partialTicks);
        if (this.customBackground == null) {
            return;
        }
        ElementList.renderListOverlay(this.f_93393_, this.f_93392_, this.f_93390_, this.f_93391_, this.f_93388_, this.f_93389_, this.customBackground);
    }

    public static void renderListOverlay(int x0, int x1, int y0, int y1, int width, int height, BackgroundTexture texture) {
        Tesselator tes = Tesselator.m_85913_();
        BufferBuilder builder = tes.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157456_((int)0, (ResourceLocation)texture.getForegroundTexture());
        RenderSystem.m_69482_();
        RenderSystem.m_69456_((int)519);
        int color = texture.getForegroundBrightness();
        builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        builder.m_5483_((double)x0, (double)y0, -100.0).m_7421_(0.0f, (float)y0 / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)(x0 + width), (double)y0, -100.0).m_7421_((float)width / 32.0f, (float)y0 / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)(x0 + width), 0.0, -100.0).m_7421_((float)width / 32.0f, 0.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)x0, 0.0, -100.0).m_7421_(0.0f, 0.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)x0, (double)height, -100.0).m_7421_(0.0f, (float)height / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)(x0 + width), (double)height, -100.0).m_7421_((float)width / 32.0f, (float)height / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)(x0 + width), (double)y1, -100.0).m_7421_((float)width / 32.0f, (float)y1 / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)x0, (double)y1, -100.0).m_7421_(0.0f, (float)y1 / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        tes.m_85914_();
        RenderSystem.m_69456_((int)515);
        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69416_((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ZERO, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        RenderSystem.m_69472_();
        RenderSystem.m_157427_(GameRenderer::m_172811_);
        builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
        builder.m_5483_((double)x0, (double)(y0 + 4), 0.0).m_6122_(0, 0, 0, 0).m_5752_();
        builder.m_5483_((double)x1, (double)(y0 + 4), 0.0).m_6122_(0, 0, 0, 0).m_5752_();
        builder.m_5483_((double)x1, (double)y0, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
        builder.m_5483_((double)x0, (double)y0, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
        builder.m_5483_((double)x0, (double)y1, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
        builder.m_5483_((double)x1, (double)y1, 0.0).m_6122_(0, 0, 0, 255).m_5752_();
        builder.m_5483_((double)x1, (double)(y1 - 4), 0.0).m_6122_(0, 0, 0, 0).m_5752_();
        builder.m_5483_((double)x0, (double)(y1 - 4), 0.0).m_6122_(0, 0, 0, 0).m_5752_();
        tes.m_85914_();
    }

    public static void renderBackground(int x0, int x1, int y0, int y1, float scroll, BackgroundTexture texture) {
        Tesselator tes = Tesselator.m_85913_();
        BufferBuilder builder = tes.m_85915_();
        RenderSystem.m_157427_(GameRenderer::m_172820_);
        RenderSystem.m_157456_((int)0, (ResourceLocation)texture.getBackgroundTexture());
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int color = texture.getBackgroundBrightness();
        builder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
        builder.m_5483_((double)x0, (double)y1, 0.0).m_7421_((float)x0 / 32.0f, ((float)y1 + scroll) / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)x1, (double)y1, 0.0).m_7421_((float)x1 / 32.0f, ((float)y1 + scroll) / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)x1, (double)y0, 0.0).m_7421_((float)x1 / 32.0f, ((float)y0 + scroll) / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        builder.m_5483_((double)x0, (double)y0, 0.0).m_7421_((float)x0 / 32.0f, ((float)y0 + scroll) / 32.0f).m_6122_(color, color, color, 255).m_5752_();
        tes.m_85914_();
    }
}

