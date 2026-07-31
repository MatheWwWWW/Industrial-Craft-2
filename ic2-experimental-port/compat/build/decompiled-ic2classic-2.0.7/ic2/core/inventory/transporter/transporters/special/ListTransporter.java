/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.transporter.transporters.special;

import ic2.core.block.machines.recipes.ItemStackStrategy;
import ic2.core.inventory.filter.IFilter;
import ic2.core.inventory.filter.StackFilter;
import ic2.core.inventory.transporter.IItemTransporter;
import ic2.core.inventory.transporter.transporters.BaseTransporter;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;

public class ListTransporter
extends BaseTransporter {
    List<IItemTransporter> transporters;

    public ListTransporter(IItemTransporter ... transporters) {
        this.transporters = ObjectArrayList.wrap((Object[])transporters);
    }

    public ListTransporter(List<IItemTransporter> transporters) {
        this.transporters = transporters;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        if (stack.m_41619_()) {
            return 0;
        }
        int total = 0;
        int m = this.transporters.size();
        for (int i = 0; i < m; ++i) {
            if ((total += this.transporters.get(i).addItem(StackUtil.copyWithSize(stack, stack.m_41613_() - total), dir, simulate)) < stack.m_41613_()) continue;
            return total;
        }
        return total;
    }

    @Override
    public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
        if (amount <= 0) {
            return ItemStack.f_41583_;
        }
        ItemStack stack = ItemStack.f_41583_;
        int m = this.transporters.size();
        for (int i = 0; i < m; ++i) {
            if (stack.m_41619_()) {
                stack = this.transporters.get(i).removeItem(filter, dir, amount, simulate);
                if (stack.m_41619_()) continue;
                filter = StackFilter.defaultCompare(stack);
                continue;
            }
            if (stack.m_41613_() >= amount) break;
            stack.m_41769_(this.transporters.get(i).removeItem(filter, dir, amount - stack.m_41613_(), simulate).m_41613_());
        }
        return stack;
    }

    @Override
    public int getInventorySize(Direction dir) {
        int total = 0;
        int m = this.transporters.size();
        for (int i = 0; i < m; ++i) {
            total += this.transporters.get(i).getInventorySize(dir);
        }
        return total;
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        Object2IntLinkedOpenCustomHashMap items = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(compareNBT));
        int m = this.transporters.size();
        for (int i = 0; i < m; ++i) {
            for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.transporters.get(i).getAllItems(dir, compareNBT))) {
                items.addTo((Object)((ItemStack)entry.getKey()), entry.getIntValue());
            }
        }
        return items;
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        IItemTransporter.InvResult result = new IItemTransporter.InvResult(compareNBT);
        int m = this.transporters.size();
        for (int i = 0; i < m; ++i) {
            result.add(this.transporters.get(i).getInventory(dir, compareNBT));
        }
        return result;
    }
}

