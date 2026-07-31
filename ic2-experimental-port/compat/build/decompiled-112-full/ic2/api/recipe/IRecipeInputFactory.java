/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.Ingredient
 *  net.minecraftforge.fluids.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.IRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;

public interface IRecipeInputFactory {
    public IRecipeInput forStack(ItemStack var1);

    public IRecipeInput forStack(ItemStack var1, int var2);

    public IRecipeInput forExactStack(ItemStack var1);

    public IRecipeInput forExactStack(ItemStack var1, int var2);

    public IRecipeInput forOreDict(String var1);

    public IRecipeInput forOreDict(String var1, int var2);

    public IRecipeInput forOreDict(String var1, int var2, int var3);

    public IRecipeInput forFluidContainer(Fluid var1);

    public IRecipeInput forFluidContainer(Fluid var1, int var2);

    public IRecipeInput forAny(IRecipeInput ... var1);

    public IRecipeInput forAny(Iterable<IRecipeInput> var1);

    public IRecipeInput forIngredient(Ingredient var1);

    public Ingredient getIngredient(IRecipeInput var1);
}

