/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.personal;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.block.personal.ContainerTradeOMatOpen;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.gui.GuiElement;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiTradeOMatOpen
extends Ic2Gui<ContainerTradeOMatOpen> {
    private static final ResourceLocation background = IC2.getIdentifier("textures/gui/guitradeomatopen.png");

    public GuiTradeOMatOpen(ContainerTradeOMatOpen containerTradeOMatOpen, Inventory inventory, Component component) {
        super(containerTradeOMatOpen, inventory, component);
        if (((ContainerTradeOMatOpen)this.f_97732_).canToggleInfinite) {
            this.addElement((GuiElement<?>)new VanillaButton(this, 152, 4, 20, 20, this.createEventSender(0)).withText("\u221e"));
        }
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 8, this.f_97727_ - 96 + 2, Localization.translate("container.inventory"), 0x404040);
        this.drawString(poseStack, 12, 23, Localization.translate("ic2.container.personalTrader.want"), 0x404040);
        this.drawString(poseStack, 12, 57, Localization.translate("ic2.container.personalTrader.offer"), 0x404040);
        this.drawString(poseStack, 108, 28, Localization.translate("ic2.container.personalTrader.totalTrades0"), 0x404040);
        this.drawString(poseStack, 108, 36, Localization.translate("ic2.container.personalTrader.totalTrades1"), 0x404040);
        this.drawString(poseStack, 112, 44, "" + ((TileEntityTradeOMat)((ContainerTradeOMatOpen)this.f_97732_).base).totalTradeCount, 0x404040);
        this.drawString(poseStack, 108, 60, Localization.translate("ic2.container.personalTrader.stock") + " " + (String)(((TileEntityTradeOMat)((ContainerTradeOMatOpen)this.f_97732_).base).stock < 0 ? "\u221e" : "" + ((TileEntityTradeOMat)((ContainerTradeOMatOpen)this.f_97732_).base).stock), 0x404040);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

