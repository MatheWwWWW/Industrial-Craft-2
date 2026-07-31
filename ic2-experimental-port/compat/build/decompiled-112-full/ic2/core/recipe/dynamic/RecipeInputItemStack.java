/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.RecipeInputIngredient;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;

public class RecipeInputItemStack
extends RecipeInputIngredient<ItemStack> {
    public static RecipeInputItemStack of(ItemStack ingredient) {
        return new RecipeInputItemStack(ingredient);
    }

    public static RecipeInputItemStack of(ItemStack ingredient, boolean consumable) {
        return new RecipeInputItemStack(ingredient, consumable);
    }

    protected RecipeInputItemStack(ItemStack ingredient) {
        super(ingredient);
    }

    protected RecipeInputItemStack(ItemStack ingredient, boolean consumable) {
        super(ingredient, consumable);
    }

    @Override
    public Object getUnspecific() {
        return ((ItemStack)this.ingredient).func_77973_b();
    }

    @Override
    public RecipeInputIngredient<ItemStack> copy() {
        return RecipeInputItemStack.of(((ItemStack)this.ingredient).func_77946_l());
    }

    @Override
    public boolean isEmpty() {
        return StackUtil.isEmpty((ItemStack)this.ingredient);
    }

    @Override
    public int getCount() {
        return StackUtil.getSize((ItemStack)this.ingredient);
    }

    @Override
    public void shrink(int amount) {
        ((ItemStack)this.ingredient).func_190918_g(amount);
    }

    @Override
    public boolean matches(Object other) {
        if (!(other instanceof ItemStack)) {
            return false;
        }
        return StackUtil.checkItemEqualityStrict((ItemStack)this.ingredient, (ItemStack)other);
    }

    @Override
    public boolean matchesStrict(Object other) {
        return this.matches(other);
    }

    @Override
    public String toStringSafe() {
        return StackUtil.toStringSafe((ItemStack)this.ingredient);
    }
}

