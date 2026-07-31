/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.cables;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.redstone.IRedstoneListener;
import ic2.core.block.cables.CableTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SplitterCableTileEntity
extends CableTileEntity
implements ITickListener,
IRedstoneListener,
ITileActivityProvider {
    boolean dirty = false;

    public SplitterCableTileEntity(BlockPos pos, BlockState state) {
        super(pos, state, 0);
    }

    @Override
    public boolean canDye(int insulation) {
        return false;
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
        return IC2Tiles.SPLITTER_CABLE;
    }

    @Override
    public boolean canInteractWith(IEnergyTile tile, Direction side) {
        if (this.addedToEnergyNet) {
            return super.canInteractWith(tile, side);
        }
        return false;
    }

    @Override
    public void onLoaded() {
        this.addedToEnergyNet = this.isActive();
        super.onLoaded();
        if (this.isActive()) {
            this.addedToEnergyNet = false;
        }
    }

    @Override
    public void onTick() {
        if (this.dirty) {
            this.dirty = false;
            if (this.m_58904_().m_46753_(this.m_58899_()) == this.addedToEnergyNet) {
                if (this.addedToEnergyNet) {
                    this.addedToEnergyNet = false;
                    EnergyNet.INSTANCE.removeTile(this);
                } else {
                    this.addedToEnergyNet = true;
                    EnergyNet.INSTANCE.addTile(this);
                }
            }
            this.setActive(!this.addedToEnergyNet);
        }
    }

    @Override
    public void onBlockUpdate(Block block, BlockPos from) {
        super.onBlockUpdate(block, from);
        this.dirty = true;
    }

    @Override
    public boolean canConnectToRedstone(Direction dir) {
        return true;
    }
}

