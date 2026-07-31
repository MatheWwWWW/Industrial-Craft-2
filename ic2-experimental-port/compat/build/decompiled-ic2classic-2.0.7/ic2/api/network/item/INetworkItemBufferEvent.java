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
import ic2.api.network.buffer.INetworkDataBuffer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;

public interface INetworkItemBufferEvent<T extends INetworkDataBuffer>
extends IPlayerPacket {
    public void onDataBufferReceived(ItemStack var1, Player var2, String var3, T var4, Dist var5);
}

