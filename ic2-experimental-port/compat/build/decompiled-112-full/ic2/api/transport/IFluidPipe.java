/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidTank
 */
package ic2.api.transport;

import ic2.api.transport.IPipe;
import net.minecraftforge.fluids.FluidTank;

public interface IFluidPipe
extends IPipe {
    public int getTransferRate();

    public FluidTank getTank();

    public int getCurrentInnerCapacity();

    public int getMaxInnerCapacity();
}

