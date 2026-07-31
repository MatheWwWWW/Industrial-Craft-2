/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  mezz.jei.api.IGuiHelper
 *  mezz.jei.api.ingredients.IIngredients
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.fml.common.registry.ForgeRegistries
 */
package ic2.jeiIntegration.recipe.machine;

import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.Recipes;
import ic2.core.ref.TeBlock;
import ic2.jeiIntegration.recipe.machine.DynamicCategory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RecyclerCategory
extends DynamicCategory<IBasicMachineRecipeManager> {
    private final List<List<ItemStack>> trueInputs;

    public RecyclerCategory(IGuiHelper guiHelper) {
        super(TeBlock.recycler, Recipes.recycler, guiHelper);
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        if (Recipes.recyclerWhitelist.isEmpty()) {
            for (Item i : ForgeRegistries.ITEMS) {
                ItemStack stack = new ItemStack(i, 1, Short.MAX_VALUE);
                if (Recipes.recyclerBlacklist.contains(stack)) continue;
                items.add(stack);
            }
        } else {
            for (IRecipeInput stack : Recipes.recyclerWhitelist) {
                items.addAll(stack.getInputs());
            }
        }
        this.trueInputs = Collections.singletonList(items);
    }

    @Override
    protected List<List<ItemStack>> getInputStacks(IIngredients wrapper) {
        return this.trueInputs;
    }
}

