/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientFilter
implements IFilter {
    Ingredient ingredient;

    public IngredientFilter(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public boolean matches(ItemStack input) {
        return this.ingredient.test(input);
    }
}

