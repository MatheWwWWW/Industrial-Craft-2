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
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

public class AdvRecipeGenerator {
    private final Consumer<FinishedRecipe> exporter;
    private final Set<String> usedNames = new HashSet<String>();

    public AdvRecipeGenerator(Consumer<FinishedRecipe> consumer) {
        this.exporter = consumer;
    }

    public Builder start(ItemLike itemLike, String ... stringArray) {
        return this.start(itemLike, 1, stringArray);
    }

    public Builder start(ItemLike itemLike, int n, String ... stringArray) {
        return new Builder(new ItemStack(itemLike, n), stringArray);
    }

    public class Builder {
        private final ItemStack result;
        private final String[] pattern;
        private final Map<Character, RecipeInputBase> key = new LinkedHashMap<Character, RecipeInputBase>();
        private boolean consuming = false;
        private boolean hidden = false;

        public Builder(ItemStack itemStack, String ... stringArray) {
            this.result = itemStack;
            this.pattern = stringArray;
        }

        public Builder key(char c, ItemLike itemLike) {
            this.key.put(Character.valueOf(c), new RecipeInputIngredient(Ingredient.m_43929_((ItemLike[])new ItemLike[]{itemLike}), 1));
            return this;
        }

        public Builder key(char c, TagKey<Item> tagKey) {
            this.key.put(Character.valueOf(c), new RecipeInputIngredient(Ingredient.m_204132_(tagKey), 1));
            return this;
        }

        public Builder key(char c, Fluid fluid, int n) {
            this.key.put(Character.valueOf(c), new RecipeInputFluidContainer(fluid, n));
            return this;
        }

        public Builder key(char c, RecipeInputBase recipeInputBase) {
            this.key.put(Character.valueOf(c), recipeInputBase);
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
            AdvRecipeGenerator.this.exporter.accept(new MachineRecipeJsonProvider(Ic2RecipeSerializers.SHAPED, string){

                public void m_7917_(JsonObject jsonObject) {
                    JsonArray jsonArray = new JsonArray();
                    for (String string : Builder.this.pattern) {
                        jsonArray.add(string);
                    }
                    jsonObject.add("pattern", (JsonElement)jsonArray);
                    JsonObject jsonObject2 = new JsonObject();
                    for (Map.Entry<Character, RecipeInputBase> entry : Builder.this.key.entrySet()) {
                        jsonObject2.add(entry.getKey().toString(), entry.getValue().toJson());
                    }
                    jsonObject.add("key", (JsonElement)jsonObject2);
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

