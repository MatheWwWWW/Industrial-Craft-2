/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.core.fluid;

import ic2.core.fluid.IC2Tank;
import java.util.function.Predicate;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class ExtractionTank
extends IC2Tank {
    public static Predicate<FluidStack> CACHE = T -> false;

    public ExtractionTank(int capacity) {
        super(capacity, CACHE);
    }

    public int fillInternal(FluidStack resource, IFluidHandler.FluidAction action) {
        if (resource.isEmpty()) {
            return 0;
        }
        if (action.simulate()) {
            if (this.fluid.isEmpty()) {
                return Math.min(this.capacity, resource.getAmount());
            }
            if (!this.fluid.isFluidEqual(resource)) {
                return 0;
            }
            return Math.min(this.capacity - this.fluid.getAmount(), resource.getAmount());
        }
        if (this.fluid.isEmpty()) {
            this.fluid = new FluidStack(resource, Math.min(this.capacity, resource.getAmount()));
            this.onContentsChanged();
            return this.fluid.getAmount();
        }
        if (!this.fluid.isFluidEqual(resource)) {
            return 0;
        }
        int filled = this.capacity - this.fluid.getAmount();
        if (resource.getAmount() < filled) {
            this.fluid.grow(resource.getAmount());
            filled = resource.getAmount();
        } else {
            this.fluid.setAmount(this.capacity);
        }
        if (filled > 0) {
            this.onContentsChanged();
        }
        return filled;
    }

    public FluidTank setValidator(Predicate<FluidStack> validator) {
        return this;
    }
}

