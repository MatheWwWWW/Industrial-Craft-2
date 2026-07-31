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
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.common.capabilities.ICapabilityProvider
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.wrapper.EmptyHandler
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
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

public class CapabilityTransporter
extends BaseTransporter {
    ICapabilityProvider prov;

    public CapabilityTransporter(ICapabilityProvider prov) {
        this.prov = prov;
    }

    @Override
    public int addItem(ItemStack stack, Direction dir, boolean simulate) {
        int i;
        if (stack.m_41619_()) {
            return 0;
        }
        IItemHandler handler = (IItemHandler)this.prov.getCapability(ForgeCapabilities.ITEM_HANDLER, dir).orElse((Object)EmptyHandler.INSTANCE);
        int size = handler.getSlots();
        if (size <= 0) {
            return 0;
        }
        int stackSize = stack.m_41613_();
        IntArrayList emptySlots = new IntArrayList(size);
        int added = 0;
        for (i = 0; i < size; ++i) {
            int adding;
            ItemStack inv = handler.getStackInSlot(i);
            if (inv.m_41619_()) {
                emptySlots.add(i);
                continue;
            }
            if (!StackUtil.isStackEqual(inv, stack) || (added += (adding = stackSize - added) - handler.insertItem(i, StackUtil.copyWithSize(stack, adding), simulate).m_41613_()) < stackSize) continue;
            return added;
        }
        size = emptySlots.size();
        for (i = 0; i < size; ++i) {
            int adding;
            if ((added += (adding = stackSize - added) - handler.insertItem(emptySlots.getInt(i), StackUtil.copyWithSize(stack, adding), simulate).m_41613_()) < stackSize) continue;
            return added;
        }
        return added;
    }

    @Override
    public ItemStack removeItem(IFilter filter, Direction dir, int amount, boolean simulate) {
        if (amount <= 0) {
            return ItemStack.f_41583_;
        }
        IItemHandler handler = (IItemHandler)this.prov.getCapability(ForgeCapabilities.ITEM_HANDLER, dir).orElse((Object)EmptyHandler.INSTANCE);
        ItemStack stack = ItemStack.f_41583_;
        int size = handler.getSlots();
        if (size <= 0) {
            return stack;
        }
        for (int i = 0; i < size; ++i) {
            ItemStack inv = handler.getStackInSlot(i);
            if (inv.m_41619_() || !filter.matches(inv) || !stack.m_41619_() && !StackUtil.isStackEqual(stack, inv)) continue;
            if (stack.m_41619_()) {
                stack = handler.extractItem(i, amount - stack.m_41613_(), simulate);
            } else {
                stack.m_41769_(handler.extractItem(i, amount - stack.m_41613_(), simulate).m_41613_());
            }
            if (stack.m_41613_() < amount) continue;
            return stack;
        }
        return stack;
    }

    @Override
    public int getInventorySize(Direction dir) {
        return ((IItemHandler)this.prov.getCapability(ForgeCapabilities.ITEM_HANDLER, dir).orElse((Object)EmptyHandler.INSTANCE)).getSlots();
    }

    @Override
    public Object2IntMap<ItemStack> getAllItems(Direction dir, boolean compareNBT) {
        IItemHandler handler = (IItemHandler)this.prov.getCapability(ForgeCapabilities.ITEM_HANDLER, dir).orElse((Object)EmptyHandler.INSTANCE);
        int slots = handler.getSlots();
        if (slots <= 0) {
            return Object2IntMaps.emptyMap();
        }
        Object2IntLinkedOpenCustomHashMap items = new Object2IntLinkedOpenCustomHashMap(ItemStackStrategy.getStrategy(compareNBT));
        for (int i = 0; i < slots; ++i) {
            ItemStack stack = handler.getStackInSlot(i);
            if (stack.m_41619_()) continue;
            items.addTo((Object)StackUtil.copyWithSize(stack, 1), stack.m_41613_());
        }
        return items;
    }

    @Override
    public IItemTransporter.InvResult getInventory(Direction dir, boolean compareNBT) {
        IItemTransporter.InvResult result = new IItemTransporter.InvResult(compareNBT);
        IItemHandler handler = (IItemHandler)this.prov.getCapability(ForgeCapabilities.ITEM_HANDLER, dir).orElse((Object)EmptyHandler.INSTANCE);
        int slots = handler.getSlots();
        if (slots > 0) {
            for (int i = 0; i < slots; ++i) {
                result.add(handler.getStackInSlot(i), handler.getSlotLimit(i));
            }
        }
        return result;
    }
}

