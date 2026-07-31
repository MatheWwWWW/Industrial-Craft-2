/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.recipe;

import ic2.api.recipe.IListRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.core.IC2;
import ic2.core.recipe.MachineRecipeHelper;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BasicListRecipeManager
extends MachineRecipeHelper<IRecipeInput, Object>
implements IListRecipeManager {
    private static final Object dummyOutput = new Object();

    @Override
    public void add(IRecipeInput input) {
        if (input == null) {
            throw new NullPointerException("Input must not be null.");
        }
        this.addRecipe(input, dummyOutput, (NBTTagCompound)null, false);
    }

    @Override
    public boolean contains(ItemStack stack) {
        if (StackUtil.isEmpty(stack)) {
            return false;
        }
        return this.getRecipe(stack) != null;
    }

    @Override
    public boolean isEmpty() {
        return this.recipes.isEmpty();
    }

    @Override
    public List<IRecipeInput> getInputs() {
        return new ArrayList<IRecipeInput>(this.recipes.keySet());
    }

    @Override
    public Iterator<IRecipeInput> iterator() {
        return this.recipes.keySet().iterator();
    }

    @Override
    public boolean addRecipe(IRecipeInput input, Object output, NBTTagCompound metadata, boolean replace) {
        for (ItemStack is : input.getInputs()) {
            MachineRecipe recipe = this.getRecipe(is);
            if (recipe == null) continue;
            if (replace) {
                do {
                    this.recipes.remove(input);
                    this.removeCachedRecipes(input);
                } while ((recipe = this.getRecipe(is)) != null);
                continue;
            }
            IC2.log.debug(LogCategory.Recipe, "Skipping %s due to duplicate recipe for %s (%s)", input, is, recipe.getInput());
            return false;
        }
        MachineRecipe<IRecipeInput, Object> recipe = new MachineRecipe<IRecipeInput, Object>(input, output, metadata);
        this.recipes.put(input, recipe);
        this.addToCache(recipe);
        return false;
    }

    @Override
    protected IRecipeInput getForInput(IRecipeInput input) {
        return input;
    }

    @Override
    protected boolean consumeContainer(ItemStack input, ItemStack container, MachineRecipe<IRecipeInput, Object> recipe) {
        return true;
    }
}

