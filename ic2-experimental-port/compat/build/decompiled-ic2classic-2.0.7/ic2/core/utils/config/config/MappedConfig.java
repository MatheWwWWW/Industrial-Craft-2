/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 */
package ic2.core.utils.config.config;

import ic2.core.utils.config.config.ConfigEntry;
import ic2.core.utils.config.config.ConfigHandler;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class MappedConfig<K, V>
implements Runnable {
    Object2ObjectMap<K, V> mapped = new Object2ObjectLinkedOpenHashMap();
    Function<Object, K> keyGenerator;
    Function<Object, V> valueGenerator;

    protected MappedConfig(Function<Object, K> keyGenerator, Function<Object, V> valueGenerator) {
        this.keyGenerator = keyGenerator;
        this.valueGenerator = valueGenerator;
    }

    public static <K, V, T> MappedConfig<K, V> create(ConfigHandler handler, ConfigEntry.ArrayConfigEntry<T> config, Function<T, K> keyGenerator, Function<T, V> valueGenerator) {
        ArrayMappedConfig<K, V, T> values = new ArrayMappedConfig<K, V, T>(config, keyGenerator, valueGenerator);
        handler.addLoadedListener(values);
        return values;
    }

    public static <K, V, T, E extends Collection<T>> MappedConfig<K, V> create(ConfigHandler handler, ConfigEntry.CollectionConfigEntry<T, E> config, Function<T, K> keyGenerator, Function<T, V> valueGenerator) {
        CollectionMappedConfig<K, V, T, E> values = new CollectionMappedConfig<K, V, T, E>(config, keyGenerator, valueGenerator);
        handler.addLoadedListener(values);
        return values;
    }

    protected abstract void getElements(Consumer<Object> var1);

    public Set<Map.Entry<K, V>> entrySet() {
        return this.mapped.entrySet();
    }

    public Set<K> keySet() {
        return this.mapped.keySet();
    }

    public Collection<V> values() {
        return this.mapped.values();
    }

    public boolean contains(K key) {
        return this.mapped.containsKey(key);
    }

    public V get(K key) {
        return (V)this.mapped.get(key);
    }

    public V getOrDefault(K key, V defaultValue) {
        return (V)this.mapped.getOrDefault(key, defaultValue);
    }

    @Override
    public void run() {
        Object2ObjectLinkedOpenHashMap mapped = new Object2ObjectLinkedOpenHashMap();
        this.getElements(arg_0 -> this.lambda$run$0((Object2ObjectMap)mapped, arg_0));
        this.mapped = mapped;
    }

    private /* synthetic */ void lambda$run$0(Object2ObjectMap mapped, Object T) {
        mapped.put(this.keyGenerator.apply(T), this.valueGenerator.apply(T));
    }

    public static class ArrayMappedConfig<K, V, T>
    extends MappedConfig<K, V> {
        ConfigEntry.ArrayConfigEntry<T> config;

        public ArrayMappedConfig(ConfigEntry.ArrayConfigEntry<T> config, Function<Object, K> keyGenerator, Function<Object, V> valueGenerator) {
            super(keyGenerator, valueGenerator);
            this.config = config;
        }

        @Override
        protected void getElements(Consumer<Object> entry) {
            Object[] values = (Object[])this.config.getValue();
            for (int i = 0; i < values.length; ++i) {
                entry.accept(values[i]);
            }
        }
    }

    public static class CollectionMappedConfig<K, V, T, E extends Collection<T>>
    extends MappedConfig<K, V> {
        ConfigEntry.CollectionConfigEntry<T, E> config;

        public CollectionMappedConfig(ConfigEntry.CollectionConfigEntry<T, E> config, Function<Object, K> keyGenerator, Function<Object, V> valueGenerator) {
            super(keyGenerator, valueGenerator);
            this.config = config;
        }

        @Override
        protected void getElements(Consumer<Object> entry) {
            ((Collection)this.config.getValue()).forEach(entry);
        }
    }
}

