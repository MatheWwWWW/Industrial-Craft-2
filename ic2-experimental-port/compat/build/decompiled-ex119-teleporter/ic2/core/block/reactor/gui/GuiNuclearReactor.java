/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.reactor.gui;

import com.google.common.base.Supplier;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.block.reactor.container.ContainerNuclearReactor;
import ic2.core.block.reactor.tileentity.TileEntityNuclearReactorElectric;
import ic2.core.gui.Area;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.TankGauge;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiNuclearReactor
extends Ic2Gui<ContainerNuclearReactor> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guinuclearreactor.png");
    private static final ResourceLocation backgroundFluid = new ResourceLocation("ic2", "textures/gui/guinuclearreactorfluid.png");

    public GuiNuclearReactor(ContainerNuclearReactor containerNuclearReactor, Inventory inventory, Component component) {
        super(containerNuclearReactor, inventory, component, 212, 243);
        IEnableHandler iEnableHandler = new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).f_97732_).base).isFluidCooled();
            }
        };
        this.addElement((GuiElement<?>)TankGauge.createBorderless(this, 10, 54, ((TileEntityNuclearReactorElectric)containerNuclearReactor.base).getinputtank(), true).withEnableHandler(iEnableHandler));
        this.addElement((GuiElement<?>)TankGauge.createBorderless(this, 190, 54, ((TileEntityNuclearReactorElectric)containerNuclearReactor.base).getoutputtank(), false).withEnableHandler(iEnableHandler));
        this.addElement((GuiElement<?>)new LinkedGauge(this, 7, 136, (IGuiValueProvider)containerNuclearReactor.base, "heat", Gauge.GaugeStyle.HeatNuclearReactor).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.NuclearReactor.gui.info.temp", ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).f_97732_).base).getGuiValue("heat") * 100.0);
            }
        }));
        this.addElement(TextLabel.create(this, 107, 136, 200, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).f_97732_).base).isFluidCooled()) {
                    return Localization.translate("ic2.NuclearReactor.gui.info.HUoutput", ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).f_97732_).base).EmitHeat);
                }
                return Localization.translate("ic2.NuclearReactor.gui.info.EUoutput", Math.round(((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).f_97732_).base).getOfferedEnergy()));
            }
        }), 5752026, false, 4, 0, false, true));
        this.addElement((GuiElement<?>)new Area(this, 5, 160, 18, 18).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)((GuiNuclearReactor)GuiNuclearReactor.this).f_97732_).base).isFluidCooled()) {
                    return "ic2.NuclearReactor.gui.mode.fluid";
                }
                return "ic2.NuclearReactor.gui.mode.electric";
            }
        }));
    }

    @Override
    protected void m_7286_(PoseStack poseStack, float f, int n, int n2) {
        int n3;
        super.m_7286_(poseStack, f, n, n2);
        int n4 = ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.f_97732_).base).getReactorSize();
        this.bindTexture();
        for (n3 = 0; n3 < 6; ++n3) {
            for (int i = n4; i < 9; ++i) {
                this.drawTexturedRect(poseStack, 26 + i * 18, 25 + n3 * 18, 16.0, 16.0, 213.0, 1.0);
            }
        }
        if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.f_97732_).base).isFluidCooled()) {
            n3 = ((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.f_97732_).base).gaugeHeatScaled(160);
            this.drawTexturedRect(poseStack, 186 - n3, 23.0, 0.0, 243.0, n3, 2.0);
            this.drawTexturedRect(poseStack, 186 - n3, 41.0, 0.0, 243.0, n3, 2.0);
            this.drawTexturedRect(poseStack, 186 - n3, 59.0, 0.0, 243.0, n3, 2.0);
            this.drawTexturedRect(poseStack, 186 - n3, 77.0, 0.0, 243.0, n3, 2.0);
            this.drawTexturedRect(poseStack, 186 - n3, 95.0, 0.0, 243.0, n3, 2.0);
            this.drawTexturedRect(poseStack, 186 - n3, 113.0, 0.0, 243.0, n3, 2.0);
            this.drawTexturedRect(poseStack, 186 - n3, 131.0, 0.0, 243.0, n3, 2.0);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        if (((TileEntityNuclearReactorElectric)((ContainerNuclearReactor)this.f_97732_).base).isFluidCooled()) {
            return backgroundFluid;
        }
        return background;
    }
}

