/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.inv;

import ic2.core.inventory.inv.INotifyInventory;
import ic2.core.inventory.inv.SimpleInventory;
import net.minecraft.world.item.ItemStack;

public class ListenerInventory
extends SimpleInventory {
    INotifyInventory listener;

    public ListenerInventory(int size, INotifyInventory listener) {
        super(size);
        this.listener = listener;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        super.setStackInSlot(slot, stack);
        if (this.listener != null) {
            this.listener.onNotify(this, slot);
        }
    }
}

