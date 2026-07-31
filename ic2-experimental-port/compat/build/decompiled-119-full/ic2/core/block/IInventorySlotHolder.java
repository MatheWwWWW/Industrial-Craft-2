/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.block;

import ic2.core.block.invslot.InvSlot;
import ic2.core.block.tileentity.Ic2TileEntity;

public interface IInventorySlotHolder<P extends Ic2TileEntity> {
    public P getParent();

    public InvSlot getInventorySlot(String var1);

    public void addInventorySlot(InvSlot var1);

    public int getBaseIndex(InvSlot var1);
}

