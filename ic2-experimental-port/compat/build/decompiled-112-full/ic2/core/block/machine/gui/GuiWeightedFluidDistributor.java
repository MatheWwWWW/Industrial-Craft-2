/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.machine.gui;

import ic2.core.block.machine.container.ContainerWeightedFluidDistributor;
import ic2.core.block.machine.gui.GuiWeightedDistributor;
import ic2.core.block.machine.tileentity.TileEntityWeightedFluidDistributor;
import ic2.core.gui.TankGauge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiWeightedFluidDistributor
extends GuiWeightedDistributor<ContainerWeightedFluidDistributor> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/GUIWeightedFluidDistributor.png");

    public GuiWeightedFluidDistributor(ContainerWeightedFluidDistributor container) {
        super(container, 211);
        this.addElement(TankGauge.createPlain(this, 33, 111, 110, 10, (IFluidTank)((TileEntityWeightedFluidDistributor)container.base).fluidTank));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

