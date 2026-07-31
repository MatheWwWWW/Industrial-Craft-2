/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.api.network.tile;

import ic2.api.network.IPlayerPacket;
import ic2.api.network.buffer.INetworkDataBuffer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;

public interface INetworkDataEventListener
extends IPlayerPacket {
    public void onDataBufferReceived(Player var1, String var2, INetworkDataBuffer var3, Dist var4);
}

