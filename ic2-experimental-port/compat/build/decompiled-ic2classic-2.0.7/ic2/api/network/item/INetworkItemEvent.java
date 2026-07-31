/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.api.network.item;

import ic2.api.network.IPlayerPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;

public interface INetworkItemEvent
extends IPlayerPacket {
    public void onEventReceived(ItemStack var1, Player var2, int var3, int var4, Dist var5);
}

