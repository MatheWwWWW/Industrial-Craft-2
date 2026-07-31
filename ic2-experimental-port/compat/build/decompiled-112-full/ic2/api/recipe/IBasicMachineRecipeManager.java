/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.api.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import java.util.Collection;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IBasicMachineRecipeManager
extends IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ItemStack> {
    public boolean addRecipe(IRecipeInput var1, NBTTagCompound var2, boolean var3, ItemStack ... var4);

    @Deprecated
    public RecipeOutput getOutputFor(ItemStack var1, boolean var2);
}

