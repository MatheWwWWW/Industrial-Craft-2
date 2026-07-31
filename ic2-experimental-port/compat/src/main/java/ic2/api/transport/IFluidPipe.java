package ic2.api.transport;

import net.minecraftforge.fluids.capability.templates.FluidTank;

/** Fluid-pipe portion of the IC2 transport API, adapted to Forge 1.19.2. */
public interface IFluidPipe extends IPipe {
    int getTransferRate();

    FluidTank getTank();

    int getCurrentInnerCapacity();

    int getMaxInnerCapacity();
}
