/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.recipe;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.recipe.MachineRecipeHelper;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.ListIterator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BasicMachineRecipeManager
extends MachineRecipeHelper<IRecipeInput, Collection<ItemStack>>
implements IBasicMachineRecipeManager {
    @Override
    protected IRecipeInput getForInput(IRecipeInput input) {
        return input;
    }

    @Override
    protected boolean consumeContainer(ItemStack input, ItemStack inContainer, MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe) {
        for (ItemStack output : recipe.getOutput()) {
            if (StackUtil.checkItemEqualityStrict(inContainer, output)) {
                return true;
            }
            if (!output.func_77973_b().hasContainerItem(output) || !StackUtil.checkItemEqualityStrict(input, output.func_77973_b().getContainerItem(output))) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean replace, ItemStack ... outputs) {
        return this.addRecipe(input, (Collection<ItemStack>)Arrays.asList(outputs), metadata, replace);
    }

    @Override
    public boolean addRecipe(IRecipeInput input, Collection<ItemStack> output, NBTTagCompound metadata, boolean replace) {
        if (input == null) {
            throw new NullPointerException("null recipe input");
        }
        if (output == null) {
            throw new NullPointerException("null recipe output");
        }
        if (output.isEmpty()) {
            throw new IllegalArgumentException("no outputs");
        }
        ArrayList<ItemStack> items = new ArrayList<ItemStack>(output.size());
        for (ItemStack stack : output) {
            if (StackUtil.isEmpty(stack)) {
                this.displayError("The output ItemStack " + StackUtil.toStringSafe(stack) + " is invalid.");
                return false;
            }
            if (input.matches(stack) && (metadata == null || !metadata.func_74764_b("ignoreSameInputOutput"))) {
                this.displayError("The output ItemStack " + stack.toString() + " is the same as the recipe input " + input + ".");
                return false;
            }
            items.add(stack.func_77946_l());
        }
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
            IC2.log.debug(LogCategory.Recipe, "Skipping %s => %s due to duplicate recipe for %s (%s => %s)", input, output, is, recipe.getInput(), recipe.getOutput());
            return false;
        }
        MachineRecipe recipe = new MachineRecipe(input, items, metadata);
        this.recipes.put(input, recipe);
        this.addToCache(recipe);
        return true;
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        MachineRecipe recipe = this.getRecipe(input);
        if (recipe == null) {
            return null;
        }
        if (!(StackUtil.getSize(input) < ((IRecipeInput)recipe.getInput()).getAmount() || input.func_77973_b().hasContainerItem(input) && StackUtil.getSize(input) != ((IRecipeInput)recipe.getInput()).getAmount())) {
            if (adjustInput) {
                if (input.func_77973_b().hasContainerItem(input)) {
                    throw new UnsupportedOperationException("can't adjust input item, use apply() instead");
                }
                input.func_190918_g(((IRecipeInput)recipe.getInput()).getAmount());
            }
            return new RecipeOutput(recipe.getMetaData(), new ArrayList<ItemStack>((Collection)recipe.getOutput()));
        }
        return null;
    }

    public void removeRecipe(ItemStack input, Collection<ItemStack> output) {
        MachineRecipe recipe = this.getRecipe(input);
        if (recipe != null && BasicMachineRecipeManager.checkListEquality((Collection)recipe.getOutput(), output)) {
            this.recipes.remove(recipe.getInput());
            this.removeCachedRecipes(recipe.getInput());
        }
    }

    private static boolean checkListEquality(Collection<ItemStack> a, Collection<ItemStack> b) {
        if (a.size() != b.size()) {
            return false;
        }
        ListIterator<ItemStack> itB = new ArrayList<ItemStack>(b).listIterator();
        block0: for (ItemStack stack : a) {
            while (itB.hasNext()) {
                if (!StackUtil.checkItemEqualityStrict(stack, itB.next())) continue;
                itB.remove();
                while (itB.hasPrevious()) {
                    itB.previous();
                }
                continue block0;
            }
            return false;
        }
        return true;
    }

    private void displayError(String msg) {
        if (!MainConfig.ignoreInvalidRecipes) {
            throw new RuntimeException(msg);
        }
        IC2.log.warn(LogCategory.Recipe, msg);
    }
}

