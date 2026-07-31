/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.base.features;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface IScrollableInventory {
    public boolean canScrollInventory(ItemStack var1);

    public int getScrollIndex(ItemStack var1);

    public void setScrollIndex(ItemStack var1, int var2);

    public int getSlotCount(ItemStack var1);

    public boolean isValidItem(ItemStack var1, ItemStack var2);

    public IndexedStack[] getItemsToSwapWith(ItemStack var1, boolean var2);

    public boolean swapItems(ItemStack var1, int var2, int var3, ItemStack var4);

    public static class IndexedStack {
        int slot;
        ItemStack stack;

        public IndexedStack(int slot, NonNullList<ItemStack> list) {
            this(slot, (ItemStack)list.get(slot));
        }

        public IndexedStack(int slot, ItemStack stack) {
            this.slot = slot;
            this.stack = stack;
        }

        public int getSlot() {
            return this.slot;
        }

        public ItemStack getStack() {
            return this.stack;
        }
    }
}

