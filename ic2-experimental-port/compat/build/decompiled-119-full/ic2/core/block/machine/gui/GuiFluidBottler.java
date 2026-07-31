/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.block.machine.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.block.machine.container.ContainerFluidBottler;
import ic2.core.block.machine.tileentity.TileEntityFluidBottler;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.TankGauge;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiFluidBottler
extends Ic2Gui<ContainerFluidBottler> {
    public GuiFluidBottler(ContainerFluidBottler containerFluidBottler, Inventory inventory, Component component) {
        super(containerFluidBottler, inventory, component, 184);
        this.addElement(EnergyGauge.asBolt(this, 12, 35, (Ic2TileEntity)containerFluidBottler.base));
        this.addElement(TankGauge.createNormal(this, 78, 34, ((TileEntityFluidBottler)containerFluidBottler.base).fluidTank));
    }

    @Override
    protected void m_7286_(PoseStack poseStack, float f, int n, int n2) {
        super.m_7286_(poseStack, f, n, n2);
        this.bindTexture();
        int n3 = Math.round(((TileEntityFluidBottler)((ContainerFluidBottler)this.f_97732_).base).getProgress() * 16.0f);
        if (n3 > 0) {
            this.drawTexturedRect(poseStack, this.f_97735_ + 61, this.f_97736_ + 36, 198.0, 0.0, n3, 13.0);
            this.drawTexturedRect(poseStack, this.f_97735_ + 61, this.f_97736_ + 73, 198.0, 0.0, n3, 13.0);
            this.drawTexturedRect(poseStack, this.f_97735_ + 99, this.f_97736_ + 55, 198.0, 0.0, n3, 13.0);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guibottler.png");
    }
}

