/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.client.friend;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.friends.FriendManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class FriendSyncRequest
extends IC2Packet {
    @Override
    public void write(FriendlyByteBuf buffer) {
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
    }

    @Override
    public void handlePacket(Player source) {
        FriendManager.getServerFriends().sendDataToPlayer(source);
    }
}

