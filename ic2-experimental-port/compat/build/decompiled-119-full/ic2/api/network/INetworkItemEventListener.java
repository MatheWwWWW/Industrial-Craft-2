/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.network;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public interface INetworkItemEventListener {
    public void onNetworkEvent(ItemStack var1, Player var2, int var3);
}

