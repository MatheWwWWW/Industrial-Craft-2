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
import ic2.core.block.machine.container.ContainerMatter;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import ic2.core.gui.TankGauge;
import ic2.core.init.Localization;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiMatter
extends Ic2Gui<ContainerMatter> {
    public String progressLabel;
    public String amplifierLabel;

    public GuiMatter(ContainerMatter containerMatter, Inventory inventory, Component component) {
        super(containerMatter, inventory, component);
        this.addElement(TankGauge.createNormal(this, 96, 22, ((TileEntityMatter)containerMatter.base).fluidTank));
        this.progressLabel = Localization.translate("ic2.Matter.gui.info.progress");
        this.amplifierLabel = Localization.translate("ic2.Matter.gui.info.amplifier");
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 8, 22, this.progressLabel, 0x404040);
        this.drawString(poseStack, 18, 31, ((TileEntityMatter)((ContainerMatter)this.f_97732_).base).getProgressAsString(), 0x404040);
        if (((TileEntityMatter)((ContainerMatter)this.f_97732_).base).scrap > 0) {
            this.drawString(poseStack, 8, 46, this.amplifierLabel, 0x404040);
            this.drawString(poseStack, 8, 58, "" + ((TileEntityMatter)((ContainerMatter)this.f_97732_).base).scrap, 0x404040);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation("ic2", "textures/gui/guimatter.png");
    }
}

