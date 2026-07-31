/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.integration.jei.recipe.machine;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class IORecipeWrapper {
    private final IRecipeInput input;
    private final Collection<ItemStack> output;

    public IORecipeWrapper(MachineRecipe<IRecipeInput, Collection<ItemStack>> machineRecipe) {
        this(machineRecipe.getInput(), machineRecipe.getOutput());
    }

    public IORecipeWrapper(IRecipeInput iRecipeInput, Collection<ItemStack> collection) {
        this.input = iRecipeInput;
        this.output = collection;
    }

    public List<List<ItemStack>> getInputs() {
        List<ItemStack> list = this.input.getInputs();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return Collections.singletonList(list);
    }

    public List<ItemStack> getOutputs() {
        return new ArrayList<ItemStack>(this.output);
    }
}

