/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemStack
 *  net.minecraft.tileentity.TileEntity
 */
package ic2.api.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public interface INetworkManager {
    public void updateTileEntityField(TileEntity var1, String var2);

    public void initiateTileEntityEvent(TileEntity var1, int var2, boolean var3);

    public void initiateItemEvent(EntityPlayer var1, ItemStack var2, int var3, boolean var4);

    public void initiateClientTileEntityEvent(TileEntity var1, int var2);

    public void initiateClientItemEvent(ItemStack var1, int var2);

    public void sendInitialData(TileEntity var1);
}

