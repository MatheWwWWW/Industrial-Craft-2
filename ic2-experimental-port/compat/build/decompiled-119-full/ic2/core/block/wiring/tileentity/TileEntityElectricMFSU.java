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

public class TileEntityElectricMFSU
extends TileEntityElectricBlock {
    public TileEntityElectricMFSU(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MFSU, blockPos, blockState, 4, 2048, 40000000);
    }

    public static class TileEntityElectricClassicMFSU
    extends TileEntityElectricBlock {
        public TileEntityElectricClassicMFSU(BlockPos blockPos, BlockState blockState) {
            super(Ic2BlockEntities.CLASSIC_MFSU, blockPos, blockState, 3, 512, 10000000);
            this.chargeSlot.setTier(4);
            this.dischargeSlot.setTier(4);
        }
    }
}

