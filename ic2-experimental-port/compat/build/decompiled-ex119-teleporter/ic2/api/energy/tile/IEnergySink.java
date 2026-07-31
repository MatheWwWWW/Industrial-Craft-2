/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyAcceptor;
import net.minecraft.core.Direction;

public interface IEnergySink
extends IEnergyAcceptor {
    public double getDemandedEnergy();

    public int getSinkTier();

    public double injectEnergy(Direction var1, double var2, double var4);
}

