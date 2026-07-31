/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package ic2.core.uu;

import ic2.core.uu.LeanItemStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

class RecipeUtil {
    RecipeUtil() {
    }

    public static List<List<LeanItemStack>> fixIngredientSize(List<ItemStack>[] listArray) {
        ArrayList<List<LeanItemStack>> arrayList = new ArrayList<List<LeanItemStack>>(listArray.length);
        for (int i = 0; i < listArray.length; ++i) {
            List<ItemStack> list = listArray[i];
            if (list == null) continue;
            ArrayList<LeanItemStack> arrayList2 = new ArrayList<LeanItemStack>(list.size());
            for (ItemStack itemStack : listArray[i]) {
                arrayList2.add(new LeanItemStack(itemStack, 1));
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    public static List<List<LeanItemStack>> convertInputs(List<ItemStack> list) {
        return Collections.singletonList(RecipeUtil.convertOutputs(list));
    }

    public static List<List<LeanItemStack>> convertIngredients(List<Ingredient> list) {
        ArrayList<List<LeanItemStack>> arrayList = new ArrayList<List<LeanItemStack>>(list.size());
        for (Ingredient ingredient : list) {
            ItemStack[] itemStackArray = ingredient.m_43908_();
            ArrayList<LeanItemStack> arrayList2 = new ArrayList<LeanItemStack>(itemStackArray.length);
            for (ItemStack itemStack : itemStackArray) {
                arrayList2.add(new LeanItemStack(itemStack));
            }
        }
        return arrayList;
    }

    public static List<LeanItemStack> convertOutputs(Collection<ItemStack> collection) {
        ArrayList<LeanItemStack> arrayList = new ArrayList<LeanItemStack>(collection.size());
        for (ItemStack itemStack : collection) {
            arrayList.add(new LeanItemStack(itemStack));
        }
        return arrayList;
    }
}

