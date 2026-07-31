/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergySource;

public interface IMultiEnergySource
extends IEnergySource {
    public boolean sendMultipleEnergyPackets();

    public int getMultipleEnergyPacketAmount();
}

