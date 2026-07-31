/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.InventoryCrafting
 *  net.minecraft.item.ItemStack
 */
package ic2.core.util;

import ic2.core.util.StackUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public abstract class InventorySlotCrafting
extends InventoryCrafting {
    protected final int width;
    protected final int height;
    protected final int size;

    public InventorySlotCrafting(int width, int height) {
        super(null, 0, 0);
        this.width = width;
        this.height = height;
        this.size = width * height;
    }

    protected boolean validIndex(int index) {
        return index >= 0 && index < this.size;
    }

    public int func_70302_i_() {
        return this.size;
    }

    public ItemStack func_70463_b(int row, int column) {
        return row >= 0 && row < this.height && column >= 0 && column < this.width ? this.func_70301_a(row + column * this.height) : StackUtil.emptyStack;
    }

    protected abstract ItemStack get(int var1);

    protected abstract void put(int var1, ItemStack var2);

    protected void clear(int index) {
        this.put(index, StackUtil.emptyStack);
    }

    public ItemStack func_70301_a(int index) {
        return !this.validIndex(index) ? StackUtil.emptyStack : this.get(index);
    }

    public ItemStack func_70304_b(int index) {
        if (this.validIndex(index)) {
            ItemStack stack = this.get(index);
            this.clear(index);
            return stack;
        }
        return StackUtil.emptyStack;
    }

    public ItemStack func_70298_a(int index, int count) {
        ItemStack stack;
        if (this.validIndex(index) && !StackUtil.isEmpty(stack = this.get(index))) {
            ItemStack ret;
            if (count >= StackUtil.getSize(stack)) {
                ret = stack;
                this.clear(index);
            } else {
                ret = StackUtil.copyWithSize(stack, count);
                this.put(index, StackUtil.decSize(stack, count));
            }
            return ret;
        }
        return StackUtil.emptyStack;
    }

    public void func_70299_a(int index, ItemStack stack) {
        if (this.validIndex(index)) {
            this.put(index, stack);
        }
    }

    public abstract void func_174888_l();

    public abstract boolean func_191420_l();

    public int func_174922_i() {
        return this.width;
    }

    public int func_174923_h() {
        return this.height;
    }
}

