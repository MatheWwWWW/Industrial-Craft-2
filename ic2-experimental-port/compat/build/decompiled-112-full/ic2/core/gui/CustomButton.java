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
import ic2.core.gui.IOverlaySupplier;
import ic2.core.gui.OverlaySupplier;
import net.minecraft.util.ResourceLocation;

public class CustomButton
extends Button<CustomButton> {
    private final ResourceLocation texture;
    private final IOverlaySupplier overlaySupplier;

    public CustomButton(GuiIC2<?> gui, int x, int y, int width, int height, IClickHandler handler) {
        this(gui, x, y, width, height, 0, 0, null, handler);
    }

    public CustomButton(GuiIC2<?> gui, int x, int y, int width, int height, int overlayX, int overlayY, ResourceLocation texture, IClickHandler handler) {
        this(gui, x, y, width, height, new OverlaySupplier(overlayX, overlayY, overlayX + width, overlayY + height), texture, handler);
    }

    public CustomButton(GuiIC2<?> gui, int x, int y, int width, int height, IOverlaySupplier overlaySupplier, ResourceLocation texture, IClickHandler handler) {
        super(gui, x, y, width, height, handler);
        this.texture = texture;
        this.overlaySupplier = overlaySupplier;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY) {
        if (this.texture != null) {
            CustomButton.bindTexture(this.texture);
            double scale = 0.00390625;
            this.gui.drawTexturedRect(this.x, this.y, this.width, this.height, (double)this.overlaySupplier.getUS() * scale, (double)this.overlaySupplier.getVS() * scale, (double)this.overlaySupplier.getUE() * scale, (double)this.overlaySupplier.getVE() * scale, false);
        }
        if (this.contains(mouseX, mouseY)) {
            this.gui.drawColoredRect(this.x, this.y, this.width, this.height, -2130706433);
        }
        super.drawBackground(mouseX, mouseY);
    }
}

