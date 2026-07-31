/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.kineticgenerator.gui;

import ic2.core.Ic2Gui;
import ic2.core.block.kineticgenerator.container.ContainerStirlingKineticGenerator;
import ic2.core.block.kineticgenerator.tileentity.TileEntityStirlingKineticGenerator;
import ic2.core.gui.TankGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiStirlingKineticGenerator
extends Ic2Gui<ContainerStirlingKineticGenerator> {
    public GuiStirlingKineticGenerator(ContainerStirlingKineticGenerator containerStirlingKineticGenerator, Inventory inventory, Component component) {
        super(containerStirlingKineticGenerator, inventory, component, 204);
        this.addElement(TankGauge.createPlain(this, 19, 47, 12, 44, ((TileEntityStirlingKineticGenerator)containerStirlingKineticGenerator.base).getInputTank()));
        this.addElement(TankGauge.createPlain(this, 145, 47, 12, 44, ((TileEntityStirlingKineticGenerator)containerStirlingKineticGenerator.base).getOutputTank()));
    }

    @Override
    protected ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guistirlingkineticgenerator.png");
    }
}

