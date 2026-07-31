/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.fluids.capability.templates.FluidTank
 */
package ic2.api.reactor;

import ic2.api.reactor.IReactor;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public interface ISteamReactor
extends IReactor {
    public FluidTank getWaterTank();

    public FluidTank getSteamTank();
}

