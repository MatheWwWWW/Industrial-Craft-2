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
import ic2.core.gui.IOverlaySupplier;
import ic2.core.gui.OverlaySupplier;
import net.minecraft.resources.ResourceLocation;

public class CustomButton
extends Button<CustomButton> {
    private final ResourceLocation texture;
    private final IOverlaySupplier overlaySupplier;

    public CustomButton(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, IClickHandler iClickHandler) {
        this(ic2Gui, n, n2, n3, n4, 0, 0, null, iClickHandler);
    }

    public CustomButton(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, int n5, int n6, ResourceLocation resourceLocation, IClickHandler iClickHandler) {
        this(ic2Gui, n, n2, n3, n4, new OverlaySupplier(n5, n6, n5 + n3, n6 + n4), resourceLocation, iClickHandler);
    }

    public CustomButton(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, IOverlaySupplier iOverlaySupplier, ResourceLocation resourceLocation, IClickHandler iClickHandler) {
        super(ic2Gui, n, n2, n3, n4, iClickHandler);
        this.texture = resourceLocation;
        this.overlaySupplier = iOverlaySupplier;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        if (this.texture != null) {
            CustomButton.bindTexture(this.texture);
            double d = 0.00390625;
            this.gui.drawTexturedRect(poseStack, this.x, this.y, this.width, this.height, (double)this.overlaySupplier.getUS() * d, (double)this.overlaySupplier.getVS() * d, (double)this.overlaySupplier.getUE() * d, (double)this.overlaySupplier.getVE() * d, false);
        }
        if (this.contains(n, n2)) {
            this.gui.drawColoredRect(poseStack, this.x, this.y, this.width, this.height, -2130706433);
        }
        super.drawBackground(poseStack, n, n2);
    }
}

