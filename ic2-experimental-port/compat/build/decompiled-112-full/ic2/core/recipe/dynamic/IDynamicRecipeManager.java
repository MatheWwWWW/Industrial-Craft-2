/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.DynamicRecipe;
import ic2.core.recipe.dynamic.RecipeInputIngredient;
import ic2.core.recipe.dynamic.RecipeOutputIngredient;
import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IDynamicRecipeManager {
    public boolean addRecipe(DynamicRecipe var1, boolean var2);

    public boolean removeRecipe(Collection<RecipeInputIngredient> var1, Collection<RecipeOutputIngredient> var2);

    public DynamicRecipe apply(ItemStack[] var1, FluidStack[] var2, boolean var3);

    public Iterable<? extends DynamicRecipe> getRecipes();

    public boolean isIterable();
}

