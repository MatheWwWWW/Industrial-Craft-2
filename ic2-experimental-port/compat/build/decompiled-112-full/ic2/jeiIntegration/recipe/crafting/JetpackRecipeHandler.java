/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.IRecipeHandler
 *  mezz.jei.api.recipe.IRecipeWrapper
 */
package ic2.jeiIntegration.recipe.crafting;

import ic2.jeiIntegration.recipe.crafting.JetpackRecipeWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class JetpackRecipeHandler
implements IRecipeHandler<JetpackRecipeWrapper> {
    public Class<JetpackRecipeWrapper> getRecipeClass() {
        return JetpackRecipeWrapper.class;
    }

    public String getRecipeCategoryUid(JetpackRecipeWrapper recipe) {
        return "minecraft.crafting";
    }

    public IRecipeWrapper getRecipeWrapper(JetpackRecipeWrapper wrapper) {
        return wrapper;
    }

    public boolean isRecipeValid(JetpackRecipeWrapper recipe) {
        return true;
    }
}

