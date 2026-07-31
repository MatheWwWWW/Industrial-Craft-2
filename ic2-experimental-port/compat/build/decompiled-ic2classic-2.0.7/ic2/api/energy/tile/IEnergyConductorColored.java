/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.item.DyeColor
 */
package ic2.api.energy.tile;

import ic2.api.energy.tile.IEnergyConductor;
import net.minecraft.world.item.DyeColor;

public interface IEnergyConductorColored
extends IEnergyConductor {
    public DyeColor getColor();
}

