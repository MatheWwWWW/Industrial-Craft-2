/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.level.material.Fluid
 */
package ic2.core.block.invslot;

import ic2.core.block.IInventorySlotHolder;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import java.util.Arrays;
import net.minecraft.world.level.material.Fluid;

public class InvSlotConsumableLiquidByTank
extends InvSlotConsumableLiquid {
    public final Ic2FluidTank tank;

    public InvSlotConsumableLiquidByTank(IInventorySlotHolder<?> iInventorySlotHolder, String string, InvSlot.Access access, int n, InvSlot.InvSide invSide, InvSlotConsumableLiquid.OpType opType, Ic2FluidTank ic2FluidTank) {
        super(iInventorySlotHolder, string, access, n, invSide, opType);
        this.tank = ic2FluidTank;
    }

    @Override
    protected boolean acceptsLiquid(Fluid fluid) {
        return this.tank.isEmpty() || this.tank.hasExactFluid(fluid);
    }

    @Override
    protected Iterable<Fluid> getPossibleFluids() {
        Ic2FluidStack ic2FluidStack = this.tank.getFluidStack();
        return ic2FluidStack != null ? Arrays.asList(ic2FluidStack.getFluid()) : null;
    }
}

