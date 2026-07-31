/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package ic2.core.uu;

import ic2.core.ref.Ic2Items;
import ic2.core.uu.IRecipeResolver;
import ic2.core.uu.LeanItemStack;
import ic2.core.uu.RecipeTransformation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ManualRecipeResolver
implements IRecipeResolver {
    private static final double transformCost = 0.0;

    @Override
    public List<RecipeTransformation> getTransformations() {
        ArrayList<RecipeTransformation> arrayList = new ArrayList<RecipeTransformation>();
        arrayList.add(ManualRecipeResolver.toTransform(Ic2Items.URANIUM_FUEL_ROD, Ic2Items.DEPLETED_URANIUM_FUEL_ROD));
        arrayList.add(ManualRecipeResolver.toTransform(Ic2Items.DUAL_URANIUM_FUEL_ROD, Ic2Items.DEPLETED_DUAL_URANIUM_FUEL_ROD));
        arrayList.add(ManualRecipeResolver.toTransform(Ic2Items.QUAD_URANIUM_FUEL_ROD, Ic2Items.DEPLETED_QUAD_URANIUM_FUEL_ROD));
        arrayList.add(ManualRecipeResolver.toTransform(Ic2Items.MOX_FUEL_ROD, Ic2Items.DEPLETED_MOX_FUEL_ROD));
        arrayList.add(ManualRecipeResolver.toTransform(Ic2Items.DUAL_MOX_FUEL_ROD, Ic2Items.DEPLETED_DUAL_MOX_FUEL_ROD));
        arrayList.add(ManualRecipeResolver.toTransform(Ic2Items.QUAD_MOX_FUEL_ROD, Ic2Items.DEPLETED_QUAD_MOX_FUEL_ROD));
        return arrayList;
    }

    private static RecipeTransformation toTransform(Item item, Item item2) {
        return ManualRecipeResolver.toTransform(new ItemStack((ItemLike)item), new ItemStack((ItemLike)item2));
    }

    private static RecipeTransformation toTransform(ItemStack itemStack, ItemStack itemStack2) {
        List<List<LeanItemStack>> list = Collections.singletonList(Collections.singletonList(new LeanItemStack(itemStack)));
        List<LeanItemStack> list2 = Collections.singletonList(new LeanItemStack(itemStack2));
        return new RecipeTransformation(0.0, list, list2);
    }
}

