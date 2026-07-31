/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.uu;

import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.ref.Ic2Items;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.ItemStack;

public class ScrapBoxResolver
implements IRecipeResolver {
    private static final double transformCost = 1.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> arrayList = new ArrayList<RecipeTransformation>();
        Map<ItemStack, Float> map = Recipes.scrapboxDrops.getDrops();
        for (Map.Entry<ItemStack, Float> entry : map.entrySet()) {
            if (StackUtil.isEmpty(entry.getKey())) {
                IC2.log.warn(LogCategory.Uu, "Invalid itemstack in scrapbox drops detected.");
                continue;
            }
            int n = Math.max(1, Math.round(1.0f / entry.getValue().floatValue()));
            List<LeanItemStack> list = Collections.singletonList(new LeanItemStack(Ic2Items.SCRAP_BOX, n));
            arrayList.add(new RecipeTransformation(1.0, Collections.singletonList(list), new LeanItemStack(entry.getKey())));
        }
        return arrayList;
    }
}

