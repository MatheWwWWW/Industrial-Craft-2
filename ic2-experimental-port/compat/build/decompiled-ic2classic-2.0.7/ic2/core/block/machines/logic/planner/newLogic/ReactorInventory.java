/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.machines.logic.planner.newLogic;

import ic2.core.block.machines.logic.planner.newLogic.ReactorLogic;
import ic2.core.inventory.inv.SimpleInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ReactorInventory
extends SimpleInventory {
    ReactorLogic logic;

    public ReactorInventory(int size, ReactorLogic logic) {
        super(size);
        this.logic = logic;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        this.logic.setStackInSlot(slot, stack);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        int m = this.getSlotCount();
        for (int i = 0; i < m; ++i) {
            this.logic.setStackInSlot(i, (ItemStack)this.slots.get(i));
        }
    }

    @Override
    public void clear() {
        super.clear();
        int m = this.getSlotCount();
        for (int i = 0; i < m; ++i) {
            this.logic.setStackInSlot(i, ItemStack.f_41583_);
        }
    }
}

