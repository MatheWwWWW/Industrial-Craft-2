/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package ic2.api.info;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILocatable {
    public BlockPos getPosition();

    public World getWorldObj();
}

