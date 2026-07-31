/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.utils.collection;

import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;

public class SimpleRegistry<V>
implements Iterable<V> {
    Map<ResourceLocation, V> registries = CollectionUtils.createLinkedMap();
    Map<String, List<ResourceLocation>> modSorted = CollectionUtils.createLinkedMap();

    public void register(ResourceLocation location, V value) {
        this.registries.put(location, value);
        List<ResourceLocation> locations = this.modSorted.get(location.m_135827_());
        if (locations == null) {
            locations = CollectionUtils.createList();
            this.modSorted.put(location.m_135827_(), locations);
        }
        locations.add(location);
    }

    public V getValue(ResourceLocation location) {
        return this.registries.get(location);
    }

    public boolean contains(ResourceLocation location) {
        return this.registries.containsKey(location);
    }

    public Set<ResourceLocation> keySet() {
        return this.registries.keySet();
    }

    @Override
    public Iterator<V> iterator() {
        return this.registries.values().iterator();
    }

    public Set<Map.Entry<ResourceLocation, V>> entrySet() {
        return this.registries.entrySet();
    }

    public List<ResourceLocation> getResourceForMod(String mod) {
        List<ResourceLocation> list = this.modSorted.get(mod);
        return list == null ? ObjectLists.emptyList() : list;
    }

    public void clear() {
        this.registries.clear();
        this.modSorted.clear();
    }
}

