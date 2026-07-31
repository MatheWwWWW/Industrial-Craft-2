/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.item.ItemStack
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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class BasicMachineRecipeManager
extends MachineRecipeHelper<IRecipeInput, Collection<ItemStack>>
implements IBasicMachineRecipeManager {
    @Override
    protected IRecipeInput getForInput(IRecipeInput iRecipeInput) {
        return iRecipeInput;
    }

    @Override
    protected boolean consumeContainer(ItemStack itemStack, ItemStack itemStack2, MachineRecipe<IRecipeInput, Collection<ItemStack>> machineRecipe) {
        for (ItemStack itemStack3 : machineRecipe.getOutput()) {
            if (StackUtil.checkItemEqualityStrict(itemStack2, itemStack3)) {
                return true;
            }
            if (!IC2.envProxy.hasRecipeRemainder(itemStack3) || !StackUtil.checkItemEqualityStrict(itemStack, IC2.envProxy.getRecipeRemainder(itemStack3))) continue;
            return true;
        }
        return false;
    }

    public boolean addRecipe(IRecipeInput iRecipeInput, CompoundTag compoundTag, boolean bl, ItemStack ... itemStackArray) {
        return this.addRecipe(iRecipeInput, Arrays.asList(itemStackArray), compoundTag, bl);
    }

    public boolean addRecipe(IRecipeInput iRecipeInput, Collection<ItemStack> collection, CompoundTag compoundTag, boolean bl) {
        if (iRecipeInput == null) {
            throw new NullPointerException("null recipe input");
        }
        if (collection == null) {
            throw new NullPointerException("null recipe output");
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("no outputs");
        }
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>(collection.size());
        for (ItemStack itemStack : collection) {
            if (StackUtil.isEmpty(itemStack)) {
                this.displayError("The output ItemStack " + StackUtil.toStringSafe(itemStack) + " is invalid.");
                return false;
            }
            if (iRecipeInput.matches(itemStack) && (compoundTag == null || !compoundTag.m_128441_("ignoreSameInputOutput"))) {
                this.displayError("The output ItemStack " + itemStack.toString() + " is the same as the recipe input " + iRecipeInput + ".");
                return false;
            }
            arrayList.add(itemStack.m_41777_());
        }
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
            IC2.log.debug(LogCategory.Recipe, "Skipping %s => %s due to duplicate recipe for %s (%s => %s)", iRecipeInput, collection, itemStack, machineRecipe.getInput(), machineRecipe.getOutput());
            return false;
        }
        MachineRecipe machineRecipe = new MachineRecipe(iRecipeInput, arrayList, compoundTag);
        this.recipes.put(iRecipeInput, machineRecipe);
        this.addToCache(machineRecipe);
        return true;
    }

    @Override
    public RecipeOutput getOutputFor(ItemStack itemStack, boolean bl) {
        MachineRecipe machineRecipe = this.getRecipe(itemStack);
        if (machineRecipe == null) {
            return null;
        }
        if (!(StackUtil.getSize(itemStack) < ((IRecipeInput)machineRecipe.getInput()).getAmount() || IC2.envProxy.hasRecipeRemainder(itemStack) && StackUtil.getSize(itemStack) != ((IRecipeInput)machineRecipe.getInput()).getAmount())) {
            if (bl) {
                if (IC2.envProxy.hasRecipeRemainder(itemStack)) {
                    throw new UnsupportedOperationException("can't adjust input item, use apply() instead");
                }
                itemStack.m_41774_(((IRecipeInput)machineRecipe.getInput()).getAmount());
            }
            return new RecipeOutput(machineRecipe.getMetaData(), new ArrayList<ItemStack>((Collection)machineRecipe.getOutput()));
        }
        return null;
    }

    public void removeRecipe(ItemStack itemStack, Collection<ItemStack> collection) {
        MachineRecipe machineRecipe = this.getRecipe(itemStack);
        if (machineRecipe != null && BasicMachineRecipeManager.checkListEquality((Collection)machineRecipe.getOutput(), collection)) {
            this.recipes.remove(machineRecipe.getInput());
            this.removeCachedRecipes((IRecipeInput)machineRecipe.getInput());
        }
    }

    private static boolean checkListEquality(Collection<ItemStack> collection, Collection<ItemStack> collection2) {
        if (collection.size() != collection2.size()) {
            return false;
        }
        ListIterator<ItemStack> listIterator = new ArrayList<ItemStack>(collection2).listIterator();
        block0: for (ItemStack itemStack : collection) {
            while (listIterator.hasNext()) {
                if (!StackUtil.checkItemEqualityStrict(itemStack, listIterator.next())) continue;
                listIterator.remove();
                while (listIterator.hasPrevious()) {
                    listIterator.previous();
                }
                continue block0;
            }
            return false;
        }
        return true;
    }

    private void displayError(String string) {
        if (!MainConfig.ignoreInvalidRecipes) {
            throw new RuntimeException(string);
        }
        IC2.log.warn(LogCategory.Recipe, string);
    }
}

