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
import ic2.core.block.kineticgenerator.container.ContainerWaterKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityWaterKineticGenerator;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiWaterKineticGenerator
extends Ic2Gui<ContainerWaterKineticGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guiwaterkineticgenerator.png");

    public GuiWaterKineticGenerator(ContainerWaterKineticGenerator containerWaterKineticGenerator, Inventory inventory, Component component) {
        super(containerWaterKineticGenerator, inventory, component);
        IEnableHandler iEnableHandler = () -> ((TileEntityWaterKineticGenerator)containerWaterKineticGenerator.base).type != TileEntityWaterKineticGenerator.BiomeState.INVALID;
        IEnableHandler iEnableHandler2 = IEnableHandler.EnableHandlers.not(iEnableHandler);
        this.addElement((GuiElement<?>)TextLabel.create(this, 38, 52, TextProvider.ofTranslated("ic2.WaterKineticGenerator.gui.wrongbiome1"), 2157374, false).withEnableHandler(iEnableHandler2));
        this.addElement((GuiElement<?>)TextLabel.create(this, 45, 69, TextProvider.ofTranslated("ic2.WaterKineticGenerator.gui.wrongbiome2"), 2157374, false).withEnableHandler(iEnableHandler2));
        IEnableHandler iEnableHandler3 = ((TileEntityWaterKineticGenerator)containerWaterKineticGenerator.base).rotorSlot::isEmpty;
        this.addElement((GuiElement<?>)TextLabel.create(this, 27, 52, TextProvider.ofTranslated("ic2.WaterKineticGenerator.gui.rotormiss"), 2157374, false).withEnableHandler(IEnableHandler.EnableHandlers.and(iEnableHandler, iEnableHandler3)));
        IEnableHandler iEnableHandler4 = IEnableHandler.EnableHandlers.not(iEnableHandler3);
        IEnableHandler iEnableHandler5 = () -> ((TileEntityWaterKineticGenerator)containerWaterKineticGenerator.base).checkSpace(((TileEntityWaterKineticGenerator)containerWaterKineticGenerator.base).getRotorDiameter(), true) == 0;
        this.addElement((GuiElement<?>)TextLabel.create(this, 20, 52, TextProvider.ofTranslated("ic2.WaterKineticGenerator.gui.rotorspace"), 2157374, false).withEnableHandler(IEnableHandler.EnableHandlers.and(iEnableHandler, iEnableHandler4, IEnableHandler.EnableHandlers.not(iEnableHandler5))));
        this.addElement((GuiElement<?>)TextLabel.create(this, 55, 52, TextProvider.of((Supplier<String>)((Supplier)() -> Localization.translate("ic2.WaterKineticGenerator.gui.output", ((TileEntityWaterKineticGenerator)containerWaterKineticGenerator.base).getKuOutput()))), 2157374, false).withEnableHandler(IEnableHandler.EnableHandlers.and(iEnableHandler, iEnableHandler4, iEnableHandler5)));
        this.addElement((GuiElement<?>)TextLabel.create(this, 46, 70, TextProvider.of((Supplier<String>)((Supplier)((TileEntityWaterKineticGenerator)containerWaterKineticGenerator.base)::getRotorHealth)), 2157374, false).withEnableHandler(IEnableHandler.EnableHandlers.and(iEnableHandler, iEnableHandler4, iEnableHandler5)));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

