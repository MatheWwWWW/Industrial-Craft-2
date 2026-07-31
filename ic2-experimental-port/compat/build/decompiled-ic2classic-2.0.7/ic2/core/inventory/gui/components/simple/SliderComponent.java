/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 */
package ic2.core.inventory.gui.components.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import java.util.Set;
import java.util.function.IntConsumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SliderComponent
extends GuiWidget {
    ResourceLocation customTexture = null;
    Box2i texture;
    Box2i inactiveTexture;
    protected int offset;
    protected double progress;
    protected int max;
    protected int multiplier;
    protected int emptyRows = 0;
    protected boolean dragging = false;
    protected IntConsumer listener = null;

    public SliderComponent(Box2i slideArea, Box2i texture) {
        this(slideArea, texture, texture, 1);
    }

    public SliderComponent(Box2i slideArea, Box2i texture, int multiplier) {
        this(slideArea, texture, texture, multiplier);
    }

    public SliderComponent(Box2i slideArea, Box2i texture, Box2i inactiveTexture) {
        this(slideArea, texture, inactiveTexture, 1);
    }

    public SliderComponent(Box2i slideArea, Box2i texture, Box2i inactiveTexture, int multiplier) {
        super(slideArea);
        this.texture = texture;
        this.inactiveTexture = inactiveTexture;
        this.multiplier = Math.max(1, multiplier);
    }

    public SliderComponent setNonEmptyRows(int rows) {
        this.emptyRows = Math.max(0, rows);
        return this;
    }

    public SliderComponent setListener(IntConsumer listener) {
        this.listener = listener;
        return this;
    }

    public SliderComponent setCustomTexture(ResourceLocation texture) {
        this.customTexture = texture;
        return this;
    }

    public SliderComponent setMax(int max) {
        if (this.max != (max = Math.max(0, max))) {
            this.max = max;
            int value = this.getMax();
            int lastOffset = this.offset;
            if (this.offset > value) {
                this.offset = value;
            }
            double d = this.progress = value <= 0 ? 0.0 : (double)this.offset / (double)value;
            if (this.offset != lastOffset && this.listener != null) {
                this.listener.accept(this.getCurrent());
            }
        }
        return this;
    }

    public SliderComponent setCurrent(int current) {
        int old = this.offset;
        int value = this.getMax();
        this.offset = Mth.m_14045_((int)(current / this.multiplier), (int)0, (int)value);
        if (this.offset != old) {
            this.progress = value <= 0 ? 0.0 : (double)this.offset / (double)value;
        }
        return this;
    }

    public int getMax() {
        return Math.max(0, this.max / this.multiplier - this.emptyRows);
    }

    public int getCurrent() {
        return this.offset * this.multiplier;
    }

    public boolean inRange(int offset) {
        return this.getCurrent() + offset < this.getMax() + this.emptyRows;
    }

    @Override
    protected void addRequests(Set<GuiWidget.ActionRequest> requests) {
        requests.add(GuiWidget.ActionRequest.DRAW_BACKGROUND);
        requests.add(GuiWidget.ActionRequest.MOUSE_INPUT);
        requests.add(GuiWidget.ActionRequest.MOUSE_SCROLL);
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        if (this.dragging) {
            this.updateSlider(this.box, mouseY);
        }
        if (this.customTexture != null) {
            this.gui.bindTexture(this.customTexture);
        }
        Box2i texture = this.getTexture();
        this.gui.drawTextureRegion(matrix, this.gui.getGuiLeft() + this.box.getX(), (float)(this.gui.getGuiTop() + this.box.getY()) + (float)((double)(this.box.getHeight() - texture.getHeight()) * this.progress), texture.getX(), texture.getY(), texture.getWidth(), texture.getHeight());
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseClick(int mouseX, int mouseY, int mouseButton) {
        this.updateSlider(this.box, mouseY);
        this.dragging = true;
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
        return false;
    }

    @Override
    @OnlyIn(value=Dist.CLIENT)
    public boolean onMouseScroll(int mouseX, int mouseY, int scroll) {
        int lastOffset = this.offset;
        int max = this.getMax();
        this.progress = max <= 0 ? 0.0 : Mth.m_14008_((double)(this.progress - (double)scroll * (1.0 / (double)max)), (double)0.0, (double)1.0);
        int n = this.offset = max <= 0 ? 0 : (int)Mth.m_14008_((double)((double)max * this.progress), (double)0.0, (double)max);
        if (this.offset != lastOffset && this.listener != null) {
            this.listener.accept(this.getCurrent());
        }
        return true;
    }

    protected void updateSlider(Box2i box, int mouseY) {
        int lastOffset = this.offset;
        int max = this.getMax();
        this.progress = Mth.m_14008_((double)(((double)mouseY - ((double)box.getY() + (double)this.texture.getHeight() * 0.5)) / (double)(box.getHeight() - this.texture.getHeight())), (double)0.0, (double)1.0);
        this.offset = (int)Mth.m_14008_((double)((double)max * this.progress), (double)0.0, (double)max);
        if (max <= 0) {
            this.progress = 0.0;
        }
        if (this.offset != lastOffset && this.listener != null) {
            this.listener.accept(this.getCurrent());
        }
    }

    private Box2i getTexture() {
        return this.isEnabled() ? this.texture : this.inactiveTexture;
    }
}

