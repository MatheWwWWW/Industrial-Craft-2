/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nonnull
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.ItemHandlerHelper
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.base.IHasInventory;
import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class IHasItemHandler
implements IItemHandler {
    IHasInventory inv;

    public IHasItemHandler(IHasInventory inv) {
        this.inv = inv;
    }

    public int getSlots() {
        return this.inv.getSlotCount();
    }

    public ItemStack getStackInSlot(int slot) {
        return this.inv.getStackInSlot(slot);
    }

    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (stack.m_41619_()) {
            return ItemStack.f_41583_;
        }
        if (!this.isItemValid(slot, stack)) {
            return stack;
        }
        ItemStack invStack = this.inv.getStackInSlot(slot);
        if (invStack.m_41619_()) {
            int max = Math.min(stack.m_41741_(), this.inv.getMaxStackSize(slot));
            if (stack.m_41613_() <= max) {
                if (!simulate) {
                    this.inv.setStackInSlot(slot, stack.m_41777_());
                }
                return ItemStack.f_41583_;
            }
            if (!simulate) {
                this.inv.setStackInSlot(slot, stack.m_41620_(max));
            } else {
                stack.m_41774_(max);
            }
            return stack;
        }
        if (!ItemHandlerHelper.canItemStacksStack((ItemStack)stack, (ItemStack)invStack)) {
            return stack;
        }
        int toAdd = Math.min(stack.m_41741_(), this.inv.getMaxStackSize(slot)) - invStack.m_41613_();
        if (toAdd <= 0) {
            return stack;
        }
        if (toAdd > stack.m_41613_()) {
            if (!simulate) {
                ItemStack copy = invStack.m_41777_();
                copy.m_41769_(stack.m_41613_());
                this.inv.setStackInSlot(slot, copy);
            }
            return ItemStack.f_41583_;
        }
        ItemStack copy = stack.m_41777_();
        copy.m_41774_(toAdd);
        if (simulate) {
            return copy;
        }
        ItemStack realCopy = invStack.m_41777_();
        realCopy.m_41769_(toAdd);
        this.inv.setStackInSlot(slot, realCopy);
        return copy;
    }

    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack split;
        if (amount == 0) {
            return ItemStack.f_41583_;
        }
        ItemStack stack = this.inv.getStackInSlot(slot);
        if (stack.m_41619_()) {
            return ItemStack.f_41583_;
        }
        if (simulate) {
            split = stack.m_41777_();
            split.m_41764_(Math.min(stack.m_41613_(), amount));
        } else {
            split = stack.m_41620_(amount);
            this.inv.setStackInSlot(slot, stack);
        }
        return split;
    }

    public int getSlotLimit(int slot) {
        return this.inv.getMaxStackSize(slot);
    }

    public boolean isItemValid(int slot, ItemStack stack) {
        return this.inv.canInsert(slot, stack);
    }
}

