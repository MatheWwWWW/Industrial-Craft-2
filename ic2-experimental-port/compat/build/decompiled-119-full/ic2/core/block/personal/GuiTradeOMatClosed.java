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
import ic2.core.block.personal.ContainerTradeOMatClosed;
import ic2.core.block.personal.TileEntityTradeOMat;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiTradeOMatClosed
extends Ic2Gui<ContainerTradeOMatClosed> {
    private static final ResourceLocation background = IC2.getIdentifier("textures/gui/guitradeomatclosed.png");

    public GuiTradeOMatClosed(ContainerTradeOMatClosed containerTradeOMatClosed, Inventory inventory, Component component) {
        super(containerTradeOMatClosed, inventory, component);
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 8, this.f_97727_ - 96 + 2, Localization.translate("container.inventory"), 0x404040);
        this.drawString(poseStack, 12, 23, Localization.translate("ic2.container.personalTrader.want"), 0x404040);
        this.drawString(poseStack, 12, 42, Localization.translate("ic2.container.personalTrader.offer"), 0x404040);
        this.drawString(poseStack, 12, 60, Localization.translate("ic2.container.personalTrader.stock"), 0x404040);
        this.drawString(poseStack, 50, 60, (String)(((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.f_97732_).base).stock < 0 ? "\u221e" : "" + ((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.f_97732_).base).stock), ((TileEntityTradeOMat)((ContainerTradeOMatClosed)this.f_97732_).base).stock != 0 ? 0x404040 : 0xFF5555);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

