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
import ic2.core.block.heatgenerator.container.ContainerRTHeatGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityRTHeatGenerator;
import ic2.core.gui.GuiElement;
import ic2.core.gui.TextLabel;
import ic2.core.gui.dynamic.TextProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiRTHeatGenerator
extends Ic2Gui<ContainerRTHeatGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guirtheatgenerator.png");

    public GuiRTHeatGenerator(final ContainerRTHeatGenerator containerRTHeatGenerator, Inventory inventory, Component component) {
        super(containerRTHeatGenerator, inventory, component);
        this.addElement((GuiElement<?>)TextLabel.create(this, 49, 66, 79, 13, TextProvider.of(new Supplier<String>(){

            public String get() {
                return ((TileEntityRTHeatGenerator)containerRTHeatGenerator.base).gettransmitHeat() + " / " + ((TileEntityRTHeatGenerator)containerRTHeatGenerator.base).getMaxHeatEmittedPerTick();
            }
        }), 5752026, false, 0, 0, true, true).withTooltip("ic2.RTHeatGenerator.gui.tooltipheat"));
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

