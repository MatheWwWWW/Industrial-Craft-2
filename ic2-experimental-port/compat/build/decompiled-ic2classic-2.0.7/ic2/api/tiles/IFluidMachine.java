/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.api.tiles;

import net.minecraft.core.Direction;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidMachine {
    public IFluidHandler getConnectedTank(Direction var1);
}

