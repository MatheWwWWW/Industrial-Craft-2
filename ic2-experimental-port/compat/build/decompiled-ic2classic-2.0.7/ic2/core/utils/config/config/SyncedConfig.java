/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.api.buffer.IReadBuffer;
import ic2.core.utils.config.config.ConfigEntry;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.UUID;
import java.util.function.Supplier;

public class SyncedConfig<T extends ConfigEntry<?>> {
    Object2ObjectMap<UUID, T> mappedEntries = new Object2ObjectLinkedOpenHashMap();
    Supplier<T> creator;

    public SyncedConfig(Supplier<T> creator, T defaultValue) {
        this.creator = creator;
        this.mappedEntries.defaultReturnValue(defaultValue);
    }

    public boolean isPresent(UUID id) {
        return this.mappedEntries.containsKey((Object)id);
    }

    public T get(UUID id) {
        return (T)((ConfigEntry)this.mappedEntries.get((Object)id));
    }

    public void onSync(IReadBuffer buffer, UUID owner) {
        ((ConfigEntry)this.mappedEntries.computeIfAbsent((Object)owner, T -> (ConfigEntry)this.creator.get())).deserializeValue(buffer);
    }
}

