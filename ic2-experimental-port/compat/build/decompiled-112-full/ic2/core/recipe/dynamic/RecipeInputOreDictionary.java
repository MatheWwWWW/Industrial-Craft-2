/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.Item
 *  net.minecraft.item.ItemStack
 *  net.minecraft.util.NonNullList
 *  net.minecraftforge.oredict.OreDictionary
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.RecipeInputIngredient;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeInputOreDictionary
extends RecipeInputIngredient<String> {
    public final int amount;
    public final Integer meta;
    private List<ItemStack> equivalents;

    public static RecipeInputOreDictionary of(String ingredient) {
        return RecipeInputOreDictionary.of(ingredient, 1);
    }

    public static RecipeInputOreDictionary of(String ingredient, int amount) {
        return RecipeInputOreDictionary.of(ingredient, amount, null);
    }

    public static RecipeInputOreDictionary of(String ingredient, int amount, Integer meta) {
        return new RecipeInputOreDictionary(ingredient, amount, meta);
    }

    protected RecipeInputOreDictionary(String ingredient) {
        this(ingredient, 1);
    }

    protected RecipeInputOreDictionary(String ingredient, int amount) {
        this(ingredient, amount, null);
    }

    protected RecipeInputOreDictionary(String ingredient, int amount, Integer meta) {
        super(ingredient);
        this.amount = amount;
        this.meta = meta;
    }

    @Override
    public Object getUnspecific() {
        return null;
    }

    @Override
    public RecipeInputIngredient<String> copy() {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean isEmpty() {
        return this.amount <= 0;
    }

    @Override
    public int getCount() {
        return this.amount;
    }

    @Override
    public void shrink(int amount) {
        throw new UnsupportedOperationException("Not supported");
    }

    @Override
    public boolean matches(Object other) {
        if (!(other instanceof ItemStack)) {
            return false;
        }
        List<ItemStack> inputs = this.getEquivalents();
        boolean useOreStackMeta = this.meta == null;
        Item subjectItem = ((ItemStack)other).func_77973_b();
        int subjectMeta = ((ItemStack)other).func_77952_i();
        for (ItemStack oreStack : inputs) {
            int metaRequired;
            Item oreItem = oreStack.func_77973_b();
            if (oreItem == null) continue;
            int n = metaRequired = useOreStackMeta ? oreStack.func_77952_i() : this.meta.intValue();
            if (subjectItem != oreItem || subjectMeta != metaRequired && metaRequired != Short.MAX_VALUE) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean matchesStrict(Object other) {
        if (!(other instanceof String)) {
            return false;
        }
        return ((String)this.ingredient).equals(other);
    }

    @Override
    public String toStringSafe() {
        return (String)this.ingredient;
    }

    private List<ItemStack> getEquivalents() {
        if (this.equivalents != null) {
            return this.equivalents;
        }
        NonNullList ret = OreDictionary.getOres((String)((String)this.ingredient));
        if (ret != OreDictionary.EMPTY_LIST) {
            this.equivalents = ret;
        }
        return ret;
    }
}

