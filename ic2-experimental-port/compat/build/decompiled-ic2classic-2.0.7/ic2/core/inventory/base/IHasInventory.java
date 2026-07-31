/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.base;

import net.minecraft.world.item.ItemStack;

public interface IHasInventory {
    public int getSlotCount();

    public ItemStack getStackInSlot(int var1);

    public void setStackInSlot(int var1, ItemStack var2);

    public int getMaxStackSize(int var1);

    public boolean canInsert(int var1, ItemStack var2);

    public boolean canExtract(int var1, ItemStack var2);
}

