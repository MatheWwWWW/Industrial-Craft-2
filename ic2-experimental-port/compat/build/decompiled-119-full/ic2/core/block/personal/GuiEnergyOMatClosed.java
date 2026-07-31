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
import ic2.core.block.personal.ContainerEnergyOMatClosed;
import ic2.core.block.personal.TileEntityEnergyOMat;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiEnergyOMatClosed
extends Ic2Gui<ContainerEnergyOMatClosed> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guienergyomatclosed.png");

    public GuiEnergyOMatClosed(ContainerEnergyOMatClosed containerEnergyOMatClosed, Inventory inventory, Component component) {
        super(containerEnergyOMatClosed, inventory, component);
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 8, this.f_97727_ - 96 + 2, Localization.translate("container.inventory"), 0x404040);
        this.drawString(poseStack, 12, 21, Localization.translate("ic2.container.personalTrader.want"), 0x404040);
        this.drawString(poseStack, 12, 39, Localization.translate("ic2.container.personalTrader.offer"), 0x404040);
        this.drawString(poseStack, 50, 39, ((TileEntityEnergyOMat)((ContainerEnergyOMatClosed)this.f_97732_).base).euOffer + " EU", 0x404040);
        this.drawString(poseStack, 12, 57, Localization.translate("ic2.container.personalTraderEnergy.paidFor", ((TileEntityEnergyOMat)((ContainerEnergyOMatClosed)this.f_97732_).base).paidFor), 0x404040);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

