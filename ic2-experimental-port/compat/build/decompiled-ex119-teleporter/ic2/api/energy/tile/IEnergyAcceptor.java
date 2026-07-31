/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.core.Direction;

public interface IEnergyAcceptor
extends IEnergyTile {
    public boolean acceptsEnergyFrom(IEnergyEmitter var1, Direction var2);
}

