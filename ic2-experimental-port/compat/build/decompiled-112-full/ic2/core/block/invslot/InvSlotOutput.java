/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.item.ItemStack;

public class InvSlotOutput
extends InvSlot {
    public InvSlotOutput(IInventorySlotHolder<?> base1, String name1, int count) {
        this(base1, name1, count, InvSlot.InvSide.BOTTOM);
    }

    public InvSlotOutput(IInventorySlotHolder<?> base1, String name1, int count, InvSlot.InvSide side) {
        super(base1, name1, InvSlot.Access.O, count, side);
    }

    @Override
    public boolean accepts(ItemStack stack) {
        return false;
    }

    public int add(Collection<ItemStack> stacks) {
        return this.add(stacks, false);
    }

    public int add(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("null ItemStack");
        }
        return this.add(Collections.singletonList(stack), false);
    }

    public boolean canAdd(Collection<ItemStack> stacks) {
        return this.add(stacks, true) == 0;
    }

    public boolean canAdd(ItemStack stack) {
        if (stack == null) {
            throw new NullPointerException("null ItemStack");
        }
        return this.add(Collections.singletonList(stack), true) == 0;
    }

    private int add(Collection<ItemStack> stacks, boolean simulate) {
        if (stacks == null || stacks.isEmpty()) {
            return 0;
        }
        ItemStack[] backup = simulate ? this.backup() : null;
        int totalAmount = 0;
        for (ItemStack stack : stacks) {
            int amount = StackUtil.getSize(stack);
            if (amount <= 0) continue;
            block1: for (int pass = 0; pass < 2; ++pass) {
                for (int i = 0; i < this.size(); ++i) {
                    ItemStack existingStack = this.get(i);
                    int space = this.getStackSizeLimit();
                    if (!StackUtil.isEmpty(existingStack)) {
                        space = Math.min(space, existingStack.func_77976_d()) - StackUtil.getSize(existingStack);
                    }
                    if (space <= 0) continue;
                    if (pass == 0 && !StackUtil.isEmpty(existingStack) && StackUtil.checkItemEqualityStrict(stack, existingStack)) {
                        if (space >= amount) {
                            this.put(i, StackUtil.incSize(existingStack, amount));
                            amount = 0;
                            break block1;
                        }
                        this.put(i, StackUtil.incSize(existingStack, space));
                        amount -= space;
                        continue;
                    }
                    if (pass != 1 || !StackUtil.isEmpty(existingStack)) continue;
                    if (space >= amount) {
                        this.put(i, StackUtil.copyWithSize(stack, amount));
                        amount = 0;
                        break block1;
                    }
                    this.put(i, StackUtil.copyWithSize(stack, space));
                    amount -= space;
                }
            }
            totalAmount += amount;
        }
        if (simulate) {
            this.restore(backup);
        }
        return totalAmount;
    }
}

