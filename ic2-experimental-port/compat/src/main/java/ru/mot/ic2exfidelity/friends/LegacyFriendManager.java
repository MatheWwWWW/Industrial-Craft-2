package ru.mot.ic2exfidelity.friends;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

/** Persistent, Classic-compatible owner/friend/action store. */
public final class LegacyFriendManager extends SavedData {
    public static final String BREAK_IRIDIUM = "player_break_iridium";
    private static final String DATA_ID = "ic2_friend_data";

    private final Map<UUID, LinkedHashMap<UUID, FriendEntry>> owners =
            new LinkedHashMap<>();

    public LegacyFriendManager() {
    }

    public LegacyFriendManager(CompoundTag root) {
        ListTag ownerList = root.m_128437_("data", 10);
        for (int ownerIndex = 0; ownerIndex < ownerList.size(); ownerIndex++) {
            CompoundTag ownerTag = ownerList.m_128728_(ownerIndex);
            if (!ownerTag.m_128441_("id")) {
                continue;
            }
            UUID owner = ownerTag.m_128342_("id");
            LinkedHashMap<UUID, FriendEntry> entries = new LinkedHashMap<>();
            ListTag friends = ownerTag.m_128437_("friends", 10);
            for (int friendIndex = 0; friendIndex < friends.size(); friendIndex++) {
                CompoundTag friendTag = friends.m_128728_(friendIndex);
                if (!friendTag.m_128441_("id")) {
                    continue;
                }
                UUID id = friendTag.m_128342_("id");
                String name = friendTag.m_128461_("name");
                LinkedHashSet<String> actions = new LinkedHashSet<>();
                ListTag actionList = friendTag.m_128437_("actions", 8);
                for (int actionIndex = 0; actionIndex < actionList.size(); actionIndex++) {
                    if (actionList.get(actionIndex) instanceof StringTag action) {
                        actions.add(action.m_7916_());
                    }
                }
                entries.put(id, new FriendEntry(id, name, actions));
            }
            if (!entries.isEmpty()) {
                owners.put(owner, entries);
            }
        }
    }

    public static LegacyFriendManager get(MinecraftServer server) {
        if (server == null) {
            throw new IllegalStateException("Friend data requested without a server");
        }
        ServerLevel overworld = server.m_129880_(Level.f_46428_);
        return overworld.m_8895_().m_164861_(
                LegacyFriendManager::new, LegacyFriendManager::new, DATA_ID);
    }

    public boolean canApply(UUID owner, UUID friend, String action) {
        Map<UUID, FriendEntry> entries = owners.get(owner);
        FriendEntry entry = entries == null ? null : entries.get(friend);
        return entry != null && entry.actions().contains(action);
    }

    public List<FriendEntry> getFriends(UUID owner) {
        Map<UUID, FriendEntry> entries = owners.get(owner);
        return entries == null
                ? Collections.emptyList()
                : new ArrayList<>(entries.values());
    }

    public void update(
            UUID owner, UUID friend, String name,
            boolean enabled, boolean breakIridium) {
        if (owner.equals(friend)) {
            return;
        }
        LinkedHashMap<UUID, FriendEntry> entries = owners.computeIfAbsent(
                owner, ignored -> new LinkedHashMap<>());
        if (!enabled) {
            entries.remove(friend);
            if (entries.isEmpty()) {
                owners.remove(owner);
            }
            m_77762_();
            return;
        }
        LinkedHashSet<String> actions = new LinkedHashSet<>();
        FriendEntry previous = entries.get(friend);
        if (previous != null) {
            actions.addAll(previous.actions());
        }
        if (breakIridium) {
            actions.add(BREAK_IRIDIUM);
        } else {
            actions.remove(BREAK_IRIDIUM);
        }
        entries.put(friend, new FriendEntry(friend, name, actions));
        m_77762_();
    }

    @Override
    public CompoundTag m_7176_(CompoundTag root) {
        ListTag ownerList = new ListTag();
        for (Map.Entry<UUID, LinkedHashMap<UUID, FriendEntry>> owner
                : owners.entrySet()) {
            CompoundTag ownerTag = new CompoundTag();
            ownerTag.m_128362_("id", owner.getKey());
            ListTag friends = new ListTag();
            for (FriendEntry friend : owner.getValue().values()) {
                CompoundTag friendTag = new CompoundTag();
                friendTag.m_128362_("id", friend.id());
                friendTag.m_128359_("name", friend.name());
                ListTag actions = new ListTag();
                for (String action : friend.actions()) {
                    actions.add(StringTag.m_129297_(action));
                }
                friendTag.m_128365_("actions", actions);
                friends.add(friendTag);
            }
            ownerTag.m_128365_("friends", friends);
            ownerList.add(ownerTag);
        }
        root.m_128365_("data", ownerList);
        return root;
    }

    public record FriendEntry(UUID id, String name, Set<String> actions) {
        public FriendEntry {
            actions = Collections.unmodifiableSet(new LinkedHashSet<>(actions));
        }

        public boolean canBreakIridium() {
            return actions.contains(BREAK_IRIDIUM);
        }
    }
}
