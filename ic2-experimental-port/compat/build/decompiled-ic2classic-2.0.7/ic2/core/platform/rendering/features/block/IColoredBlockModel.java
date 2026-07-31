/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.platform.rendering.features.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public interface IColoredBlockModel {
    public int getTintedIndexFor(BlockState var1, Direction var2, int var3);
}

