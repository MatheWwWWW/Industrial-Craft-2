/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.Level
 */
package ic2.api.info;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public interface ILocatable {
    public BlockPos getPosition();

    public Level getWorldObj();
}

