/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.invslot.InvSlot;

public interface IInventorySlotHolder<P extends TileEntityBlock> {
    public P getParent();

    public InvSlot getInventorySlot(String var1);

    public void addInventorySlot(InvSlot var1);

    public int getBaseIndex(InvSlot var1);
}

