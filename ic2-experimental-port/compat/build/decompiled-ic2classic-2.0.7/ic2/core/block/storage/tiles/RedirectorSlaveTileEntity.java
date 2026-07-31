/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntArrayList
 *  it.unimi.dsi.fastutil.ints.IntCollection
 *  it.unimi.dsi.fastutil.ints.IntList
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.ints.IntSets
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 */
package ic2.core.block.storage.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchRemovable;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.tiles.RedirectorMasterTileEntity;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class RedirectorSlaveTileEntity
extends BaseTileEntity
implements IMultiEnergySource,
ITickListener,
IWrenchRemovable {
    public static final IntSet EMPTY_REMOVER = IntSets.singleton((int)0);
    boolean addedToEnet;
    IntList packetEnergy = new IntArrayList();
    IntList addedPackets = new IntArrayList();
    int energyCache = 0;
    boolean acceptsEnergy = true;
    boolean sent = false;
    int index = 0;
    int lastIndex = 0;

    public RedirectorSlaveTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    public void acceptPacket(int packet) {
        if (this.sent) {
            this.addedPackets.add(packet);
            return;
        }
        this.packetEnergy.add(packet);
    }

    public boolean canAcceptPacket() {
        return this.acceptsEnergy;
    }

    @Override
    public void onTick() {
        this.packetEnergy.removeAll((IntCollection)EMPTY_REMOVER);
        this.acceptsEnergy = this.packetEnergy.size() == 0 && this.energyCache < 32;
        this.packetEnergy.addAll(this.addedPackets);
        this.addedPackets.clear();
        this.index = 0;
        this.lastIndex = 0;
        this.sent = false;
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        NBTUtils.putInt(compound, "cache", this.energyCache, 0);
        NBTUtils.putBoolean(compound, "accepts", this.acceptsEnergy, false);
        NBTUtils.putIntArray(compound, "packets", this.packetEnergy.toIntArray());
        NBTUtils.putIntArray(compound, "addedpackets", this.addedPackets.toIntArray());
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energyCache = compound.m_128451_("cache");
        this.acceptsEnergy = compound.m_128471_("accepts");
        this.packetEnergy.addAll((IntList)IntArrayList.wrap((int[])compound.m_128465_("packets")));
        this.addedPackets.addAll((IntList)IntArrayList.wrap((int[])compound.m_128465_("addedpackets")));
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.REDIRECTOR_SLAVE;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.8;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
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
        BlockEntity tile;
        if (this.addedToEnet && this.isSimulating()) {
            this.addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        if (this.isSimulating() && (tile = DirectionList.getNeighborTile(this, this.getFacing())) instanceof RedirectorMasterTileEntity) {
            ((RedirectorMasterTileEntity)tile).onNodeRemoved();
        }
        super.onUnloaded(chunk);
    }

    @Override
    public boolean canEmitEnergy(IEnergyAcceptor acceptor, Direction side) {
        return side != this.getFacing();
    }

    @Override
    public int getSourceTier() {
        return 2;
    }

    @Override
    public int getMaxEnergyOutput() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getProvidedEnergy() {
        this.sent = true;
        if (this.index != this.lastIndex) {
            this.lastIndex = this.index;
        }
        ++this.index;
        return this.lastIndex >= this.packetEnergy.size() ? Math.min(32, this.energyCache) : this.packetEnergy.getInt(this.lastIndex);
    }

    @Override
    public void consumeEnergy(int consumed) {
        if (this.lastIndex >= this.packetEnergy.size()) {
            this.energyCache -= consumed;
            return;
        }
        this.packetEnergy.set(this.lastIndex, this.packetEnergy.getInt(this.lastIndex) - consumed);
    }

    @Override
    public void onPacketFailed() {
        if (this.lastIndex >= this.packetEnergy.size()) {
            return;
        }
        this.energyCache += this.packetEnergy.getInt(this.lastIndex);
        this.packetEnergy.set(this.lastIndex, 0);
    }

    @Override
    public boolean hasMultiplePackets() {
        return true;
    }

    @Override
    public int getPacketCount() {
        return (this.energyCache == 0 ? 0 : 1) + this.packetEnergy.size();
    }
}

