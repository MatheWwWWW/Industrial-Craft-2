/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.machine.gui;

import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.block.machine.container.ContainerFluidDistributor;
import ic2.core.block.machine.tileentity.TileEntityFluidDistributor;
import ic2.core.gui.TankGauge;
import ic2.core.init.Localization;
import java.io.IOException;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;

public class GuiFluidDistributor
extends GuiIC2<ContainerFluidDistributor> {
    public GuiFluidDistributor(ContainerFluidDistributor container) {
        super(container, 184);
        this.addElement(TankGauge.createPlain(this, 29, 38, 55, 47, (IFluidTank)((TileEntityFluidDistributor)container.base).fluidTank));
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidDistributor.gui.mode.info"), 112, 47, 5752026);
        if (((TileEntityFluidDistributor)((ContainerFluidDistributor)this.container).base).getActive()) {
            this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidDistributor.gui.mode.concentrate"), 95, 71, 5752026);
        } else {
            this.field_146289_q.func_78276_b(Localization.translate("ic2.FluidDistributor.gui.mode.distribute"), 95, 71, 5752026);
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.func_73864_a(mouseX, mouseY, mouseButton);
        if ((mouseX -= this.field_147003_i) >= 117 && (mouseY -= this.field_147009_r) >= 58 && mouseX <= 135 && mouseY <= 66) {
            IC2.network.get(false).initiateClientTileEntityEvent((TileEntity)((ContainerFluidDistributor)this.container).base, 1);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/GUIFluidDistributor.png");
    }
}

