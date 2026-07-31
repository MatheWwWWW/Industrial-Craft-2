/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraft.item.crafting.FurnaceRecipes
 */
package ic2.core.uu;

import ic2.core.IC2;
import ic2.core.recipe.AdvRecipe;
import ic2.core.util.LogCategory;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import ic2.core.uu.RecipeUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class VanillaSmeltingResolver
implements IRecipeResolver {
    private static final double transformCost = 14.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> ret = new ArrayList<RecipeTransformation>();
        for (Map.Entry entry : FurnaceRecipes.func_77602_a().func_77599_b().entrySet()) {
            try {
                List<List<LeanItemStack>> inputs = RecipeUtil.convertIngredients(AdvRecipe.expand(entry.getKey()));
                LeanItemStack output = new LeanItemStack((ItemStack)entry.getValue());
                ret.add(new RecipeTransformation(14.0, inputs, output));
            }
            catch (IllegalArgumentException e) {
                IC2.log.warn(LogCategory.Uu, e, "invalid recipe");
            }
        }
        return ret;
    }
}

