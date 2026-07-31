/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.GlStateManager$DestFactor
 *  com.mojang.blaze3d.platform.GlStateManager$SourceFactor
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.gui.components.AbstractButton
 *  net.minecraft.client.gui.narration.NarratedElementType
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.utils.config.gui.widgets;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.utils.config.gui.config.IListOwner;
import ic2.core.utils.config.gui.widgets.GuiUtils;
import ic2.core.utils.config.gui.widgets.Icon;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class CarbonIconCheckbox
extends AbstractButton {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");
    private boolean selected;
    Icon selectedIcon;
    Icon unselectedIcon;
    Runnable listener;
    Component tooltip;
    IListOwner owner;

    public CarbonIconCheckbox(int x, int y, int width, int height, Icon selectedIcon, Icon unselectedIcon, boolean selected) {
        super(x, y, width, height, (Component)Component.m_237119_());
        this.selectedIcon = selectedIcon;
        this.unselectedIcon = unselectedIcon;
        this.selected = selected;
    }

    public CarbonIconCheckbox withListener(Runnable listener) {
        this.listener = listener;
        return this;
    }

    public CarbonIconCheckbox setTooltip(IListOwner owner, String tooltips) {
        this.owner = owner;
        this.tooltip = Component.m_237115_((String)tooltips);
        return this;
    }

    public void m_5691_() {
        boolean bl = this.selected = !this.selected;
        if (this.listener != null) {
            this.listener.run();
        }
    }

    public void setSelected(boolean value) {
        this.selected = value;
    }

    public boolean selected() {
        return this.selected;
    }

    public void m_6303_(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
        RenderSystem.m_157456_((int)0, (ResourceLocation)TEXTURE);
        RenderSystem.m_69482_();
        RenderSystem.m_157429_((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69408_((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GuiUtils.drawTextureRegion(stack, this.f_93620_, this.f_93621_, this.m_198029_() ? 20.0f : 0.0f, 0.0f, this.f_93618_, this.f_93619_, 20.0f, 20.0f, 64.0f, 64.0f);
        GuiUtils.drawTextureRegion(stack, this.f_93620_ + 2, this.f_93621_ + 2, this.f_93618_ - 4, this.f_93619_ - 4, this.selected ? this.selectedIcon : this.unselectedIcon, 16.0f, 16.0f);
        if (this.owner != null && this.m_5953_(mouseX, mouseY)) {
            this.owner.addTooltips(this.tooltip);
        }
    }

    public void m_142291_(NarrationElementOutput output) {
        output.m_169146_(NarratedElementType.TITLE, (Component)this.m_5646_());
        if (!this.f_93623_) {
            return;
        }
        output.m_169146_(NarratedElementType.USAGE, (Component)Component.m_237115_((String)(this.m_93696_() ? "narration.checkbox.usage.hovered" : "narration.checkbox.usage.focused")));
    }
}

