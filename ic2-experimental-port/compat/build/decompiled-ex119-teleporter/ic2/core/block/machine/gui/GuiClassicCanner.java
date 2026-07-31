/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerClassicCanner;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.Gauge;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.dynamic.IGuiValueProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiClassicCanner
extends Ic2Gui<ContainerClassicCanner> {
    public static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/gui_canner_classic.png");

    public GuiClassicCanner(ContainerClassicCanner containerClassicCanner, Inventory inventory, Component component) {
        super(containerClassicCanner, inventory, component);
        this.addElement(new LinkedGauge(this, 74, 36, (IGuiValueProvider)containerClassicCanner.base, "progress", Gauge.GaugeStyle.ProgressLongArrow));
        this.addElement(EnergyGauge.asBolt(this, 34, 28, (Ic2TileEntity)containerClassicCanner.base));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

