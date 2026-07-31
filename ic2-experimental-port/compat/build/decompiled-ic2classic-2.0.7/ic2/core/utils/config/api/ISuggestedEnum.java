/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 */
package ic2.core.utils.config.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;

public interface ISuggestedEnum<T extends Enum<T>> {
    public String getName(T var1);

    public static void registerWrapper(Class<? extends Enum<?>> clz, ISuggestedEnum<?> wrapper) {
        EnumStorage.WRAPPERS.put(clz, wrapper);
    }

    public static <T extends Enum<T>> ISuggestedEnum<T> getWrapper(T value) {
        if (value instanceof ISuggestedEnum) {
            return (ISuggestedEnum)((Object)value);
        }
        return EnumStorage.WRAPPERS.get(value.getClass());
    }

    public static class EnumStorage {
        private static final Map<Class<? extends Enum<?>>, ISuggestedEnum<?>> WRAPPERS = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectOpenHashMap());
    }
}

