/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.ItemHandlerHelper
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.transporter.transporters.ItemHandlerTransporter;
import ic2.core.utils.helpers.StackUtil;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class SimulatedIItemHandler
implements IItemHandler {
    IItemHandler handler;
    Direction dir;
    NonNullList<ItemStack> inv;
    int[] baseStackSize;

    public SimulatedIItemHandler(IItemHandler handler, Direction dir) {
        this.handler = handler;
        this.inv = NonNullList.m_122780_((int)handler.getSlots(), (Object)ItemStack.f_41583_);
        this.baseStackSize = new int[this.inv.size()];
        for (int i = 0; i < this.inv.size(); ++i) {
            ItemStack stack = handler.getStackInSlot(i).m_41777_();
            this.baseStackSize[i] = stack.m_41613_();
            this.inv.set(i, (Object)stack);
        }
    }

    public static IItemHandler createSimulation(IItemHandler handler, Direction dir, Object2IntMap<ItemStack> preload) {
        if (handler == null) {
            return null;
        }
        return new SimulatedIItemHandler(handler, dir).preload(preload);
    }

    public IItemHandler preload(Object2IntMap<ItemStack> preload) {
        ItemHandlerTransporter transporter = new ItemHandlerTransporter(this);
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(preload)) {
            ItemStack stack = (ItemStack)entry.getKey();
            if (stack.m_41619_()) continue;
            transporter.addItem(StackUtil.copyWithSize(stack, entry.getIntValue()), this.dir, false);
        }
        return this;
    }

    public int getSlots() {
        return this.inv.size();
    }

    public ItemStack getStackInSlot(int slot) {
        return (ItemStack)this.inv.get(slot);
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        int alreadyAdded;
        if (stack.m_41619_()) {
            return ItemStack.f_41583_;
        }
        if (!this.isItemValid(slot, stack)) {
            return stack;
        }
        int limit = this.getSlotLimit(slot);
        int added = limit - this.handler.insertItem(slot, StackUtil.copyWithSize(stack, limit), true).m_41613_();
        int left = Math.min(added - (alreadyAdded = ((ItemStack)this.inv.get(slot)).m_41613_() - this.baseStackSize[slot]), stack.m_41613_());
        if (left <= 0) {
            return stack;
        }
        return this.insert(slot, StackUtil.copyWithSize(stack, Math.max(left, stack.m_41613_())), false);
    }

    public ItemStack insert(int slot, ItemStack stack, boolean simulate) {
        boolean reachedLimit;
        ItemStack existing = (ItemStack)this.inv.get(slot);
        int limit = this.getSlotLimit(slot);
        if (!existing.m_41619_()) {
            if (!ItemHandlerHelper.canItemStacksStack((ItemStack)stack, (ItemStack)existing)) {
                return stack;
            }
            limit -= existing.m_41613_();
        }
        if (limit <= 0) {
            return stack;
        }
        boolean bl = reachedLimit = stack.m_41613_() > limit;
        if (!simulate) {
            if (existing.m_41619_()) {
                this.inv.set(slot, (Object)(reachedLimit ? ItemHandlerHelper.copyStackWithSize((ItemStack)stack, (int)limit) : stack));
            } else {
                existing.m_41769_(reachedLimit ? limit : stack.m_41613_());
            }
        }
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize((ItemStack)stack, (int)(stack.m_41613_() - limit)) : ItemStack.f_41583_;
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount == 0 || slot < 0 || slot >= this.inv.size()) {
            return ItemStack.f_41583_;
        }
        ItemStack existing = (ItemStack)this.inv.get(slot);
        if (existing.m_41619_()) {
            return ItemStack.f_41583_;
        }
        int toExtract = Math.min(amount, existing.m_41741_());
        if (existing.m_41613_() <= toExtract) {
            if (!simulate) {
                this.inv.set(slot, (Object)ItemStack.f_41583_);
                return existing;
            }
            return existing.m_41777_();
        }
        if (!simulate) {
            this.inv.set(slot, (Object)ItemHandlerHelper.copyStackWithSize((ItemStack)existing, (int)(existing.m_41613_() - toExtract)));
        }
        return ItemHandlerHelper.copyStackWithSize((ItemStack)existing, (int)toExtract);
    }

    protected int getStackLimit(int slot, ItemStack stack) {
        return this.getSlotLimit(slot);
    }

    public int getSlotLimit(int slot) {
        return this.handler.getSlotLimit(slot);
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        return this.handler.isItemValid(slot, stack);
    }
}

