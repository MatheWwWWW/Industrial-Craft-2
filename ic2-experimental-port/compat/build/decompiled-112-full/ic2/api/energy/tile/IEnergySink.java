/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyAcceptor;
import net.minecraft.util.EnumFacing;

public interface IEnergySink
extends IEnergyAcceptor {
    public double getDemandedEnergy();

    public int getSinkTier();

    public double injectEnergy(EnumFacing var1, double var2, double var4);
}

