/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.base.tiles.impls;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseTransformerTileEntity
extends BaseTileEntity
implements IEnergySink,
IMultiEnergySource,
ITickListener,
IEUStorage,
IWrenchableTile,
ITileActivityProvider {
    public int lowOutput;
    public int highOutput;
    public int maxEnergy;
    @NetworkInfo
    public int energy = 0;
    public boolean addedToEnergyNet = false;

    public BaseTransformerTileEntity(BlockPos pos, BlockState state, int low, int high, int max) {
        super(pos, state);
        this.lowOutput = low;
        this.highOutput = high;
        this.maxEnergy = max;
        this.addGuiFields("energy");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, 0);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        EnergyNet.INSTANCE.updateTile(this);
    }

    @Override
    public boolean setActive(boolean isActive) {
        if (super.setActive(isActive)) {
            EnergyNet.INSTANCE.updateTile(this);
            return true;
        }
        return false;
    }

    @Override
    public int getMaxEU() {
        return this.maxEnergy;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getTier() {
        return this.getSinkTier();
    }

    @Override
    public void onTick() {
        this.handleRedstone();
        this.setActive(this.isRedstonePowered());
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !this.addedToEnergyNet) {
            this.addedToEnergyNet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && this.addedToEnergyNet) {
            this.addedToEnergyNet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return this.getFacing() != dir;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return this.isActive() == (this.getFacing() != side);
    }

    @Override
    public int getSourceTier() {
        return EnergyNet.INSTANCE.getTierFromPower(this.isActive() ? this.highOutput : this.lowOutput);
    }

    @Override
    public int getMaxEnergyOutput() {
        return this.isActive() ? this.highOutput : this.lowOutput;
    }

    @Override
    public int getProvidedEnergy() {
        int provided = this.isActive() ? this.highOutput : this.lowOutput;
        return this.energy >= provided ? provided : 0;
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.energy = Math.max(0, this.energy - consumed);
        this.updateGuiField("energy");
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return this.isActive() == (this.getFacing() == side);
    }

    @Override
    public boolean hasMultiplePackets() {
        return !this.isActive();
    }

    @Override
    public int getPacketCount() {
        return !this.isActive() ? 4 : 1;
    }

    @Override
    public int getSinkTier() {
        return EnergyNet.INSTANCE.getTierFromPower(this.isActive() ? this.lowOutput : this.highOutput);
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if ((this.isActive() ? this.lowOutput : this.highOutput) < amount || amount <= 0) {
            return amount;
        }
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
        }
        return amount - added;
    }
}

