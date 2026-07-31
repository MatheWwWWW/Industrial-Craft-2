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
import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.Image;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiWindKineticGenerator
extends Ic2Gui<ContainerWindKineticGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guiwindkineticgenerator.png");

    public GuiWindKineticGenerator(final ContainerWindKineticGenerator containerWindKineticGenerator, Inventory inventory, Component component) {
        super(containerWindKineticGenerator, inventory, component);
        this.addElement(TextLabel.create(this, 17, 48, 143, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                if (!((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).hasRotor()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.rotormiss");
                }
                if (!((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).rotorHasSpace()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.rotorspace");
                }
                if (!((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).isWindStrongEnough()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.windweak1");
                }
                return Localization.translate("ic2.WindKineticGenerator.gui.output", ((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).getKuOutput());
            }
        }), 2157374, false, 4, 0, false, true));
        this.addElement(TextLabel.create(this, 17, 66, 143, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                if (!((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).hasRotor() || !((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).rotorHasSpace()) {
                    return null;
                }
                if (!((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).isWindStrongEnough()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.windweak2");
                }
                return ((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).getRotorHealth() + " %";
            }
        }), 2157374, false, 4, 0, false, true));
        IEnableHandler iEnableHandler = new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityWindKineticGenerator)containerWindKineticGenerator.base).isRotorOverloaded();
            }
        };
        this.addElement((GuiElement<?>)((Image)Image.create(this, 44, 20, 30, 26, background, 256, 256, 176, 0, 206, 26).withEnableHandler(iEnableHandler)).withTooltip("ic2.WindKineticGenerator.error.overload"));
        this.addElement((GuiElement<?>)((Image)Image.create(this, 102, 20, 30, 26, background, 256, 256, 176, 0, 206, 26).withEnableHandler(iEnableHandler)).withTooltip("ic2.WindKineticGenerator.error.overload"));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

