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

public interface IRotatableBlock {
    public boolean hasRotation(BlockState var1);

    public Direction getRotation(BlockState var1);
}

