/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.transport;

import ic2.api.transport.IPipe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IItemTransportTile
extends IPipe {
    public int putItems(ItemStack var1, EnumFacing var2, boolean var3);

    public ItemStack getContents();

    public void setContents(ItemStack var1);

    public int getMaxStackSizeAllowed();

    public int getTransferRate();
}

