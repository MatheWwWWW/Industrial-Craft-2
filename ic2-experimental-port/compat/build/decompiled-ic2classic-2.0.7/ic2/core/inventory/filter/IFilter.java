/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.filter;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface IFilter {
    public boolean matches(ItemStack var1);
}

