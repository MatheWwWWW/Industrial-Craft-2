/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class Pipes {

    public static class HighCapacityPipeTileEntity
    extends PipeTileEntity {
        public HighCapacityPipeTileEntity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        public int getTransferRate() {
            return 1000;
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.HIGH_CAPACITY_PIPE;
        }
    }

    public static class HighPressurePipeTileEntity
    extends PipeTileEntity {
        public HighPressurePipeTileEntity(BlockPos pos, BlockState state) {
            super(pos, state);
        }

        @Override
        public int getPressure() {
            return 100;
        }

        @Override
        public BlockEntityType<?> createType() {
            return IC2Tiles.HIGH_PRESSURE_PIPE;
        }
    }
}

