/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.storage.tiles.tank;

import ic2.api.util.DirectionList;
import ic2.api.util.ILocation;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.multiblock.IMultiBlockFluidExporter;
import ic2.core.block.storage.tiles.tank.BaseValveTileEntity;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PushingValveTileEntity
extends BaseValveTileEntity
implements ILocation,
ITickListener,
ISimpleFluidSource {
    ICache<IFluidHandler> cache = new FilteredCapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER, x$0 -> this.isTile((BlockEntity)x$0));
    boolean addedToFluidNet = false;

    public PushingValveTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.cache);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.PUSHING_VALVE;
    }

    @Override
    public void onTick() {
        BlockEntity tile = this.getMaster();
        if (tile instanceof IMultiBlockFluidExporter) {
            ((IMultiBlockFluidExporter)tile).exportFluids(this.cache, this, null);
        }
    }

    @Override
    public void onLoaded() {
        if (!this.addedToFluidNet && this.isSimulating()) {
            this.addedToFluidNet = true;
            FluidNet.INSTANCE.addPipe(this);
        }
        super.onLoaded();
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToFluidNet && this.isSimulating()) {
            this.addedToFluidNet = false;
            FluidNet.INSTANCE.removePipe(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return false;
    }
}

