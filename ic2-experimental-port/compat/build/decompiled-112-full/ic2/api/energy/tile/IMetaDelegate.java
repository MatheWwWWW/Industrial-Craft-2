/*
 * Decompiled with CFR 0.152.
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyTile;
import java.util.List;

public interface IMetaDelegate
extends IEnergyTile {
    public List<IEnergyTile> getSubTiles();
}

