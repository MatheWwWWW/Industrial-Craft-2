/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import ic2.api.item.IElectricItemManager;
import net.minecraft.world.item.ItemStack;

public interface ISpecialElectricItem {
    public IElectricItemManager getManager(ItemStack var1);
}

