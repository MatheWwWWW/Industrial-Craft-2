/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.client.gui.ScreenUtils
 *  net.minecraftforge.client.gui.widget.ExtendedButton
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ScreenUtils;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public class IconButton
extends ExtendedButton
implements ITooltipProvider {
    ItemStack display;
    Component toolTip;
    boolean durability = false;
    boolean iconOnly = false;

    public IconButton(int xPos, int yPos, int width, int height, ItemStack display, Button.OnPress handler) {
        super(xPos, yPos, width, height, (Component)Component.m_237119_(), handler);
        this.display = display;
    }

    public IconButton setToolTip(Component toolTip) {
        this.toolTip = toolTip;
        return this;
    }

    public IconButton setToolTip(String s, Object ... args) {
        return this.setToolTip((Component)Component.m_237110_((String)s, (Object[])args));
    }

    public IconButton setToolTip(String s) {
        return this.setToolTip((Component)Component.m_237115_((String)s));
    }

    public IconButton setIconOnly() {
        this.iconOnly = true;
        return this;
    }

    public IconButton setDisplay(ItemStack stack) {
        this.display = stack;
        return this;
    }

    public IconButton setDurability(boolean show) {
        this.durability = show;
        return this;
    }

    public ItemStack getDisplay() {
        return this.display;
    }

    public void m_6303_(PoseStack mStack, int mouseX, int mouseY, float partial) {
        if (this.f_93624_) {
            Minecraft mc = Minecraft.m_91087_();
            boolean bl = this.f_93622_ = mouseX >= this.f_93620_ && mouseY >= this.f_93621_ && mouseX < this.f_93620_ + this.f_93618_ && mouseY < this.f_93621_ + this.f_93619_;
            if (!this.iconOnly) {
                int k = this.m_7202_(this.m_198029_());
                ScreenUtils.blitWithBorder((PoseStack)mStack, (ResourceLocation)f_93617_, (int)this.f_93620_, (int)this.f_93621_, (int)0, (int)(46 + k * 20), (int)this.f_93618_, (int)this.f_93619_, (int)200, (int)20, (int)2, (int)3, (int)2, (int)2, (float)this.m_93252_());
                this.m_7906_(mStack, mc, mouseX, mouseY);
                Component buttonText = this.m_6035_();
                int strWidth = mc.f_91062_.m_92852_((FormattedText)buttonText);
                int ellipsisWidth = mc.f_91062_.m_92895_("...");
                if (strWidth > this.f_93618_ - 6 && strWidth > ellipsisWidth) {
                    buttonText = Component.m_237113_((String)(mc.f_91062_.m_92854_((FormattedText)buttonText, this.f_93618_ - 6 - ellipsisWidth).getString() + "..."));
                }
                IconButton.m_93215_((PoseStack)mStack, (Font)mc.f_91062_, (Component)buttonText, (int)(this.f_93620_ + this.f_93618_ / 2), (int)(this.f_93621_ + (this.f_93619_ - 8) / 2), (int)this.getFGColor());
            }
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
        if (this.f_93624_ && this.m_198029_() && this.toolTip != null) {
            tooltip.accept(this.toolTip);
        }
    }
}

