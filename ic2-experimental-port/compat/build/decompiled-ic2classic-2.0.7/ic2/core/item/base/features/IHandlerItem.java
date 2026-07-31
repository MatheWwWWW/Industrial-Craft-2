/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.base.features;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface IHandlerItem {
    public void handleInventory(ItemStack var1, IConfigurableInventory var2);

    public static interface IConfigurableInventory {
        public void setDefaultMaxStackSize(int var1);

        public void setMaxStackSizeForItem(Item var1, int var2);
    }
}

