/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.block.inherit.Ic2FenceBlock;
import ic2.core.block.machine.container.ContainerMagnetizer;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiMagnetizer
extends Ic2Gui<ContainerMagnetizer> {
    public GuiMagnetizer(ContainerMagnetizer containerMagnetizer, Inventory inventory, Component component) {
        super(containerMagnetizer, inventory, component);
        this.addElement(EnergyGauge.asBolt(this, 11, 28, (Ic2TileEntity)containerMagnetizer.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guimagnetizer.png");
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        if (Ic2FenceBlock.hasMetalShoes(((ContainerMagnetizer)this.f_97732_).getPlayer())) {
            this.drawString(poseStack, 18, 66, Localization.translate("ic2.Magnetizer.gui.hasMetalShoes"), 0x40FF40);
        } else {
            this.drawString(poseStack, 18, 66, Localization.translate("ic2.Magnetizer.gui.noMetalShoes"), 0xFF4040);
        }
    }
}

