/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring.tileentity;

import ic2.core.block.wiring.tileentity.TileEntityTransformer;
import ic2.core.profile.NotClassic;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@NotClassic
public class TileEntityTransformerEV
extends TileEntityTransformer {
    public TileEntityTransformerEV(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.EV_TRANSFORMER, blockPos, blockState, 4);
    }
}

