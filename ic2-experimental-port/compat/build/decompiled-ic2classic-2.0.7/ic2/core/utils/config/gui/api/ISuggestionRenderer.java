/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
 *  net.minecraft.network.chat.Component
 */
package ic2.core.utils.config.gui.api;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import net.minecraft.network.chat.Component;

public interface ISuggestionRenderer {
    public Component renderSuggestion(PoseStack var1, String var2, int var3, int var4);

    public static class Registry {
        private static final Map<Class<?>, ISuggestionRenderer> REGISTRY = Object2ObjectMaps.synchronize((Object2ObjectMap)new Object2ObjectOpenHashMap());

        public static void register(Class<?> clz, ISuggestionRenderer suggestion) {
            REGISTRY.putIfAbsent(clz, suggestion);
        }

        public static ISuggestionRenderer getRendererForType(Class<?> clz) {
            return REGISTRY.get(clz);
        }

        public static ISuggestionRenderer getRendererForType(Object obj) {
            if (obj == null) {
                return null;
            }
            return Registry.getRendererForType(obj instanceof Class ? (Class<?>)obj : obj.getClass());
        }
    }
}

