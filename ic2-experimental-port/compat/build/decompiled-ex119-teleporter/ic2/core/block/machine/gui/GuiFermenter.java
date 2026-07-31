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
import ic2.core.block.machine.container.ContainerFermenter;
import ic2.core.block.machine.tileentity.TileEntityFermenter;
import ic2.core.gui.Gauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.LinkedGauge;
import ic2.core.gui.TankGauge;
import ic2.core.gui.dynamic.IGuiValueProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiFermenter
extends Ic2Gui<ContainerFermenter> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/guifermenter.png");

    public GuiFermenter(final ContainerFermenter containerFermenter, Inventory inventory, Component component) {
        super(containerFermenter, inventory, component, 184);
        this.addElement(TankGauge.createPlain(this, 38, 49, 48, 30, ((TileEntityFermenter)containerFermenter.base).getInputTank()));
        this.addElement(TankGauge.createNormal(this, 125, 22, ((TileEntityFermenter)containerFermenter.base).getOutputTank()));
        this.addElement((GuiElement<?>)new LinkedGauge(this, 42, 41, (IGuiValueProvider)containerFermenter.base, "heat", Gauge.GaugeStyle.HeatFermenter).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.Fermenter.gui.info.conversion") + " " + (int)(((TileEntityFermenter)containerFermenter.base).getGuiValue("heat") * 100.0) + "%";
            }
        }));
        this.addElement((GuiElement<?>)new LinkedGauge(this, 38, 88, (IGuiValueProvider)containerFermenter.base, "progress", Gauge.GaugeStyle.ProgressFermenter).withTooltip("ic2.Fermenter.gui.info.waste"));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

