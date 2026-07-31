/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.SingleItemRecipe
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SingleItemRecipe;

public class SingleSerializer
implements RecipeSerializer<SingleItemRecipe> {
    public SingleItemRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        if (json.get("enabled").getAsBoolean()) {
            Supplier<SingleItemRecipe> provider = AdvRecipeRegistry.INSTANCE.getSingleRecipes().getValue(recipeId);
            return provider == null ? null : provider.get();
        }
        return null;
    }

    public SingleItemRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        return null;
    }

    public void toNetwork(FriendlyByteBuf buffer, SingleItemRecipe recipe) {
    }
}

