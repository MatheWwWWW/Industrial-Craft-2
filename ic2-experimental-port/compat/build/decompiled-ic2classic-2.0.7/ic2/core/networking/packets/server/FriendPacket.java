/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.world.entity.player.Player
 */
package ic2.core.networking.packets.server;

import ic2.core.IC2;
import ic2.core.networking.packets.IC2Packet;
import ic2.core.platform.player.friends.Friend;
import ic2.core.platform.player.friends.FriendManager;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;

public class FriendPacket
extends IC2Packet {
    Object2ObjectMap<UUID, Set<Friend>> updateData = CollectionUtils.createLinkedMap();

    public FriendPacket() {
    }

    public FriendPacket(Map<UUID, Set<Friend>> friends) {
        this.updateData.putAll(friends);
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.m_130130_(this.updateData.size());
        for (Object2ObjectMap.Entry entry : Object2ObjectMaps.fastIterable(this.updateData)) {
            buffer.m_130077_((UUID)entry.getKey());
            Set friends = (Set)entry.getValue();
            buffer.m_130130_(friends.size());
            for (Friend friend : friends) {
                friend.write(buffer);
            }
        }
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        int size = buffer.m_130242_();
        for (int i = 0; i < size; ++i) {
            UUID owner = buffer.m_130259_();
            ObjectSortedSet friends = CollectionUtils.createLinkedSet();
            int subSize = buffer.m_130242_();
            for (int j = 0; j < subSize; ++j) {
                friends.add((Friend)new Friend(buffer));
            }
            this.updateData.put((Object)owner, friends);
        }
    }

    @Override
    public void handlePacket(Player source) {
        FriendManager.getClientFriends().receiveData(this.updateData);
        IC2.PLATFORM.sendPlayerSpecificConfig();
    }
}

