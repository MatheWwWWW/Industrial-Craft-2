/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.fluids.IFluidTank
 */
package ic2.core.block.base.misc.comparator.types.base;

import ic2.core.block.base.misc.comparator.BaseComparator;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.IFluidTank;

public class TankComparator
extends BaseComparator {
    IFluidTank tank;

    public TankComparator(String id, Component name, IFluidTank tank) {
        super(id, name);
        this.tank = tank;
    }

    @Override
    protected int createValue() {
        return TankComparator.value(this.tank.getFluidAmount(), this.tank.getCapacity(), 15);
    }
}

