/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.Button;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import net.minecraft.resources.ResourceLocation;

public class VanillaButton
extends Button<VanillaButton> {
    protected IEnableHandler disableHandler;
    private static final ResourceLocation texture = new ResourceLocation("textures/gui/widgets.png");
    private static final int uNormal = 0;
    private static final int vNormal = 66;
    private static final int uHover = 0;
    private static final int vHover = 86;
    private static final int uDisabled = 0;
    private static final int vDisabled = 46;
    private static final int rawWidth = 200;
    private static final int rawHeight = 20;
    private static final int minLeft = 2;
    private static final int minRight = 2;
    private static final int minTop = 2;
    private static final int minBottom = 3;
    private static final int colorNormal = 0xE0E0E0;
    private static final int colorHover = 0xFFFFA0;
    private static final int colorDisabled = 0xA0A0A0;

    public VanillaButton(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, IClickHandler iClickHandler) {
        super(ic2Gui, n, n2, n3, n4, iClickHandler);
    }

    public VanillaButton withDisableHandler(IEnableHandler iEnableHandler) {
        this.disableHandler = iEnableHandler;
        return this;
    }

    public boolean isDisabled() {
        return this.disableHandler != null && !this.disableHandler.isEnabled();
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        int n3;
        int n4;
        VanillaButton.bindTexture(texture);
        if (this.isDisabled()) {
            n4 = 0;
            n3 = 46;
        } else if (!this.isActive(n, n2)) {
            n4 = 0;
            n3 = 66;
        } else {
            n4 = 0;
            n3 = 86;
        }
        int n5 = 2;
        int n6 = 2;
        while (this.width < n5 + n6) {
            if (n5 > n6) {
                --n5;
                continue;
            }
            --n6;
        }
        int n7 = this.x;
        int n8 = this.width;
        int n9 = Math.min(n8, 200) - n6;
        VanillaButton.drawVerticalPiece(this.gui, poseStack, n7, this.y, n9, this.height, n4, n3);
        n7 += n9;
        n8 -= n9;
        while (n8 > 200 - n5) {
            n9 = Math.min(n8, 200 - n5) - n6;
            VanillaButton.drawVerticalPiece(this.gui, poseStack, n7, this.y, n9, this.height, n4 + n5, n3);
            n7 += n9;
            n8 -= n9;
        }
        VanillaButton.drawVerticalPiece(this.gui, poseStack, n7, this.y, n8, this.height, n4 + 200 - n8, n3);
        super.drawBackground(poseStack, n, n2);
    }

    private static void drawVerticalPiece(Ic2Gui<?> ic2Gui, PoseStack poseStack, int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = 2;
        int n8 = 3;
        while (n4 < n7 + n8) {
            if (n7 > n8) {
                --n7;
                continue;
            }
            --n8;
        }
        int n9 = Math.min(n4, 20) - n8;
        ic2Gui.drawTexturedRect(poseStack, n, n2, n3, n9, n5, n6);
        n2 += n9;
        n4 -= n9;
        while (n4 > 20 - n7) {
            n9 = Math.min(n4, 20 - n7) - n8;
            ic2Gui.drawTexturedRect(poseStack, n, n2, n3, n9, n5, n6 + n7);
            n2 += n9;
            n4 -= n9;
        }
        ic2Gui.drawTexturedRect(poseStack, n, n2, n3, n4, n5, n6 + 20 - n4);
    }

    protected boolean isActive(int n, int n2) {
        return this.contains(n, n2);
    }

    @Override
    protected int getTextColor(int n, int n2) {
        return this.isDisabled() ? 0xA0A0A0 : (this.isActive(n, n2) ? 0xFFFFA0 : 0xE0E0E0);
    }

    @Override
    protected boolean onMouseClick(int n, int n2, MouseButton mouseButton) {
        return this.isDisabled() ? false : super.onMouseClick(n, n2, mouseButton);
    }
}

