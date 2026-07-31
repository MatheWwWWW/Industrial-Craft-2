/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.init;

import ic2.core.util.ItemComparableItemStack;
import ic2.core.util.StackUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.world.item.ItemStack;

public class OreValues {
    private static final Map<ItemComparableItemStack, Integer> stackValues = new HashMap<ItemComparableItemStack, Integer>();

    public static void add(ItemStack itemStack, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("value has to be > 0");
        }
        ItemComparableItemStack itemComparableItemStack = new ItemComparableItemStack(itemStack, true);
        Integer n2 = stackValues.put(itemComparableItemStack, n);
        if (n2 != null && n2 > n) {
            stackValues.put(itemComparableItemStack, n2);
        }
    }

    public static int get(ItemStack itemStack) {
        if (StackUtil.isEmpty(itemStack)) {
            return 0;
        }
        Integer n = stackValues.get(new ItemComparableItemStack(itemStack, false));
        return n != null ? n * StackUtil.getSize(itemStack) : 0;
    }

    public static int get(List<ItemStack> list) {
        int n = 0;
        for (ItemStack itemStack : list) {
            n += OreValues.get(itemStack);
        }
        return n;
    }
}

