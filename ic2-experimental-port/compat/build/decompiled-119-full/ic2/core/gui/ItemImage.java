/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.Ic2Gui;
import ic2.core.gui.GuiElement;
import ic2.core.util.StackUtil;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;

public class ItemImage
extends GuiElement<ItemImage> {
    private final Supplier<ItemStack> itemSupplier;

    public ItemImage(Ic2Gui<?> ic2Gui, int n, int n2, Supplier<ItemStack> supplier) {
        super(ic2Gui, n, n2, 16, 16);
        this.itemSupplier = supplier;
    }

    @Override
    public void drawBackground(PoseStack poseStack, int n, int n2) {
        super.drawBackground(poseStack, n, n2);
        ItemStack itemStack = this.itemSupplier.get();
        if (!StackUtil.isEmpty(itemStack)) {
            this.gui.drawItem(this.x, this.y, itemStack);
        }
    }
}

