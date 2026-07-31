/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ClickType
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.slot;

import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotHologramSlot
extends Slot {
    protected final ItemStack[] stacks;
    protected final int index;
    protected final int stackSizeLimit;
    protected final ChangeCallback changeCallback;

    public SlotHologramSlot(ItemStack[] itemStackArray, int n, int n2, int n3, int n4, ChangeCallback changeCallback) {
        super((Container)new DummyInventory(), 0, n2, n3);
        if (n >= itemStackArray.length) {
            throw new ArrayIndexOutOfBoundsException(n);
        }
        this.stacks = itemStackArray;
        this.index = n;
        this.stackSizeLimit = n4;
        this.changeCallback = changeCallback;
    }

    public boolean m_8010_(Player player) {
        return false;
    }

    public int m_6641_() {
        return this.stackSizeLimit;
    }

    public boolean m_5857_(ItemStack itemStack) {
        return false;
    }

    public ItemStack m_7993_() {
        return StackUtil.wrapEmpty(this.stacks[this.index]);
    }

    public void m_5852_(ItemStack itemStack) {
        this.stacks[this.index] = itemStack;
    }

    public void m_6654_() {
        if (Util.inDev()) {
            System.out.println(StackUtil.toStringSafe(this.stacks));
        }
        if (this.changeCallback != null) {
            this.changeCallback.onChanged(this.index);
        }
    }

    public ItemStack m_6201_(int n) {
        return StackUtil.emptyStack;
    }

    public ItemStack slotClick(int n, ClickType clickType, Player player, AbstractContainerMenu abstractContainerMenu) {
        if (Util.inDev() && player.m_20193_().f_46443_) {
            System.out.printf("button=%d clickType=%s stack=%s%n", n, clickType, abstractContainerMenu.m_142621_());
        }
        if (clickType == ClickType.PICKUP && (n == 0 || n == 1)) {
            ItemStack itemStack = abstractContainerMenu.m_142621_();
            ItemStack itemStack2 = this.stacks[this.index];
            if (!StackUtil.isEmpty(itemStack)) {
                int n2;
                int n3;
                int n4 = StackUtil.getSize(itemStack2);
                if (n4 + (n3 = n == 0 ? StackUtil.getSize(itemStack) : 1) > (n2 = Math.min(itemStack.m_41741_(), this.stackSizeLimit))) {
                    n3 = Math.max(0, n2 - n4);
                }
                if (n4 == 0) {
                    this.stacks[this.index] = StackUtil.copyWithSize(itemStack, n3);
                } else if (StackUtil.checkItemEquality(itemStack, itemStack2)) {
                    if (Util.inDev()) {
                        System.out.println("add " + n3 + " to " + itemStack2 + " -> " + (n4 + n3));
                    }
                    this.stacks[this.index] = StackUtil.incSize(itemStack2, n3);
                } else {
                    this.stacks[this.index] = StackUtil.copyWithSize(itemStack, Math.min(StackUtil.getSize(itemStack), n2));
                }
            } else if (!StackUtil.isEmpty(itemStack2)) {
                int n5;
                this.stacks[this.index] = n == 0 ? StackUtil.emptyStack : ((n5 = StackUtil.getSize(itemStack2) / 2) <= 0 ? StackUtil.emptyStack : StackUtil.setSize(itemStack2, n5));
            }
            this.m_6654_();
        }
        return StackUtil.emptyStack;
    }

    private static final class DummyInventory
    implements Container {
        private DummyInventory() {
        }

        public int m_6643_() {
            return 1;
        }

        public boolean m_7983_() {
            return false;
        }

        public ItemStack m_8020_(int n) {
            return StackUtil.emptyStack;
        }

        public ItemStack m_7407_(int n, int n2) {
            return StackUtil.emptyStack;
        }

        public ItemStack m_8016_(int n) {
            return StackUtil.emptyStack;
        }

        public void m_6836_(int n, ItemStack itemStack) {
        }

        public void m_6596_() {
        }

        public boolean m_6542_(Player player) {
            return true;
        }

        public void m_6211_() {
        }
    }

    public static interface ChangeCallback {
        public void onChanged(int var1);
    }
}

