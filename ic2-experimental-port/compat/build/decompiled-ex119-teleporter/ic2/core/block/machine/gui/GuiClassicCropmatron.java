/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerClassicCropmatron;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiClassicCropmatron
extends Ic2Gui<ContainerClassicCropmatron> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/gui_cropmatron_classic.png");

    public GuiClassicCropmatron(ContainerClassicCropmatron containerClassicCropmatron, Inventory inventory, Component component) {
        super(containerClassicCropmatron, inventory, component);
        this.addElement(EnergyGauge.asBolt(this, 29, 39, (Ic2TileEntity)containerClassicCropmatron.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

