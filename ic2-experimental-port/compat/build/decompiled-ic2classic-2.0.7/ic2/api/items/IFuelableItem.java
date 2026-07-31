/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items;

import net.minecraft.world.item.ItemStack;

public interface IFuelableItem {
    public ItemStack fill(ItemStack var1, int var2);

    public boolean canFuel(ItemStack var1);

    public boolean hasFuel(ItemStack var1);

    public int getFuel(ItemStack var1, int var2, boolean var3);
}

