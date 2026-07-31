/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.recipe;

import ic2.api.recipe.ICraftingRecipeManager;
import ic2.core.recipe.AdvRecipe;
import ic2.core.recipe.AdvShapelessRecipe;
import ic2.core.recipe.GradualRecipe;
import net.minecraft.item.ItemStack;

public class AdvCraftingRecipeManager
implements ICraftingRecipeManager {
    @Override
    public void addRecipe(ItemStack output, Object ... input) {
        AdvRecipe.addAndRegister(output, input);
    }

    @Override
    public void addShapelessRecipe(ItemStack output, Object ... input) {
        AdvShapelessRecipe.addAndRegister(output, input);
    }

    public void addGradualRecipe(ItemStack output, int amount, Object ... args) {
        GradualRecipe.addAndRegister(output, amount, args);
    }
}

