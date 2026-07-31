/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.saveddata.SavedData
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.api.distmarker.OnlyIn
 *  net.minecraftforge.server.ServerLifecycleHooks
 */
package ic2.core.platform.player.friends;

import ic2.core.IC2;
import ic2.core.networking.PacketManager;
import ic2.core.networking.packets.client.friend.FriendSyncRequest;
import ic2.core.networking.packets.server.FriendPacket;
import ic2.core.platform.player.friends.Friend;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.NBTListWrapper;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.server.ServerLifecycleHooks;

public class FriendManager
extends SavedData {
    static final FriendManager CLIENT_MANAGER = new FriendManager();
    static final Supplier<FriendManager> PROVIDER = FriendManager::new;
    Map<UUID, Map<UUID, Friend>> friend_data = CollectionUtils.createLinkedMap();
    Map<UUID, Set<Friend>> known_Friends = CollectionUtils.createLinkedMap();
    boolean ignoreUpdates = false;

    public FriendManager() {
    }

    public FriendManager(CompoundTag nbt) {
        this.known_Friends.clear();
        this.friend_data.clear();
        for (CompoundTag data : NBTListWrapper.wrap(nbt.m_128437_("data", 10), CompoundTag.class)) {
            UUID id = data.m_128342_("id");
            Object2ObjectMap<UUID, Friend> friendMap = CollectionUtils.createMap();
            ObjectSortedSet friends = CollectionUtils.createLinkedSet();
            for (CompoundTag friendEntry : NBTListWrapper.wrap(data.m_128437_("friends", 10), CompoundTag.class)) {
                Friend friend = new Friend(friendEntry);
                friends.add((Friend)friend);
                friendMap.put(friend.getId(), friend);
            }
            if (friends.isEmpty()) continue;
            this.known_Friends.put(id, (Set<Friend>)friends);
            this.friend_data.put(id, (Map<UUID, Friend>)friendMap);
        }
    }

    @OnlyIn(value=Dist.CLIENT)
    public static FriendManager getClientFriends() {
        return CLIENT_MANAGER;
    }

    public static FriendManager getFriends() {
        return IC2.PLATFORM.isRendering() ? CLIENT_MANAGER : FriendManager.getServerFriends();
    }

    public static FriendManager getServerFriends() {
        return (FriendManager)ServerLifecycleHooks.getCurrentServer().m_129880_(Level.f_46428_).m_8895_().m_164861_(FriendManager::new, FriendManager::new, "ic2_friend_data");
    }

    public CompoundTag m_7176_(CompoundTag compound) {
        ListTag list = new ListTag();
        for (Map.Entry<UUID, Set<Friend>> entry : this.known_Friends.entrySet()) {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128362_("id", entry.getKey());
            ListTag friends = new ListTag();
            for (Friend friend : entry.getValue()) {
                friends.add((Object)friend.write(new CompoundTag()));
            }
            nbt.m_128365_("friends", (Tag)friends);
            list.add((Object)nbt);
        }
        compound.m_128365_("data", (Tag)list);
        return compound;
    }

    public void updateFriendData(Player player, List<Friend> friend) {
        UUID data = player.m_20148_();
        this.known_Friends.remove(data);
        this.friend_data.remove(data);
        int m = friend.size();
        for (int i = 0; i < m; ++i) {
            this.addFriend(data, friend.get(i));
        }
        this.m_77762_();
        if (IC2.PLATFORM.isSimulating()) {
            this.sync();
        }
    }

    public boolean addFriend(UUID person, Friend friend) {
        if (person == friend.getId()) {
            return false;
        }
        Set<Friend> friends = this.known_Friends.get(person);
        if (friends == null) {
            friends = CollectionUtils.createLinkedSet();
            this.known_Friends.put(person, friends);
        }
        if (friends.add(friend)) {
            Map<UUID, Friend> map = this.friend_data.get(person);
            if (map == null) {
                map = CollectionUtils.createMap();
                this.friend_data.put(person, map);
            }
            this.m_77762_();
            return map.put(friend.getId(), friend) == null;
        }
        return false;
    }

    public boolean removeFriend(UUID person, UUID otherPerson) {
        Map<UUID, Friend> map = this.friend_data.get(person);
        if (map == null) {
            return false;
        }
        Set<Friend> friends = this.known_Friends.get(person);
        if (friends.remove(map.remove(otherPerson))) {
            if (friends.isEmpty()) {
                this.known_Friends.remove(person);
            }
            if (map.isEmpty()) {
                this.friend_data.remove(person);
            }
            this.m_77762_();
            return true;
        }
        return false;
    }

    public Friend getFriend(UUID owner, UUID friend) {
        Map<UUID, Friend> friends = this.friend_data.get(owner);
        return friends == null ? null : friends.get(friend);
    }

    public List<Friend> getFriends(UUID owner) {
        Set<Friend> set = this.known_Friends.get(owner);
        return set == null ? ObjectLists.emptyList() : new ObjectArrayList(set);
    }

    public void sendDataToPlayer(Player player) {
        PacketManager.INSTANCE.sendToPlayer(new FriendPacket(this.known_Friends), player);
    }

    public void sync() {
        PacketManager.INSTANCE.sendToAllPlayers(new FriendPacket(this.known_Friends));
    }

    public void requestSync() {
        PacketManager.INSTANCE.sendToServer(new FriendSyncRequest());
    }

    public void setIgnoreUpdates(boolean value) {
        this.ignoreUpdates = value;
    }

    @OnlyIn(value=Dist.CLIENT)
    public void receiveData(Object2ObjectMap<UUID, Set<Friend>> updateData) {
        if (this.ignoreUpdates) {
            return;
        }
        this.known_Friends.clear();
        this.friend_data.clear();
        for (Map.Entry entry : Object2ObjectMaps.fastIterable(updateData)) {
            UUID owner = (UUID)entry.getKey();
            for (Friend friend : (Set)entry.getValue()) {
                this.addFriend(owner, friend);
            }
        }
    }
}

