/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.uu;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.MachineRecipe;
import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import ic2.core.uu.RecipeUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

public class MachineRecipeResolver
implements IRecipeResolver {
    private static final double transformCost = 14.0;
    private final IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?> manager;

    public MachineRecipeResolver(IMachineRecipeManager<IRecipeInput, Collection<ItemStack>, ?> manager) {
        this.manager = manager;
    }

    @Override
    public List<RecipeTransformation> getTransformations() {
        if (!this.manager.isIterable()) {
            return Collections.emptyList();
        }
        ArrayList<RecipeTransformation> ret = new ArrayList<RecipeTransformation>();
        for (MachineRecipe<IRecipeInput, Collection<ItemStack>> recipe : this.manager.getRecipes()) {
            try {
                List<List<LeanItemStack>> inputs = RecipeUtil.convertIngredients(recipe.getInput().getInputs());
                List<LeanItemStack> outputs = RecipeUtil.convertOutputs(recipe.getOutput());
                ret.add(new RecipeTransformation(14.0, inputs, outputs));
            }
            catch (IllegalArgumentException e) {
                IC2.log.warn(LogCategory.Uu, e, "invalid recipe");
            }
        }
        return ret;
    }
}

