/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;

public interface IEnergyConductor
extends IEnergyAcceptor,
IEnergyEmitter {
    public double getConductionLoss();

    public double getInsulationEnergyAbsorption();

    public double getInsulationBreakdownEnergy();

    public double getConductorBreakdownEnergy();

    public void removeInsulation();

    public void removeConductor();
}

