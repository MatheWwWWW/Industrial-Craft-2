/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import ic2.jeiIntegration.recipe.machine.IORecipeWrapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.item.ItemStack;

public class AdvancedIORecipeWrapper
extends IORecipeWrapper {
    private final IRecipeInput secondary;

    AdvancedIORecipeWrapper(MachineRecipe<IRecipeInput, Collection<ItemStack>> container, IRecipeInput input, IORecipeCategory<?> category) {
        super(container, category);
        this.secondary = input;
    }

    @Override
    public List<List<ItemStack>> getInputs() {
        ArrayList<List<ItemStack>> list = new ArrayList<List<ItemStack>>(2);
        list.addAll(super.getInputs());
        list.add(this.secondary.getInputs());
        return list;
    }
}

