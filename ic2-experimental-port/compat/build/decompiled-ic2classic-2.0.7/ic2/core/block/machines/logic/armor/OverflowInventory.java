/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.armor;

import ic2.core.inventory.inv.SimpleInventory;
import net.minecraft.world.item.ItemStack;

public class OverflowInventory
extends SimpleInventory {
    public OverflowInventory(int size) {
        super(size);
        this.setMaxStackSize(1);
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot >= this.size ? ItemStack.f_41583_ : super.getStackInSlot(slot);
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        if (slot < this.size) {
            super.setStackInSlot(slot, stack);
        }
    }
}

