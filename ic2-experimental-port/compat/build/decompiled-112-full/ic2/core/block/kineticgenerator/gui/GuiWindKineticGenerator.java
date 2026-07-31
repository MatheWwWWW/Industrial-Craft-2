/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.block.kineticgenerator.gui;

import com.google.common.base.Supplier;
import ic2.core.GuiIC2;
import ic2.core.block.kineticgenerator.container.ContainerWindKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWindKineticGenerator;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.Image;
import ic2.core.gui.Text;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.util.ResourceLocation;

public class GuiWindKineticGenerator
extends GuiIC2<ContainerWindKineticGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/GUIWindKineticGenerator.png");

    public GuiWindKineticGenerator(final ContainerWindKineticGenerator container) {
        super(container);
        this.addElement(Text.create(this, 17, 48, 143, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                if (!((TileEntityWindKineticGenerator)container.base).hasRotor()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.rotormiss");
                }
                if (!((TileEntityWindKineticGenerator)container.base).rotorHasSpace()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.rotorspace");
                }
                if (!((TileEntityWindKineticGenerator)container.base).isWindStrongEnough()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.windweak1");
                }
                return Localization.translate("ic2.WindKineticGenerator.gui.output", ((TileEntityWindKineticGenerator)container.base).getKuOutput());
            }
        }), 2157374, false, 4, 0, false, true));
        this.addElement(Text.create(this, 17, 66, 143, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                if (!((TileEntityWindKineticGenerator)container.base).hasRotor() || !((TileEntityWindKineticGenerator)container.base).rotorHasSpace()) {
                    return null;
                }
                if (!((TileEntityWindKineticGenerator)container.base).isWindStrongEnough()) {
                    return Localization.translate("ic2.WindKineticGenerator.gui.windweak2");
                }
                return ((TileEntityWindKineticGenerator)container.base).getRotorHealth() + " %";
            }
        }), 2157374, false, 4, 0, false, true));
        IEnableHandler warningEnabler = new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityWindKineticGenerator)container.base).isRotorOverloaded();
            }
        };
        this.addElement((GuiElement<?>)((Image)Image.create(this, 44, 20, 30, 26, background, 256, 256, 176, 0, 206, 26).withEnableHandler(warningEnabler)).withTooltip("ic2.WindKineticGenerator.error.overload"));
        this.addElement((GuiElement<?>)((Image)Image.create(this, 102, 20, 30, 26, background, 256, 256, 176, 0, 206, 26).withEnableHandler(warningEnabler)).withTooltip("ic2.WindKineticGenerator.error.overload"));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

