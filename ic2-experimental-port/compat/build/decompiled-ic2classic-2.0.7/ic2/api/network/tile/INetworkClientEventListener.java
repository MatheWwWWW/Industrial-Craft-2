/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 */
package ic2.api.network.tile;

import ic2.api.network.IPlayerPacket;
import net.minecraft.world.entity.player.Player;

public interface INetworkClientEventListener
extends IPlayerPacket {
    public void onClientDataReceived(Player var1, int var2, int var3);
}

