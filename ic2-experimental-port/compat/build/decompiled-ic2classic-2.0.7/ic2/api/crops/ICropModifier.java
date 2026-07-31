/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.crops;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ICropModifier {
    public boolean canChangeSeedMode(ItemStack var1);

    public static boolean canToggleSeedMode(ItemStack stack) {
        ICropModifier mod;
        Item item = stack.m_41720_();
        return item instanceof ICropModifier && (mod = (ICropModifier)item).canChangeSeedMode(stack);
    }
}

