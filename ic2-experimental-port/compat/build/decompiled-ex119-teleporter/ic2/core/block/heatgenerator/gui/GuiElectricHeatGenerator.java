/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.heatgenerator.gui;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.heatgenerator.container.ContainerElectricHeatGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityElectricHeatGenerator;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiElectricHeatGenerator
extends Ic2Gui<ContainerElectricHeatGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guielectricheatgenerator.png");

    public GuiElectricHeatGenerator(ContainerElectricHeatGenerator containerElectricHeatGenerator, Inventory inventory, Component component) {
        super(containerElectricHeatGenerator, inventory, component);
        this.addElement((GuiElement<?>)new SlotGrid(this, 43, 26, 5, 2, SlotGrid.SlotStyle.Normal).withTooltip("ic2.ElectricHeatGenerator.gui.coils"));
        this.addElement(EnergyGauge.asBolt(this, 12, 44, (Ic2TileEntity)containerElectricHeatGenerator.base));
        this.addElement((GuiElement<?>)TextLabel.create(this, 34, 66, 109, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.ElectricHeatGenerator.gui.hUmax", ((TileEntityElectricHeatGenerator)((ContainerElectricHeatGenerator)((GuiElectricHeatGenerator)GuiElectricHeatGenerator.this).f_97732_).base).gettransmitHeat(), ((TileEntityElectricHeatGenerator)((ContainerElectricHeatGenerator)((GuiElectricHeatGenerator)GuiElectricHeatGenerator.this).f_97732_).base).getMaxHeatEmittedPerTick());
            }
        }), 5752026, false, true, true).withTooltip("ic2.ElectricHeatGenerator.gui.tooltipheat"));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

