/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.electric;

import ic2.api.items.electric.IElectricItemManager;
import net.minecraft.world.item.ItemStack;

public interface ICustomElectricItem {
    public IElectricItemManager getManager(ItemStack var1);
}

