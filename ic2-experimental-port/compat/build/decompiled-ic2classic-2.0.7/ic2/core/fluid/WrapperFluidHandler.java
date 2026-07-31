/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 *  org.jetbrains.annotations.NotNull
 */
package ic2.core.fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class WrapperFluidHandler
extends FluidTank {
    public WrapperFluidHandler(IFluidHandler handler) {
        super(handler.getTankCapacity(0));
        this.setFluid(handler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE));
    }

    public static class FillHandlerWrapper
    implements IFluidHandler {
        IFluidHandler handler;

        public FillHandlerWrapper(IFluidHandler handler) {
            this.handler = handler;
        }

        public int getTanks() {
            return this.handler.getTanks();
        }

        public FluidStack getFluidInTank(int tank) {
            return this.handler.getFluidInTank(tank);
        }

        public int getTankCapacity(int tank) {
            return this.handler.getTankCapacity(tank);
        }

        public boolean isFluidValid(int tank, FluidStack stack) {
            return this.handler.isFluidValid(tank, stack);
        }

        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            return this.handler.fill(resource, IFluidHandler.FluidAction.SIMULATE);
        }

        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return this.handler.drain(resource, IFluidHandler.FluidAction.SIMULATE);
        }

        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return this.handler.drain(maxDrain, IFluidHandler.FluidAction.SIMULATE);
        }
    }

    public static class DrainHandlerWrapper
    implements IFluidHandler {
        IFluidHandler handler;

        public DrainHandlerWrapper(IFluidHandler handler) {
            this.handler = handler;
        }

        public int getTanks() {
            return this.handler.getTanks();
        }

        @NotNull
        public FluidStack getFluidInTank(int tank) {
            return this.handler.getFluidInTank(tank);
        }

        public int getTankCapacity(int tank) {
            return this.handler.getTankCapacity(tank);
        }

        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            return this.handler.isFluidValid(tank, stack);
        }

        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            return 0;
        }

        @NotNull
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return this.handler.drain(resource, action);
        }

        @NotNull
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return this.handler.drain(maxDrain, action);
        }
    }
}

