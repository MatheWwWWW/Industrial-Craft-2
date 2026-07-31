/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.NonNullList
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.Recipe
 */
package ic2.core.uu;

import ic2.core.IC2;
import ic2.core.util.StackUtil;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class RecipeResolver
implements IRecipeResolver {
    private static final double transformCost = 1.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> arrayList = new ArrayList<RecipeTransformation>();
        for (Recipe recipe : IC2.sideProxy.getRecipeManager().m_44051_()) {
            NonNullList nonNullList = recipe.m_7527_();
            ItemStack itemStack = recipe.m_8043_();
            if (StackUtil.isEmpty(itemStack) || nonNullList.isEmpty()) continue;
            arrayList.add(new RecipeTransformation(1.0, RecipeResolver.toDoubleStackList((List<Ingredient>)nonNullList), new LeanItemStack(itemStack)));
        }
        return arrayList;
    }

    private static List<List<LeanItemStack>> toDoubleStackList(List<Ingredient> list) {
        ArrayList<List<LeanItemStack>> arrayList = new ArrayList<List<LeanItemStack>>(list.size());
        for (Ingredient ingredient : list) {
            ItemStack[] itemStackArray = ingredient.m_43908_();
            ArrayList<LeanItemStack> arrayList2 = new ArrayList<LeanItemStack>(itemStackArray.length);
            for (ItemStack itemStack : itemStackArray) {
                arrayList2.add(new LeanItemStack(itemStack));
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }
}

