/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.util.EnumFacing;

public interface IEnergyAcceptor
extends IEnergyTile {
    public boolean acceptsEnergyFrom(IEnergyEmitter var1, EnumFacing var2);
}

