/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package ic2.core.slot;

import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHologramSlot
extends Slot {
    protected final ItemStack[] stacks;
    protected final int index;
    protected final int stackSizeLimit;
    protected final ChangeCallback changeCallback;

    public SlotHologramSlot(ItemStack[] stacks, int index, int x, int y, int stackSizeLimit, ChangeCallback changeCallback) {
        super(null, 0, x, y);
        if (index >= stacks.length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        this.stacks = stacks;
        this.index = index;
        this.stackSizeLimit = stackSizeLimit;
        this.changeCallback = changeCallback;
    }

    public boolean func_82869_a(EntityPlayer player) {
        return false;
    }

    public int func_75219_a() {
        return this.stackSizeLimit;
    }

    public boolean func_75214_a(ItemStack stack) {
        return false;
    }

    public ItemStack func_75211_c() {
        return StackUtil.wrapEmpty(this.stacks[this.index]);
    }

    public void func_75215_d(ItemStack stack) {
        this.stacks[this.index] = stack;
    }

    public void func_75218_e() {
        if (Util.inDev()) {
            System.out.println(StackUtil.toStringSafe(this.stacks));
        }
        if (this.changeCallback != null) {
            this.changeCallback.onChanged(this.index);
        }
    }

    public ItemStack func_75209_a(int amount) {
        return StackUtil.emptyStack;
    }

    public boolean func_75217_a(IInventory inventory, int index) {
        return false;
    }

    public ItemStack slotClick(int dragType, ClickType clickType, EntityPlayer player) {
        if (Util.inDev() && player.func_130014_f_().field_72995_K) {
            System.out.printf("dragType=%d clickType=%s stack=%s%n", dragType, clickType, player.field_71071_by.func_70445_o());
        }
        if (clickType == ClickType.PICKUP && (dragType == 0 || dragType == 1)) {
            ItemStack playerStack = player.field_71071_by.func_70445_o();
            ItemStack slotStack = this.stacks[this.index];
            if (!StackUtil.isEmpty(playerStack)) {
                int limit;
                int extraSize;
                int curSize = StackUtil.getSize(slotStack);
                if (curSize + (extraSize = dragType == 0 ? StackUtil.getSize(playerStack) : 1) > (limit = Math.min(playerStack.func_77976_d(), this.stackSizeLimit))) {
                    extraSize = Math.max(0, limit - curSize);
                }
                if (curSize == 0) {
                    this.stacks[this.index] = StackUtil.copyWithSize(playerStack, extraSize);
                } else if (StackUtil.checkItemEquality(playerStack, slotStack)) {
                    if (Util.inDev()) {
                        System.out.println("add " + extraSize + " to " + slotStack + " -> " + (curSize + extraSize));
                    }
                    this.stacks[this.index] = StackUtil.incSize(slotStack, extraSize);
                } else {
                    this.stacks[this.index] = StackUtil.copyWithSize(playerStack, Math.min(StackUtil.getSize(playerStack), limit));
                }
            } else if (!StackUtil.isEmpty(slotStack)) {
                int newSize;
                this.stacks[this.index] = dragType == 0 ? StackUtil.emptyStack : ((newSize = StackUtil.getSize(slotStack) / 2) <= 0 ? StackUtil.emptyStack : StackUtil.setSize(slotStack, newSize));
            }
            this.func_75218_e();
        }
        return StackUtil.emptyStack;
    }

    public static interface ChangeCallback {
        public void onChanged(int var1);
    }
}

