/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.api.distmarker.Dist
 */
package ic2.core.block.machines.tiles.mv;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.network.tile.INetworkClientEventListener;
import ic2.api.network.tile.INetworkDataEventListener;
import ic2.api.tiles.readers.IEUStorage;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.tiles.BaseTileEntity;
import ic2.core.block.machines.containers.mv.BaseTeleporterContainer;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.networking.buffers.data.BaseTeleporterBuffer;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.collection.SerializableCollection;
import ic2.core.utils.helpers.TeleportUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;

public class BaseTeleporterTileEntity
extends BaseTileEntity
implements IEnergySink,
IEUStorage,
ITileGui,
INetworkClientEventListener,
INetworkDataEventListener,
IWrenchableTile,
ITileActivityProvider {
    public static final Map<ResourceKey<Level>, Map<String, Set<LocalTarget>>> ACTIVE_TELEPORTERS = Object2ObjectMaps.synchronize(CollectionUtils.createMap());
    public int maxEnergy = 100000;
    @NetworkInfo
    public int energy = 0;
    boolean addedToEnet = false;
    @NetworkInfo
    public String networkID = "Default";
    @NetworkInfo
    public String name = "New Teleporter";
    @NetworkInfo
    public SerializableCollection<LocalTarget> targetList = SerializableCollection.createClientList(this.isRendering(), LocalTarget::new);
    LocalTarget myTarget;

    public BaseTeleporterTileEntity(BlockPos pos, BlockState state) {
        super(pos, state);
        this.addGuiFields("energy", "targetList");
        this.addNetworkFields("networkID", "name");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
    }

    @Override
    public void m_183515_(CompoundTag compound) {
        super.m_183515_(compound);
        compound.m_128405_("energy", this.energy);
        compound.m_128359_("name", this.name);
        compound.m_128359_("network", this.networkID);
    }

    @Override
    public void m_142466_(CompoundTag compound) {
        super.m_142466_(compound);
        this.energy = compound.m_128451_("energy");
        this.networkID = compound.m_128461_("network");
        this.name = compound.m_128461_("name");
    }

    @Override
    public boolean isAllowingUI() {
        return false;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new BaseTeleporterContainer(this, player, windowID);
    }

    @Override
    public BlockEntityType<?> createType() {
        return IC2Tiles.BASE_TELEPORTER;
    }

    public Set<LocalTarget> getTargets() {
        Set<LocalTarget> set;
        Map<String, Set<LocalTarget>> map = ACTIVE_TELEPORTERS.get(this.m_58904_().m_46472_());
        if (map == null) {
            map = CollectionUtils.createMap();
            ACTIVE_TELEPORTERS.put((ResourceKey<Level>)this.m_58904_().m_46472_(), map);
        }
        if ((set = map.get(this.networkID)) == null) {
            set = CollectionUtils.createLinkedSet();
            map.put(this.networkID, set);
        }
        return set;
    }

    public void syncTargets() {
        ObjectLinkedOpenHashSet globalTargets = new ObjectLinkedOpenHashSet(this.getTargets());
        globalTargets.remove(this.myTarget);
        Iterator iter = globalTargets.iterator();
        while (iter.hasNext()) {
            if (((LocalTarget)iter.next()).isInReach(this.myTarget)) continue;
            iter.remove();
        }
        Set localTargets = (Set)this.targetList.getCasted();
        if (localTargets.retainAll((Collection<?>)globalTargets) || localTargets.addAll(globalTargets)) {
            this.updateTileField("targetList");
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating()) {
            if (!this.addedToEnet) {
                this.addedToEnet = true;
                EnergyNet.INSTANCE.addTile(this);
            }
            this.myTarget = new LocalTarget(this.m_58899_().m_121878_(), this.name);
            Set<LocalTarget> targets = this.getTargets();
            targets.add(this.myTarget);
            this.notifyTargets(targets);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating()) {
            if (this.addedToEnet) {
                this.addedToEnet = false;
                EnergyNet.INSTANCE.removeTile(this);
            }
            Set<LocalTarget> targets = this.getTargets();
            targets.remove(this.myTarget);
            this.notifyTargets(targets);
        }
        super.onUnloaded(chunk);
    }

    public void notifyTargets(Set<LocalTarget> targets) {
        for (LocalTarget target : targets) {
            BlockEntity tile;
            BlockPos pos = target.getPos();
            if (!this.f_58857_.m_46749_(pos) || !((tile = this.f_58857_.m_7702_(pos)) instanceof BaseTeleporterTileEntity)) continue;
            ((BaseTeleporterTileEntity)tile).syncTargets();
        }
    }

    @Override
    public void onDataBufferReceived(Player player, String id, INetworkDataBuffer data, Dist target) {
        if (target.isDedicatedServer() && data instanceof BaseTeleporterBuffer) {
            BaseTeleporterBuffer buffer = (BaseTeleporterBuffer)data;
            switch (buffer.getType()) {
                case 1: {
                    Set<LocalTarget> targets = this.getTargets();
                    targets.remove(this.myTarget);
                    this.notifyTargets(targets);
                    this.networkID = buffer.getNetworkID();
                    this.updateTileField("networkID");
                    targets = this.getTargets();
                    targets.add(this.myTarget);
                    this.notifyTargets(targets);
                    break;
                }
                case 2: {
                    Set<LocalTarget> targets = this.getTargets();
                    targets.remove(this.myTarget);
                    this.notifyTargets(targets);
                    this.name = buffer.getName();
                    this.updateTileField("name");
                    this.myTarget = new LocalTarget(this.m_58899_().m_121878_(), this.name);
                    targets.add(this.myTarget);
                    this.notifyTargets(targets);
                    break;
                }
                case 3: {
                    Set<LocalTarget> targets = this.getTargets();
                    targets.remove(this.myTarget);
                    this.notifyTargets(targets);
                    this.networkID = buffer.getNetworkID();
                    this.name = buffer.getName();
                    this.myTarget = new LocalTarget(this.m_58899_().m_121878_(), this.name);
                    targets = this.getTargets();
                    targets.add(this.myTarget);
                    this.notifyTargets(targets);
                    this.updateTileFields("networkID", "name");
                }
            }
        }
    }

    @Override
    public void onClientDataReceived(Player entity, int key, int value) {
        if (key == 1 && value >= 0) {
            BlockEntity tile;
            if (this.energy < 4000) {
                return;
            }
            LocalTarget[] targets = this.targetList.get().toArray(new LocalTarget[this.targetList.size()]);
            if (targets.length > value && (tile = this.f_58857_.m_7702_(targets[value].getPos())) instanceof BaseTeleporterTileEntity) {
                BaseTeleporterTileEntity base = (BaseTeleporterTileEntity)tile;
                this.energy -= 4000;
                this.setActive(this.energy >= 4000);
                this.updateGuiField("energy");
                entity.m_36222_(IC2Stats.DISTANCE_TELEPORTED, (int)Math.sqrt(this.m_58899_().m_123331_((Vec3i)tile.m_58899_())) * 1000);
                TeleportUtil.teleportEntity((Entity)entity, (ServerLevel)base.m_58904_(), tile.m_58899_(), base.getFacing());
                entity.m_6915_();
            }
        }
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
        return 0.75;
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
        return 2;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter emitter, Direction side) {
        return true;
    }

    @Override
    public int getSinkTier() {
        return 2;
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEnergy - this.energy;
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        if (amount > 128 || amount <= 0) {
            return 0;
        }
        int added = Math.min(amount, this.maxEnergy - this.energy);
        if (added > 0) {
            this.energy += added;
            this.updateGuiField("energy");
            this.setActive(this.energy >= 4000);
            this.handleNonTickComparators();
        }
        return amount - added;
    }

    public static class LocalTarget
    implements INetworkDataBuffer {
        public static final int MAX_DISTANCE = 10000;
        long position;
        String name;

        public LocalTarget() {
        }

        public LocalTarget(long position, String name) {
            this.position = position;
            this.name = name;
        }

        @Override
        public void write(IOutputBuffer buffer) {
            buffer.writeLong(this.position);
            buffer.writeString(this.name);
        }

        @Override
        public void read(IInputBuffer buffer) {
            this.position = buffer.readLong();
            this.name = buffer.readString();
        }

        public CompoundTag write() {
            CompoundTag nbt = new CompoundTag();
            nbt.m_128356_("pos", this.position);
            nbt.m_128359_("name", this.name);
            return nbt;
        }

        public void read(CompoundTag nbt) {
            this.position = nbt.m_128454_("pos");
            this.name = nbt.m_128461_("name");
        }

        public long getPosition() {
            return this.position;
        }

        public String getName() {
            return this.name;
        }

        public BlockPos getPos() {
            return BlockPos.m_122022_((long)this.position);
        }

        public boolean isInReach(LocalTarget target) {
            int z;
            BlockPos from = this.getPos();
            BlockPos to = target.getPos();
            int x = from.m_123341_() - to.m_123341_();
            return x * x + (z = from.m_123343_() - to.m_123343_()) * z <= 10000;
        }
    }
}

