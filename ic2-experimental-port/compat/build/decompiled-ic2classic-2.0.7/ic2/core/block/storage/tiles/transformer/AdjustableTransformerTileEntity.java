/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles.transformer;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkClientEventListener;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.container.AdjustableTransformerContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class AdjustableTransformerTileEntity
extends BaseTileEntity
implements IMultiEnergySource,
IEnergySink,
IEUStorage,
ITileGui,
INetworkClientEventListener,
IWrenchableTile,
ICopyableSettings {
    @NetworkInfo
    public int energy;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_8)
    public int packetCount = 1;
    @NetworkInfo(value=NetworkInfo.BitLevel.BIT_24)
    public int energyPacket = 32;
    @NetworkInfo
    public int maxEnergy = 256;
    public boolean addedToEnergyNet = false;

    public AdjustableTransformerTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("energy", "maxEnergy", "packetCount", "energyPacket");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.TRANSFORMER_ADJUSTABLE;
    }

    @Override
    public boolean isAllowingUI() {
        return false;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new AdjustableTransformerContainer(this, player, windowID);
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "energy", this.energy, 0);
        NBTUtils.putInt(compound, "max_energy", this.maxEnergy, 256);
        NBTUtils.putInt(compound, "packets", this.packetCount, 1);
        NBTUtils.putInt(compound, "packet_energy", this.energyPacket, 32);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = NBTUtils.getInt(compound, "energy", 0);
        this.maxEnergy = NBTUtils.getInt(compound, "max_energy", 256);
        this.packetCount = NBTUtils.getInt(compound, "packets", 1);
        this.energyPacket = NBTUtils.getInt(compound, "packet_energy", 32);
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        NBTUtils.putInt(compound, "max_energy", this.maxEnergy, 256);
        NBTUtils.putInt(compound, "packets", this.packetCount, 1);
        NBTUtils.putInt(compound, "packet_energy", this.energyPacket, 32);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        this.maxEnergy = NBTUtils.getInt(compound, "max_energy", 256);
        this.packetCount = NBTUtils.getInt(compound, "packets", 1);
        this.energyPacket = NBTUtils.getInt(compound, "packet_energy", 32);
        this.updateGuiFields("energyPacket", "maxEnergy", "packetCount");
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 0) {
            this.packetCount = Mth.m_14045_((int)value, (int)1, (int)32);
            this.updateGuiField("packetCount");
        }
        if (key == 1) {
            this.energyPacket = Mth.m_14045_((int)value, (int)1, (int)32768);
            this.maxEnergy = this.energyPacket * 32;
            this.updateGuiFields("energyPacket", "maxEnergy");
        }
    }

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        EnergyNet.INSTANCE.updateTile(this);
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
    public double getDropRate(Player player) {
        return 0.8;
    }

    @Override
    public int getSourceTier() {
        return EnergyNet.INSTANCE.getTierFromPower(this.energyPacket);
    }

    @Override
    public int getMaxEnergyOutput() {
        return Short.MAX_VALUE;
    }

    @Override
    public int getProvidedEnergy() {
        return this.energy < this.energyPacket ? 0 : this.energyPacket;
    }

    @Override
    public void consumeEnergy(int consumed) {
        this.energy -= consumed;
        this.updateGuiField("energy");
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return this.getFacing() != side;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return this.getFacing() == side;
    }

    @Override
    public int getStoredEU() {
        return this.energy;
    }

    @Override
    public int getMaxEU() {
        return this.maxEnergy;
    }

    @Override
    public int getTier() {
        return 6;
    }

    @Override
    public int getSinkTier() {
        return 6;
    }

    @Override
    public int getRequestedEnergy() {
        return Math.max(0, this.maxEnergy - this.energy);
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount <= 0) {
            return amount;
        }
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
        }
        return amount - added;
    }

    @Override
    public boolean hasMultiplePackets() {
        return this.packetCount > 1;
    }

    @Override
    public int getPacketCount() {
        return this.packetCount;
    }
}

