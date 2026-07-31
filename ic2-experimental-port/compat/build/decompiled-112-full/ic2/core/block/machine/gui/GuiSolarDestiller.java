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
import ic2.core.block.machine.container.ContainerSolarDestiller;
import ic2.core.block.machine.tileentity.TileEntitySolarDestiller;
import ic2.core.gui.TankGauge;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiSolarDestiller
extends GuiIC2<ContainerSolarDestiller> {
    public GuiSolarDestiller(ContainerSolarDestiller container) {
        super(container, 184);
        this.addElement(TankGauge.createPlain(this, 37, 43, 53, 18, (IFluidTank)((TileEntitySolarDestiller)container.base).inputTank));
        this.addElement(TankGauge.createPlain(this, 115, 55, 17, 43, (IFluidTank)((TileEntitySolarDestiller)container.base).outputTank));
    }

    @Override
    protected void func_146976_a(float f, int x, int y) {
        super.func_146976_a(f, x, y);
        this.bindTexture();
        if (((TileEntitySolarDestiller)((ContainerSolarDestiller)this.container).base).canWork()) {
            this.func_73729_b(this.field_147003_i + 36, this.field_147009_r + 26, 0, 184, 97, 29);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/GUISolarDestiller.png");
    }
}

