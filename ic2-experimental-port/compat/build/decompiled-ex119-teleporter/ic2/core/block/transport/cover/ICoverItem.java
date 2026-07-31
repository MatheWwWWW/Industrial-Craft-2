/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.block.transport.cover;

import ic2.core.block.transport.cover.CoverProperty;
import ic2.core.block.transport.cover.ICoverHolder;
import ic2.core.fluid.Ic2FluidStack;
import java.util.Set;
import net.minecraft.world.item.ItemStack;

public interface ICoverItem {
    public boolean isSuitableFor(ItemStack var1, Set<CoverProperty> var2);

    public boolean onTick(ItemStack var1, ICoverHolder var2);

    public boolean allowsInput(ItemStack var1);

    public boolean allowsInput(Ic2FluidStack var1);

    public boolean allowsOutput(ItemStack var1);

    public boolean allowsOutput(Ic2FluidStack var1);
}

