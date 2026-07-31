/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.item.EnumDyeColor
 *  net.minecraft.util.EnumFacing
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;

public interface IColoredEnergyTile
extends IEnergyTile {
    public EnumDyeColor getColor(EnumFacing var1);
}

