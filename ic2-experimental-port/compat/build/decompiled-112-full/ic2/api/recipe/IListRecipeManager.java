/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.recipe;

import ic2.api.recipe.IRecipeInput;
import java.util.List;
import net.minecraft.item.ItemStack;

public interface IListRecipeManager
extends Iterable<IRecipeInput> {
    public void add(IRecipeInput var1);

    public boolean contains(ItemStack var1);

    public boolean isEmpty();

    public List<IRecipeInput> getInputs();
}

