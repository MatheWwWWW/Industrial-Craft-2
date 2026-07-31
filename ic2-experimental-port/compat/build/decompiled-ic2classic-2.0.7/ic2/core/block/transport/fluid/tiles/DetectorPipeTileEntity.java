/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.tiles.PipeTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DetectorPipeTileEntity
extends PipeTileEntity
implements ITickListener,
IRedstoneProvider,
IRedstoneListener,
ITileActivityProvider {
    long lastFluid = -1L;

    public DetectorPipeTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.DETECTOR_PIPE;
    }

    @Override
    public boolean needsUpdateTick() {
        return this.isSimulating();
    }

    @Override
    public void onTick() {
        if (this.clock(20)) {
            long out = FluidNet.INSTANCE.getStats(this).getOut();
            this.setActive(this.lastFluid != out && out > 0L);
            this.lastFluid = out;
        }
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }

    @Override
    public int getCommonSignalStrength(Direction side) {
        return this.isActive() ? 15 : 0;
    }
}

