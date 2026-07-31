/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.core.block.steam;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiBlockController {
    public World getWorld();

    public BlockPos getPos();

    public boolean isInvalid();

    public boolean hasValidStructure();

    public boolean isFormed();
}

