/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.network.chat.Component
 */
package ic2.core.inventory.gui.components.base;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.feature.ITooltipProvider;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

public class ImprovedTextWidget
extends EditBox
implements ITooltipProvider {
    Consumer<String> actualResponder = null;
    boolean autoUpdating = false;
    Component tooltip;
    boolean external = false;

    public ImprovedTextWidget(int x, int y, int width, int height) {
        this(Minecraft.m_91087_().f_91062_, x, y, width, height, (Component)Component.m_237119_());
    }

    public ImprovedTextWidget(int x, int y, int width, int height, Component text) {
        super(Minecraft.m_91087_().f_91062_, x, y, width, height, text);
    }

    public ImprovedTextWidget(Font font, int x, int y, int width, int height) {
        this(font, x, y, width, height, (Component)Component.m_237119_());
    }

    public ImprovedTextWidget(Font font, int x, int y, int width, int height, Component text) {
        super(font, x, y, width, height, text);
        super.m_94151_(this::onTextChanged);
    }

    public ImprovedTextWidget setToolTip(Component tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public ImprovedTextWidget setToolTip(String s, Object ... args) {
        return this.setToolTip((Component)Component.m_237110_((String)s, (Object[])args));
    }

    public ImprovedTextWidget setToolTip(String s) {
        return this.setToolTip((Component)Component.m_237115_((String)s));
    }

    public ImprovedTextWidget setAutoUpdating(boolean value) {
        this.autoUpdating = value;
        return this;
    }

    public void setExternalValue(String newValue) {
        this.external = true;
        this.m_94144_(newValue);
        this.external = false;
    }

    public void m_94151_(Consumer<String> responderIn) {
        this.actualResponder = responderIn;
    }

    protected void m_7207_(boolean focused) {
        super.m_7207_(focused);
        if (!focused && !this.autoUpdating) {
            this.actualResponder.accept(this.m_94155_());
        }
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257) {
            if (this.actualResponder != null) {
                this.actualResponder.accept(this.m_94155_());
            }
            this.m_94178_(false);
            return true;
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    private void onTextChanged(String s) {
        if (this.external) {
            return;
        }
        if (this.actualResponder != null && !this.autoUpdating) {
            this.actualResponder.accept(s);
        }
    }

    public void m_6305_(PoseStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        p_230430_1_.m_85837_(0.0, 0.0, (double)this.m_93252_());
        super.m_6305_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        p_230430_1_.m_85837_(0.0, 0.0, (double)(-this.m_93252_()));
    }

    @Override
    public void addToolTip(IC2Screen gui, int x, int y, Consumer<Component> tooltip) {
        if (this.m_5953_(x, y) && this.tooltip != null) {
            tooltip.accept(this.tooltip);
        }
    }
}

