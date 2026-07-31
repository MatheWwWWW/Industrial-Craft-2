/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.util.ResourceLocation
 */
package ic2.core.gui;

import ic2.core.ContainerBase;
import ic2.core.GuiIC2;
import ic2.core.gui.GuiElement;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public abstract class GuiDefaultBackground<T extends ContainerBase<? extends IInventory>>
extends GuiIC2<T> {
    public GuiDefaultBackground(T container) {
        super(container);
    }

    public GuiDefaultBackground(T container, int ySize) {
        super(container, ySize);
    }

    public GuiDefaultBackground(T container, int xSize, int ySize) {
        super(container, xSize, ySize);
    }

    @Override
    protected void drawBackgroundAndTitle(float partialTicks, int mouseX, int mouseY) {
        int side;
        GuiElement.bindCommonTexture();
        this.drawTexturedRect(-16.0, -16.0, 32.0, 32.0, 0.0, 0.0);
        this.drawTexturedRect(this.field_146999_f - 16, -16.0, 32.0, 32.0, 64.0, 0.0);
        this.drawTexturedRect(-16.0, this.field_147000_g - 16, 32.0, 32.0, 0.0, 64.0);
        this.drawTexturedRect(this.field_146999_f - 16, this.field_147000_g - 16, 32.0, 32.0, 64.0, 64.0);
        for (side = 0; side < 2; ++side) {
            int y = this.field_147000_g * side - 16;
            int v = 64 * side;
            for (int x = 16; x < this.field_146999_f - 16; x += 32) {
                int width = Math.min(32, this.field_146999_f - 16 - x);
                this.drawTexturedRect(x, y, width, 32.0, 32.0, v);
            }
        }
        for (side = 0; side < 2; ++side) {
            int x = this.field_146999_f * side - 16;
            int u = 64 * side;
            for (int y = 16; y < this.field_147000_g - 16; y += 32) {
                int height = Math.min(32, this.field_147000_g - 16 - y);
                this.drawTexturedRect(x, y, 32.0, height, u, 32.0);
            }
        }
        for (int y = 16; y < this.field_147000_g - 16; y += 32) {
            int height = Math.min(32, this.field_147000_g - 16 - y);
            for (int x = 16; x < this.field_146999_f - 16; x += 32) {
                int width = Math.min(32, this.field_146999_f - 16 - x);
                this.drawTexturedRect(x, y, width, height, 32.0, 32.0);
            }
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return null;
    }
}

