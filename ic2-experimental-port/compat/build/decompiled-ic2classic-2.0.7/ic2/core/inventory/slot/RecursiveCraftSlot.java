/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.inventory.slot;

import ic2.core.inventory.base.IHasInventory;
import ic2.core.inventory.slot.LockedSlot;

public class RecursiveCraftSlot
extends LockedSlot {
    public RecursiveCraftSlot(IHasInventory inv, int index, int xPosition, int yPosition) {
        super(inv, index, xPosition, yPosition);
    }
}

