/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.RecipeIngredient;

public abstract class RecipeOutputIngredient<T>
extends RecipeIngredient<T> {
    protected RecipeOutputIngredient(T ingredient) {
        super(ingredient);
    }

    public abstract RecipeOutputIngredient<T> copy();
}

