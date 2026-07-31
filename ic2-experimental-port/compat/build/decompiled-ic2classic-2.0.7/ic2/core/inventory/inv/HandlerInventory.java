/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.items.IItemHandler
 *  net.minecraftforge.items.IItemHandlerModifiable
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.base.IHasInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class HandlerInventory
implements IHasInventory {
    IItemHandler handler;

    public HandlerInventory(IItemHandler handler) {
        this.handler = handler;
    }

    @Override
    public int getSlotCount() {
        return this.handler.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.handler.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (this.handler instanceof IItemHandlerModifiable) {
            ((IItemHandlerModifiable)this.handler).setStackInSlot(slot, stack);
        }
    }

    @Override
    public int getMaxStackSize(int slot) {
        return this.handler.getSlotLimit(slot);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return false;
    }
}

