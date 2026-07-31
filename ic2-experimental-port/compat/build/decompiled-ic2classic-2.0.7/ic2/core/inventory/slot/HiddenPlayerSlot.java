/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.Container
 *  net.minecraft.world.inventory.Slot
 */
package ic2.core.inventory.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class HiddenPlayerSlot
extends Slot {
    public HiddenPlayerSlot(Container inventoryIn, int index) {
        super(inventoryIn, index, 0, 0);
    }

    public boolean m_6659_() {
        return false;
    }
}

