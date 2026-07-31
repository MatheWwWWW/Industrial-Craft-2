/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.base.IHasInventory;
import net.minecraft.world.item.ItemStack;

public class InventoryWrapper
implements IHasInventory {
    IHasInventory wrapper;

    public InventoryWrapper(IHasInventory wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public int getSlotCount() {
        return this.wrapper.getSlotCount();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.wrapper.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.wrapper.setStackInSlot(slot, stack);
    }

    @Override
    public int getMaxStackSize(int slot) {
        return this.wrapper.getMaxStackSize(slot);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return this.wrapper.canInsert(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return this.wrapper.canExtract(slot, stack);
    }
}

