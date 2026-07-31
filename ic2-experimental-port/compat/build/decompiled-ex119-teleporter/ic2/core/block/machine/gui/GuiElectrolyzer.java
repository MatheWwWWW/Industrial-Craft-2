/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.IC2;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerElectrolyzer;
import ic2.core.block.machine.tileentity.TileEntityElectrolyzer;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomGauge;
import ic2.core.gui.ElectrolyzerTank;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.RecipeButton;
import ic2.core.gui.TankFluidSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiElectrolyzer
extends Ic2Gui<ContainerElectrolyzer> {
    private static final ResourceLocation background = IC2.getIdentifier("textures/gui/guielectrolyzer.png");

    public GuiElectrolyzer(ContainerElectrolyzer containerElectrolyzer, Inventory inventory, Component component) {
        super(containerElectrolyzer, inventory, component);
        this.addElement(EnergyGauge.asBolt(this, 12, 44, (Ic2TileEntity)containerElectrolyzer.base));
        this.addElement(TankFluidSlot.createFluidSlot(this, 78, 16, ((TileEntityElectrolyzer)containerElectrolyzer.base).getInput()));
        ElectrolyzerTank[] electrolyzerTankArray = new ElectrolyzerTank[5];
        for (int i = 0; i < electrolyzerTankArray.length; ++i) {
            ElectrolyzerTank object = electrolyzerTankArray[i] = new ElectrolyzerTank(this, 36 + 21 * i, 61, i);
            this.addElement(object);
        }
        GuiElement<?> guiElement = null;
        for (ElectrolyzerGauges electrolyzerGauges : ElectrolyzerGauges.values()) {
            int n = electrolyzerGauges.ordinal();
            guiElement = (GuiElement<?>)new CustomGauge(this, 36 + electrolyzerGauges.offset, 36, (CustomGauge.IGaugeRatioProvider)containerElectrolyzer.base, electrolyzerGauges.properties).withEnableHandler(() -> electrolyzerTankArray[n].isActive());
            this.addElement(guiElement);
        }
        if (RecipeButton.canUse()) {
            assert (guiElement != null);
            this.addElement(new RecipeButton(guiElement, new String[]{"electrolyzer"}));
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }

    public static enum ElectrolyzerGauges {
        ONE_TANK(new Gauge.GaugePropertyBuilder(57, 232, 12, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down).build(), 48),
        TWO_TANK(new Gauge.GaugePropertyBuilder(1, 232, 54, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down).build(), 24),
        THREE_TANK(new Gauge.GaugePropertyBuilder(41, 159, 54, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down).build(), 24),
        FOUR_TANK(new Gauge.GaugePropertyBuilder(1, 208, 96, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down).build(), 3),
        FIVE_TANK(new Gauge.GaugePropertyBuilder(1, 184, 96, 23, Gauge.GaugePropertyBuilder.GaugeOrientation.Down).build(), 3);

        public final int offset;
        public final Gauge.GaugeProperties properties;

        private ElectrolyzerGauges(Gauge.GaugeProperties gaugeProperties, int n2) {
            this.properties = gaugeProperties;
            this.offset = n2;
        }
    }
}

