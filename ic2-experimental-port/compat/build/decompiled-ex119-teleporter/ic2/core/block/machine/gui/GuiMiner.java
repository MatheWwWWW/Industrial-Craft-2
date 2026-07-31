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
import ic2.core.block.machine.container.ContainerMiner;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiMiner
extends Ic2Gui<ContainerMiner> {
    public GuiMiner(ContainerMiner containerMiner, Inventory inventory, Component component) {
        super(containerMiner, inventory, component);
        this.addElement(EnergyGauge.asBolt(this, 155, 41, (Ic2TileEntity)containerMiner.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guiminer.png");
    }
}

