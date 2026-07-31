/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.data.recipes.FinishedRecipe
 *  net.minecraft.world.item.ItemStack
 */
package ic2.data.recipe.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.recipe.input.RecipeInputBase;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.data.recipe.helper.MachineRecipeJsonProvider;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.ItemStack;

public class CannerBottleRecipeGenerator {
    private final Consumer<FinishedRecipe> exporter;

    public CannerBottleRecipeGenerator(Consumer<FinishedRecipe> consumer) {
        this.exporter = consumer;
    }

    public void add(final RecipeInputBase recipeInputBase, final RecipeInputBase recipeInputBase2, final ItemStack itemStack, String string) {
        this.exporter.accept(new MachineRecipeJsonProvider(Ic2RecipeSerializers.CANNER_BOTTLE, string){

            public void m_7917_(JsonObject jsonObject) {
                jsonObject.add("container_ingredient", recipeInputBase.toJson());
                jsonObject.add("fill_ingredient", recipeInputBase2.toJson());
                jsonObject.add("result", (JsonElement)RecipeIo.resultToJson(itemStack));
            }
        });
    }
}

