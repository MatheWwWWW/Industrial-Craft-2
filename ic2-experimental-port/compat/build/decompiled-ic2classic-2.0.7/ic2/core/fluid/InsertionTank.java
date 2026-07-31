/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.fluid;

import ic2.core.fluid.IC2Tank;
import java.util.function.Predicate;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class InsertionTank
extends IC2Tank {
    public InsertionTank(int capacity) {
        super(capacity);
    }

    public InsertionTank(int capacity, Predicate<FluidStack> validator) {
        super(capacity, validator);
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Override
    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        return FluidStack.EMPTY;
    }

    public FluidStack drainInternally(int maxDrain, IFluidHandler.FluidAction action) {
        int drained = maxDrain;
        if (this.fluid.getAmount() < drained) {
            drained = this.fluid.getAmount();
        }
        FluidStack stack = new FluidStack(this.fluid, drained);
        if (action.execute() && drained > 0) {
            this.fluid.shrink(drained);
        }
        if (drained > 0) {
            this.onContentsChanged();
        }
        return stack;
    }
}

