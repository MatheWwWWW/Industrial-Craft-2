/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.Fluid
 *  net.minecraftforge.fluids.FluidStack
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import java.util.Arrays;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

public class InvSlotConsumableLiquidByTank
extends InvSlotConsumableLiquid {
    public final IFluidTank tank;

    public InvSlotConsumableLiquidByTank(IInventorySlotHolder<?> base1, String name1, InvSlot.Access access1, int count, InvSlot.InvSide preferredSide1, InvSlotConsumableLiquid.OpType opType, IFluidTank tank1) {
        super(base1, name1, access1, count, preferredSide1, opType);
        this.tank = tank1;
    }

    @Override
    protected boolean acceptsLiquid(Fluid fluid) {
        FluidStack fs = this.tank.getFluid();
        return fs == null || fs.getFluid() == fluid;
    }

    @Override
    protected Iterable<Fluid> getPossibleFluids() {
        FluidStack fs = this.tank.getFluid();
        if (fs == null) {
            return null;
        }
        return Arrays.asList(fs.getFluid());
    }
}

