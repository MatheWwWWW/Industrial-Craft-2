/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.AbstractCookingRecipe
 *  net.minecraft.world.item.crafting.RecipeSerializer
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class CookingSerializer
implements RecipeSerializer<AbstractCookingRecipe> {
    public AbstractCookingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        if (json.get("enabled").getAsBoolean()) {
            Supplier<AbstractCookingRecipe> provider = AdvRecipeRegistry.INSTANCE.getCooking().getValue(recipeId);
            return provider == null ? null : provider.get();
        }
        return null;
    }

    public AbstractCookingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        return null;
    }

    public void toNetwork(FriendlyByteBuf buffer, AbstractCookingRecipe recipe) {
    }
}

