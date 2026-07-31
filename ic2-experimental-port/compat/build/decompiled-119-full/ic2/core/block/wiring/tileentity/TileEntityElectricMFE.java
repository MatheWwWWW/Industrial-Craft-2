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

public class TileEntityElectricMFE
extends TileEntityElectricBlock {
    public TileEntityElectricMFE(BlockPos blockPos, BlockState blockState) {
        super(Ic2BlockEntities.MFE, blockPos, blockState, 3, 512, 4000000);
    }

    public static class TileEntityElectricClassicMFE
    extends TileEntityElectricBlock {
        public TileEntityElectricClassicMFE(BlockPos blockPos, BlockState blockState) {
            super(Ic2BlockEntities.CLASSIC_MFE, blockPos, blockState, 2, 128, 600000);
            this.chargeSlot.setTier(3);
            this.dischargeSlot.setTier(3);
        }
    }
}

