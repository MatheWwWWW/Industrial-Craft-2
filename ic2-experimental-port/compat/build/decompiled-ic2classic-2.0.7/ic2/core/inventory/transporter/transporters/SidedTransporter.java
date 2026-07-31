/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.core.Direction
 *  net.minecraft.world.WorldlyContainer
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter.transporters;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.transporters.BaseTransporter;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;

public class SidedTransporter
extends BaseTransporter {
    WorldlyContainer inventory;

    public SidedTransporter(WorldlyContainer inventory) {
        this.inventory = inventory;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        int i;
        if (stack.m_41619_()) {
            return 0;
        }
        int[] slots = this.inventory.m_7071_(dir);
        if (slots == null || slots.length <= 0) {
            return 0;
        }
        int invCapacity = this.inventory.m_6893_();
        IntArrayList emptySlots = new IntArrayList(slots.length);
        int stackSize = stack.m_41613_();
        int added = 0;
        for (i = 0; i < slots.length; ++i) {
            int slot = slots[i];
            if (!this.inventory.m_7013_(slot, stack) || !this.inventory.m_7155_(slot, stack, dir)) continue;
            ItemStack inv = this.inventory.m_8020_(slot);
            if (inv.m_41619_()) {
                emptySlots.add(slot);
                continue;
            }
            if (StackUtil.isStackEqual(inv, stack)) {
                int room = Math.min(inv.m_41741_() - inv.m_41613_(), invCapacity);
                if (room <= 0) continue;
                int toAdd = Math.min(room, stackSize - added);
                if (!simulate) {
                    inv.m_41769_(toAdd);
                    this.inventory.m_6836_(slot, inv);
                }
                added += toAdd;
            }
            if (added < stackSize) continue;
            if (!simulate) {
                this.inventory.m_6596_();
            }
            return added;
        }
        int m = emptySlots.size();
        for (i = 0; i < m; ++i) {
            int toAdd = Math.min(invCapacity, stackSize - added);
            if (!simulate) {
                this.inventory.m_6836_(emptySlots.getInt(i), this.copyWithSize(stack, toAdd));
            }
            if ((added += toAdd) < stackSize) continue;
            if (!simulate) {
                this.inventory.m_6596_();
            }
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
        int[] slots = this.inventory.m_7071_(dir);
        if (slots == null || slots.length <= 0) {
            return stack;
        }
        for (int i = 0; i < slots.length; ++i) {
            int slot = slots[i];
            ItemStack inv = this.inventory.m_8020_(slot);
            if (inv.m_41619_() || !this.inventory.m_7157_(slot, stack, dir) || !filter.matches(inv) || !stack.m_41619_() && !StackUtil.isStackEqual(stack, inv)) continue;
            int toRemove = amount - stack.m_41613_();
            if (simulate) {
                if (stack.m_41619_()) {
                    stack = this.copyWithSize(inv, Math.min(toRemove, inv.m_41613_()));
                    continue;
                }
                stack.m_41769_(Math.min(inv.m_41613_(), toRemove));
            } else {
                if (stack.m_41619_()) {
                    stack = this.inventory.m_7407_(slot, toRemove);
                    continue;
                }
                stack.m_41769_(this.inventory.m_7407_(slot, toRemove).m_41613_());
            }
            if (stack.m_41613_() < amount) continue;
            if (!simulate) {
                this.inventory.m_6596_();
            }
            return stack;
        }
        return stack;
    }

    @Override
    public int getInventorySize(Direction dir) {
        return this.inventory.m_7071_(dir).length;
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        int[] slots = this.inventory.m_7071_(dir);
        if (slots == null || slots.length <= 0) {
            return Object2IntMaps.emptyMap();
        }
        Object2IntLinkedOpenCustomHashMap items = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(compareNBT));
        for (int i = 0; i < slots.length; ++i) {
            ItemStack stack = this.inventory.m_8020_(slots[i]);
            if (stack.m_41619_()) continue;
            items.addTo((Object)StackUtil.copyWithSize(stack, 1), stack.m_41613_());
        }
        return items;
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        IItemTransporter.InvResult result = new IItemTransporter.InvResult(compareNBT);
        int[] slots = this.inventory.m_7071_(dir);
        if (slots.length > 0) {
            int m = slots.length;
            for (int i = 0; i < m; ++i) {
                result.add(this.inventory.m_8020_(slots[i]), this.inventory.m_6893_());
            }
        }
        return result;
    }
}

