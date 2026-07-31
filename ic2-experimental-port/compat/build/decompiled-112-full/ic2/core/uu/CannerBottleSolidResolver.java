/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.uu;

import ic2.api.recipe.ICannerBottleRecipeManager;
import ic2.api.recipe.MachineRecipe;
import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import ic2.core.uu.RecipeUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CannerBottleSolidResolver
implements IRecipeResolver {
    private static final double transformCost = 0.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> ret = new ArrayList<RecipeTransformation>();
        for (MachineRecipe recipe : Recipes.cannerBottle.getRecipes()) {
            try {
                List<LeanItemStack> container = RecipeUtil.convertOutputs(((ICannerBottleRecipeManager.Input)recipe.getInput()).container.getInputs());
                List<LeanItemStack> fill = RecipeUtil.convertOutputs(((ICannerBottleRecipeManager.Input)recipe.getInput()).fill.getInputs());
                if (container.isEmpty() || fill.isEmpty()) continue;
                ArrayList<List<LeanItemStack>> inputs = new ArrayList<List<LeanItemStack>>(2);
                inputs.add(container);
                inputs.add(fill);
                ret.add(new RecipeTransformation(0.0, inputs, RecipeUtil.convertOutputs(Collections.singletonList(recipe.getOutput()))));
            }
            catch (IllegalArgumentException e) {
                IC2.log.warn(LogCategory.Uu, e, "invalid recipe");
            }
        }
        return ret;
    }
}

