/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  it.unimi.dsi.fastutil.objects.ObjectIterator
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.block.machines.logic.miner;

import ic2.api.items.electric.IMiningDrill;
import ic2.core.block.machines.logic.miner.BlockMiningTarget;
import ic2.core.block.machines.logic.miner.FluidMiningTarget;
import ic2.core.block.machines.logic.miner.IMiningTarget;
import ic2.core.block.machines.tiles.hv.RocketMinerTileEntity;
import ic2.core.block.machines.tiles.lv.MinerTileEntity;
import ic2.core.utils.collection.NBTListWrapper;
import ic2.core.utils.helpers.NBTUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MiningQueue {
    BlockPos masterPos;
    LongSet workers = new LongLinkedOpenHashSet();
    Long2ObjectMap<MinerTileEntity> activeWorkers = new Long2ObjectLinkedOpenHashMap();
    Long2ObjectMap<BooleanSupplier> pumpCache = new Long2ObjectLinkedOpenHashMap();
    LinkedList<IMiningTarget> queue = new LinkedList();
    int targetY;

    public MiningQueue(BlockPos masterPos, CompoundTag data) {
        this(masterPos);
        this.workers.addAll((LongCollection)LongArrayList.wrap((long[])data.m_128467_("known_workers")));
        this.targetY = data.m_128451_("yLevel");
        for (CompoundTag nbt : NBTListWrapper.wrap(data.m_128437_("queue", 10), CompoundTag.class)) {
            this.queue.add(nbt.m_128445_("type") == 1 ? new FluidMiningTarget(nbt) : new BlockMiningTarget(nbt));
        }
    }

    public MiningQueue(BlockPos masterPos) {
        this.masterPos = masterPos;
        this.targetY = masterPos.m_123342_() - 1;
    }

    public void loadIfPossible(Level level) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        LongIterator iter = this.workers.iterator();
        while (iter.hasNext()) {
            BlockEntity entity;
            if (!level.m_46749_((BlockPos)pos.m_122188_(iter.nextLong())) || !((entity = level.m_7702_((BlockPos)pos)) instanceof MinerTileEntity)) continue;
            MinerTileEntity miner = (MinerTileEntity)entity;
            if (entity instanceof RocketMinerTileEntity) continue;
            miner.setQueue(this);
            this.pumpCache.put(pos.m_121878_(), miner::hasPumps);
        }
    }

    public void addWorker(MinerTileEntity tile) {
        if (!tile.setMaster(this.masterPos, this)) {
            return;
        }
        this.workers.add(tile.getPosition().m_121878_());
        this.pumpCache.put(tile.m_58899_().m_121878_(), tile::hasPumps);
    }

    public void removeWorker(MinerTileEntity tile) {
        this.workers.remove(tile.getPosition().m_121878_());
        this.pumpCache.remove(tile.getPosition().m_121878_());
    }

    public void claimTarget(BlockPos pos, MinerTileEntity tile) {
        this.activeWorkers.putIfAbsent(pos.m_121878_(), (Object)tile);
    }

    public void releaseTarget(BlockPos pos, MinerTileEntity tile) {
        this.activeWorkers.remove(pos.m_121878_(), (Object)tile);
    }

    public boolean isAssisting(BlockPos pos, MinerTileEntity tile) {
        MinerTileEntity mine = (MinerTileEntity)this.activeWorkers.get(pos.m_121878_());
        return mine != null && mine != tile;
    }

    public void assist(MinerTileEntity tile, BlockPos pos, int extraProgress) {
        MinerTileEntity mine = (MinerTileEntity)this.activeWorkers.get(pos.m_121878_());
        if (mine != null) {
            mine.assistMiner(tile, extraProgress);
        }
    }

    public boolean hasPumps() {
        for (BooleanSupplier prov : this.pumpCache.values()) {
            if (!prov.getAsBoolean()) continue;
            return true;
        }
        return false;
    }

    public boolean assistRandomly(MinerTileEntity tile, ItemStack stack, IMiningDrill drill) {
        if (this.activeWorkers.isEmpty()) {
            return false;
        }
        for (MinerTileEntity other : this.activeWorkers.values()) {
            if (!other.canAssist(tile)) continue;
            other.assistMiner(tile, stack, drill);
            return true;
        }
        return false;
    }

    public void onUnloaded(BlockPos pos) {
        ObjectIterator iter = this.activeWorkers.values().iterator();
        while (iter.hasNext()) {
            if (!((MinerTileEntity)iter.next()).m_58899_().equals((Object)pos)) continue;
            iter.remove();
        }
    }

    public int size() {
        return this.workers.size();
    }

    public void remove(Level level) {
        this.activeWorkers.clear();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        LongIterator iter = this.workers.iterator();
        while (iter.hasNext()) {
            BlockEntity entity = level.m_7702_((BlockPos)pos.m_122188_(iter.nextLong()));
            if (!(entity instanceof MinerTileEntity)) continue;
            MinerTileEntity miner = (MinerTileEntity)entity;
            miner.clear(this.masterPos);
        }
        this.workers.clear();
        this.pumpCache.clear();
    }

    public LinkedList<IMiningTarget> getTaskQueue() {
        return this.queue;
    }

    public BlockPos getMasterPos() {
        return this.masterPos;
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        NBTUtils.putLongArray(tag, "known_workers", this.workers.toLongArray());
        tag.m_128405_("yLevel", this.targetY);
        ListTag list = new ListTag();
        for (IMiningTarget target : this.queue) {
            CompoundTag nbt = target.save();
            nbt.m_128344_("type", target.getID());
            list.add((Object)nbt);
        }
        if (!list.isEmpty()) {
            tag.m_128365_("queue", (Tag)list);
        }
        return tag;
    }

    public int getYLevel() {
        return this.targetY;
    }

    public void setYLevel(int yLevel) {
        this.targetY = yLevel;
    }
}

