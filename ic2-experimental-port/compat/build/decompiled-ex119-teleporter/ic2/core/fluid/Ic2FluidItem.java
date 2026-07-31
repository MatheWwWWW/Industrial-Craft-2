/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  org.apache.commons.lang3.mutable.Mutable
 */
package ic2.core.fluid;

import ic2.core.fluid.Ic2FluidStack;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.Mutable;

public interface Ic2FluidItem {
    public Ic2FluidStack getFluidStack(ItemStack var1);

    public int getCapacityMb(ItemStack var1);

    public Ic2FluidStack drainMb(ItemStack var1, int var2, boolean var3, Mutable<ItemStack> var4);

    public int drainMb(ItemStack var1, Ic2FluidStack var2, boolean var3, Mutable<ItemStack> var4);

    public int fillMb(ItemStack var1, Ic2FluidStack var2, boolean var3, Mutable<ItemStack> var4);
}

