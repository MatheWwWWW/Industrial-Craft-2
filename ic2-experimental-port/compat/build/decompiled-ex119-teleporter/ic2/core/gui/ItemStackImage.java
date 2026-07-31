/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Supplier
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.gui;

import com.google.common.base.Supplier;
import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.util.StackUtil;
import net.minecraft.world.item.ItemStack;

public class ItemStackImage
extends GuiElement<ItemStackImage> {
    private final Supplier<ItemStack> itemSupplier;

    public ItemStackImage(Ic2Gui<?> ic2Gui, int n, int n2, Supplier<ItemStack> supplier) {
        super(ic2Gui, n, n2, 16, 16);
        this.itemSupplier = supplier;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        super.drawBackground(poseStack, n, n2);
        ItemStack itemStack = (ItemStack)this.itemSupplier.get();
        if (!StackUtil.isEmpty(itemStack)) {
            this.gui.drawItemStack(this.x, this.y, itemStack);
        }
    }

    @Override
    public void drawForeground(PoseStack poseStack, int n, int n2) {
        ItemStack itemStack;
        if (this.contains(n, n2) && !StackUtil.isEmpty(itemStack = (ItemStack)this.itemSupplier.get())) {
            this.gui.drawTooltip(poseStack, n, n2, itemStack);
        }
    }
}

