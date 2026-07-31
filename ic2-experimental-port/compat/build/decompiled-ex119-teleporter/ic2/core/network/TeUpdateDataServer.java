/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.level.ServerPlayer
 */
package ic2.core.network;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.level.ServerPlayer;

public class TeUpdateDataServer {
    private final Set<String> globalFields = new HashSet<String>();
    private final Map<ServerPlayer, Set<String>> playerFieldMap = new IdentityHashMap<ServerPlayer, Set<String>>();

    TeUpdateDataServer() {
    }

    void addGlobalField(String string) {
        if (!this.globalFields.add(string)) {
            return;
        }
        if (!this.playerFieldMap.isEmpty()) {
            for (Set<String> set : this.playerFieldMap.values()) {
                set.remove(string);
            }
        }
    }

    void addPlayerField(String string, ServerPlayer serverPlayer) {
        if (this.globalFields.contains(string)) {
            return;
        }
        Set<String> set = this.playerFieldMap.get(serverPlayer);
        if (set == null) {
            set = new HashSet<String>();
            this.playerFieldMap.put(serverPlayer, set);
        }
        set.add(string);
    }

    Collection<String> getGlobalFields() {
        return this.globalFields;
    }

    Collection<String> getPlayerFields(ServerPlayer serverPlayer) {
        Set<String> set = this.playerFieldMap.get(serverPlayer);
        if (set == null) {
            return Collections.emptyList();
        }
        return set;
    }
}

