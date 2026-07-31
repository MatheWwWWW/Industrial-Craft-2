/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.inventory.IInventory
 *  net.minecraft.inventory.Slot
 *  net.minecraft.item.ItemStack
 */
package ic2.core.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotRadioactive
extends Slot {
    public SlotRadioactive(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean func_75214_a(ItemStack stack) {
        return this.field_75224_c.func_94041_b(this.field_75222_d, stack);
    }
}

