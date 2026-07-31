/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Inventory
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.ContainerBase;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public abstract class GuiDefaultBackground<T extends ContainerBase<? extends Container>>
extends Ic2Gui<T> {
    public GuiDefaultBackground(T t, Inventory inventory, Component component) {
        super(t, inventory, component);
    }

    public GuiDefaultBackground(T t, Inventory inventory, Component component, int n) {
        super(t, inventory, component, n);
    }

    public GuiDefaultBackground(T t, Inventory inventory, Component component, int n, int n2) {
        super(t, inventory, component, n, n2);
    }

    @Override
    protected void drawBackgroundAndTitle(PoseStack poseStack, float f, int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        GuiElement.bindCommonTexture();
        int n8 = this.f_97726_;
        int n9 = this.f_97727_;
        this.drawTexturedRect(poseStack, -16.0, -16.0, 32.0, 32.0, 0.0, 0.0);
        this.drawTexturedRect(poseStack, n8 - 16, -16.0, 32.0, 32.0, 64.0, 0.0);
        this.drawTexturedRect(poseStack, -16.0, n9 - 16, 32.0, 32.0, 0.0, 64.0);
        this.drawTexturedRect(poseStack, n8 - 16, n9 - 16, 32.0, 32.0, 64.0, 64.0);
        for (n7 = 0; n7 < 2; ++n7) {
            n6 = n9 * n7 - 16;
            n5 = 64 * n7;
            for (n4 = 16; n4 < n8 - 16; n4 += 32) {
                n3 = Math.min(32, n8 - 16 - n4);
                this.drawTexturedRect(poseStack, n4, n6, n3, 32.0, 32.0, n5);
            }
        }
        for (n7 = 0; n7 < 2; ++n7) {
            n6 = n8 * n7 - 16;
            n5 = 64 * n7;
            for (n4 = 16; n4 < n9 - 16; n4 += 32) {
                n3 = Math.min(32, n9 - 16 - n4);
                this.drawTexturedRect(poseStack, n6, n4, 32.0, n3, n5, 32.0);
            }
        }
        for (n7 = 16; n7 < n9 - 16; n7 += 32) {
            n6 = Math.min(32, n9 - 16 - n7);
            for (n5 = 16; n5 < n8 - 16; n5 += 32) {
                n4 = Math.min(32, n8 - 16 - n5);
                this.drawTexturedRect(poseStack, n5, n7, n4, n6, 32.0, 32.0);
            }
        }
    }

    @Override
    protected ResourceLocation getTexture() {
        return null;
    }
}

