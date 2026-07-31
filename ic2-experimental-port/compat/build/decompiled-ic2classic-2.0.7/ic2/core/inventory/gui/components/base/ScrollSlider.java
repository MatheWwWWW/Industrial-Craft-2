/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  net.minecraftforge.client.gui.ScreenUtils
 *  net.minecraftforge.client.gui.widget.ForgeSlider
 */
package ic2.core.inventory.gui.components.base;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.feature.ITooltipProvider;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.client.gui.widget.ForgeSlider;

public class ScrollSlider
extends ForgeSlider
implements ITooltipProvider {
    private double scroll_offset = 0.0;
    boolean dragging = false;
    Component tooltip;
    Consumer<ScrollSlider> listener;

    public ScrollSlider(int xPos, int yPos, int width, int height, Component prefix, Component suf, double minVal, double maxVal, double currentVal, boolean drawString, Consumer<ScrollSlider> listener) {
        super(xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, drawString);
        this.listener = listener;
    }

    public ScrollSlider(int xPos, int yPos, int width, int height, Component prefix, Component suf, double minVal, double maxVal, double currentVal, Consumer<ScrollSlider> listener) {
        super(xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, true);
        this.listener = listener;
    }

    public ScrollSlider setScrollEffect(double scrollEffect) {
        this.scroll_offset = scrollEffect / (this.maxValue - this.minValue);
        return this;
    }

    public ScrollSlider setToolTip(Component tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public ScrollSlider setToolTip(String s, Object ... args) {
        return this.setToolTip((Component)Component.m_237110_((String)s, (Object[])args));
    }

    public ScrollSlider setToolTip(String s) {
        return this.setToolTip((Component)Component.m_237115_((String)s));
    }

    @Override
    public void addToolTip(IC2Screen gui, int x, int y, Consumer<Component> tooltips) {
        if (this.m_198029_() && this.tooltip != null) {
            tooltips.accept(this.tooltip);
        }
    }

    public boolean m_5953_(double p_isMouseOver_1_, double p_isMouseOver_3_) {
        return this.dragging || super.m_5953_(p_isMouseOver_1_, p_isMouseOver_3_);
    }

    public void m_5716_(double mouseX, double mouseY) {
        super.m_5716_(mouseX, mouseY);
        this.dragging = true;
    }

    public void m_7691_(double mouseX, double mouseY) {
        super.m_7691_(mouseX, mouseY);
        this.dragging = false;
    }

    public boolean m_6050_(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
        if (this.scroll_offset != 0.0 && this.f_93623_ && this.f_93624_) {
            this.f_93577_ += this.scroll_offset * p_mouseScrolled_5_ * (double)(Screen.m_96638_() ? 10 : 1) * (double)(Screen.m_96637_() ? 100 : 1);
            this.m_5697_();
        }
        return this.scroll_offset != 0.0;
    }

    public void m_93611_(double d) {
        super.m_93611_(d);
        this.updateSlider(false);
    }

    protected void m_7906_(PoseStack stack, Minecraft mc, int mouseX, int mouseY) {
        RenderSystem.m_157456_((int)0, (ResourceLocation)f_93617_);
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int i = (this.m_198029_() ? 2 : 1) * 20;
        ScreenUtils.blitWithBorder((PoseStack)stack, (ResourceLocation)f_93617_, (int)(this.f_93620_ + (int)(this.f_93577_ * (double)(this.f_93618_ - 8))), (int)this.f_93621_, (int)0, (int)(46 + i), (int)8, (int)this.f_93619_, (int)200, (int)20, (int)2, (int)3, (int)2, (int)2, (float)this.m_93252_());
        if (this.dragging) {
            this.setValueFromMouse(mouseX);
        }
    }

    private void setValueFromMouse(double mouseX) {
        this.setSliderValue((mouseX - (double)(this.f_93620_ + 4)) / (double)(this.f_93618_ - 8));
    }

    private void setSliderValue(double value) {
        double oldValue = this.f_93577_;
        this.f_93577_ = this.snapToNearest(value);
        if (!Mth.m_14082_((double)oldValue, (double)this.f_93577_)) {
            this.m_5697_();
        }
        this.m_5695_();
    }

    public void m_5697_() {
        this.updateSlider(true);
    }

    public void updateSlider(boolean sendListener) {
        this.f_93577_ = Mth.m_14008_((double)this.f_93577_, (double)0.0, (double)1.0);
        this.m_5695_();
        if (this.listener != null && sendListener) {
            this.listener.accept(this);
        }
    }

    private double snapToNearest(double value) {
        if (this.stepSize <= 0.0) {
            return Mth.m_14008_((double)value, (double)0.0, (double)1.0);
        }
        value = Mth.m_14139_((double)Mth.m_14008_((double)value, (double)0.0, (double)1.0), (double)this.minValue, (double)this.maxValue);
        value = this.stepSize * (double)Math.round(value / this.stepSize);
        value = this.minValue > this.maxValue ? Mth.m_14008_((double)value, (double)this.maxValue, (double)this.minValue) : Mth.m_14008_((double)value, (double)this.minValue, (double)this.maxValue);
        return Mth.m_144914_((double)value, (double)this.minValue, (double)this.maxValue, (double)0.0, (double)1.0);
    }
}

