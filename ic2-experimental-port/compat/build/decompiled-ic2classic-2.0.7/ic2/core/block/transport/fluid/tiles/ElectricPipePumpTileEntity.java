/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.util.Mth
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.fluids.capability.IFluidHandler
 */
package ic2.core.block.transport.fluid.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.transport.fluid.tiles.SimplePipePumpTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ElectricPipePumpTileEntity
extends SimplePipePumpTileEntity
implements IEnergySink,
IEUStorage {
    public int energy;
    boolean addedToEnergyNet;

    public ElectricPipePumpTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.ELECTRIC_PIPE_PUMP;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return true;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getMaxEU() {
        return 10000;
    }

    @Override
    public int getTier() {
        return 4;
    }

    @Override
    public int getSinkTier() {
        return 4;
    }

    @Override
    public int getRequestedEnergy() {
        return 10000 - this.energy;
    }

    @Override
    public void onLoaded() {
        if (!this.addedToEnergyNet && this.isSimulating()) {
            this.addedToEnergyNet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
        super.onLoaded();
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.addedToEnergyNet && this.isSimulating()) {
            this.addedToEnergyNet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public int getDrainAmount() {
        return Math.min(10000, this.energy * 100);
    }

    @Override
    public int getPressure() {
        return 250;
    }

    @Override
    public void drain(IFluidHandler handler, int draining) {
        super.drain(handler, draining);
        this.energy -= Mth.m_14167_((float)((float)draining * 0.01f));
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > 10000 || amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, 10000 - this.energy);
        if (added > 0) {
            this.energy += added;
        }
        return amount - added;
    }
}

