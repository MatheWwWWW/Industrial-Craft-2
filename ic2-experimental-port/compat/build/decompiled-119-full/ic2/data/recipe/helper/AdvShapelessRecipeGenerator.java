/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.data.recipe.helper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.recipe.input.RecipeInputBase;
import ic2.core.recipe.input.RecipeInputFluidContainer;
import ic2.core.recipe.input.RecipeInputIngredient;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.data.recipe.helper.MachineRecipeJsonProvider;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

public class AdvShapelessRecipeGenerator {
    private final Consumer<FinishedRecipe> exporter;
    private final Set<String> usedNames = new HashSet<String>();

    public AdvShapelessRecipeGenerator(Consumer<FinishedRecipe> consumer) {
        this.exporter = consumer;
    }

    public Builder start(ItemLike itemLike) {
        return this.start(itemLike, 1);
    }

    public Builder start(ItemLike itemLike, int n) {
        return new Builder(new ItemStack(itemLike, n));
    }

    public class Builder {
        private final ItemStack result;
        private final List<RecipeInputBase> ingredient = new ArrayList<RecipeInputBase>();
        private boolean consuming = false;
        private boolean hidden = false;

        public Builder(ItemStack itemStack) {
            this.result = itemStack;
        }

        public Builder add(ItemLike itemLike) {
            this.ingredient.add(new RecipeInputIngredient(Ingredient.m_43929_((ItemLike[])new ItemLike[]{itemLike}), 1));
            return this;
        }

        public Builder add(Ingredient ingredient) {
            this.ingredient.add(new RecipeInputIngredient(ingredient, 1));
            return this;
        }

        public Builder add(TagKey<Item> tagKey) {
            this.ingredient.add(new RecipeInputIngredient(Ingredient.m_204132_(tagKey), 1));
            return this;
        }

        public Builder add(Fluid fluid, int n) {
            this.ingredient.add(new RecipeInputFluidContainer(fluid, n));
            return this;
        }

        public Builder add(RecipeInputBase recipeInputBase) {
            this.ingredient.add(recipeInputBase);
            return this;
        }

        public Builder consuming() {
            this.consuming = true;
            return this;
        }

        public Builder hidden() {
            this.hidden = true;
            return this;
        }

        public void finish(String string) {
            AdvShapelessRecipeGenerator.this.exporter.accept(new MachineRecipeJsonProvider(Ic2RecipeSerializers.SHAPELESS, string){

                public void m_7917_(JsonObject jsonObject) {
                    JsonArray jsonArray = new JsonArray();
                    for (RecipeInputBase recipeInputBase : Builder.this.ingredient) {
                        jsonArray.add(recipeInputBase.toJson());
                    }
                    jsonObject.add("ingredients", (JsonElement)jsonArray);
                    jsonObject.add("result", (JsonElement)RecipeIo.resultToJson(Builder.this.result));
                    if (Builder.this.consuming) {
                        jsonObject.addProperty("consuming", Boolean.valueOf(true));
                    }
                    if (Builder.this.hidden) {
                        jsonObject.addProperty("hidden", Boolean.valueOf(true));
                    }
                }
            });
        }
    }
}

