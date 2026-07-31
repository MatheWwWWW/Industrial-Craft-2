package ru.mot.ic2exfidelity.integration;

import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.AdvRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.crafting.Ingredient;

/** Presents IC2 shaped recipes to Forge/JEI using their declared dimensions. */
public final class AdvRecipeIngredientCompat {
    private AdvRecipeIngredientCompat() {
    }

    public static NonNullList<Ingredient> getIngredients(AdvRecipe recipe) {
        NonNullList<Ingredient> result = NonNullList.m_122779_();
        if (recipe.hidden) {
            return result;
        }

        int mask = recipe.masks[0];
        int compactIndex = 0;
        for (int y = 0; y < recipe.inputHeight; y++) {
            for (int x = 0; x < recipe.inputWidth; x++) {
                int gridIndex = y * 3 + x;
                if (((mask >>> (8 - gridIndex)) & 1) != 0) {
                    IRecipeInput input = recipe.input[compactIndex++];
                    result.add(input.getIngredient());
                } else {
                    result.add(Ingredient.f_43901_);
                }
            }
        }
        return result;
    }
}
