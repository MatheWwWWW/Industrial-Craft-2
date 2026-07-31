/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tank;

import ic2.core.block.storage.tank.TileEntityTank;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityBronzeTank
extends TileEntityTank {
    public TileEntityBronzeTank(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.BRONZE_TANK, blockPos, blockState, 32);
    }
}

