/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.slot;

import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotInvSlot
extends Slot {
    public final InvSlot invSlot;
    public final int index;

    public SlotInvSlot(InvSlot invSlot, int n, int n2, int n3) {
        super((Container)invSlot.base.getParent(), invSlot.base.getBaseIndex(invSlot) + n, n2, n3);
        this.invSlot = invSlot;
        this.index = n;
    }

    public boolean m_5857_(ItemStack itemStack) {
        return this.invSlot.accepts(itemStack);
    }

    public ItemStack m_7993_() {
        return this.invSlot.get(this.index);
    }

    public void m_5852_(ItemStack itemStack) {
        this.invSlot.put(this.index, itemStack);
        this.m_6654_();
    }

    public ItemStack m_6201_(int n) {
        ItemStack itemStack;
        if (n <= 0) {
            return StackUtil.emptyStack;
        }
        ItemStack itemStack2 = this.invSlot.get(this.index);
        if (StackUtil.isEmpty(itemStack2)) {
            return StackUtil.emptyStack;
        }
        n = Math.min(n, StackUtil.getSize(itemStack2));
        if (StackUtil.getSize(itemStack2) == n) {
            itemStack = itemStack2;
            this.invSlot.clear(this.index);
        } else {
            itemStack = StackUtil.copyWithSize(itemStack2, n);
            this.invSlot.put(this.index, StackUtil.decSize(itemStack2, n));
        }
        this.m_6654_();
        return itemStack;
    }

    public int m_6641_() {
        return this.invSlot.getStackSizeLimit();
    }

    public void m_142406_(Player player, ItemStack itemStack) {
        super.m_142406_(player, itemStack);
        this.invSlot.onPickupFromSlot(player, itemStack);
    }
}

