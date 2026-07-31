/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package ic2.core.energy.grid;

import ic2.api.energy.tile.IEnergyTile;
import java.util.List;
import net.minecraft.core.BlockPos;

class GridChange {
    final Type type;
    final BlockPos pos;
    final IEnergyTile ioTile;
    List<IEnergyTile> subTiles;

    GridChange(Type type, BlockPos blockPos, IEnergyTile iEnergyTile) {
        this.type = type;
        this.pos = blockPos;
        this.ioTile = iEnergyTile;
    }

    static enum Type {
        ADDITION,
        REMOVAL;

    }
}

