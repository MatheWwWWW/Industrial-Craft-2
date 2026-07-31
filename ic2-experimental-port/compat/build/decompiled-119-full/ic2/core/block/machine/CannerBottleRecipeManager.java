/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class CannerBottleRecipeManager
implements ICannerBottleRecipeManager {
    private final List<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> recipes = new ArrayList<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>>();

    public boolean addRecipe(IRecipeInput iRecipeInput, IRecipeInput iRecipeInput2, ItemStack itemStack, boolean bl) {
        return this.addRecipe(new ICannerBottleRecipeManager.Input(iRecipeInput, iRecipeInput2), itemStack, null, bl);
    }

    @Override
    @Deprecated
    public void addRecipe(IRecipeInput iRecipeInput, IRecipeInput iRecipeInput2, ItemStack itemStack) {
        if (!this.addRecipe(iRecipeInput, iRecipeInput2, itemStack, false)) {
            throw new IllegalStateException("ambiguous canner bottle recipe: " + iRecipeInput + " + " + iRecipeInput2 + " -> " + itemStack);
        }
    }

    public boolean addRecipe(ICannerBottleRecipeManager.Input input, ItemStack itemStack, CompoundTag compoundTag, boolean bl) {
        Iterator<MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>> iterator = this.recipes.iterator();
        block0: while (iterator.hasNext()) {
            MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> machineRecipe = iterator.next();
            for (ItemStack itemStack2 : input.container.getInputs()) {
                for (ItemStack itemStack3 : input.fill.getInputs()) {
                    if (!machineRecipe.getInput().matches(itemStack2, itemStack3)) continue;
                    if (bl) {
                        iterator.remove();
                        continue block0;
                    }
                    IC2.log.warn(LogCategory.Recipe, "ambiguous recipe: [" + input.container.getInputs() + "+" + input.fill.getInputs() + " -> " + itemStack + "], conflicts with [" + machineRecipe.getInput().container.getInputs() + "+" + machineRecipe.getInput().fill.getInputs() + " -> " + machineRecipe.getOutput() + "]");
                    return false;
                }
            }
        }
        this.recipes.add(new MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack>(input, itemStack));
        return true;
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack itemStack, ItemStack itemStack2, boolean bl, boolean bl2) {
        if (bl2 ? StackUtil.isEmpty(itemStack) && StackUtil.isEmpty(itemStack2) : StackUtil.isEmpty(itemStack) || StackUtil.isEmpty(itemStack2)) {
            return null;
        }
        for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> machineRecipe : this.recipes) {
            ICannerBottleRecipeManager.Input input = machineRecipe.getInput();
            if (bl2 && StackUtil.isEmpty(itemStack)) {
                if (!input.fill.matches(itemStack2)) continue;
                return new RecipeOutput(null, machineRecipe.getOutput());
            }
            if (bl2 && StackUtil.isEmpty(itemStack2)) {
                if (!input.container.matches(itemStack)) continue;
                return new RecipeOutput(null, machineRecipe.getOutput());
            }
            if (!input.matches(itemStack, itemStack2)) continue;
            if (!bl2 && (StackUtil.isEmpty(itemStack) || StackUtil.getSize(itemStack) < input.container.getAmount() || StackUtil.getSize(itemStack2) < input.fill.getAmount())) break;
            if (bl) {
                if (!StackUtil.isEmpty(itemStack)) {
                    itemStack.m_41774_(input.container.getAmount());
                }
                itemStack2.m_41774_(input.fill.getAmount());
            }
            new RecipeOutput(null, machineRecipe.getOutput());
            break;
        }
        return null;
    }

    @Override
    public MachineRecipeResult<ICannerBottleRecipeManager.Input, ItemStack, ICannerBottleRecipeManager.RawInput> apply(ICannerBottleRecipeManager.RawInput rawInput, boolean bl) {
        boolean bl2 = StackUtil.isEmpty(rawInput.container);
        boolean bl3 = StackUtil.isEmpty(rawInput.fill);
        if (!bl && (bl2 || bl3)) {
            return null;
        }
        if (bl && bl2 && bl3) {
            return null;
        }
        for (MachineRecipe<ICannerBottleRecipeManager.Input, ItemStack> machineRecipe : this.recipes) {
            if (!bl2 && (!machineRecipe.getInput().container.matches(rawInput.container) || machineRecipe.getInput().container.getAmount() > StackUtil.getSize(rawInput.container)) || !bl3 && (!machineRecipe.getInput().fill.matches(rawInput.fill) || machineRecipe.getInput().fill.getAmount() > StackUtil.getSize(rawInput.fill))) continue;
            return machineRecipe.getResult(new ICannerBottleRecipeManager.RawInput(bl2 ? StackUtil.emptyStack : StackUtil.copyShrunk(rawInput.container, machineRecipe.getInput().container.getAmount()), bl3 ? StackUtil.emptyStack : StackUtil.copyShrunk(rawInput.fill, machineRecipe.getInput().fill.getAmount())));
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

