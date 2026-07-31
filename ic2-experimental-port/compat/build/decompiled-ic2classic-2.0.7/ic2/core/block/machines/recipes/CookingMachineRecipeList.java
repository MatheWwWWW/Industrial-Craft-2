/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.AbstractCookingRecipe
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 *  net.minecraft.world.item.crafting.RecipeManager
 *  net.minecraft.world.item.crafting.RecipeType
 */
package ic2.core.block.machines.recipes;

import com.google.gson.JsonObject;
import ic2.api.recipes.ingridients.inputs.IngredientInput;
import ic2.api.recipes.ingridients.recipes.IRecipeOutput;
import ic2.api.recipes.ingridients.recipes.SimpleRecipeOutput;
import ic2.api.recipes.misc.RecipeMods;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.MachineRecipeList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public class CookingMachineRecipeList
extends MachineRecipeList {
    boolean loadedRecipes = false;
    RecipeType<?> type;
    int expectedCookTime;

    public CookingMachineRecipeList(RecipeType<?> type, int time) {
        super("Empty", null);
        this.listeners.clear();
        this.type = type;
        this.expectedCookTime = time;
    }

    @Override
    public void registerListener(Consumer<IMachineRecipeList> listener) {
    }

    @Override
    public void reload() {
        this.loadedRecipes = false;
        this.mapped = false;
        this.recipeMap.clear();
        this.recipes.clear();
    }

    public void fetchRecipes() {
        RecipeManager manager = IC2.PLATFORM.getRecipes();
        for (Recipe entry : manager.m_44051_()) {
            if (entry.m_6671_() != this.type) continue;
            AbstractCookingRecipe recipe = (AbstractCookingRecipe)entry;
            int extraTime = recipe.m_43753_() - this.expectedCookTime;
            CompoundTag nbt = null;
            if (extraTime != 0) {
                nbt = RecipeMods.RECIPE_TIME.create((double)recipe.m_43753_() / (double)this.expectedCookTime);
            }
            this.addRecipe(recipe.m_6423_(), (IRecipeOutput)new SimpleRecipeOutput(Collections.singletonList(recipe.m_8043_()), nbt, recipe.m_43750_()), new IngredientInput((Ingredient)entry.m_7527_().get(0), 1));
        }
    }

    @Override
    public List<ResourceLocation> getAllRecipes() {
        if (!this.loadedRecipes) {
            this.loadedRecipes = true;
            this.fetchRecipes();
        }
        return super.getAllRecipes();
    }

    @Override
    public List<IMachineRecipeList.RecipeEntry> getAllEntries() {
        if (!this.loadedRecipes) {
            this.loadedRecipes = true;
            this.fetchRecipes();
        }
        return super.getAllEntries();
    }

    @Override
    public void writeRecipes(FriendlyByteBuf buffer) {
    }

    @Override
    public void readRecipes(FriendlyByteBuf buffer) {
    }

    @Override
    public Map<ResourceLocation, JsonObject> writeRecipes() {
        return Collections.emptyMap();
    }

    @Override
    public void readRecipe(ResourceLocation id, JsonObject data) throws Exception {
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(ItemStack stack, boolean hasStackSize) {
        if (!this.loadedRecipes) {
            this.loadedRecipes = true;
            this.fetchRecipes();
        }
        for (IMachineRecipeList.RecipeEntry subEntry : this.recipes.values()) {
            if (!subEntry.getInputs()[0].matches(stack)) continue;
            return subEntry;
        }
        return null;
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(Predicate<IMachineRecipeList.RecipeEntry> checker) {
        if (!this.loadedRecipes) {
            this.loadedRecipes = true;
            this.fetchRecipes();
        }
        return super.getRecipe(checker);
    }

    @Override
    public IMachineRecipeList.RecipeEntry getRecipe(ResourceLocation location) {
        if (!this.loadedRecipes) {
            this.loadedRecipes = true;
            this.fetchRecipes();
        }
        return super.getRecipe(location);
    }
}

