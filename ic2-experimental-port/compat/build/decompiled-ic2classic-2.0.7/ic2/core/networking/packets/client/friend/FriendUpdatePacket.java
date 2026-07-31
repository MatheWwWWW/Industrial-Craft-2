/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.client.friend;

import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.utils.collection.CollectionUtils;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class FriendUpdatePacket
extends IC2Packet {
    List<Friend> newFriendData = CollectionUtils.createList();

    public FriendUpdatePacket() {
    }

    public FriendUpdatePacket(List<Friend> newFriendData) {
        this.newFriendData = newFriendData;
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.newFriendData.size());
        for (Friend friend : this.newFriendData) {
            friend.write(buffer);
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            this.newFriendData.add(new Friend(buffer));
        }
    }

    @Override
    public void handlePacket(Player source) {
        FriendManager.getServerFriends().updateFriendData(source, this.newFriendData);
    }
}

