/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.gui;

import ic2.core.GuiIC2;
import ic2.core.gui.Button;
import ic2.core.gui.IClickHandler;
import ic2.core.gui.IEnableHandler;
import ic2.core.gui.MouseButton;
import net.minecraft.util.ResourceLocation;

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

    public VanillaButton(GuiIC2<?> gui, int x, int y, int width, int height, IClickHandler handler) {
        super(gui, x, y, width, height, handler);
    }

    public VanillaButton withDisableHandler(IEnableHandler handler) {
        this.disableHandler = handler;
        return this;
    }

    public boolean isDisabled() {
        return this.disableHandler != null && !this.disableHandler.isEnabled();
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        int v;
        int u;
        VanillaButton.bindTexture(texture);
        if (this.isDisabled()) {
            u = 0;
            v = 46;
        } else if (!this.isActive(mouseX, mouseY)) {
            u = 0;
            v = 66;
        } else {
            u = 0;
            v = 86;
        }
        int minLeft = 2;
        int minRight = 2;
        while (this.width < minLeft + minRight) {
            if (minLeft > minRight) {
                --minLeft;
                continue;
            }
            --minRight;
        }
        int cx = this.x;
        int remainingWidth = this.width;
        int cWidth = Math.min(remainingWidth, 200) - minRight;
        VanillaButton.drawVerticalPiece(this.gui, cx, this.y, cWidth, this.height, u, v);
        cx += cWidth;
        remainingWidth -= cWidth;
        while (remainingWidth > 200 - minLeft) {
            cWidth = Math.min(remainingWidth, 200 - minLeft) - minRight;
            VanillaButton.drawVerticalPiece(this.gui, cx, this.y, cWidth, this.height, u + minLeft, v);
            cx += cWidth;
            remainingWidth -= cWidth;
        }
        VanillaButton.drawVerticalPiece(this.gui, cx, this.y, remainingWidth, this.height, u + 200 - remainingWidth, v);
        super.drawBackground(mouseX, mouseY);
    }

    private static void drawVerticalPiece(GuiIC2<?> gui, int x, int y, int width, int height, int u, int v) {
        int minTop = 2;
        int minBottom = 3;
        while (height < minTop + minBottom) {
            if (minTop > minBottom) {
                --minTop;
                continue;
            }
            --minBottom;
        }
        int cHeight = Math.min(height, 20) - minBottom;
        gui.drawTexturedRect(x, y, width, cHeight, u, v);
        y += cHeight;
        height -= cHeight;
        while (height > 20 - minTop) {
            cHeight = Math.min(height, 20 - minTop) - minBottom;
            gui.drawTexturedRect(x, y, width, cHeight, u, v + minTop);
            y += cHeight;
            height -= cHeight;
        }
        gui.drawTexturedRect(x, y, width, height, u, v + 20 - height);
    }

    protected boolean isActive(int mouseX, int mouseY) {
        return this.contains(mouseX, mouseY);
    }

    @Override
    protected int getTextColor(int mouseX, int mouseY) {
        return this.isDisabled() ? 0xA0A0A0 : (this.isActive(mouseX, mouseY) ? 0xFFFFA0 : 0xE0E0E0);
    }

    @Override
    protected boolean onMouseClick(int mouseX, int mouseY, MouseButton button) {
        return this.isDisabled() ? false : super.onMouseClick(mouseX, mouseY, button);
    }
}

