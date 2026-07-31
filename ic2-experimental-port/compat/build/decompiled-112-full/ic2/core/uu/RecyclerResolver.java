/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.uu;

import ic2.api.recipe.Recipes;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import ic2.core.util.StackUtil;
import ic2.core.uu.ILateRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

public class RecyclerResolver
implements ILateRecipeResolver {
    private static final double transformCost = 55.0 * (double)TileEntityRecycler.recycleChance() / 4000.0 * 107.0;

    @Override
    public List<RecipeTransformation> getTransformations(Iterable<LeanItemStack> obtainableStacks) {
        ArrayList<LeanItemStack> input = new ArrayList<LeanItemStack>();
        for (LeanItemStack obtainableStack : obtainableStacks) {
            ItemStack stack = obtainableStack.toMcStack();
            if (StackUtil.isEmpty(stack) || ((Collection)Recipes.recycler.apply(stack, false).getOutput()).isEmpty()) continue;
            input.add(new LeanItemStack(stack, TileEntityRecycler.recycleChance()));
        }
        return Arrays.asList(new RecipeTransformation(transformCost, Collections.singletonList(input), new LeanItemStack(ItemName.crafting.getItemStack(CraftingItemType.scrap))));
    }
}

