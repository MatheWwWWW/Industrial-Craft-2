/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.gui;

import ic2.core.Ic2Gui;
import ic2.core.fluid.Ic2FluidStack;
import ic2.core.fluid.Ic2FluidTank;
import ic2.core.gui.AbstractFluidSlot;

public class TankFluidSlot
extends AbstractFluidSlot {
    final Ic2FluidTank tank;

    public static TankFluidSlot createFluidSlot(Ic2Gui<?> ic2Gui, int n, int n2, Ic2FluidTank ic2FluidTank) {
        return new TankFluidSlot(ic2Gui, n, n2, 18, 18, ic2FluidTank);
    }

    protected TankFluidSlot(Ic2Gui<?> ic2Gui, int n, int n2, int n3, int n4, Ic2FluidTank ic2FluidTank) {
        super(ic2Gui, n, n2, n3, n4);
        if (ic2FluidTank == null) {
            throw new NullPointerException("Null FluidTank instance.");
        }
        this.tank = ic2FluidTank;
    }

    @Override
    protected Ic2FluidStack getFluidStack() {
        return this.tank.getFluidStack();
    }
}

