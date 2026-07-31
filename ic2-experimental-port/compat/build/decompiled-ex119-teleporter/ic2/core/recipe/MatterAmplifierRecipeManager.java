/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.recipe;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.MachineRecipeResult;
import ic2.core.util.StackUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class MatterAmplifierRecipeManager
implements IMachineRecipeManager<IRecipeInput, Integer, ItemStack> {
    private final List<MachineRecipe<IRecipeInput, Integer>> recipes = new ArrayList<MachineRecipe<IRecipeInput, Integer>>();

    public boolean addRecipe(IRecipeInput iRecipeInput, Integer n, CompoundTag compoundTag, boolean bl) {
        if (n <= 0) {
            throw new IllegalArgumentException("non-positive amplification");
        }
        for (ItemStack itemStack : iRecipeInput.getInputs()) {
            MachineRecipe<IRecipeInput, Integer> machineRecipe = this.getRecipe(itemStack, true);
            if (machineRecipe == null) continue;
            if (!bl) {
                return false;
            }
            this.recipes.remove(machineRecipe);
        }
        this.recipes.add(new MachineRecipe<IRecipeInput, Integer>(iRecipeInput, n));
        return true;
    }

    @Override
    public MachineRecipeResult<IRecipeInput, Integer, ItemStack> apply(ItemStack itemStack, boolean bl) {
        MachineRecipe<IRecipeInput, Integer> machineRecipe = this.getRecipe(itemStack, bl);
        if (machineRecipe == null) {
            return null;
        }
        return machineRecipe.getResult(StackUtil.copyShrunk(itemStack, machineRecipe.getInput().getAmount()));
    }

    private MachineRecipe<IRecipeInput, Integer> getRecipe(ItemStack itemStack, boolean bl) {
        for (MachineRecipe<IRecipeInput, Integer> machineRecipe : this.recipes) {
            if (!machineRecipe.getInput().matches(itemStack) || !bl && machineRecipe.getInput().getAmount() > StackUtil.getSize(itemStack)) continue;
            return machineRecipe;
        }
        return null;
    }

    @Override
    public Iterable<? extends MachineRecipe<IRecipeInput, Integer>> getRecipes() {
        return this.recipes;
    }

    @Override
    public boolean isIterable() {
        return true;
    }
}

