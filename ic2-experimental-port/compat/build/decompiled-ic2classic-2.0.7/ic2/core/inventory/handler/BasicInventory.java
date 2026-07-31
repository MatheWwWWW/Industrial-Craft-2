/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.ItemHandlerHelper
 */
package ic2.core.inventory.handler;

import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.handler.IModularSlot;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.filter.ISlotFilter;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class BasicInventory
implements IItemHandler {
    InventoryHandler handler;
    Direction dir;
    IModularSlot slots;

    public BasicInventory(InventoryHandler handler, Direction dir, IModularSlot slots) {
        this.handler = handler;
        this.dir = dir;
        this.slots = slots;
    }

    public int getSlots() {
        return this.slots.getSlotCount();
    }

    public ItemStack getStackInSlot(int slot) {
        return this.handler.getInventory().getStackInSlot(this.slots.getRealSlot(slot));
    }

    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        ItemStack invStack;
        if (stack.m_41619_()) {
            return ItemStack.f_41583_;
        }
        int realSlot = this.slots.getRealSlot(slot);
        if (!this.handler.getSlotAccess(realSlot, this.dir).canImport()) {
            return stack;
        }
        ISlotFilter filter = this.handler.getInputFilter(realSlot);
        if (filter != null && !filter.matches(realSlot, stack)) {
            return stack;
        }
        IHasInventory inv = this.handler.getInventory();
        int maxStack = Integer.MAX_VALUE;
        if (this.handler.isNoDuplicateMode()) {
            if (!this.canInsert(realSlot, stack)) {
                return stack;
            }
            maxStack = this.getRoom(stack);
            if (maxStack <= 0) {
                return stack;
            }
        }
        if ((invStack = inv.getStackInSlot(realSlot)).m_41619_()) {
            int max = Math.min(Math.min(stack.m_41741_(), inv.getMaxStackSize(realSlot)), maxStack);
            if (stack.m_41613_() <= max) {
                ItemStack copy = stack.m_41777_();
                if (!simulate) {
                    inv.setStackInSlot(realSlot, copy);
                }
                return ItemStack.f_41583_;
            }
            if (!simulate) {
                inv.setStackInSlot(realSlot, stack.m_41620_(max));
            } else {
                stack.m_41774_(max);
            }
            return stack;
        }
        if (!ItemHandlerHelper.canItemStacksStack((ItemStack)stack, (ItemStack)invStack)) {
            return stack;
        }
        int toAdd = Math.min(Math.min(stack.m_41741_(), inv.getMaxStackSize(realSlot)) - invStack.m_41613_(), maxStack);
        if (toAdd <= 0) {
            return stack;
        }
        if (toAdd > stack.m_41613_()) {
            if (!simulate) {
                ItemStack copy = invStack.m_41777_();
                copy.m_41769_(stack.m_41613_());
                inv.setStackInSlot(realSlot, copy);
            }
            return ItemStack.f_41583_;
        }
        ItemStack copy = stack.m_41777_();
        if (simulate) {
            copy.m_41774_(toAdd);
            return copy;
        }
        copy.m_41774_(toAdd);
        ItemStack realCopy = invStack.m_41777_();
        realCopy.m_41769_(toAdd);
        inv.setStackInSlot(realSlot, realCopy);
        return copy;
    }

    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack split;
        if (amount <= 0) {
            return ItemStack.f_41583_;
        }
        int realSlot = this.slots.getRealSlot(slot);
        if (!this.handler.getSlotAccess(realSlot, this.dir).canExport()) {
            return ItemStack.f_41583_;
        }
        IHasInventory inv = this.handler.getInventory();
        ItemStack stack = inv.getStackInSlot(realSlot);
        if (stack.m_41619_()) {
            return ItemStack.f_41583_;
        }
        ISlotFilter exportFilter = this.handler.getOutputFilter(realSlot);
        if (exportFilter != null && !exportFilter.matches(realSlot, stack)) {
            return ItemStack.f_41583_;
        }
        if (simulate) {
            split = stack.m_41777_();
            split.m_41764_(Math.min(stack.m_41613_(), amount));
        } else {
            split = stack.m_41620_(amount);
            inv.setStackInSlot(realSlot, stack);
        }
        return split;
    }

    public int getSlotLimit(int slot) {
        return this.handler.getInventory().getMaxStackSize(this.slots.getRealSlot(slot));
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        int realSlot = this.slots.getRealSlot(slot);
        ISlotFilter filter = this.handler.getInputFilter(realSlot);
        return filter == null || filter.matches(realSlot, stack);
    }

    protected boolean canInsert(int mySlot, ItemStack item) {
        IHasInventory inv = this.handler.getInventory();
        ItemStack myStack = inv.getStackInSlot(mySlot);
        if (!myStack.m_41619_() && !ItemHandlerHelper.canItemStacksStack((ItemStack)myStack, (ItemStack)item)) {
            return false;
        }
        int count = 0;
        boolean allowStack = this.handler.isMultiStack();
        Item toInsert = item.m_41720_();
        int m = this.slots.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack;
            int slot = this.slots.getRealSlot(i);
            if (slot == mySlot && !allowStack || (stack = inv.getStackInSlot(slot)).m_41720_() != toInsert) continue;
            count += stack.m_41613_();
            if (allowStack) continue;
            return false;
        }
        return !allowStack || count < this.getLimit(item);
    }

    protected int getRoom(ItemStack toInsert) {
        int count = 0;
        IHasInventory inv = this.handler.getInventory();
        int m = this.slots.getSlotCount();
        for (int i = 0; i < m; ++i) {
            ItemStack stack = inv.getStackInSlot(this.slots.getRealSlot(i));
            if (!StackUtil.isStackEqual(stack, toInsert)) continue;
            count += stack.m_41613_();
        }
        return this.getLimit(toInsert) - count;
    }

    private int getLimit(ItemStack stack) {
        int value = this.handler.getMaxStackSize(stack.m_41720_());
        return value == -1 ? stack.m_41741_() : value;
    }
}

