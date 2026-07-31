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
import ic2.core.block.machine.container.ContainerCropmatron;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.TankGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiCropmatron
extends Ic2Gui<ContainerCropmatron> {
    public GuiCropmatron(ContainerCropmatron containerCropmatron, Inventory inventory, Component component) {
        super(containerCropmatron, inventory, component, 192);
        this.addElement(EnergyGauge.asBolt(this, 138, 82, (Ic2TileEntity)containerCropmatron.base));
        this.addElement(TankGauge.createPlain(this, 11, 26, 24, 47, ((TileEntityCropmatron)containerCropmatron.base).getWaterTank()));
        this.addElement(TankGauge.createPlain(this, 105, 26, 24, 47, ((TileEntityCropmatron)containerCropmatron.base).getExTank()));
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guicropmatron.png");
    }
}

