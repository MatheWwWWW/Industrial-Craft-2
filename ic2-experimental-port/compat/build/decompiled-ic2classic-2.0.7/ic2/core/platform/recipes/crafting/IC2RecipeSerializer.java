/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.GsonHelper
 *  net.minecraft.world.item.crafting.RecipeSerializer
 *  net.minecraftforge.registries.ForgeRegistries
 */
package ic2.core.platform.recipes.crafting;

import com.google.gson.JsonObject;
import ic2.core.platform.recipes.crafting.CookingSerializer;
import ic2.core.platform.recipes.crafting.RecipeIC2Base;
import ic2.core.platform.recipes.crafting.RepairRecipe;
import ic2.core.platform.recipes.crafting.ShapedIC2Recipe;
import ic2.core.platform.recipes.crafting.ShapelessIC2Recipe;
import ic2.core.platform.recipes.crafting.SingleSerializer;
import ic2.core.platform.recipes.crafting.SmithingSerializer;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.SimpleRegistry;
import java.util.function.BiFunction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.ForgeRegistries;

public class IC2RecipeSerializer
implements RecipeSerializer<RecipeIC2Base> {
    public static final IC2RecipeSerializer INSTANCE = new IC2RecipeSerializer();
    public static final SimpleRegistry<BiFunction<FriendlyByteBuf, ResourceLocation, RecipeIC2Base>> RECIPE_SERIALIZER = CollectionUtils.createRegistry();
    public static final SimpleRegistry<BiFunction<ResourceLocation, JsonObject, RecipeIC2Base>> RECIPE_PARSER = CollectionUtils.createRegistry();

    public static void register() {
        ForgeRegistries.RECIPE_SERIALIZERS.register(new ResourceLocation("ic2", "crafting_ic2_automated"), (Object)INSTANCE);
        ForgeRegistries.RECIPE_SERIALIZERS.register(new ResourceLocation("ic2", "cooking_ic2_serializer"), (Object)new CookingSerializer());
        ForgeRegistries.RECIPE_SERIALIZERS.register(new ResourceLocation("ic2", "single_item_ic2_serializer"), (Object)new SingleSerializer());
        ForgeRegistries.RECIPE_SERIALIZERS.register(new ResourceLocation("ic2", "smithing_ic2_serializer"), (Object)new SmithingSerializer());
        RECIPE_SERIALIZER.register(new ResourceLocation("ic2", "shaped"), ShapedIC2Recipe::new);
        RECIPE_SERIALIZER.register(new ResourceLocation("ic2", "shapeless"), ShapelessIC2Recipe::new);
        RECIPE_SERIALIZER.register(new ResourceLocation("ic2", "repair"), RepairRecipe::new);
        RECIPE_PARSER.register(new ResourceLocation("ic2", "shaped"), ShapedIC2Recipe::create);
        RECIPE_PARSER.register(new ResourceLocation("ic2", "shapeless"), ShapelessIC2Recipe::create);
        RECIPE_PARSER.register(new ResourceLocation("ic2", "repair"), RepairRecipe::create);
    }

    public RecipeIC2Base fromJson(ResourceLocation recipeId, JsonObject json) {
        if (!GsonHelper.m_13855_((JsonObject)json, (String)"enabled", (boolean)true)) {
            return null;
        }
        if (json.has("sub_type")) {
            return RECIPE_PARSER.getValue(ResourceLocation.m_135820_((String)GsonHelper.m_13906_((JsonObject)json, (String)"sub_type"))).apply(recipeId, json);
        }
        return AdvRecipeRegistry.INSTANCE.getRecipes().getValue(recipeId);
    }

    public RecipeIC2Base fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        try {
            return RECIPE_SERIALIZER.getValue(buffer.m_130281_()).apply(buffer, recipeId);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void toNetwork(FriendlyByteBuf buffer, RecipeIC2Base recipe) {
        try {
            buffer.m_130085_(recipe.getMetaSerializer());
            recipe.serialize(buffer);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

