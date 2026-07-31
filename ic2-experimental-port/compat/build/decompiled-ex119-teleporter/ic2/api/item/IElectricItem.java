/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.api.item;

import net.minecraft.world.item.ItemStack;

public interface IElectricItem {
    public boolean canProvideEnergy(ItemStack var1);

    public double getMaxCharge(ItemStack var1);

    public int getTier(ItemStack var1);

    public double getTransferLimit(ItemStack var1);
}

