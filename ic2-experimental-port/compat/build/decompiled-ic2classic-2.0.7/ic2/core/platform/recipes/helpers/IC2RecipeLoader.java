/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.util.profiling.ProfilerFiller
 */
package ic2.core.platform.recipes.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.IC2;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

public class IC2RecipeLoader
extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final IC2RecipeLoader INSTANCE = new IC2RecipeLoader();

    public IC2RecipeLoader() {
        super(GSON, "ic2_recipes");
    }

    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resources, ProfilerFiller profiler) {
        Object2ObjectSortedMap<String, Map> result = CollectionUtils.createLinkedMap();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            String[] s = entry.getKey().m_135815_().split("\\/", 2);
            try {
                result.computeIfAbsent(s[0], T -> CollectionUtils.createLinkedMap()).put(new ResourceLocation(entry.getKey().m_135827_(), s[1]), GsonHelper.m_13918_((JsonElement)entry.getValue(), (String)"Top Element"));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        IC2.RECIPES.get(true).loadDataPackRecipes((Map<String, Map<ResourceLocation, JsonObject>>)result);
    }
}

