/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.core.block.transport.cover;

import ic2.core.block.transport.cover.ICoverItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

public class CoverRegistry {
    private static final List<ItemStack> covers = new ArrayList<ItemStack>();

    public static ItemStack register(ItemStack stack) {
        if (!(stack.func_77973_b() instanceof ICoverItem)) {
            throw new IllegalArgumentException("The stack must represent an ICoverItem.");
        }
        covers.add(stack);
        return stack;
    }

    public static Iterable<ItemStack> getCovers() {
        return Collections.unmodifiableCollection(covers);
    }
}

