/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BasicListRecipeManager
extends MachineRecipeHelper<IRecipeInput, Object>
implements IListRecipeManager {
    private static final Object dummyOutput = new Object();

    @Override
    public void add(IRecipeInput iRecipeInput) {
        if (iRecipeInput == null) {
            throw new NullPointerException("Input must not be null.");
        }
        this.addRecipe(iRecipeInput, dummyOutput, null, false);
    }

    @Override
    public boolean contains(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return false;
        }
        return this.getRecipe(itemStack) != null;
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

    public boolean addRecipe(IRecipeInput iRecipeInput, Object object, CompoundTag compoundTag, boolean bl) {
        for (ItemStack itemStack : iRecipeInput.getInputs()) {
            MachineRecipe machineRecipe = this.getRecipe(itemStack);
            if (machineRecipe == null) continue;
            if (bl) {
                do {
                    this.recipes.remove(iRecipeInput);
                    this.removeCachedRecipes(iRecipeInput);
                } while ((machineRecipe = this.getRecipe(itemStack)) != null);
                continue;
            }
            IC2.log.debug(LogCategory.Recipe, "Skipping %s due to duplicate recipe for %s (%s)", iRecipeInput, itemStack, machineRecipe.getInput());
            return false;
        }
        MachineRecipe<IRecipeInput, Object> machineRecipe = new MachineRecipe<IRecipeInput, Object>(iRecipeInput, object, compoundTag);
        this.recipes.put(iRecipeInput, machineRecipe);
        this.addToCache(machineRecipe);
        return false;
    }

    @Override
    protected IRecipeInput getForInput(IRecipeInput iRecipeInput) {
        return iRecipeInput;
    }

    @Override
    protected boolean consumeContainer(ItemStack itemStack, ItemStack itemStack2, MachineRecipe<IRecipeInput, Object> machineRecipe) {
        return true;
    }
}

