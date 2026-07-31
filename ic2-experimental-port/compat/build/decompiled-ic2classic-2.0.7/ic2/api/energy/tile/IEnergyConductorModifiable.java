/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyConductor;

public interface IEnergyConductorModifiable
extends IEnergyConductor {
    public boolean tryAddInsulation();

    public boolean tryRemoveInsulation();
}

