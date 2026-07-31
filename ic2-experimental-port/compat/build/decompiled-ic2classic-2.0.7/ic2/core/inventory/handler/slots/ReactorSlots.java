/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 */
package ic2.core.inventory.handler.slots;

import ic2.core.block.base.tiles.impls.BaseNuclearReactorTileEntity;
import ic2.core.inventory.handler.IModularSlot;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class ReactorSlots
implements IModularSlot {
    BaseNuclearReactorTileEntity tile;

    public ReactorSlots(BaseNuclearReactorTileEntity tile) {
        this.tile = tile;
    }

    @Override
    public int getSlotCount() {
        return 6 * this.tile.getWidth();
    }

    @Override
    public int getRealSlot(int slotIndex) {
        return this.tile.getSlotArray()[slotIndex];
    }

    @Override
    public boolean containsSlot(int slot) {
        return new IntOpenHashSet(this.tile.getSlotArray()).contains(slot);
    }
}

