/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.heatgenerator.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.block.heatgenerator.container.ContainerFluidHeatGenerator;
import ic2.core.block.heatgenerator.tileentity.TileEntityFluidHeatGenerator;
import ic2.core.gui.TankGauge;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiFluidHeatGenerator
extends Ic2Gui<ContainerFluidHeatGenerator> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guifluidheatgenerator.png");

    public GuiFluidHeatGenerator(ContainerFluidHeatGenerator containerFluidHeatGenerator, Inventory inventory, Component component) {
        super(containerFluidHeatGenerator, inventory, component);
        this.addElement(TankGauge.createNormal(this, 70, 20, ((TileEntityFluidHeatGenerator)containerFluidHeatGenerator.base).getFluidTank()));
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 96, 33, Localization.translate("ic2.FluidHeatGenerator.gui.info.Emit") + ((TileEntityFluidHeatGenerator)((ContainerFluidHeatGenerator)this.f_97732_).base).gettransmitHeat(), 5752026);
        this.drawString(poseStack, 96, 52, Localization.translate("ic2.FluidHeatGenerator.gui.info.MaxEmit") + ((TileEntityFluidHeatGenerator)((ContainerFluidHeatGenerator)this.f_97732_).base).getMaxHeatEmittedPerTick(), 5752026);
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

