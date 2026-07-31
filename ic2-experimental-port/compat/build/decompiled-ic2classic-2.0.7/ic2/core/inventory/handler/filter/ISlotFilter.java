/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.handler.filter;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface ISlotFilter {
    public boolean matches(int var1, ItemStack var2);
}

