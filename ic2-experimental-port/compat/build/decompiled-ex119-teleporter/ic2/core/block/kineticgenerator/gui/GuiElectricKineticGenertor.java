/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.gui;

import com.google.common.base.Supplier;
import ic2.core.Ic2Gui;
import ic2.core.block.kineticgenerator.container.ContainerElectricKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityElectricKineticGenerator;
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

public class GuiElectricKineticGenertor
extends Ic2Gui<ContainerElectricKineticGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guielectrickineticgenerator.png");

    public GuiElectricKineticGenertor(ContainerElectricKineticGenerator containerElectricKineticGenerator, Inventory inventory, Component component) {
        super(containerElectricKineticGenerator, inventory, component);
        this.addElement((GuiElement<?>)new SlotGrid(this, 43, 26, 5, 2, SlotGrid.SlotStyle.Normal).withTooltip("ic2.ElectricKineticGenerator.gui.motors"));
        this.addElement(EnergyGauge.asBolt(this, 12, 44, (Ic2TileEntity)containerElectricKineticGenerator.base));
        this.addElement((GuiElement<?>)TextLabel.create(this, 29, 66, 119, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.ElectricKineticGenerator.gui.kUmax", ((TileEntityElectricKineticGenerator)((ContainerElectricKineticGenerator)((GuiElectricKineticGenertor)GuiElectricKineticGenertor.this).f_97732_).base).getMaxKU(), ((TileEntityElectricKineticGenerator)((ContainerElectricKineticGenerator)((GuiElectricKineticGenertor)GuiElectricKineticGenertor.this).f_97732_).base).getMaxKUForGUI());
            }
        }), 5752026, false, true, true).withTooltip("ic2.ElectricKineticGenerator.gui.tooltipkin"));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

