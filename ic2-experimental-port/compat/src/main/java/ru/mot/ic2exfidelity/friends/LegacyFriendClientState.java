package ru.mot.ic2exfidelity.friends;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/** Client-side snapshot used only by the friend settings screen. */
public final class LegacyFriendClientState {
    private static final Map<UUID, LegacyFriendManager.FriendEntry> FRIENDS =
            new LinkedHashMap<>();

    private LegacyFriendClientState() {
    }

    public static synchronized void replace(
            List<LegacyFriendManager.FriendEntry> entries) {
        FRIENDS.clear();
        for (LegacyFriendManager.FriendEntry entry : entries) {
            FRIENDS.put(entry.id(), entry);
        }
    }

    public static synchronized LegacyFriendManager.FriendEntry get(UUID id) {
        return FRIENDS.get(id);
    }

    public static synchronized void put(
            UUID id, String name, boolean enabled, boolean breakIridium) {
        if (!enabled) {
            FRIENDS.remove(id);
            return;
        }
        java.util.Set<String> actions = breakIridium
                ? java.util.Set.of(LegacyFriendManager.BREAK_IRIDIUM)
                : java.util.Set.of();
        FRIENDS.put(id, new LegacyFriendManager.FriendEntry(id, name, actions));
    }
}
