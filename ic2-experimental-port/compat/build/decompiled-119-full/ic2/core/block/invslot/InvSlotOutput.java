/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.world.item.ItemStack;

public class InvSlotOutput
extends InvSlot {
    public InvSlotOutput(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n) {
        this(iInventorySlotHolder, string, n, InvSlot.InvSide.BOTTOM);
    }

    public InvSlotOutput(IInventorySlotHolder<?> iInventorySlotHolder, String string, int n, InvSlot.InvSide invSide) {
        super(iInventorySlotHolder, string, InvSlot.Access.O, n, invSide);
    }

    @Override
    public boolean accepts(ItemStack itemStack) {
        return false;
    }

    public int add(Collection<ItemStack> collection) {
        return this.add(collection, false);
    }

    public int add(ItemStack itemStack) {
        if (itemStack == null) {
            throw new NullPointerException("null ItemStack");
        }
        return this.add(Collections.singletonList(itemStack), false);
    }

    public boolean canAdd(Collection<ItemStack> collection) {
        return this.add(collection, true) == 0;
    }

    public boolean canAdd(ItemStack itemStack) {
        if (itemStack == null) {
            throw new NullPointerException("null ItemStack");
        }
        return this.add(Collections.singletonList(itemStack), true) == 0;
    }

    private int add(Collection<ItemStack> collection, boolean bl) {
        if (collection == null || collection.isEmpty()) {
            return 0;
        }
        ItemStack[] itemStackArray = bl ? this.backup() : null;
        int n = 0;
        for (ItemStack itemStack : collection) {
            int n2 = StackUtil.getSize(itemStack);
            if (n2 <= 0) continue;
            block1: for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < this.size(); ++j) {
                    ItemStack itemStack2 = this.get(j);
                    int n3 = this.getStackSizeLimit();
                    if (!StackUtil.isEmpty(itemStack2)) {
                        n3 = Math.min(n3, itemStack2.m_41741_()) - StackUtil.getSize(itemStack2);
                    }
                    if (n3 <= 0) continue;
                    if (i == 0 && !StackUtil.isEmpty(itemStack2) && StackUtil.checkItemEqualityStrict(itemStack, itemStack2)) {
                        if (n3 >= n2) {
                            this.put(j, StackUtil.incSize(itemStack2, n2));
                            n2 = 0;
                            break block1;
                        }
                        this.put(j, StackUtil.incSize(itemStack2, n3));
                        n2 -= n3;
                        continue;
                    }
                    if (i != 1 || !StackUtil.isEmpty(itemStack2)) continue;
                    if (n3 >= n2) {
                        this.put(j, StackUtil.copyWithSize(itemStack, n2));
                        n2 = 0;
                        break block1;
                    }
                    this.put(j, StackUtil.copyWithSize(itemStack, n3));
                    n2 -= n3;
                }
            }
            n += n2;
        }
        if (bl) {
            this.restore(itemStackArray);
        }
        return n;
    }
}

