/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.personal.trade;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.block.personal.trade.Trade;
import ic2.core.fluid.IC2Tank;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidTrade
extends Trade {
    public IC2Tank offered = new IC2Tank(128000);

    @Override
    public void write(IOutputBuffer buffer) {
        super.write(buffer);
        buffer.writeFluidStack(this.offered.getFluid());
    }

    @Override
    public void read(IInputBuffer buffer) {
        super.read(buffer);
        this.offered.setFluid(buffer.readFluidStack());
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        super.save(nbt);
        NBTUtils.put(nbt, "fluid", this.offered.writeToNBT(new CompoundTag()));
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.offered.readFromNBT(nbt.m_128469_("fluid"));
    }

    public int getCount(IFluidHandler handler) {
        return this.offered.isEmpty() ? 0 : handler.drain(new FluidStack(this.offered.getFluid(), Integer.MAX_VALUE), IFluidHandler.FluidAction.SIMULATE).getAmount() / this.offered.getFluidAmount();
    }

    public boolean hasStock(IFluidHandler extract, IFluidHandler insert, IFluidHandler.FluidAction action) {
        FluidStack stack;
        if (this.offered.isEmpty()) {
            return false;
        }
        FluidStack fluidStack = stack = this.infinite ? this.offered.getFluid() : extract.drain(this.offered.getFluid(), action);
        if (stack.getAmount() < this.offered.getFluidAmount()) {
            return false;
        }
        return insert.fill(stack, action) >= this.offered.getFluidAmount();
    }
}

