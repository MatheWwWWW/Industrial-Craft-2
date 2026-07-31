/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 */
package ic2.api.upgrade;

import ic2.api.upgrade.IUpgradeItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.item.ItemStack;

public class UpgradeRegistry {
    private static final List<ItemStack> upgrades = new ArrayList<ItemStack>();

    public static ItemStack register(ItemStack stack) {
        if (!(stack.func_77973_b() instanceof IUpgradeItem)) {
            throw new IllegalArgumentException("The stack must represent an IUpgradeItem.");
        }
        upgrades.add(stack);
        return stack;
    }

    public static Iterable<ItemStack> getUpgrades() {
        return Collections.unmodifiableCollection(upgrades);
    }
}

