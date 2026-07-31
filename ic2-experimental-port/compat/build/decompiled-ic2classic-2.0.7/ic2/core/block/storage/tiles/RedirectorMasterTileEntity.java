/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.block.storage.tiles;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.api.tiles.ICopyableSettings;
import ic2.api.util.DirectionList;
import ic2.core.block.base.cache.ICache;
import ic2.core.block.base.cache.TileCache;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.storage.container.MasterNodeContainer;
import ic2.core.block.storage.tiles.RedirectorSlaveTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.networking.buffers.data.NodeBuffer;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.helpers.NBTUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;

public class RedirectorMasterTileEntity
extends BaseTileEntity
implements IEnergySink,
ITileGui,
INetworkDataEventListener,
IWrenchableTile,
ICopyableSettings {
    boolean addedToEnet;
    int energyStorage;
    @NetworkInfo
    public int[] shares = new int[6];
    @NetworkInfo
    public byte sides = 0;
    ICache<RedirectorSlaveTileEntity> caches = new TileCache<RedirectorSlaveTileEntity>(this, DirectionList.ALL, RedirectorSlaveTileEntity.class);

    public RedirectorMasterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addCaches(this.caches);
        this.addNetworkFields("shares", "sides");
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        long data = 0L;
        for (int i = 0; i < 6; ++i) {
            data |= ((long)this.shares[i] & 0xFFL) << i * 8;
        }
        compound.m_128356_("shares", data);
        NBTUtils.putInt(compound, "cache", this.energyStorage, 0);
        compound.m_128344_("sides", this.sides);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        long value = compound.m_128454_("shares");
        for (int i = 0; i < 6; ++i) {
            this.shares[i] = (int)(value >> i * 8 & 0xFFL);
        }
        this.energyStorage = compound.m_128451_("cache");
        this.sides = compound.m_128445_("sides");
    }

    @Override
    public void saveSettings(CompoundTag compound) {
        long data = 0L;
        for (int i = 0; i < 6; ++i) {
            data |= ((long)this.shares[i] & 0xFFL) << i * 8;
        }
        compound.m_128356_("shares", data);
        compound.m_128344_("sides", this.sides);
    }

    @Override
    public void loadSettings(CompoundTag compound) {
        if (this.sides != compound.m_128451_("sides")) {
            return;
        }
        long value = compound.m_128454_("shares");
        int[] newShares = new int[6];
        int total = 0;
        for (int i = 0; i < 6; ++i) {
            newShares[i] = (int)(value >> i * 8 & 0xFFL);
            total += newShares[i];
        }
        if (total == 100) {
            this.shares = newShares;
            this.updateGuiField("shares");
        }
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.REDIRECTOR_MASTER;
    }

    @Override
    public boolean canSetFacing(Direction dir) {
        return dir != this.getFacing();
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 0.8;
    }

    public void onNodeRemoved() {
        this.onBlockUpdate(Blocks.f_50016_, this.m_58899_());
    }

    @Override
    protected void onCachesUpdated() {
        int side = this.caches.getPresentSides().getCode();
        if (side != this.sides) {
            this.sides = (byte)side;
            this.updateTileField("sides");
        }
        int total = 0;
        for (int i = 0; i < 6; ++i) {
            total += this.shares[i];
        }
        if (total != 0 || this.caches.isEmpty()) {
            return;
        }
        this.shares[this.caches.getPresentSides().getRandomFacing().m_122411_()] = 100;
        this.updateTileField("shares");
    }

    @Override
    public void onLoaded() {
        int side;
        super.onLoaded();
        if (!this.addedToEnet && this.isSimulating()) {
            this.addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
        if ((side = this.caches.getPresentSides().getCode()) != this.sides) {
            this.sides = (byte)side;
            this.updateTileField("sides");
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

    @Override
    public void setFacing(Direction facing) {
        super.setFacing(facing);
        if (this.isSimulating()) {
            EnergyNet.INSTANCE.updateTile(this);
        }
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return side == this.getFacing();
    }

    @Override
    public int getSinkTier() {
        return 13;
    }

    @Override
    public int getRequestedEnergy() {
        int found = 0;
        for (Direction dir : this.caches) {
            RedirectorSlaveTileEntity neighbor = this.caches.getHandler(dir);
            if (neighbor == null || !neighbor.canAcceptPacket()) continue;
            ++found;
        }
        return found == 0 ? 0 : Integer.MAX_VALUE;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        return this.sendToOthers(amount + this.energyStorage);
    }

    public int sendToOthers(int energy) {
        if (energy < 6) {
            this.energyStorage += energy - this.energyStorage;
            return 0;
        }
        int found = 0;
        float percentage = 0.0f;
        DirectionList list = DirectionList.EMPTY;
        for (Direction direction : this.caches) {
            RedirectorSlaveTileEntity neighbor = this.caches.getHandler(direction);
            if (neighbor == null || !neighbor.canAcceptPacket()) continue;
            ++found;
            list = list.add(direction);
            percentage += (float)this.shares[direction.m_122411_()];
        }
        if (found == 0) {
            return energy - this.energyStorage;
        }
        percentage /= 100.0f;
        int consumed = 0;
        for (Direction dir : list) {
            int packet;
            RedirectorSlaveTileEntity neighbor = this.caches.getHandler(dir);
            if (neighbor == null || (packet = Math.min(energy - consumed, (int)Math.ceil((float)energy * ((float)this.shares[dir.m_122411_()] / 100.0f / percentage)))) <= 0) continue;
            neighbor.acceptPacket(packet);
            consumed += packet;
        }
        int n = this.energyStorage;
        int internal = Math.min(consumed, this.energyStorage);
        this.energyStorage -= internal;
        return energy - consumed - (n - this.energyStorage);
    }

    @Override
    public boolean hasGui(Player player, InteractionHand hand, Direction side) {
        return player.m_21120_(hand).m_41720_() != IC2Blocks.REDIRECTOR_SLAVE.m_5456_();
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new MasterNodeContainer(this, player, windowID);
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        if (target.isDedicatedServer() && data instanceof NodeBuffer) {
            int[] values = ((NodeBuffer)data).getData();
            if (values.length != 6) {
                return;
            }
            int total = 0;
            for (int i = 0; i < 6; ++i) {
                total += values[i];
            }
            if (total == 100) {
                this.shares = values;
                this.updateTileField("shares");
            }
        }
    }
}

