/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.IRecipeHandler
 *  mezz.jei.api.recipe.IRecipeWrapper
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.jeiIntegration.recipe.machine.CannerEnrichmentWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CannerEnrichmentHandler
implements IRecipeHandler<CannerEnrichmentWrapper> {
    public Class<CannerEnrichmentWrapper> getRecipeClass() {
        return CannerEnrichmentWrapper.class;
    }

    public String getRecipeCategoryUid(CannerEnrichmentWrapper recipe) {
        return recipe.category.getUid();
    }

    public IRecipeWrapper getRecipeWrapper(CannerEnrichmentWrapper recipe) {
        return recipe;
    }

    public boolean isRecipeValid(CannerEnrichmentWrapper recipe) {
        return !recipe.getAdditives().isEmpty() && recipe.getInput() != null && recipe.getOutput() != null;
    }
}

