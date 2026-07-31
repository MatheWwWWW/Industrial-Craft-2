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
import ic2.core.block.kineticgenerator.container.ContainerSteamKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntitySteamKineticGenerator;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.Image;
import ic2.core.gui.SlotGrid;
import ic2.core.gui.TankGauge;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiSteamKineticGenerator
extends Ic2Gui<ContainerSteamKineticGenerator> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("ic2", "textures/gui/guisteamkineticgenerator.png");

    public GuiSteamKineticGenerator(final ContainerSteamKineticGenerator containerSteamKineticGenerator, Inventory inventory, Component component) {
        super(containerSteamKineticGenerator, inventory, component);
        this.addElement(TankGauge.createPlain(this, 75, 21, 26, 26, ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).getDistilledWaterTank()));
        this.addElement((GuiElement<?>)new SlotGrid(this, 80, 26, SlotGrid.SlotStyle.Plain).withTooltip((java.util.function.Supplier<String>)new Supplier<String>(){

            public String get() {
                if (!((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).hasTurbine()) {
                    return "ic2.SteamKineticGenerator.gui.turbineslot";
                }
                return null;
            }
        }));
        this.addElement((GuiElement<?>)((Image)Image.create(this, 36, 20, 30, 26, TEXTURE, 256, 256, 176, 0, 206, 26).withEnableHandler(new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).hasTurbine() && ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).isVentingSteam();
            }
        })).withTooltip("ic2.SteamKineticGenerator.gui.ventingWarning"));
        this.addElement((GuiElement<?>)((Image)Image.create(this, 110, 20, 30, 26, TEXTURE, 256, 256, 176, 0, 206, 26).withEnableHandler(new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).hasTurbine() && ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).isThrottled();
            }
        })).withTooltip("ic2.SteamKineticGenerator.gui.condensationwarrning"));
        this.addElement(TextLabel.create(this, 8, 51, 160, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate(this.getRaw());
            }

            private String getRaw() {
                if (!((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).hasTurbine()) {
                    return "ic2.SteamKineticGenerator.gui.error.noturbine";
                }
                if (((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).isTurbineBlockedByWater()) {
                    return "ic2.SteamKineticGenerator.gui.error.filledupwithwater";
                }
                if (((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).getActive()) {
                    return "ic2.SteamKineticGenerator.gui.aktive";
                }
                return "ic2.SteamKineticGenerator.gui.waiting";
            }
        }), () -> {
            if (!((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).hasTurbine() || ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).isTurbineBlockedByWater()) {
                return 14946604;
            }
            return 2157374;
        }, false, 4, 0, false, true));
        this.addElement((GuiElement<?>)TextLabel.create(this, 8, 68, 160, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return Localization.translate("ic2.SteamKineticGenerator.gui.turbine.ouput", ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).getKUoutput());
            }
        }), 2157374, false, 4, 0, false, true).withEnableHandler(new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).hasTurbine() && !((TileEntitySteamKineticGenerator)containerSteamKineticGenerator.base).isTurbineBlockedByWater();
            }
        }));
    }

    @Override
    protected ResourceLocation getTexture() {
        return TEXTURE;
    }
}

