/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.IRecipeHandler
 *  mezz.jei.api.recipe.IRecipeWrapper
 */
package ic2.jeiIntegration.recipe.crafting;

import ic2.core.recipe.GradualRecipe;
import ic2.jeiIntegration.recipe.crafting.GradualRecipeWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class GradualRecipeHandler
implements IRecipeHandler<GradualRecipe> {
    public Class<GradualRecipe> getRecipeClass() {
        return GradualRecipe.class;
    }

    public String getRecipeCategoryUid(GradualRecipe recipe) {
        return "minecraft.crafting";
    }

    public IRecipeWrapper getRecipeWrapper(GradualRecipe recipe) {
        return new GradualRecipeWrapper(recipe);
    }

    public boolean isRecipeValid(GradualRecipe recipe) {
        return recipe.canShow() && recipe.chargeMaterial != null;
    }
}

