/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntList
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.fluid;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ArrayFluidHandler
implements IFluidHandler {
    IFluidHandler[] handler;
    IntList tankSlots = new IntArrayList();
    IntList tankIndexes = new IntArrayList();

    public ArrayFluidHandler(List<IFluidHandler> handler) {
        this(handler.toArray(new IFluidHandler[handler.size()]));
    }

    public ArrayFluidHandler(IFluidHandler ... handler) {
        this.handler = handler;
        for (int i = 0; i < handler.length; ++i) {
            IFluidHandler entry = handler[i];
            int m = entry.getTanks();
            for (int j = 0; j < m; ++j) {
                this.tankIndexes.add(i);
                this.tankSlots.add(j);
            }
        }
    }

    public int getTanks() {
        return this.tankIndexes.size();
    }

    public FluidStack getFluidInTank(int tank) {
        return this.handler[this.tankIndexes.getInt(tank)].getFluidInTank(this.tankSlots.getInt(tank));
    }

    public int getTankCapacity(int tank) {
        return this.handler[this.tankIndexes.getInt(tank)].getTankCapacity(this.tankSlots.getInt(tank));
    }

    public boolean isFluidValid(int tank, FluidStack stack) {
        return this.handler[this.tankIndexes.getInt(tank)].isFluidValid(this.tankSlots.getInt(tank), stack);
    }

    public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        int filled = 0;
        for (int i = 0; i < this.handler.length; ++i) {
            if ((filled += this.handler[i].fill(new FluidStack(resource, resource.getAmount() - filled), action)) < resource.getAmount()) continue;
            return filled;
        }
        return filled;
    }

    public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
        int drained = 0;
        for (int i = 0; i < this.handler.length; ++i) {
            if ((drained += this.handler[i].drain(new FluidStack(resource, resource.getAmount() - drained), action).getAmount()) < resource.getAmount()) continue;
            return new FluidStack(resource, drained);
        }
        return drained == 0 ? FluidStack.EMPTY : new FluidStack(resource, drained);
    }

    public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
        FluidStack stack = FluidStack.EMPTY;
        for (int i = 0; i < this.handler.length; ++i) {
            if (stack.isEmpty()) {
                stack = this.handler[i].drain(maxDrain, action);
            } else {
                stack.grow(this.handler[i].drain(new FluidStack(stack, maxDrain - stack.getAmount()), action).getAmount());
            }
            if (stack.getAmount() < maxDrain) continue;
            return stack;
        }
        return stack;
    }
}

