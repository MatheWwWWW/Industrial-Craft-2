/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.recipe.dynamic;

import ic2.core.recipe.dynamic.RecipeOutputIngredient;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;

public class RecipeOutputItemStack
extends RecipeOutputIngredient<ItemStack> {
    public static RecipeOutputItemStack of(ItemStack ingredient) {
        return new RecipeOutputItemStack(ingredient);
    }

    protected RecipeOutputItemStack(ItemStack ingredient) {
        super(ingredient);
    }

    @Override
    public RecipeOutputIngredient<ItemStack> copy() {
        return RecipeOutputItemStack.of(((ItemStack)this.ingredient).func_77946_l());
    }

    @Override
    public boolean isEmpty() {
        return StackUtil.isEmpty((ItemStack)this.ingredient);
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

