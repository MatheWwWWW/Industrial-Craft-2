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
import ic2.core.block.machine.container.ContainerScanner;
import ic2.core.block.machine.tileentity.TileEntityScanner;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.CustomButton;
import ic2.core.gui.EnergyGauge;
import ic2.core.gui.GuiElement;
import ic2.core.gui.IEnableHandler;
import ic2.core.init.Localization;
import ic2.core.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiScanner
extends Ic2Gui<ContainerScanner> {
    private static final ResourceLocation background = new ResourceLocation("ic2", "textures/gui/guiscanner.png");
    private final String[] info = new String[9];

    public GuiScanner(final ContainerScanner containerScanner, Inventory inventory, Component component) {
        super(containerScanner, inventory, component);
        this.addElement(EnergyGauge.asBolt(this, 12, 25, (Ic2TileEntity)containerScanner.base));
        this.addElement((GuiElement<?>)((CustomButton)new CustomButton(this, 102, 49, 12, 12, 176, 57, background, this.createEventSender(0)).withEnableHandler(new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityScanner)containerScanner.base).getState() == TileEntityScanner.State.COMPLETED || ((TileEntityScanner)containerScanner.base).getState() == TileEntityScanner.State.TRANSFER_ERROR || ((TileEntityScanner)containerScanner.base).getState() == TileEntityScanner.State.FAILED;
            }
        })).withTooltip("ic2.Scanner.gui.button.delete"));
        this.addElement((GuiElement<?>)((CustomButton)new CustomButton(this, 143, 49, 24, 12, 176, 69, background, this.createEventSender(1)).withEnableHandler(new IEnableHandler(){

            @Override
            public boolean isEnabled() {
                return ((TileEntityScanner)containerScanner.base).getState() == TileEntityScanner.State.COMPLETED || ((TileEntityScanner)containerScanner.base).getState() == TileEntityScanner.State.TRANSFER_ERROR;
            }
        })).withTooltip("ic2.Scanner.gui.button.save"));
        this.info[1] = Localization.translate("ic2.Scanner.gui.info1");
        this.info[2] = Localization.translate("ic2.Scanner.gui.info2");
        this.info[3] = Localization.translate("ic2.Scanner.gui.info3");
        this.info[4] = Localization.translate("ic2.Scanner.gui.info4");
        this.info[5] = Localization.translate("ic2.Scanner.gui.info5");
        this.info[6] = Localization.translate("ic2.Scanner.gui.info6");
        this.info[7] = Localization.translate("ic2.Scanner.gui.info7");
        this.info[8] = Localization.translate("ic2.Scanner.gui.info8");
    }

    @Override
    protected void drawForegroundLayer(PoseStack poseStack, int n, int n2) {
        super.drawForegroundLayer(poseStack, n, n2);
        this.drawString(poseStack, 105, 6, this.info[5] + ":", 0x404040);
        TileEntityScanner tileEntityScanner = (TileEntityScanner)((ContainerScanner)this.f_97732_).base;
        switch (tileEntityScanner.getState()) {
            case IDLE: {
                this.drawString(poseStack, 10, 69, Localization.translate("ic2.Scanner.gui.idle"), 15461152);
                break;
            }
            case NO_STORAGE: {
                this.drawString(poseStack, 10, 69, this.info[2], 15461152);
                break;
            }
            case SCANNING: {
                this.drawString(poseStack, 10, 69, this.info[1], 2157374);
                this.drawString(poseStack, 125, 69, tileEntityScanner.getPercentageDone() + "%", 2157374);
                break;
            }
            case NO_ENERGY: {
                this.drawString(poseStack, 10, 69, this.info[3], 14094352);
                break;
            }
            case ALREADY_RECORDED: {
                this.drawString(poseStack, 10, 69, this.info[8], 14094352);
                break;
            }
            case FAILED: {
                this.drawString(poseStack, 10, 69, this.info[4], 2157374);
                this.drawString(poseStack, 110, 30, this.info[6], 14094352);
                break;
            }
            case COMPLETED: 
            case TRANSFER_ERROR: {
                if (tileEntityScanner.getState() == TileEntityScanner.State.COMPLETED) {
                    this.drawString(poseStack, 10, 69, this.info[4], 2157374);
                }
                if (tileEntityScanner.getState() == TileEntityScanner.State.TRANSFER_ERROR) {
                    this.drawString(poseStack, 10, 69, this.info[7], 14094352);
                }
                this.drawString(poseStack, 105, 25, Util.toSiString(tileEntityScanner.patternUu, 4) + "B UUM", 0xFFFFFF);
                this.drawString(poseStack, 105, 36, Util.toSiString(tileEntityScanner.patternEu, 4) + "EU", 0xFFFFFF);
            }
        }
    }

    @Override
    protected void m_7286_(PoseStack poseStack, float f, int n, int n2) {
        super.m_7286_(poseStack, f, n, n2);
        this.bindTexture();
        TileEntityScanner tileEntityScanner = (TileEntityScanner)((ContainerScanner)this.f_97732_).base;
        int n3 = tileEntityScanner.getSubPercentageDoneScaled(66);
        if (n3 > 0) {
            this.drawTexturedRect(poseStack, this.f_97735_ + 30, this.f_97736_ + 20, 176.0, 14.0, n3, 43.0);
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return background;
    }
}

