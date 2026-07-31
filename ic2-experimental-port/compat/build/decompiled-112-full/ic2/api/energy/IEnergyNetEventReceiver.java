/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy;

import ic2.api.energy.tile.IEnergyTile;

public interface IEnergyNetEventReceiver {
    public void onAdd(IEnergyTile var1);

    public void onRemove(IEnergyTile var1);
}

