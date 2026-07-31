/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.wearable.base;

import ic2.core.inventory.base.IHasHeldSlotInventory;
import net.minecraft.world.item.ItemStack;

public interface IHasArmorInventory
extends IHasHeldSlotInventory {
    public boolean hasArmorInventory(ItemStack var1, ItemStack var2);
}

