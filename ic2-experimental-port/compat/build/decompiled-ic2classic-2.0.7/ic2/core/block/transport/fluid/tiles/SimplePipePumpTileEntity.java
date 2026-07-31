/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.common.capabilities.ForgeCapabilities
 *  net.minecraftforge.fluids.capability.IFluidHandler
 *  net.minecraftforge.fluids.capability.IFluidHandler$FluidAction
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.FilteredCapabilityCache;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.block.transport.fluid.graph.ISimpleFluidSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class SimplePipePumpTileEntity
extends BaseTileEntity
implements ISimpleFluidSource,
ITickListener {
    protected ICache<IFluidHandler> fluidCache = new FilteredCapabilityCache<IFluidHandler>(this, DirectionList.ALL, ForgeCapabilities.FLUID_HANDLER, T -> !(T instanceof IFluidPipe));
    boolean addedToFluidNet = false;

    public SimplePipePumpTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.fluidCache);
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
    public void onTick() {
        if (this.shouldNotTick()) {
            return;
        }
        IFluidHandler handler = this.fluidCache.getHandler(this.getFacing());
        if (handler == null) {
            this.removeFromTick();
            return;
        }
        int amount = this.getDrainAmount();
        if (amount <= 0) {
            return;
        }
        if ((amount = FluidNet.INSTANCE.sendFluid(this, null, handler.drain(amount, IFluidHandler.FluidAction.SIMULATE), this.getPressure())) <= 0) {
            return;
        }
        this.drain(handler, amount);
    }

    public boolean shouldNotTick() {
        return this.invClock(20);
    }

    public int getDrainAmount() {
        return 400;
    }

    public int getPressure() {
        return 100;
    }

    public void drain(IFluidHandler handler, int draining) {
        handler.drain(draining, IFluidHandler.FluidAction.EXECUTE);
    }

    @Override
    protected void onCachesUpdated() {
        if (this.fluidCache.getHandler(this.getFacing()) == null) {
            return;
        }
        this.addToTick();
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return false;
    }
}

