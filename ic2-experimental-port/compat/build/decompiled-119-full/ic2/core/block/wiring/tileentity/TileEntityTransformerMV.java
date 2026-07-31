/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring.tileentity;

import ic2.core.block.wiring.tileentity.TileEntityTransformer;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityTransformerMV
extends TileEntityTransformer {
    public TileEntityTransformerMV(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MV_TRANSFORMER, blockPos, blockState, 2);
    }
}

