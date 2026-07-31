/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.wiring.tileentity;

import ic2.core.block.wiring.tileentity.TileEntityElectricBlock;
import ic2.core.ref.Ic2BlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityElectricBatBox
extends TileEntityElectricBlock {
    public TileEntityElectricBatBox(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.BATBOX, blockPos, blockState, 1, 32, 40000);
    }
}

