/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.base.IHasInventory;
import net.minecraft.world.item.ItemStack;

public class RangedInventory
implements IHasInventory {
    IHasInventory inventory;
    int[] slots;
    boolean outputOnly = false;

    public RangedInventory(IHasInventory inventory, int ... slots) {
        this.inventory = inventory;
        this.slots = slots;
    }

    public RangedInventory setOutputOnly() {
        this.outputOnly = true;
        return this;
    }

    @Override
    public int getSlotCount() {
        return this.slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory.getStackInSlot(this.slots[slot]);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.inventory.setStackInSlot(this.slots[slot], stack);
    }

    @Override
    public int getMaxStackSize(int slot) {
        return this.inventory.getMaxStackSize(this.slots[slot]);
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack) {
        return !this.outputOnly && this.inventory.canInsert(this.slots[slot], stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack) {
        return this.outputOnly || this.inventory.canExtract(this.slots[slot], stack);
    }
}

