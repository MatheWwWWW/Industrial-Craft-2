/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  net.minecraft.data.recipes.FinishedRecipe
 */
package ic2.data.recipe.helper;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.recipe.input.RecipeInputBase;
import ic2.core.recipe.v2.RecipeIo;
import ic2.core.ref.Ic2RecipeSerializers;
import ic2.data.recipe.helper.MachineRecipeJsonProvider;
import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;

public class CannerEnrichRecipeGenerator {
    private final Consumer<FinishedRecipe> exporter;

    public CannerEnrichRecipeGenerator(Consumer<FinishedRecipe> consumer) {
        this.exporter = consumer;
    }

    public void add(final Ic2FluidStack ic2FluidStack, final RecipeInputBase recipeInputBase, final Ic2FluidStack ic2FluidStack2, String string) {
        this.exporter.accept(new MachineRecipeJsonProvider(Ic2RecipeSerializers.CANNER_ENRICH, string){

            public void m_7917_(JsonObject jsonObject) {
                jsonObject.add("input_ingredient", (JsonElement)RecipeIo.fluidStackToJson(ic2FluidStack));
                jsonObject.add("additive_ingredient", recipeInputBase.toJson());
                jsonObject.add("result", (JsonElement)RecipeIo.fluidStackToJson(ic2FluidStack2));
            }
        });
    }
}

