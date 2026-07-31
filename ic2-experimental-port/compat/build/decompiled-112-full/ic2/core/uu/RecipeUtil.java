/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.uu;

import ic2.core.uu.LeanItemStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

class RecipeUtil {
    RecipeUtil() {
    }

    public static List<List<LeanItemStack>> fixIngredientSize(List<ItemStack>[] x) {
        ArrayList<List<LeanItemStack>> ret = new ArrayList<List<LeanItemStack>>(x.length);
        for (int i = 0; i < x.length; ++i) {
            List<ItemStack> listIn = x[i];
            if (listIn == null) continue;
            ArrayList<LeanItemStack> listOut = new ArrayList<LeanItemStack>(listIn.size());
            for (ItemStack stack : x[i]) {
                listOut.add(new LeanItemStack(stack, 1));
            }
            ret.add(listOut);
        }
        return ret;
    }

    public static List<List<LeanItemStack>> convertIngredients(List<ItemStack> x) {
        return Collections.singletonList(RecipeUtil.convertOutputs(x));
    }

    public static List<LeanItemStack> convertOutputs(Collection<ItemStack> x) {
        ArrayList<LeanItemStack> ret = new ArrayList<LeanItemStack>(x.size());
        for (ItemStack stack : x) {
            ret.add(new LeanItemStack(stack));
        }
        return ret;
    }
}

