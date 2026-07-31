/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter.transporters;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.transporters.BaseTransporter;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class IHasInventoryTransporter
extends BaseTransporter {
    IHasInventory inventory;

    public IHasInventoryTransporter(IHasInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        int i;
        if (stack.m_41619_()) {
            return 0;
        }
        int invSize = this.getInventorySize(dir);
        IntArrayList emptySlots = new IntArrayList(invSize);
        int stackSize = stack.m_41613_();
        int added = 0;
        for (i = 0; i < invSize; ++i) {
            if (!this.inventory.canInsert(i, stack)) continue;
            ItemStack inv = this.inventory.getStackInSlot(i);
            if (inv.m_41619_()) {
                emptySlots.add(i);
                continue;
            }
            if (StackUtil.isStackEqual(inv, stack)) {
                int room = Math.min(inv.m_41741_() - inv.m_41613_(), this.inventory.getMaxStackSize(i));
                if (room <= 0) continue;
                int toAdd = Math.min(room, stackSize - added);
                if (!simulate) {
                    inv.m_41769_(toAdd);
                    this.inventory.setStackInSlot(i, inv);
                }
                added += toAdd;
            }
            if (added < stackSize) continue;
            return added;
        }
        int m = emptySlots.size();
        for (i = 0; i < m; ++i) {
            int toAdd = Math.min(this.inventory.getMaxStackSize(i), stackSize - added);
            if (!simulate) {
                this.inventory.setStackInSlot(emptySlots.getInt(i), this.copyWithSize(stack, toAdd));
            }
            if ((added += toAdd) < stackSize) continue;
            return added;
        }
        return added;
    }

    @Override
    public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
        if (amount <= 0) {
            return ItemStack.f_41583_;
        }
        ItemStack stack = ItemStack.f_41583_;
        int invSize = this.getInventorySize(dir);
        for (int i = 0; i < invSize; ++i) {
            ItemStack inv = this.inventory.getStackInSlot(i);
            if (inv.m_41619_() || !filter.matches(inv) || !stack.m_41619_() && !StackUtil.isStackEqual(stack, inv)) continue;
            int toRemove = amount - stack.m_41613_();
            if (simulate) {
                if (stack.m_41619_()) {
                    stack = this.copyWithSize(inv, Math.min(toRemove, inv.m_41613_()));
                    continue;
                }
                stack.m_41769_(Math.min(inv.m_41613_(), toRemove));
            } else {
                ItemStack existing = this.inventory.getStackInSlot(i);
                if (stack.m_41619_()) {
                    stack = existing.m_41620_(toRemove);
                    this.inventory.setStackInSlot(i, existing);
                    continue;
                }
                stack.m_41769_(existing.m_41620_(toRemove).m_41613_());
                this.inventory.setStackInSlot(i, existing);
            }
            if (stack.m_41613_() < amount) continue;
            return stack;
        }
        return stack;
    }

    @Override
    public int getInventorySize(Direction dir) {
        return this.inventory.getSlotCount();
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        int slots = this.inventory.getSlotCount();
        if (slots <= 0) {
            return Object2IntMaps.emptyMap();
        }
        Object2IntLinkedOpenCustomHashMap items = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(compareNBT));
        for (int i = 0; i < slots; ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            items.addTo((Object)StackUtil.copyWithSize(stack, 1), stack.m_41613_());
        }
        return items;
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        IItemTransporter.InvResult result = new IItemTransporter.InvResult(compareNBT);
        int slots = this.inventory.getSlotCount();
        if (slots > 0) {
            for (int i = 0; i < slots; ++i) {
                result.add(this.inventory.getStackInSlot(i), this.inventory.getMaxStackSize(i));
            }
        }
        return result;
    }
}

