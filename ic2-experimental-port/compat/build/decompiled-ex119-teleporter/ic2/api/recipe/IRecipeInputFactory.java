/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.api.recipe;

import ic2.api.recipe.IRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;

@Deprecated(forRemoval=true)
public interface IRecipeInputFactory {
    public IRecipeInput forItem(ItemLike var1);

    public IRecipeInput forStack(ItemStack var1);

    public IRecipeInput forStack(ItemStack var1, int var2);

    public IRecipeInput forTag(String var1, int var2);

    public IRecipeInput forFluidContainer(Fluid var1, int var2);

    public IRecipeInput forIngredient(Ingredient var1, int var2);

    @Deprecated
    public Ingredient getIngredient(IRecipeInput var1);
}

