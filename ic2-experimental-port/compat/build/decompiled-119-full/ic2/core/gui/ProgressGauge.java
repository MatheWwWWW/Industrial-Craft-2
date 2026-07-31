/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.block.comp.Process;
import ic2.core.block.tileentity.Ic2TileEntity;
import ic2.core.gui.GuiElement;
import ic2.core.util.Util;

public class ProgressGauge
extends GuiElement<ProgressGauge> {
    private final Process process;
    private final ProgressBarType type;

    public ProgressGauge(Ic2Gui<?> ic2Gui, int n, int n2, Ic2TileEntity ic2TileEntity, ProgressBarType progressBarType) {
        super(ic2Gui, n, n2, progressBarType.w, progressBarType.h);
        this.type = progressBarType;
        this.process = ic2TileEntity.getComponent(Process.class);
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        ProgressGauge.bindCommonTexture();
        this.gui.drawTexturedRect(poseStack, this.x, this.y, this.type.w, this.type.h, this.type.emptyX, this.type.emptyY);
        int n3 = Util.limit((int)Math.round(this.getProgressRatio() * (double)this.type.w), 0, this.type.w);
        if (n3 > 0) {
            this.gui.drawTexturedRect(poseStack, this.x, this.y, n3, this.type.h, this.type.fullX, this.type.fullY);
        }
    }

    protected double getProgressRatio() {
        return this.process.getProgressRatio();
    }

    public static enum ProgressBarType {
        type_1(165, 0, 165, 16, 22, 15),
        type_2(165, 35, 165, 52, 21, 11),
        type_3(165, 64, 165, 80, 22, 15),
        type_4(165, 96, 165, 112, 22, 15),
        type_5(133, 64, 133, 80, 18, 15);

        private int emptyX;
        private int emptyY;
        private int fullX;
        private int fullY;
        private int w;
        private int h;

        private ProgressBarType(int n2, int n3, int n4, int n5, int n6, int n7) {
            this.emptyX = n2;
            this.emptyY = n3;
            this.fullX = n4;
            this.fullY = n5;
            this.w = n6;
            this.h = n7;
        }
    }
}

