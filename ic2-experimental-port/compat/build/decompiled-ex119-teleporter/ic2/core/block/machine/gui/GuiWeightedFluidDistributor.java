/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
import ic2.core.block.machine.gui.GuiWeightedDistributor;
import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
import ic2.core.gui.TankGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiWeightedFluidDistributor
extends GuiWeightedDistributor<ContainerWeightedFluidDistributor> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/guiweightedfluiddistributor.png");

    public GuiWeightedFluidDistributor(ContainerWeightedFluidDistributor containerWeightedFluidDistributor, Inventory inventory, Component component) {
        super(containerWeightedFluidDistributor, inventory, component, 211);
        this.addElement(TankGauge.createPlain(this, 33, 111, 110, 10, ((TileEntityWeightedFluidDistributor)containerWeightedFluidDistributor.base).fluidTank));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

