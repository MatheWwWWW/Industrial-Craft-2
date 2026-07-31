/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.client.gui.ScreenUtils
 */
package ic2.core.inventory.gui.components.base;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.filter.SpecialFilters;
import ic2.core.inventory.gui.components.base.ToolTipButton;
import ic2.core.platform.rendering.RenderUtils;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ScreenUtils;

public class ColorButton
extends ToolTipButton {
    DyeColor color;
    ItemStack stack = ItemStack.f_41583_;
    Consumer<ColorButton> handler;

    public ColorButton(int xPos, int yPos, int width, int height, DyeColor color, Consumer<ColorButton> handler) {
        super(xPos, yPos, width, height, (Component)Component.m_237119_(), null);
        this.handler = handler;
        this.setColor(color);
    }

    public ColorButton setColor(DyeColor color) {
        this.color = color;
        this.stack = SpecialFilters.createDyeItem(color);
        return this;
    }

    public DyeColor getColor() {
        return this.color;
    }

    public void m_5691_() {
        this.handler.accept(this);
    }

    public void m_6303_(PoseStack mStack, int mouseX, int mouseY, float partial) {
        if (this.f_93624_) {
            Minecraft mc = Minecraft.m_91087_();
            this.f_93622_ = mouseX >= this.f_93620_ && mouseY >= this.f_93621_ && mouseX < this.f_93620_ + this.f_93618_ && mouseY < this.f_93621_ + this.f_93619_;
            int k = this.m_7202_(this.m_198029_());
            ScreenUtils.blitWithBorder((PoseStack)mStack, (ResourceLocation)f_93617_, (int)this.f_93620_, (int)this.f_93621_, (int)0, (int)(46 + k * 20), (int)this.f_93618_, (int)this.f_93619_, (int)200, (int)20, (int)2, (int)3, (int)2, (int)2, (float)this.m_93252_());
            this.m_7906_(mStack, mc, mouseX, mouseY);
            if (!this.stack.m_41619_()) {
                RenderSystem.m_157456_((int)0, (ResourceLocation)InventoryMenu.f_39692_);
                mStack.m_85836_();
                mStack.m_85837_((double)this.f_93620_, (double)this.f_93621_, 0.0);
                float xScale = 20.0f / (float)this.f_93618_;
                float yScale = 20.0f / (float)this.f_93619_;
                mStack.m_85841_(1.0f / xScale, 1.0f / yScale, 1.0f);
                Lighting.m_84931_();
                RenderSystem.m_69482_();
                RenderUtils.renderGuiItem(mc.m_91291_(), mStack, this.stack, 2.0f, 2.0f);
                RenderSystem.m_69465_();
                mStack.m_85849_();
            }
        }
    }
}

