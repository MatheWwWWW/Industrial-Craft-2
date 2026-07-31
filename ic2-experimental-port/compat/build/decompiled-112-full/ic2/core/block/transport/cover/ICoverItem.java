/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.ItemStack
 *  net.minecraftforge.common.capabilities.Capability
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.core.block.transport.cover;

import ic2.core.block.transport.cover.CoverProperty;
import ic2.core.block.transport.cover.ICoverHolder;
import java.util.Collection;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

public interface ICoverItem {
    public boolean isSuitableFor(ItemStack var1, Set<CoverProperty> var2);

    public boolean onTick(ItemStack var1, ICoverHolder var2);

    public boolean allowsInput(ItemStack var1);

    public boolean allowsInput(FluidStack var1);

    public boolean allowsOutput(ItemStack var1);

    public boolean allowsOutput(FluidStack var1);

    public Collection<? extends Capability<?>> getProvidedCapabilities();
}

