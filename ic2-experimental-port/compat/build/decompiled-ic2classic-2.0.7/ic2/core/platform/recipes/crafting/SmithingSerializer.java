/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraft.world.item.crafting.UpgradeRecipe
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;

public class SmithingSerializer
implements RecipeSerializer<UpgradeRecipe> {
    public UpgradeRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
        if (json.get("enabled").getAsBoolean()) {
            Supplier<UpgradeRecipe> provider = AdvRecipeRegistry.INSTANCE.getSmithingRecipes().getValue(recipeId);
            return provider == null ? null : provider.get();
        }
        return null;
    }

    public UpgradeRecipe fromNetwork(ResourceLocation p_199426_1_, FriendlyByteBuf p_199426_2_) {
        return null;
    }

    public void toNetwork(FriendlyByteBuf p_199427_1_, UpgradeRecipe p_199427_2_) {
    }
}

