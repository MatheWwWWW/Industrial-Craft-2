/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntList
 */
package ic2.core.inventory.handler.slots;

import ic2.core.inventory.handler.IModularSlot;
import it.unimi.dsi.fastutil.ints.IntList;

public class ListModularSlot
implements IModularSlot {
    IntList list;

    public ListModularSlot(IntList list) {
        this.list = list;
    }

    @Override
    public int getSlotCount() {
        return this.list.size();
    }

    @Override
    public int getRealSlot(int slotIndex) {
        return this.list.getInt(slotIndex);
    }

    @Override
    public boolean containsSlot(int slot) {
        return this.list.contains(slot);
    }
}

