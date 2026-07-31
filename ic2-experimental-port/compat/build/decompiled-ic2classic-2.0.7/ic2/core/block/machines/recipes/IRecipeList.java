/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonPrimitive
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface IRecipeList {
    public void writeRecipes(FriendlyByteBuf var1);

    public void readRecipes(FriendlyByteBuf var1);

    default public Map<ResourceLocation, JsonObject> writeRecipes() {
        return Collections.emptyMap();
    }

    default public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
    }

    default public String getFolder() {
        return "Empty";
    }

    public static JsonObject mapArray(String id, JsonArray array) {
        JsonObject obj = new JsonObject();
        obj.add(id, (JsonElement)array);
        return obj;
    }

    public static boolean isRemover(JsonObject obj) {
        return obj.has("remove");
    }

    public static void getEntriesToRemove(JsonObject obj, Consumer<ResourceLocation> toRemove) {
        IRecipeList.iteratePrimitive(obj.get("remove"), T -> {
            ResourceLocation result = ResourceLocation.m_135820_((String)T.getAsString());
            if (result != null) {
                toRemove.accept(result);
            }
        });
    }

    public static void iterateObject(JsonElement element, Consumer<JsonObject> result) {
        if (element == null) {
            return;
        }
        if (element.isJsonObject()) {
            result.accept(element.getAsJsonObject());
        } else if (element.isJsonArray()) {
            for (JsonElement el : element.getAsJsonArray()) {
                IRecipeList.iterateObject(el, result);
            }
        }
    }

    public static void iteratePrimitive(JsonElement element, Consumer<JsonPrimitive> result) {
        if (element == null) {
            return;
        }
        if (element.isJsonPrimitive()) {
            result.accept(element.getAsJsonPrimitive());
        } else if (element.isJsonArray()) {
            for (JsonElement el : element.getAsJsonArray()) {
                IRecipeList.iteratePrimitive(el, result);
            }
        }
    }
}

