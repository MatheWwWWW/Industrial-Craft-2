/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

public interface IColoredEnergyTile
extends IEnergyTile {
    public DyeColor getColor(Direction var1);

    default public boolean canInteractWith(IEnergyTile iEnergyTile, Direction direction) {
        if (iEnergyTile instanceof IColoredEnergyTile) {
            IColoredEnergyTile iColoredEnergyTile = (IColoredEnergyTile)iEnergyTile;
            DyeColor dyeColor = this.getColor(direction);
            DyeColor dyeColor2 = iColoredEnergyTile.getColor(direction.m_122424_());
            return dyeColor == null || dyeColor2 == null || dyeColor == dyeColor2;
        }
        return true;
    }
}

