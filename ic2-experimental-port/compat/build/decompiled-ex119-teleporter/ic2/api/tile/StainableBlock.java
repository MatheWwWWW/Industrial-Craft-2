/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.Level
 */
package ic2.api.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public interface StainableBlock {
    public DyeColor getColor(Level var1, BlockPos var2, Direction var3);

    public boolean setColor(Level var1, BlockPos var2, Direction var3, DyeColor var4);
}

