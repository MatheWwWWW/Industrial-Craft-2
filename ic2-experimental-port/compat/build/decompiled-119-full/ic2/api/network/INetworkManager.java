/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.api.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface INetworkManager {
    public void updateTileEntityField(BlockEntity var1, String var2);

    public void initiateTileEntityEvent(BlockEntity var1, int var2, boolean var3);

    public void initiateItemEvent(Player var1, ItemStack var2, int var3, boolean var4);

    public void initiateClientTileEntityEvent(BlockEntity var1, int var2);

    public void initiateClientItemEvent(ItemStack var1, int var2);

    public void sendInitialData(BlockEntity var1);
}

