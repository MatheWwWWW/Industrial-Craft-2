/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.base.features.multiblock;

import ic2.core.block.base.cache.ICache;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import net.minecraft.core.Direction;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IMultiBlockFluidExporter {
    public void exportFluids(ICache<IFluidHandler> var1, IFluidPipe var2, Direction var3);
}

