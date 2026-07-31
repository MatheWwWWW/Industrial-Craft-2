/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.IRecipe
 *  net.minecraft.item.crafting.Ingredient
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.fml.common.registry.ForgeRegistries
 */
package ic2.core.uu;

import ic2.core.util.StackUtil;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class RecipeResolver
implements IRecipeResolver {
    private static final double transformCost = 1.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> ret = new ArrayList<RecipeTransformation>();
        for (IRecipe irecipe : ForgeRegistries.RECIPES) {
            NonNullList inputs = irecipe.func_192400_c();
            ItemStack output = irecipe.func_77571_b();
            if (StackUtil.isEmpty(output) || inputs.isEmpty()) continue;
            ret.add(new RecipeTransformation(1.0, RecipeResolver.toDoubleStackList((List<Ingredient>)inputs), new LeanItemStack(output)));
        }
        return ret;
    }

    private static List<List<LeanItemStack>> toDoubleStackList(List<Ingredient> list) {
        ArrayList<List<LeanItemStack>> ret = new ArrayList<List<LeanItemStack>>(list.size());
        for (Ingredient ingredient : list) {
            ItemStack[] arr = ingredient.func_193365_a();
            ArrayList<LeanItemStack> toAdd = new ArrayList<LeanItemStack>(arr.length);
            for (ItemStack stack : arr) {
                toAdd.add(new LeanItemStack(stack));
            }
            ret.add(toAdd);
        }
        return ret;
    }
}

