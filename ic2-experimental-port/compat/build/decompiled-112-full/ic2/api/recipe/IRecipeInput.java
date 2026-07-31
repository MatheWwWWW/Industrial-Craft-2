/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.Ingredient
 */
package ic2.api.recipe;

import ic2.api.recipe.Recipes;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public interface IRecipeInput {
    public boolean matches(ItemStack var1);

    public int getAmount();

    public List<ItemStack> getInputs();

    default public Ingredient getIngredient() {
        return Recipes.inputFactory.getIngredient(this);
    }
}

