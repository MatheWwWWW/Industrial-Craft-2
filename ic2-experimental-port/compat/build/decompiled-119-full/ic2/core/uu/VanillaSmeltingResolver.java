/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.crafting.Ingredient
 *  net.minecraft.world.item.crafting.RecipeType
 *  net.minecraft.world.item.crafting.SmeltingRecipe
 */
package ic2.core.uu;

import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import ic2.core.uu.RecipeUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class VanillaSmeltingResolver
implements IRecipeResolver {
    private static final double transformCost = 14.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> arrayList = new ArrayList<RecipeTransformation>();
        for (SmeltingRecipe smeltingRecipe : IC2.sideProxy.getRecipeManager().m_44013_(RecipeType.f_44108_)) {
            try {
                List<List<LeanItemStack>> list = RecipeUtil.convertIngredients((List<Ingredient>)smeltingRecipe.m_7527_());
                LeanItemStack leanItemStack = new LeanItemStack(smeltingRecipe.m_8043_());
                arrayList.add(new RecipeTransformation(14.0, list, leanItemStack));
            }
            catch (IllegalArgumentException illegalArgumentException) {
                IC2.log.warn(LogCategory.Uu, illegalArgumentException, "invalid recipe");
            }
        }
        return arrayList;
    }
}

