/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.readers;

import net.minecraft.world.item.ItemStack;

public interface IWrenchTool {
    public double getActualLoss(ItemStack var1, double var2);

    default public boolean shouldRenderOverlay(ItemStack stack) {
        return true;
    }
}

