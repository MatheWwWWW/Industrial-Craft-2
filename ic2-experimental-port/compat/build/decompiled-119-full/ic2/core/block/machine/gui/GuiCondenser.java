/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerCondenser;
import ic2.core.block.machine.tileentity.TileEntityCondenser;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.TankGauge;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiCondenser
extends Ic2Gui<ContainerCondenser> {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("ic2", "textures/gui/guicondenser.png");

    public GuiCondenser(final ContainerCondenser containerCondenser, Inventory inventory, Component component) {
        super(containerCondenser, inventory, component, 184);
        Supplier<String> supplier = new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.Condenser.gui.tooltipvent", ((TileEntityCondenser)containerCondenser.base).ventEUCost);
            }
        };
        this.addElement((GuiElement<?>)new SlotGrid(this, 25, 25, 1, 2, SlotGrid.SlotStyle.Normal).withTooltip((java.util.function.Supplier<String>)supplier));
        this.addElement((GuiElement<?>)new SlotGrid(this, 133, 25, 1, 2, SlotGrid.SlotStyle.Normal).withTooltip((java.util.function.Supplier<String>)supplier));
        this.addElement(EnergyGauge.asBolt(this, 12, 26, (Ic2TileEntity)containerCondenser.base));
        this.addElement(TankGauge.createPlain(this, 46, 27, 84, 33, ((TileEntityCondenser)containerCondenser.base).getInputTank()));
        this.addElement(TankGauge.createPlain(this, 46, 74, 84, 15, ((TileEntityCondenser)containerCondenser.base).getOutputTank()));
        this.addElement(new LinkedGauge(this, 47, 63, (IGuiValueProvider)containerCondenser.base, "progress", Gauge.GaugeStyle.ProgressCondenser));
    }

    @Override
    protected ResourceLocation getTexture() {
        return BACKGROUND;
    }
}

