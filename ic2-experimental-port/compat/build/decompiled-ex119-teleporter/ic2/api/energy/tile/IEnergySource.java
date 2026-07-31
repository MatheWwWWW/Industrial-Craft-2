/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyEmitter;

public interface IEnergySource
extends IEnergyEmitter {
    public double getOfferedEnergy();

    public void drawEnergy(double var1);

    public int getSourceTier();
}

