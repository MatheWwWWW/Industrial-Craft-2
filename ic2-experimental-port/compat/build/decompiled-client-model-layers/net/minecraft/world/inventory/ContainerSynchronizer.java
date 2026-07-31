/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public interface ContainerSynchronizer {
    public void m_142589_(AbstractContainerMenu var1, NonNullList<ItemStack> var2, ItemStack var3, int[] var4);

    public void m_142074_(AbstractContainerMenu var1, int var2, ItemStack var3);

    public void m_142529_(AbstractContainerMenu var1, ItemStack var2);

    public void m_142145_(AbstractContainerMenu var1, int var2, int var3);
}

