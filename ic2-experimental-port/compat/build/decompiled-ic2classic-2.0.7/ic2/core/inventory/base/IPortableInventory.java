/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.base;

import ic2.core.inventory.base.IHasGui;
import ic2.core.inventory.base.IHasInventory;
import net.minecraft.world.item.ItemStack;

public interface IPortableInventory
extends IHasGui,
IHasInventory {
    public ItemStack getInventoryStack();

    public IPortableInventory load(ItemStack var1);
}

