/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerSteamGenerator;
import ic2.core.block.machine.tileentity.TileEntitySteamGenerator;
import ic2.core.gui.CustomButton;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.MouseButton;
import ic2.core.gui.TankGauge;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GuiSteamGenerator
extends Ic2Gui<ContainerSteamGenerator> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("ic2", "textures/gui/guisteamgenerator.png");

    public GuiSteamGenerator(ContainerSteamGenerator containerSteamGenerator, Inventory inventory, Component component) {
        super(containerSteamGenerator, inventory, component, 220);
        this.addElement(TankGauge.createPlain(this, 10, 155, 75, 47, ((TileEntitySteamGenerator)containerSteamGenerator.base).waterTank));
        this.addElement((GuiElement<?>)new LinkedGauge(this, 13, 70, (IGuiValueProvider)containerSteamGenerator.base, "heat", Gauge.GaugeStyle.HeatSteamGenerator).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.SteamGenerator.gui.systemheat", Float.valueOf(((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getSystemHeat()));
            }
        }));
        this.addElement((GuiElement<?>)new LinkedGauge(this, 155, 61, (IGuiValueProvider)containerSteamGenerator.base, "calcification", Gauge.GaugeStyle.CalcificationSteamGenerator).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.SteamGenerator.gui.calcification", Float.valueOf(((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getCalcification())) + "%";
            }
        }));
        this.addElement((GuiElement<?>)TextLabel.create(this, 91, 172, 59, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return ((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getInputMB() + Localization.translate("ic2.generic.text.mb") + Localization.translate("ic2.generic.text.tick");
            }
        }), 2157374, false, true, true).withTooltip("ic2.SteamGenerator.gui.info.waterinput"));
        this.addElement((GuiElement<?>)TextLabel.create(this, 31, 133, 111, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.SteamGenerator.gui.heatInput", ((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getHeatInput());
            }
        }), 2157374, false, 4, 0, false, true).withTooltip("ic2.SteamGenerator.gui.info.heatinput"));
        this.addElement((GuiElement<?>)TextLabel.create(this, 22, 35, 42, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.SteamGenerator.gui.pressurevalve", ((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getPressure());
            }
        }), 2157374, false, 4, 0, false, true).withTooltip("ic2.SteamGenerator.gui.info.pressvalve"));
        this.addElement((GuiElement<?>)TextLabel.create(this, 66, 25, 81, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return ((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getOutputMB() + Localization.translate("ic2.generic.text.mb") + Localization.translate("ic2.generic.text.tick");
            }
        }), 2157374, false, 4, 0, false, true).withTooltip("ic2.SteamGenerator.gui.info.fluidoutput"));
        this.addElement(TextLabel.create(this, 66, 45, 100, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate(((TileEntitySteamGenerator)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base).getOutputFluidName());
            }
        }), 2157374, false, 4, 0, false, true));
        for (int n = 0; n < 4; n = (int)((byte)(n + 1))) {
            int n2 = (int)Math.pow(10.0, 3 - n);
            int n3 = 10 * n;
            this.addElement(new SteamBoilerButton(92 + n3, 186, 9, 9, -n2));
            this.addElement(new SteamBoilerButton(92 + n3, 162, 9, 9, n2));
            if (n == 3) continue;
            n2 = (int)Math.pow(10.0, 2 - n);
            this.addElement(new SteamBoilerButton(23 + n3, 49, 9, 9, -(2000 + n2)));
            this.addElement(new SteamBoilerButton(23 + n3, 25, 9, 9, 2000 + n2));
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return BACKGROUND;
    }

    private class SteamBoilerButton
    extends CustomButton {
        public SteamBoilerButton(int n, int n2, int n3, int n4, final int n5) {
            super(GuiSteamGenerator.this, n, n2, n3, n4, new IClickHandler(){

                @Override
                public void onClick(MouseButton mouseButton) {
                    if (mouseButton == MouseButton.left) {
                        IC2.network.get(false).initiateClientTileEntityEvent((BlockEntity)((ContainerSteamGenerator)((GuiSteamGenerator)GuiSteamGenerator.this).f_97732_).base, n5);
                    }
                }
            });
        }
    }
}

