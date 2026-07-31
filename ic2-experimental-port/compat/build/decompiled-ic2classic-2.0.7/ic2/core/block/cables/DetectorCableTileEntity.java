/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.cables;

import ic2.api.energy.EnergyNet;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.base.features.redstone.IRedstoneProvider;
import ic2.core.block.cables.CableTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class DetectorCableTileEntity
extends CableTileEntity
implements ITickListener,
IRedstoneProvider,
IRedstoneListener,
ITileActivityProvider {
    long lastEU = -1L;

    public DetectorCableTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0);
    }

    @Override
    public void onTick() {
        if (this.clock(20)) {
            long out = EnergyNet.INSTANCE.getStats(this).getEnergyOut();
            this.setActive(this.lastEU != out && out > 0L);
            this.lastEU = out;
        }
    }

    @Override
    public double getConductionLoss() {
        return 0.5;
    }

    @Override
    public int getInsulationEnergyAbsorption() {
        return 9001;
    }

    @Override
    public int getInsulationBreakdownEnergy() {
        return 9001;
    }

    @Override
    public int getConductorBreakdownEnergy() {
        return 2049;
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.DETECTOR_CABLE;
    }

    @Override
    public int getCommonSignalStrength(Direction side) {
        return this.isActive() ? 15 : 0;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }
}

