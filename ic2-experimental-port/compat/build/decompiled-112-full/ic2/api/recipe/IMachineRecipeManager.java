/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.api.recipe;

import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import net.minecraft.nbt.NBTTagCompound;

public interface IMachineRecipeManager<RI, RO, I> {
    public boolean addRecipe(RI var1, RO var2, NBTTagCompound var3, boolean var4);

    public MachineRecipeResult<RI, RO, I> apply(I var1, boolean var2);

    public Iterable<? extends MachineRecipe<RI, RO>> getRecipes();

    public boolean isIterable();
}

