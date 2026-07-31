/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.ingredients.IIngredients
 *  mezz.jei.api.recipe.BlankRecipeWrapper
 *  net.minecraft.item.ItemStack
 */
package ic2.jeiIntegration.recipe.crafting;

import ic2.api.recipe.IRecipeInput;
import ic2.core.recipe.AdvShapelessRecipe;
import ic2.core.ref.ItemName;
import ic2.core.util.Ic2Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class AdvShapelessRecipeWrapper
extends BlankRecipeWrapper {
    private final AdvShapelessRecipe recipe;

    public AdvShapelessRecipeWrapper(AdvShapelessRecipe recipe) {
        this.recipe = recipe;
    }

    public List<List<ItemStack>> getInputs() {
        ItemStack stack;
        ArrayList<List<ItemStack>> ret = new ArrayList<List<ItemStack>>(this.recipe.input.length);
        for (IRecipeInput input : this.recipe.input) {
            ret.add(input.getInputs());
        }
        if (ret.size() == 1 && ((List)ret.get(0)).size() == 1 && (stack = (ItemStack)((List)ret.get(0)).get(0)).func_77973_b() == ItemName.painter.getInstance() && stack.func_77960_j() == Short.MAX_VALUE) {
            ret.set(0, Arrays.stream(Ic2Color.values).map(ItemName.painter::getItemStack).collect(Collectors.toList()));
        }
        return ret;
    }

    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, this.getInputs());
        ingredients.setOutput(ItemStack.class, (Object)this.recipe.func_77571_b());
    }
}

