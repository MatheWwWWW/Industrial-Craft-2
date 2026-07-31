/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.Button;
import ic2.core.gui.IClickHandler;

public class BasicButton
extends Button<BasicButton> {
    private final ButtonStyle style;

    public static BasicButton create(Ic2Gui<?> ic2Gui, int n, int n2, IClickHandler iClickHandler, ButtonStyle buttonStyle) {
        return new BasicButton(ic2Gui, n, n2, iClickHandler, buttonStyle);
    }

    protected BasicButton(Ic2Gui<?> ic2Gui, int n, int n2, IClickHandler iClickHandler, ButtonStyle buttonStyle) {
        super(ic2Gui, n, n2, buttonStyle.width, buttonStyle.height, iClickHandler);
        this.style = buttonStyle;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        BasicButton.bindCommonTexture();
        this.gui.drawTexturedRect(poseStack, this.x, this.y, this.style.width, this.style.height, this.style.u, this.style.v);
        super.drawBackground(poseStack, n, n2);
    }

    public static enum ButtonStyle {
        AdvMinerReset(192, 32, 36, 15),
        AdvMinerMode(228, 32, 18, 15),
        AdvMinerSilkTouch(192, 47, 18, 15);

        final int u;
        final int v;
        final int width;
        final int height;

        private ButtonStyle(int n2, int n3, int n4, int n5) {
            this.u = n2;
            this.v = n3;
            this.width = n4;
            this.height = n5;
        }
    }
}

