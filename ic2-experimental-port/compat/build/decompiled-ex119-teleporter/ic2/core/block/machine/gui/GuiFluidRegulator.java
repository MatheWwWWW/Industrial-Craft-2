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
import ic2.core.block.machine.container.ContainerFluidRegulator;
import ic2.core.block.machine.tileentity.TileEntityFluidRegulator;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.TankGauge;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiFluidRegulator
extends Ic2Gui<ContainerFluidRegulator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guifluidregulator.png");

    public GuiFluidRegulator(ContainerFluidRegulator containerFluidRegulator, Inventory inventory, Component component) {
        super(containerFluidRegulator, inventory, component, 184);
        this.addElement(EnergyGauge.asBolt(this, 12, 39, (Ic2TileEntity)containerFluidRegulator.base));
        this.addElement(TankGauge.createNormal(this, 78, 34, ((TileEntityFluidRegulator)containerFluidRegulator.base).getFluidTank()));
        for (int i = 0; i < 4; ++i) {
            int n = (int)Math.pow(10.0, 3 - i);
            this.addElement(new CustomButton(this, 102 + i * 10, 44, 9, 9, this.createEventSender(n)));
            this.addElement(new CustomButton(this, 102 + i * 10, 68, 9, 9, this.createEventSender(-n)));
        }
        this.addElement(new CustomButton(this, 152, 44, 9, 9, this.createEventSender(1001)));
        this.addElement(new CustomButton(this, 152, 68, 9, 9, this.createEventSender(1002)));
        this.addElement(TextLabel.create(this, 105, 57, TextProvider.of((Supplier<String>)((Supplier)() -> ((TileEntityFluidRegulator)containerFluidRegulator.base).getoutputmb() + Localization.translate("ic2.generic.text.mb"))), 2157374, false));
        this.addElement(TextLabel.create(this, 145, 57, TextProvider.of((Supplier<String>)((Supplier)() -> ((TileEntityFluidRegulator)containerFluidRegulator.base).getmodegui())), 2157374, false));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

