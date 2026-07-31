/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter;

import ic2.core.inventory.filter.IFilter;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.world.item.ItemStack;

public class StackFilter
implements IFilter {
    ItemStack stack;
    int flags;

    public StackFilter(ItemStack stack, int flags) {
        this.stack = stack;
        this.flags = flags;
    }

    public static IFilter defaultCompare(ItemStack stack) {
        return new StackFilter(stack, 20);
    }

    public static IFilter fuzzyCompare(ItemStack stack) {
        return new StackFilter(stack, 12);
    }

    public static IFilter oreCompare(ItemStack stack) {
        return new StackFilter(stack, 36);
    }

    public static IFilter damageCompare(ItemStack stack) {
        return new StackFilter(stack, 68);
    }

    public static IFilter emptyCompare(ItemStack stack) {
        return new StackFilter(stack, 5);
    }

    @Override
    public boolean matches(ItemStack input) {
        return StackUtil.isStackEqual(this.stack, input, this.flags);
    }
}

