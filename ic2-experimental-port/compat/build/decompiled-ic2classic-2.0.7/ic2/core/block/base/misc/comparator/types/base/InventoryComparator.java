/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.core.block.base.misc.comparator.BaseComparator;
import ic2.core.inventory.base.IHasInventory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class InventoryComparator
extends BaseComparator {
    IHasInventory inventory;
    boolean slots;

    public InventoryComparator(String id, Component name, IHasInventory inventory, boolean slots) {
        super(id, name);
        this.inventory = inventory;
        this.slots = slots;
    }

    @Override
    protected int createValue() {
        if (this.slots) {
            int full = 0;
            int m = this.inventory.getSlotCount();
            for (int i = 0; i < m; ++i) {
                full += this.inventory.getStackInSlot(i).m_41619_() ? 0 : 1;
            }
            return InventoryComparator.value(full, this.inventory.getSlotCount(), 15);
        }
        int total = 0;
        int count = 0;
        int m = this.inventory.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.m_41619_()) {
                total += this.inventory.getMaxStackSize(i);
                continue;
            }
            total += Math.min(stack.m_41741_(), this.inventory.getMaxStackSize(i));
            count = stack.m_41613_();
        }
        return InventoryComparator.value(count, total, 15);
    }
}

