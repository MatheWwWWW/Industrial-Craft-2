/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generators.tiles;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.ISteamReactorChamber;
import ic2.api.util.DirectionList;
import ic2.core.block.base.tiles.impls.BaseReactorChamberTileEntity;
import ic2.core.block.transport.fluid.graph.FluidNet;
import ic2.core.block.transport.fluid.graph.IFluidPipe;
import ic2.core.platform.registries.IC2Tiles;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SteamReactorChamberTileEntity
extends BaseReactorChamberTileEntity
implements ISteamReactorChamber,
IFluidPipe {
    public IReactor reactor;
    boolean addedToFluidNet = false;

    public SteamReactorChamberTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public IReactor getReactor() {
        if (this.reactor != null && ((BlockEntity)this.reactor).m_58901_()) {
            this.reactor = null;
        }
        if (this.reactor == null) {
            for (Direction dir : DirectionList.ALL) {
                BlockEntity tile = DirectionList.getNeighborTile(this, dir);
                if (!(tile instanceof ISteamReactor)) continue;
                this.reactor = (IReactor)tile;
            }
        }
        if (this.reactor == null) {
            this.m_58900_().m_60690_(this.m_58904_(), this.m_58899_(), Blocks.f_50016_, this.m_58899_(), false);
        }
        return this.reactor;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.STEAM_CHAMBER;
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (!this.addedToFluidNet && this.isSimulating()) {
            this.addedToFluidNet = true;
            FluidNet.INSTANCE.addPipe(this);
        }
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
    public boolean hasAnchor(Direction side) {
        return false;
    }

    @Override
    public boolean addAnchor(Direction side) {
        return false;
    }

    @Override
    public boolean removeAnchor(Direction side) {
        return false;
    }

    @Override
    public boolean canPushFluid(Direction dir) {
        return true;
    }

    @Override
    public boolean canReceiveFluid(Direction dir) {
        return true;
    }

    @Override
    public List<IFluidPipe.FluidOutput> getOutputs() {
        return Collections.emptyList();
    }

    @Override
    public boolean isFluidAcceptor() {
        return true;
    }

    @Override
    public Iterable<Direction> getEmitterSources() {
        return ObjectLists.emptyList();
    }
}

