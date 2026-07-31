/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.inventory.base;

import ic2.core.inventory.base.IHasHeldGui;
import ic2.core.inventory.base.IPortableInventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public interface IHasHeldSlotInventory
extends IHasHeldGui {
    default public boolean hasInventory(ItemStack stack) {
        return true;
    }

    public IPortableInventory getInventory(Player var1, ItemStack var2, Slot var3);
}

