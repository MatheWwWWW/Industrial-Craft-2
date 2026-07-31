/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.StackedContents
 *  net.minecraft.world.inventory.CraftingContainer
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block;

import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

public abstract class SimpleCraftingInventory
extends CraftingContainer {
    private final int size;

    public SimpleCraftingInventory(int n, int n2) {
        super(null, n, n2);
        this.size = n * n2;
    }

    public ItemStack m_8020_(int n) {
        if (n >= this.size) {
            return StackUtil.emptyStack;
        }
        return StackUtil.wrapEmpty(this.get(n));
    }

    public void m_6836_(int n, ItemStack itemStack) {
        this.set(n, itemStack);
    }

    public ItemStack m_8016_(int n) {
        ItemStack itemStack;
        if (n >= this.size || StackUtil.isEmpty(itemStack = this.get(n))) {
            return StackUtil.emptyStack;
        }
        this.set(n, StackUtil.emptyStack);
        return itemStack;
    }

    public ItemStack m_7407_(int n, int n2) {
        ItemStack itemStack;
        if (n >= this.size || n2 <= 0 || StackUtil.isEmpty(itemStack = this.get(n))) {
            return StackUtil.emptyStack;
        }
        return itemStack.m_41620_(n2);
    }

    public boolean m_7983_() {
        for (int i = 0; i < this.size; ++i) {
            if (StackUtil.isEmpty(this.get(i))) continue;
            return false;
        }
        return true;
    }

    public void m_6211_() {
        for (int i = 0; i < this.size; ++i) {
            this.set(i, StackUtil.emptyStack);
        }
    }

    public void m_5809_(StackedContents stackedContents) {
        for (int i = 0; i < this.size; ++i) {
            ItemStack itemStack = this.get(i);
            if (StackUtil.isEmpty(itemStack)) continue;
            stackedContents.m_36466_(itemStack);
        }
    }

    protected abstract ItemStack get(int var1);

    protected abstract void set(int var1, ItemStack var2);

    public static class InvSlotCraftingInventory
    extends SimpleCraftingInventory {
        private final InvSlot invSlot;

        public InvSlotCraftingInventory(InvSlot invSlot, int n) {
            super(n, (invSlot.size() + n - 1) / n);
            this.invSlot = invSlot;
        }

        @Override
        protected ItemStack get(int n) {
            return this.invSlot.get(n);
        }

        @Override
        protected void set(int n, ItemStack itemStack) {
            this.invSlot.put(n, itemStack);
        }
    }

    public static class ArrayCraftingInventory
    extends SimpleCraftingInventory {
        private final ItemStack[] items;

        public ArrayCraftingInventory(ItemStack[] itemStackArray, int n) {
            super(n, (itemStackArray.length + n - 1) / n);
            this.items = itemStackArray;
        }

        @Override
        protected ItemStack get(int n) {
            return this.items[n];
        }

        @Override
        protected void set(int n, ItemStack itemStack) {
            this.items[n] = itemStack;
        }
    }
}

