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

import ic2.core.GuiIC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerCropmatron;
import ic2.core.block.machine.tileentity.TileEntityCropmatron;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.TankGauge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiCropmatron
extends GuiIC2<ContainerCropmatron> {
    public GuiCropmatron(ContainerCropmatron container) {
        super(container, 192);
        this.addElement(EnergyGauge.asBolt(this, 138, 82, (TileEntityBlock)container.base));
        this.addElement(TankGauge.createPlain(this, 11, 26, 24, 47, (IFluidTank)((TileEntityCropmatron)container.base).getWaterTank()));
        this.addElement(TankGauge.createPlain(this, 105, 26, 24, 47, (IFluidTank)((TileEntityCropmatron)container.base).getExTank()));
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/GUICropmatron.png");
    }
}

