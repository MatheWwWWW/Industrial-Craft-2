/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.gui.components.base;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.feature.ITooltipProvider;
import ic2.core.platform.rendering.RenderUtils;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;

public class IconTooltip
extends AbstractWidget
implements ITooltipProvider {
    ItemStack display;
    Component toolTip;
    boolean durability = false;

    public IconTooltip(int x, int y, int width, int height, ItemStack display) {
        super(x, y, width, height, (Component)Component.m_237119_());
        this.display = display;
    }

    public IconTooltip setToolTip(Component toolTip) {
        this.toolTip = toolTip;
        return this;
    }

    public IconTooltip setToolTip(String s, Object ... args) {
        return this.setToolTip((Component)Component.m_237110_((String)s, (Object[])args));
    }

    public IconTooltip setToolTip(String s) {
        return this.setToolTip((Component)Component.m_237115_((String)s));
    }

    public IconTooltip setDisplay(ItemStack stack) {
        this.display = stack;
        return this;
    }

    public IconTooltip setDurability(boolean show) {
        this.durability = show;
        return this;
    }

    protected boolean m_7972_(int button) {
        return false;
    }

    public void m_6303_(PoseStack mStack, int mouseX, int mouseY, float partial) {
        if (this.f_93624_) {
            Minecraft mc = Minecraft.m_91087_();
            this.f_93622_ = mouseX >= this.f_93620_ && mouseY >= this.f_93621_ && mouseX < this.f_93620_ + this.f_93618_ && mouseY < this.f_93621_ + this.f_93619_;
            RenderSystem.m_157456_((int)0, (ResourceLocation)InventoryMenu.f_39692_);
            mStack.m_85836_();
            mStack.m_85837_((double)this.f_93620_, (double)this.f_93621_, 0.0);
            float xScale = 20.0f / (float)this.f_93618_;
            float yScale = 20.0f / (float)this.f_93619_;
            mStack.m_85841_(1.0f / xScale, 1.0f / yScale, 1.0f);
            Lighting.m_84931_();
            RenderSystem.m_69482_();
            RenderUtils.renderGuiItem(mc.m_91291_(), mStack, this.display, 2.0f, 2.0f);
            if (this.durability) {
                RenderUtils.renderGuiItemDecorations(mStack, mc.f_91062_, this.display, 2, 2);
            }
            RenderSystem.m_69465_();
            mStack.m_85849_();
        }
    }

    @Override
    public void addToolTip(IC2Screen gui, int x, int y, Consumer<Component> tooltip) {
        if (this.m_198029_() && this.toolTip != null) {
            tooltip.accept(this.toolTip);
        }
    }

    public void m_142291_(NarrationElementOutput p_169152_) {
    }
}

