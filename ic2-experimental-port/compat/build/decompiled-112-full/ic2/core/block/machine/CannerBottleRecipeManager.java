/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.nbt.NBTTagCompound
 */
package ic2.core.block.machine;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CannerBottleRecipeManager
implements ICannerBottleRecipeManager {
    private final List<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> recipes = new ArrayList<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>>();

    @Override
    public boolean addRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output, boolean replace) {
        return this.addRecipe(new ICannerBottleRecipeManager.Input(container, fill), output, (NBTTagCompound)null, replace);
    }

    @Override
    @Deprecated
    public void addRecipe(IRecipeInput container, IRecipeInput fill, ItemStack output) {
        if (!this.addRecipe(container, fill, output, false)) {
            throw new IllegalStateException("ambiguous canner bottle recipe: " + container + " + " + fill + " -> " + output);
        }
    }

    @Override
    public boolean addRecipe(ICannerBottleRecipeManager.Input input, ItemStack output, NBTTagCompound metadata, boolean replace) {
        Iterator<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> it = this.recipes.iterator();
        block0: while (it.hasNext()) {
            MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe = it.next();
            for (ItemStack containerStack : input.container.getInputs()) {
                for (ItemStack fillStack : input.fill.getInputs()) {
                    if (!recipe.getInput().matches(containerStack, fillStack)) continue;
                    if (replace) {
                        it.remove();
                        continue block0;
                    }
                    IC2.log.warn(LogCategory.Recipe, "ambiguous recipe: [" + input.container.getInputs() + "+" + input.fill.getInputs() + " -> " + output + "], conflicts with [" + recipe.getInput().container.getInputs() + "+" + recipe.getInput().fill.getInputs() + " -> " + recipe.getOutput() + "]");
                    return false;
                }
            }
        }
        this.recipes.add(new MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>(input, output));
        return true;
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack container, ItemStack fill, boolean adjustInput, boolean acceptTest) {
        if (acceptTest ? StackUtil.isEmpty(container) && StackUtil.isEmpty(fill) : StackUtil.isEmpty(container) || StackUtil.isEmpty(fill)) {
            return null;
        }
        for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe : this.recipes) {
            ICannerBottleRecipeManager.Input recipeInput = recipe.getInput();
            if (acceptTest && StackUtil.isEmpty(container)) {
                if (!recipeInput.fill.matches(fill)) continue;
                return new RecipeOutput(null, recipe.getOutput());
            }
            if (acceptTest && StackUtil.isEmpty(fill)) {
                if (!recipeInput.container.matches(container)) continue;
                return new RecipeOutput(null, recipe.getOutput());
            }
            if (!recipeInput.matches(container, fill)) continue;
            if (!acceptTest && (StackUtil.isEmpty(container) || StackUtil.getSize(container) < recipeInput.container.getAmount() || StackUtil.getSize(fill) < recipeInput.fill.getAmount())) break;
            if (adjustInput) {
                if (!StackUtil.isEmpty(container)) {
                    container.func_190918_g(recipeInput.container.getAmount());
                }
                fill.func_190918_g(recipeInput.fill.getAmount());
            }
            new RecipeOutput(null, recipe.getOutput());
            break;
        }
        return null;
    }

    @Override
    public MachineRecipeResult<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> apply(ICannerBottleRecipeManager.RawInput input, boolean acceptTest) {
        boolean emptyContainer = StackUtil.isEmpty(input.container);
        boolean emptyFill = StackUtil.isEmpty(input.fill);
        if (!acceptTest && (emptyContainer || emptyFill)) {
            return null;
        }
        if (acceptTest && emptyContainer && emptyFill) {
            return null;
        }
        for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> recipe : this.recipes) {
            if (!emptyContainer && (!recipe.getInput().container.matches(input.container) || recipe.getInput().container.getAmount() > StackUtil.getSize(input.container)) || !emptyFill && (!recipe.getInput().fill.matches(input.fill) || recipe.getInput().fill.getAmount() > StackUtil.getSize(input.fill))) continue;
            return recipe.getResult(new ICannerBottleRecipeManager.RawInput(emptyContainer ? StackUtil.emptyStack : StackUtil.copyShrunk(input.container, recipe.getInput().container.getAmount()), emptyFill ? StackUtil.emptyStack : StackUtil.copyShrunk(input.fill, recipe.getInput().fill.getAmount())));
        }
        return null;
    }

    @Override
    public Iterable<? extends MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> getRecipes() {
        return this.recipes;
    }

    @Override
    public boolean isIterable() {
        return true;
    }
}

