/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.items.electric;

import net.minecraft.world.item.ItemStack;

public interface IElectricItem {
    public boolean canProvideEnergy(ItemStack var1);

    public int getCapacity(ItemStack var1);

    public int getTier(ItemStack var1);

    public int getTransferLimit(ItemStack var1);
}

