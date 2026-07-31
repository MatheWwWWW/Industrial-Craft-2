/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.util.ResourceLocation
 *  net.minecraftforge.fluids.IFluidTank
 *  net.minecraftforge.fml.relauncher.Side
 *  net.minecraftforge.fml.relauncher.SideOnly
 */
package ic2.core.block.reactor.gui;

import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.block.reactor.container.ContainerNuclearReactor;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.gui.Area;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.TankGauge;
import ic2.core.gui.Text;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(value=Side.CLIENT)
public class GuiNuclearReactor
extends GuiIC2<ContainerNuclearReactor> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUINuclearReactor.png");
    private static final ResourceLocation backgroundFluid = new ResourceLocation("ic2", "textures/gui/GUINuclearReactorFluid.png");

    public GuiNuclearReactor(ContainerNuclearReactor container) {
        super(container, 212, 243);
        IEnableHandler enableHandler = new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).container).base).isFluidCooled();
            }
        };
        this.addElement((GuiElement<?>)TankGauge.createBorderless(this, 10, 54, (IFluidTank)((TileEntityNuclearReactorElectric)container.base).getinputtank(), true).withEnableHandler(enableHandler));
        this.addElement((GuiElement<?>)TankGauge.createBorderless(this, 190, 54, (IFluidTank)((TileEntityNuclearReactorElectric)container.base).getoutputtank(), false).withEnableHandler(enableHandler));
        this.addElement((GuiElement<?>)new LinkedGauge(this, 7, 136, (IGuiValueProvider)container.base, "heat", Gauge.GaugeStyle.HeatNuclearReactor).withTooltip(new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.NuclearReactor.gui.info.temp", ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).container).base).getGuiValue("heat") * 100.0);
            }
        }));
        this.addElement(Text.create(this, 107, 136, 200, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).container).base).isFluidCooled()) {
                    return Localization.translate("ic2.NuclearReactor.gui.info.HUoutput", ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).container).base).EmitHeat);
                }
                return Localization.translate("ic2.NuclearReactor.gui.info.EUoutput", Math.round(((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).container).base).getOfferedEnergy()));
            }
        }), 5752026, false, 4, 0, false, true));
        this.addElement((GuiElement<?>)new Area(this, 5, 160, 18, 18).withTooltip(new Supplier<String>(){

            public String get() {
                if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).container).base).isFluidCooled()) {
                    return "ic2.NuclearReactor.gui.mode.fluid";
                }
                return "ic2.NuclearReactor.gui.mode.electric";
            }
        }));
    }

    @Override
    protected void func_146976_a(float partialTicks, int mouseX, int mouseY) {
        super.func_146976_a(partialTicks, mouseX, mouseY);
        int size = ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).getReactorSize();
        int startX = 26;
        int startY = 25;
        this.bindTexture();
        for (int y = 0; y < 6; ++y) {
            for (int x = size; x < 9; ++x) {
                this.drawTexturedRect(26 + x * 18, 25 + y * 18, 16.0, 16.0, 213.0, 1.0);
            }
        }
        if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).isFluidCooled()) {
            int heat = ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).gaugeHeatScaled(160);
            this.drawTexturedRect(186 - heat, 23.0, 0.0, 243.0, heat, 2.0);
            this.drawTexturedRect(186 - heat, 41.0, 0.0, 243.0, heat, 2.0);
            this.drawTexturedRect(186 - heat, 59.0, 0.0, 243.0, heat, 2.0);
            this.drawTexturedRect(186 - heat, 77.0, 0.0, 243.0, heat, 2.0);
            this.drawTexturedRect(186 - heat, 95.0, 0.0, 243.0, heat, 2.0);
            this.drawTexturedRect(186 - heat, 113.0, 0.0, 243.0, heat, 2.0);
            this.drawTexturedRect(186 - heat, 131.0, 0.0, 243.0, heat, 2.0);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.container).base).isFluidCooled()) {
            return backgroundFluid;
        }
        return background;
    }
}

