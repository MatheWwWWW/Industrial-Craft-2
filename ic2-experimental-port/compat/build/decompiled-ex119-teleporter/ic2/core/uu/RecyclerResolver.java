/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.uu;

import ic2.api.recipe.Recipes;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import ic2.core.ref.Ic2Items;
import ic2.core.util.StackUtil;
import ic2.core.uu.ILateRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class RecyclerResolver
implements ILateRecipeResolver {
    private static final double transformCost = 55.0 * (double)TileEntityRecycler.recycleChance() / 4000.0 * 107.0;

    @Override
    public List<RecipeTransformation> getTransformations(Iterable<LeanItemStack> iterable) {
        ArrayList<LeanItemStack> arrayList = new ArrayList<LeanItemStack>();
        for (LeanItemStack leanItemStack : iterable) {
            ItemStack itemStack = leanItemStack.toMcStack();
            if (StackUtil.isEmpty(itemStack) || ((Collection)Recipes.recycler.apply(itemStack, false).getOutput()).isEmpty()) continue;
            arrayList.add(new LeanItemStack(itemStack, TileEntityRecycler.recycleChance()));
        }
        return Arrays.asList(new RecipeTransformation(transformCost, Collections.singletonList(arrayList), new LeanItemStack(Ic2Items.SCRAP)));
    }
}

