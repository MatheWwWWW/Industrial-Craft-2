/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import java.util.Collection;
import net.minecraft.world.item.ItemStack;

public interface IBasicMachineRecipeManager
extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> {
    @Deprecated
    public RecipeOutput getOutputFor(ItemStack var1, boolean var2);
}

