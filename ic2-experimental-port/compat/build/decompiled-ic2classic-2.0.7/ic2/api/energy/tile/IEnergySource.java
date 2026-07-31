/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyEmitter;

public interface IEnergySource
extends IEnergyEmitter {
    public int getSourceTier();

    public int getMaxEnergyOutput();

    public int getProvidedEnergy();

    public void consumeEnergy(int var1);

    default public void onPacketFailed() {
    }
}

