/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.IIngredients
 *  mezz.jei.api.recipe.BlankRecipeWrapper
 *  net.minecraft.item.ItemStack
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.jeiIntegration.recipe.machine.IORecipeCategory;
import java.util.Arrays;
import java.util.List;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CannerCanningWrapper
extends BlankRecipeWrapper {
    private final IRecipeInput input;
    private final IRecipeInput can;
    private final ItemStack output;
    final IORecipeCategory<ICannerBottleRecipeManager> category;

    CannerCanningWrapper(ICannerBottleRecipeManager.Input input, ItemStack output, IORecipeCategory<ICannerBottleRecipeManager> category) {
        this.input = input.fill;
        this.can = input.container;
        this.output = output;
        this.category = category;
    }

    public List<ItemStack> getInput() {
        return this.input.getInputs();
    }

    public List<ItemStack> getCan() {
        return this.can.getInputs();
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, Arrays.asList(this.getInput(), this.getCan()));
        ingredients.setOutput(ItemStack.class, (Object)this.getOutput());
    }
}

