/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.recipe;

import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;

public interface IMachineRecipeManager<RI, RO, I> {
    public MachineRecipeResult<RI, RO, I> apply(I var1, boolean var2);

    public Iterable<? extends MachineRecipe<RI, RO>> getRecipes();

    public boolean isIterable();
}

