/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.uu;

import ic2.api.recipe.Recipes;
import ic2.core.IC2;
import ic2.core.item.type.CraftingItemType;
import ic2.core.ref.ItemName;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;

public class ScrapBoxResolver
implements IRecipeResolver {
    private static final double transformCost = 1.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> ret = new ArrayList<RecipeTransformation>();
        Map<ItemStack, Float> dropMap = Recipes.scrapboxDrops.getDrops();
        for (Map.Entry<ItemStack, Float> drop : dropMap.entrySet()) {
            if (StackUtil.isEmpty(drop.getKey())) {
                IC2.log.warn(LogCategory.Uu, "Invalid itemstack in scrapbox drops detected.");
                continue;
            }
            int amount = Math.max(1, Math.round(1.0f / drop.getValue().floatValue()));
            List<LeanItemStack> input = Collections.singletonList(new LeanItemStack(ItemName.crafting.getItemStack(CraftingItemType.scrap_box), amount));
            ret.add(new RecipeTransformation(1.0, Collections.singletonList(input), new LeanItemStack(drop.getKey())));
        }
        return ret;
    }
}

