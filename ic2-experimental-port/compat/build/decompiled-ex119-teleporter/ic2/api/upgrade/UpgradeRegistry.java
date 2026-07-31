/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.upgrade;

import ic2.api.upgrade.IUpgradeItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.world.item.ItemStack;

public class UpgradeRegistry {
    private static final List<ItemStack> upgrades = new ArrayList<ItemStack>();

    public static ItemStack register(ItemStack itemStack) {
        if (!(itemStack.m_41720_() instanceof IUpgradeItem)) {
            throw new IllegalArgumentException("The stack must represent an IUpgradeItem.");
        }
        upgrades.add(itemStack);
        return itemStack;
    }

    public static Iterable<ItemStack> getUpgrades() {
        return Collections.unmodifiableCollection(upgrades);
    }
}

