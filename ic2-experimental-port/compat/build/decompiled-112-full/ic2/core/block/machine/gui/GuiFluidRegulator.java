/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.machine.gui;

import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.machine.container.ContainerFluidRegulator;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.TankGauge;
import ic2.core.init.Localization;
import java.io.IOException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;

public class GuiFluidRegulator
extends GuiIC2<ContainerFluidRegulator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIFluidRegulator.png");

    public GuiFluidRegulator(ContainerFluidRegulator container) {
        super(container, 184);
        this.addElement(EnergyGauge.asBolt(this, 12, 39, (TileEntityBlock)container.base));
        this.addElement(TankGauge.createNormal(this, 78, 34, (IFluidTank)((TileEntityFluidRegulator)container.base).getFluidTank()));
    }

    @Override
    protected void drawForegroundLayer(int mouseX, int mouseY) {
        super.drawForegroundLayer(mouseX, mouseY);
        this.field_146289_q.func_78276_b(((TileEntityFluidRegulator)((ContainerFluidRegulator)this.container).base).getoutputmb() + Localization.translate("ic2.generic.text.mb"), 105, 57, 2157374);
        this.field_146289_q.func_78276_b(((TileEntityFluidRegulator)((ContainerFluidRegulator)this.container).base).getmodegui(), 145, 57, 2157374);
    }

    @Override
    protected void func_73864_a(int i, int j, int k) throws IOException {
        super.func_73864_a(i, j, k);
        int xMin = (this.field_146294_l - this.field_146999_f) / 2;
        int yMin = (this.field_146295_m - this.field_147000_g) / 2;
        int x = i - xMin;
        int y = j - yMin;
        TileEntityFluidRegulator te = (TileEntityFluidRegulator)((ContainerFluidRegulator)this.container).base;
        if (x >= 102 && y >= 68 && x <= 110 && y <= 76) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, -1000);
        }
        if (x >= 112 && y >= 68 && x <= 120 && y <= 76) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, -100);
        }
        if (x >= 122 && y >= 68 && x <= 130 && y <= 76) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, -10);
        }
        if (x >= 132 && y >= 68 && x <= 140 && y <= 76) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, -1);
        }
        if (x >= 132 && y >= 44 && x <= 140 && y <= 52) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, 1);
        }
        if (x >= 122 && y >= 44 && x <= 130 && y <= 52) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, 10);
        }
        if (x >= 112 && y >= 44 && x <= 120 && y <= 52) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, 100);
        }
        if (x >= 102 && y >= 44 && x <= 110 && y <= 52) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, 1000);
        }
        if (x >= 151 && y >= 44 && x <= 161 && y <= 52) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, 1001);
        }
        if (x >= 151 && y >= 68 && x <= 161 && y <= 76) {
            IC2.network.get(false).initiateClientTileEntityEvent(te, 1002);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

