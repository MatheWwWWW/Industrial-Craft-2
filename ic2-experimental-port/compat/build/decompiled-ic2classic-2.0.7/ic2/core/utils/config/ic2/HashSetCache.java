/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.utils.config.ic2;

import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigHandler;
import java.util.Set;

public class HashSetCache<T> {
    public final ConfigEntry<? extends T[]> configEntry;
    private final Set<T> cache = CollectionUtils.createSet();

    private HashSetCache(ConfigEntry<? extends T[]> configEntry, ConfigHandler configHandler) {
        this.configEntry = configEntry;
        configHandler.addLoadedListener(this::reload);
    }

    private void reload() {
        this.cache.clear();
        for (T val : this.configEntry.getValue()) {
            this.cache.add(val);
        }
    }

    public boolean contains(T value) {
        return this.cache.contains(value);
    }

    public static <T> HashSetCache<T> create(ConfigEntry<? extends T[]> configEntry, ConfigHandler configHandler) {
        return new HashSetCache<T>(configEntry, configHandler);
    }
}

