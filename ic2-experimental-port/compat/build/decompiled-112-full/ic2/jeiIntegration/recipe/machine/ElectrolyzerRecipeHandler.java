/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.IRecipeHandler
 *  mezz.jei.api.recipe.IRecipeWrapper
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.jeiIntegration.recipe.machine.ElectrolyzerWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ElectrolyzerRecipeHandler
implements IRecipeHandler<ElectrolyzerWrapper> {
    public Class<ElectrolyzerWrapper> getRecipeClass() {
        return ElectrolyzerWrapper.class;
    }

    public String getRecipeCategoryUid(ElectrolyzerWrapper recipe) {
        return recipe.category.getUid();
    }

    public IRecipeWrapper getRecipeWrapper(ElectrolyzerWrapper recipe) {
        return recipe;
    }

    public boolean isRecipeValid(ElectrolyzerWrapper recipe) {
        return recipe.getFluidInput() != null && !recipe.getFluidOutputs().isEmpty();
    }
}

