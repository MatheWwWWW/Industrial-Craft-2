/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.material.Fluid
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.fluids.FluidStack
 */
package ic2.api.core;

import ic2.api.network.INetworkManager;
import ic2.api.ticks.ITickScheduler;
import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fluids.FluidStack;

public interface APIHelper {
    public List<ItemStack> getFluidContainers(Fluid var1);

    public FluidStack createSteam(int var1);

    public ITickScheduler getTickHelper();

    public INetworkManager getNetworkManager();

    public INetworkManager getNetworkManager(Dist var1);
}

