/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.recipes.registries;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.api.recipes.registries.IRecipeFilter;

public interface IRecyclerRecipeList
extends IMachineRecipeList {
    public IRecipeFilter getBlackList();
}

