/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.crafting.Ingredient
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientNBTFilter
implements IFilter {
    Ingredient item;
    ItemStack nbtData;
    boolean nbt;

    public IngredientNBTFilter(Ingredient item, ItemStack nbtData, boolean nbt) {
        this.item = item;
        this.nbtData = nbtData;
        this.nbt = nbt;
    }

    @Override
    public boolean matches(ItemStack input) {
        return this.item.test(input) && (!this.nbt || StackUtil.isNBTExact(this.nbtData, input, false));
    }

    public Ingredient getItem() {
        return this.item;
    }

    public ItemStack getNBTData() {
        return this.nbtData;
    }

    public boolean isNBT() {
        return this.nbt;
    }
}

