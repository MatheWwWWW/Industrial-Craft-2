/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.recipe.IRecipeHandler
 *  mezz.jei.api.recipe.IRecipeWrapper
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.core.util.StackUtil;
import ic2.jeiIntegration.recipe.machine.CannerCanningWrapper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CannerCanningHandler
implements IRecipeHandler<CannerCanningWrapper> {
    public Class<CannerCanningWrapper> getRecipeClass() {
        return CannerCanningWrapper.class;
    }

    public String getRecipeCategoryUid(CannerCanningWrapper recipe) {
        return recipe.category.getUid();
    }

    public IRecipeWrapper getRecipeWrapper(CannerCanningWrapper recipe) {
        return recipe;
    }

    public boolean isRecipeValid(CannerCanningWrapper recipe) {
        return !recipe.getInput().isEmpty() && !recipe.getCan().isEmpty() && !StackUtil.isEmpty(recipe.getOutput());
    }
}

