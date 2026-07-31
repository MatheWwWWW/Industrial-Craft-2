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
 *  net.minecraftforge.items.IItemHandler
 */
package ic2.core.inventory.transporter.transporters.special;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class OnlyExistingTransporter
implements IItemTransporter {
    IItemHandler handler;

    public OnlyExistingTransporter(IItemHandler handler) {
        this.handler = handler;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        int i;
        if (stack.m_41619_()) {
            return 0;
        }
        int size = this.handler.getSlots();
        if (size <= 0) {
            return 0;
        }
        int stackSize = stack.m_41613_();
        IntArrayList emptySlots = new IntArrayList(size);
        boolean found = false;
        int added = 0;
        for (i = 0; i < size; ++i) {
            ItemStack inv = this.handler.getStackInSlot(i);
            if (inv.m_41619_()) {
                emptySlots.add(i);
                continue;
            }
            if (StackUtil.isStackEqual(inv, stack)) {
                int adding;
                found = true;
                if ((added += (adding = stackSize - added) - this.handler.insertItem(i, StackUtil.copyWithSize(stack, adding), simulate).m_41613_()) < stackSize) continue;
                return added;
            }
            if (found || inv.m_41720_() != stack.m_41720_()) continue;
            found = true;
        }
        if (!found) {
            return added;
        }
        size = emptySlots.size();
        for (i = 0; i < size; ++i) {
            int adding;
            if ((added += (adding = stackSize - added) - this.handler.insertItem(emptySlots.getInt(i), StackUtil.copyWithSize(stack, adding), simulate).m_41613_()) < stackSize) continue;
            return added;
        }
        return added;
    }

    @Override
    public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
        return ItemStack.f_41583_;
    }

    @Override
    public int getInventorySize(Direction dir) {
        return this.handler.getSlots();
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        int slots = this.handler.getSlots();
        if (slots <= 0) {
            return Object2IntMaps.emptyMap();
        }
        Object2IntLinkedOpenCustomHashMap items = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(compareNBT));
        for (int i = 0; i < slots; ++i) {
            ItemStack stack = this.handler.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            items.addTo((Object)StackUtil.copyWithSize(stack, 1), stack.m_41613_());
        }
        return items;
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        IItemTransporter.InvResult result = new IItemTransporter.InvResult(compareNBT);
        int slots = this.handler.getSlots();
        if (slots > 0) {
            for (int i = 0; i < slots; ++i) {
                ItemStack stack = this.handler.getStackInSlot(i);
                if (stack.m_41619_()) continue;
                result.add(stack, this.handler.getSlotLimit(i));
            }
        }
        return result;
    }
}

