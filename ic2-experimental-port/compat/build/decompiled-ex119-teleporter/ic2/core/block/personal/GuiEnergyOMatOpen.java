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
import ic2.core.Ic2Gui;
import ic2.core.block.personal.ContainerEnergyOMatOpen;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.gui.GuiElement;
import ic2.core.gui.VanillaButton;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiEnergyOMatOpen
extends Ic2Gui<ContainerEnergyOMatOpen> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guienergyomatopen.png");

    public GuiEnergyOMatOpen(ContainerEnergyOMatOpen containerEnergyOMatOpen, Inventory inventory, Component component) {
        super(containerEnergyOMatOpen, inventory, component);
        this.addElement((GuiElement<?>)new VanillaButton(this, 102, 16, 32, 10, this.createEventSender(0)).withText("-100k"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 102, 26, 32, 10, this.createEventSender(1)).withText("-10k"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 102, 36, 32, 10, this.createEventSender(2)).withText("-1k"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 102, 46, 32, 10, this.createEventSender(3)).withText("-100"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 134, 16, 32, 10, this.createEventSender(4)).withText("+100k"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 134, 26, 32, 10, this.createEventSender(5)).withText("+10k"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 134, 36, 32, 10, this.createEventSender(6)).withText("+1k"));
        this.addElement((GuiElement<?>)new VanillaButton(this, 134, 46, 32, 10, this.createEventSender(7)).withText("+100"));
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 8, this.f_97727_ - 96 + 2, Localization.translate("container.inventory"), 0x404040);
        this.drawString(poseStack, 100, 60, Localization.translate("ic2.container.personalTrader.offer"), 0x404040);
        this.drawString(poseStack, 100, 68, ((TileEntityEnergyOMat)((ContainerEnergyOMatOpen)this.f_97732_).base).euOffer + " EU", 0x404040);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

