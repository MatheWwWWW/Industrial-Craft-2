/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.generators.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergyTile;
import ic2.api.reactor.IReactorChamber;
import ic2.api.reactor.ISteamReactorChamber;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.util.DirectionList;
import ic2.core.IC2;
import ic2.core.block.base.tiles.impls.BaseNuclearReactorTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricNuclearReactorTileEntity
extends BaseNuclearReactorTileEntity
implements IEnergySource,
IMultiEnergyTile,
IEUProducer {
    boolean addedToEnet = false;

    public ElectricNuclearReactorTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return true;
    }

    @Override
    public int getSourceTier() {
        return EnergyNet.INSTANCE.getTierFromPower(this.getProvidedEnergy());
    }

    @Override
    public int getMaxEnergyOutput() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getProvidedEnergy() {
        return (int)(this.output * (float)IC2.CONFIG.reactorOutput.get());
    }

    @Override
    public float getEUProduction() {
        return this.getProvidedEnergy();
    }

    @Override
    public void consumeEnergy(int consumed) {
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.NUCLEAR_REACTOR;
    }

    @Override
    public boolean isValidChamber(BlockEntity tile) {
        return super.isValidChamber(tile) && !(tile instanceof ISteamReactorChamber);
    }

    @Override
    public List<IEnergyTile> getTiles() {
        ObjectList subTiles = CollectionUtils.createList();
        subTiles.add((ElectricNuclearReactorTileEntity)this);
        for (Direction dir : DirectionList.ALL) {
            BlockEntity tile = DirectionList.getNeighborTile(this, dir);
            if (!(tile instanceof IReactorChamber) || !(tile instanceof IEnergyTile)) continue;
            subTiles.add((IEnergyTile)((IEnergyTile)tile));
        }
        return subTiles;
    }

    @Override
    public void onChamberRefresh() {
        boolean memory = this.refreshChambers;
        super.onChamberRefresh();
        if (memory) {
            EnergyNet.INSTANCE.updateTile(this);
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (!this.addedToEnet && this.isSimulating()) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnet && this.isSimulating()) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }
}

