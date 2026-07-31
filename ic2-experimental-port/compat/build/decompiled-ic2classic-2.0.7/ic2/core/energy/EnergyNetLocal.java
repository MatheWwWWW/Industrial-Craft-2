/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.ints.Int2ObjectMap
 *  it.unimi.dsi.fastutil.ints.IntBidirectionalIterator
 *  it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongArrayList
 *  it.unimi.dsi.fastutil.longs.LongList
 *  it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
 *  it.unimi.dsi.fastutil.objects.Object2ObjectMaps
 *  it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectLists
 *  it.unimi.dsi.fastutil.objects.ObjectSet
 *  it.unimi.dsi.fastutil.objects.ObjectSets
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 */
package ic2.core.energy;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.PacketStats;
import ic2.api.energy.TransferStats;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.energy.tile.IMultiEnergyTile;
import ic2.api.util.DirectionList;
import ic2.api.util.IC2DamageSource;
import ic2.core.IC2;
import ic2.core.energy.EnergyNetGrid;
import ic2.core.energy.EnergyNetPaths;
import ic2.core.entity.explosion.IC2Explosion;
import ic2.core.item.wearable.armor.HazmatArmor;
import ic2.core.platform.registries.IC2Stats;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntLinkedOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps;
import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import it.unimi.dsi.fastutil.objects.ObjectSets;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EnergyNetLocal {
    public static final Predicate<LivingEntity> SHOCK_PREDICATE = EnergyNetLocal::isShockable;
    Random rand = new Random();
    Level world;
    Long2ObjectMap<IEnergyTile[]> registeredTiles = new Long2ObjectOpenHashMap();
    Long2ObjectMap<IEnergySource> sources = new Long2ObjectLinkedOpenHashMap();
    Map<IEnergyTile, Connectivity> connectivity = CollectionUtils.createMap();
    Map<IEnergyTile, List<IEnergyTile>> cachedMultiTiles = CollectionUtils.createMap();
    Int2ObjectMap<EnergyNetGrid> grids = new Int2ObjectLinkedOpenHashMap();
    Object2IntMap<IEnergyConductor> cableToGrids = new Object2IntOpenHashMap();
    List<IEnergyTile[]> directConnections = new ObjectArrayList();
    IntSet dirtyGrids = new IntLinkedOpenHashSet();
    Object2LongOpenHashMap<IEnergyTile>[] tileStats = new Object2LongOpenHashMap[]{new Object2LongOpenHashMap(), new Object2LongOpenHashMap(), new Object2LongOpenHashMap(), new Object2LongOpenHashMap()};
    Random random = new Random();
    EnergyNetPaths energySourcePaths = new EnergyNetPaths();
    Predicate<IEnergyTile> remover = T -> !this.registeredTiles.containsKey(T.getPosition().m_121878_());
    Object2IntLinkedOpenHashMap<LivingEntity> entitiesToShock = new Object2IntLinkedOpenHashMap();

    EnergyNetLocal(Level world) {
        this.world = world;
    }

    private static boolean isShockable(LivingEntity living) {
        Player player;
        if (living.m_5833_() || !living.m_6084_()) {
            return false;
        }
        if (HazmatArmor.isFullHazmatSuit(living)) {
            return false;
        }
        return !(living instanceof Player) || !(player = (Player)living).m_7500_();
    }

    public void onTickStart() {
        if (this.entitiesToShock.size() > 0) {
            for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(this.entitiesToShock)) {
                LivingEntity target = (LivingEntity)entry.getKey();
                int damage = (entry.getIntValue() + 63) / 64;
                if (!target.m_6084_() || damage <= 0) continue;
                target.m_6469_((DamageSource)IC2DamageSource.newShockDamage((Entity)target), (float)damage);
                if (!(target instanceof Player)) continue;
                Player player = (Player)target;
                player.m_36222_(IC2Stats.CABLE_SHOCK_DAMAGE, damage);
            }
            this.entitiesToShock.clear();
        }
    }

    public void onTickStop() {
        if (this.dirtyGrids.size() > 0) {
            int[] array = this.dirtyGrids.toIntArray();
            this.dirtyGrids.clear();
            for (int i = 0; i < array.length; ++i) {
                EnergyNetGrid grid = (EnergyNetGrid)this.grids.get(array[i]);
                if (grid == null || grid.cables.isEmpty()) continue;
                grid.processChanges();
            }
        }
        if (this.directConnections.size() > 0) {
            Object2ObjectSortedMap sets = CollectionUtils.createLinkedMap();
            for (IEnergyTile[] tiles : this.directConnections) {
                ObjectSet receivers = (ObjectSet)sets.get((Object)tiles[0]);
                if (receivers == null) {
                    receivers = CollectionUtils.createSet();
                    sets.put((Object)tiles[0], receivers);
                }
                receivers.add((IEnergyTile)tiles[1]);
            }
            sets.keySet().removeIf(this.remover);
            for (Map.Entry entry : Object2ObjectMaps.fastIterable(sets)) {
                IEnergySource source = (IEnergySource)entry.getKey();
                this.energySourcePaths.addSourcePaths(source, this.discoverPaths(source, (Set)entry.getValue(), false, source.getMaxEnergyOutput()));
            }
            this.directConnections.clear();
        }
        if (this.sources.size() > 0) {
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable((Long2ObjectMap)new Long2ObjectLinkedOpenHashMap(this.sources))) {
                if (!this.registeredTiles.containsKey(entry.getLongKey())) continue;
                IEnergySource source = (IEnergySource)entry.getValue();
                int offered = source.getProvidedEnergy();
                if (source instanceof IMultiEnergySource && ((IMultiEnergySource)source).hasMultiplePackets()) {
                    int removed;
                    int packetCount = ((IMultiEnergySource)source).getPacketCount();
                    for (int i = 0; i < packetCount && offered >= 1 && (removed = offered - this.sendEnergyTo(entry.getLongKey(), source, offered)) > 0; ++i) {
                        source.consumeEnergy(removed);
                        offered = source.getProvidedEnergy();
                        this.addPacket(source, removed, false);
                    }
                    continue;
                }
                int removed = offered - this.sendEnergyTo(entry.getLongKey(), source, offered);
                if (removed <= 0) continue;
                source.consumeEnergy(removed);
                this.addPacket(source, removed, false);
            }
        }
    }

    private int sendEnergyTo(long key, IEnergySource source, int energyToSend) {
        if (!this.registeredTiles.containsKey(key)) {
            return energyToSend;
        }
        Set<EnergyPath> paths = this.energySourcePaths.get(source);
        if (paths.isEmpty()) {
            return energyToSend;
        }
        double totalInvLoss = 0.0;
        int energyWithLoss = energyToSend;
        boolean lossStop = false;
        boolean noRequestStop = false;
        ObjectArrayList activePaths = new ObjectArrayList();
        for (EnergyPath path : paths) {
            if (path.loss >= (double)energyToSend || path.target.getRequestedEnergy() < 1) {
                if (path.loss >= (double)energyToSend) {
                    lossStop = true;
                    continue;
                }
                noRequestStop = true;
                continue;
            }
            totalInvLoss += 1.0 / path.loss;
            energyWithLoss -= (int)path.loss;
            activePaths.add((Object)path);
        }
        if (activePaths.isEmpty()) {
            if (lossStop && !noRequestStop) {
                source.onPacketFailed();
            }
            return energyToSend;
        }
        ObjectLists.shuffle((ObjectList)activePaths, (Random)this.random);
        while (activePaths.size() - energyWithLoss > 0 && activePaths.size() > 0) {
            EnergyPath path = (EnergyPath)activePaths.remove(activePaths.size() - 1);
            totalInvLoss -= 1.0 / path.loss;
            energyWithLoss += (int)path.loss;
        }
        if (activePaths.isEmpty()) {
            if (lossStop && !noRequestStop) {
                source.onPacketFailed();
            }
            return energyToSend;
        }
        Object2IntLinkedOpenHashMap suppliedPaths = new Object2IntLinkedOpenHashMap();
        int sourceTier = EnergyNet.INSTANCE.getPowerFromTier(source.getSourceTier());
        while (activePaths.size() > 0 && energyToSend > 0) {
            int consumed = 0;
            double newTotalInvLoss = 0.0;
            ObjectArrayList currentPaths = activePaths;
            activePaths = new ObjectArrayList();
            for (EnergyPath energyPath : currentPaths) {
                int energyLoss;
                IEnergySink target = energyPath.target;
                int energyProvided = (int)Math.floor((double)Math.round((double)energyToSend / totalInvLoss / energyPath.loss * 100000.0) / 100000.0);
                if (energyProvided > (energyLoss = (int)Math.floor(energyPath.loss))) {
                    int providing = energyProvided - energyLoss;
                    int adding = Math.min(providing, target.getRequestedEnergy());
                    if (adding <= 0) continue;
                    if (providing > EnergyNet.INSTANCE.getPowerFromTier(target.getSinkTier())) {
                        this.explodeTiles(target);
                        continue;
                    }
                    double energyReturned = target.acceptEnergy(energyPath.targetDirection, adding, sourceTier);
                    if (energyReturned == 0.0) {
                        if (target.getRequestedEnergy() >= 1) {
                            activePaths.add((Object)energyPath);
                            newTotalInvLoss += 1.0 / energyPath.loss;
                        }
                    } else if (energyReturned >= (double)(energyProvided - energyLoss)) {
                        energyReturned = energyProvided - energyLoss;
                        IC2.LOGGER.warn("API ERROR: " + target.getClass().getSimpleName() + ", At" + target.getPosition() + " didn't implement getRequestedEnergy() properly, no energy from acceptEnergy accepted although getRequestedEnergy() returned true: Requested: " + adding + ", Returned: " + energyReturned);
                    }
                    consumed = (int)((double)consumed + ((double)adding - energyReturned + (double)energyLoss));
                    int energyInjected = (int)((double)adding - energyReturned);
                    if (energyInjected <= 0) continue;
                    suppliedPaths.addTo((Object)energyPath, energyInjected);
                    continue;
                }
                activePaths.add((Object)energyPath);
                newTotalInvLoss += 1.0 / energyPath.loss;
            }
            if (consumed == 0 && !activePaths.isEmpty()) {
                newTotalInvLoss -= 1.0 / ((EnergyPath)activePaths.remove((int)(activePaths.size() - 1))).loss;
            }
            totalInvLoss = newTotalInvLoss;
            energyToSend -= consumed;
        }
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable((Object2IntMap)suppliedPaths)) {
            List entities;
            EnergyPath path = (EnergyPath)entry.getKey();
            int supplied = entry.getIntValue();
            path.totalEnergyConducted += (long)supplied;
            this.addPacket(path.target, supplied, true);
            if (supplied > path.minInsulationEnergyAbsorption && (entities = this.world.m_6443_(LivingEntity.class, new AABB((double)(path.minX - 3), (double)(path.minY - 3), (double)(path.minZ - 3), (double)(path.maxX + 4), (double)(path.maxY + 4), (double)(path.maxZ + 4)), SHOCK_PREDICATE)).size() > 0) {
                Object2IntLinkedOpenHashMap shockMap = new Object2IntLinkedOpenHashMap();
                for (IEnergyConductor cable : path.cables) {
                    int shockAbsorbed = cable.getInsulationEnergyAbsorption();
                    if (shockAbsorbed >= supplied) continue;
                    AABB conductorBox = cable.isLavaLogged() ? new AABB(cable.getPosition()) : new AABB(cable.getPosition()).m_82400_(1.0);
                    AABB waterBox = cable.isWaterlogged() ? new AABB(cable.getPosition()).m_82400_(3.0) : null;
                    for (LivingEntity entity : entities) {
                        int shockEnergy;
                        if (!entity.m_20191_().m_82381_(conductorBox) && (waterBox == null || !entity.m_20070_() || !entity.m_20191_().m_82381_(waterBox)) || (shockEnergy = supplied - shockAbsorbed) <= 0 || shockMap.getInt((Object)entity) >= shockEnergy) continue;
                        shockMap.put((Object)entity, shockEnergy);
                    }
                }
                if (shockMap.size() > 0) {
                    for (Object2IntMap.Entry shockEntry : Object2IntMaps.fastIterable((Object2IntMap)shockMap)) {
                        this.entitiesToShock.addTo((Object)((LivingEntity)shockEntry.getKey()), shockEntry.getIntValue());
                    }
                }
            }
            if (supplied >= path.minInsulationBreakdownEnergy) {
                path.minInsulationEnergyAbsorption = Integer.MAX_VALUE;
                for (IEnergyConductor conductor : path.cables) {
                    if (supplied >= conductor.getInsulationBreakdownEnergy()) {
                        conductor.removeInsulation();
                        continue;
                    }
                    path.minInsulationEnergyAbsorption = Math.min(path.minInsulationEnergyAbsorption, conductor.getInsulationEnergyAbsorption());
                }
            }
            if (supplied < path.minConductorBreakdownEnergy) continue;
            for (IEnergyConductor conductor : path.cables) {
                if (supplied < conductor.getConductorBreakdownEnergy()) continue;
                conductor.removeConductor();
                this.removeTile(conductor);
            }
        }
        return energyToSend;
    }

    public void updateTile(IEnergyTile tile) {
        IEnergyTile[] tiles = (IEnergyTile[])this.registeredTiles.get(tile.getPosition().m_121878_());
        if (tiles == null || tiles[0] != tile) {
            return;
        }
        this.removeTile(tile);
        this.addTile(tile);
    }

    public void addTile(IEnergyTile tile) {
        IEnergySink sink;
        IEnergySource source;
        if (!(tile instanceof IEnergySource || tile instanceof IEnergySink || tile instanceof IEnergyConductor)) {
            return;
        }
        if (tile instanceof IEnergySource && ((source = (IEnergySource)tile).getSourceTier() > 13 || source.getSourceTier() < 0)) {
            IC2.LOGGER.warn("Tile [" + tile.getClass() + "] has a Invalid Source Tier, Ignoring, Valid Range=0-13");
            return;
        }
        if (tile instanceof IEnergySink && ((sink = (IEnergySink)tile).getSinkTier() > 13 || sink.getSinkTier() < 0)) {
            IC2.LOGGER.warn("Tile [" + tile.getClass() + "] has a Invalid Sink Tier, Ignoring, Valid Range=0-13");
            return;
        }
        if (tile instanceof IMultiEnergyTile) {
            this.addMultiTile((IMultiEnergyTile)tile);
            return;
        }
        this.addSimpleTile(tile);
    }

    private void addMultiTile(IMultiEnergyTile tile) {
        if (this.registeredTiles.containsKey(tile.getPosition().m_121878_())) {
            IC2.LOGGER.info("Double Registered EnergyTile at [World=" + tile.getWorldObj().m_46472_().m_135782_().toString() + ", Pos=" + tile.getPosition() + "]");
            return;
        }
        ObjectArrayList tiles = new ObjectArrayList(tile.getTiles());
        this.cachedMultiTiles.put(tile, (List<IEnergyTile>)tiles);
        for (IEnergyTile child : tiles) {
            long position = child.getPosition().m_121878_();
            if (this.registeredTiles.containsKey(position)) continue;
            this.registeredTiles.put(position, (Object)new IEnergyTile[]{tile, child});
            this.update(child.getPosition());
        }
        this.addToGrids(tile);
        if (tile instanceof IEnergySource) {
            this.sources.put(tile.getPosition().m_121878_(), (Object)((IEnergySource)((Object)tile)));
        }
    }

    private void addSimpleTile(IEnergyTile tile) {
        long position = tile.getPosition().m_121878_();
        if (this.registeredTiles.containsKey(position)) {
            IC2.LOGGER.info("Double Registered EnergyTile at [World=" + tile.getWorldObj().m_46472_().m_135782_().toString() + ", Pos=" + tile.getPosition() + "]");
            return;
        }
        this.registeredTiles.put(position, (Object)new IEnergyTile[]{tile, tile});
        this.update(tile.getPosition());
        this.addToGrids(tile);
        if (tile instanceof IEnergySource) {
            this.sources.put(tile.getPosition().m_121878_(), (Object)((IEnergySource)tile));
        }
    }

    private void addToGrids(IEnergyTile tile) {
        Connectivity connections = new Connectivity(tile, this);
        this.connectivity.put(tile, connections);
        boolean cable = tile instanceof IEnergyConductor;
        if (cable) {
            IEnergyTile target;
            IntLinkedOpenHashSet toCombine = new IntLinkedOpenHashSet();
            ObjectList sinksToAdd = CollectionUtils.createList();
            ObjectList sourcesToAdd = CollectionUtils.createList();
            if (tile instanceof IEnergySource) {
                sourcesToAdd.add((IEnergySource)((IEnergySource)tile));
            }
            if (tile instanceof IEnergySink) {
                sinksToAdd.add((IEnergySink)((IEnergySink)tile));
            }
            for (EnergyLink link : connections.getReceivers()) {
                target = link.target;
                if (target instanceof IEnergyConductor) {
                    toCombine.add(this.cableToGrids.getInt((Object)target));
                }
                if (!(target instanceof IEnergySink)) continue;
                sinksToAdd.add((IEnergySink)((IEnergySink)target));
            }
            for (EnergyLink link : connections.getEmitters()) {
                target = link.target;
                if (target instanceof IEnergyConductor) {
                    toCombine.add(this.cableToGrids.getInt((Object)target));
                }
                if (!(target instanceof IEnergySource)) continue;
                sourcesToAdd.add((IEnergySource)((IEnergySource)target));
            }
            if (toCombine.isEmpty()) {
                grid = EnergyNetGrid.createNewGrid(this);
                grid.addConductor((IEnergyConductor)tile);
                grid.addSinks((Collection<IEnergySink>)sinksToAdd);
                grid.addSource((Collection<IEnergySource>)sourcesToAdd);
                this.grids.put(grid.getGridID(), (Object)grid);
            } else if (toCombine.size() == 1) {
                grid = (EnergyNetGrid)this.grids.get(toCombine.firstInt());
                grid.addConductor((IEnergyConductor)tile);
                grid.addSinks((Collection<IEnergySink>)sinksToAdd);
                grid.addSource((Collection<IEnergySource>)sourcesToAdd);
            } else {
                grid = EnergyNetGrid.createNewGrid(this);
                IntBidirectionalIterator intBidirectionalIterator = toCombine.iterator();
                while (intBidirectionalIterator.hasNext()) {
                    int id = (Integer)intBidirectionalIterator.next();
                    EnergyNetGrid subGrid = (EnergyNetGrid)this.grids.remove(id);
                    grid.addGrid(subGrid);
                    subGrid.destroyGrid();
                }
                grid.addConductor((IEnergyConductor)tile);
                grid.addSource((Collection<IEnergySource>)sourcesToAdd);
                grid.addSinks((Collection<IEnergySink>)sinksToAdd);
                grid.finishCombining();
                this.grids.put(grid.getGridID(), (Object)grid);
            }
        } else {
            IEnergyTile targetTile;
            if (tile instanceof IEnergySink) {
                IEnergySink sink = (IEnergySink)tile;
                for (EnergyLink link : connections.getEmitters()) {
                    targetTile = link.target;
                    if (targetTile instanceof IEnergyConductor) {
                        ((EnergyNetGrid)this.grids.get(this.cableToGrids.getInt((Object)targetTile))).addSink(sink);
                    }
                    if (!(targetTile instanceof IEnergySource)) continue;
                    this.directConnections.add(new IEnergyTile[]{targetTile, sink});
                }
            }
            if (tile instanceof IEnergySource) {
                IEnergySource source = (IEnergySource)tile;
                for (EnergyLink link : connections.getReceivers()) {
                    targetTile = link.target;
                    if (targetTile instanceof IEnergyConductor) {
                        ((EnergyNetGrid)this.grids.get(this.cableToGrids.getInt((Object)targetTile))).addSource(source);
                    }
                    if (!(targetTile instanceof IEnergySink)) continue;
                    this.directConnections.add(new IEnergyTile[]{source, targetTile});
                }
            }
        }
    }

    void splitGrid(EnergyNetGrid grid) {
        this.grids.remove(grid.getGridID());
        ArrayList<EnergyNetGrid> gridsToAdd = new ArrayList<EnergyNetGrid>();
        ObjectSortedSet left = CollectionUtils.createLinkedSet();
        left.addAll(grid.cables.keySet());
        while (left.size() > 0) {
            EnergyNetGrid newGrid = EnergyNetGrid.createNewGrid(this);
            newGrid.setFlag(2);
            PriorityQueue toProcess = CollectionUtils.createInsertionQueue();
            toProcess.enqueue((Object)((IEnergyConductor)left.iterator().next()));
            while (!toProcess.isEmpty()) {
                IEnergyTile tile;
                IEnergyConductor cable = (IEnergyConductor)toProcess.dequeue();
                if (!this.registeredTiles.containsKey(cable.getPosition().m_121878_())) continue;
                left.remove(cable);
                newGrid.addConductor(cable);
                Connectivity targets = this.connectivity.get(cable);
                for (EnergyLink link : targets.getReceivers()) {
                    tile = link.target;
                    if (tile instanceof IEnergyConductor && left.remove(tile)) {
                        toProcess.enqueue((Object)((IEnergyConductor)tile));
                    }
                    if (!(tile instanceof IEnergySink)) continue;
                    newGrid.addSink((IEnergySink)tile);
                }
                for (EnergyLink link : targets.getEmitters()) {
                    tile = link.target;
                    if (tile instanceof IEnergyConductor && left.remove(tile)) {
                        toProcess.enqueue((Object)((IEnergyConductor)tile));
                    }
                    if (!(tile instanceof IEnergySource)) continue;
                    newGrid.addSource((IEnergySource)tile);
                }
            }
            gridsToAdd.add(newGrid);
            this.grids.put(newGrid.getGridID(), (Object)newGrid);
            newGrid.subPaths.transferPaths(grid.subPaths.getSubPaths(newGrid.sources, newGrid.newSinks));
            newGrid.finishSplit();
        }
        grid.subPaths.reset();
        for (EnergyNetGrid newGrid : gridsToAdd) {
            newGrid.subPaths.addSubSet();
        }
    }

    public void removeTile(IEnergyTile tile) {
        if (tile instanceof IMultiEnergyTile) {
            this.removeMultiTile((IMultiEnergyTile)tile);
        } else {
            this.removeSimpleTile(tile);
        }
        for (int i = 0; i < 4; ++i) {
            this.tileStats[i].removeLong((Object)tile);
        }
    }

    private void removeMultiTile(IMultiEnergyTile tile) {
        if (!this.registeredTiles.containsKey(tile.getPosition().m_121878_())) {
            return;
        }
        for (IEnergyTile child : this.cachedMultiTiles.remove(tile)) {
            long position = child.getPosition().m_121878_();
            if (!this.registeredTiles.containsKey(position)) continue;
            this.registeredTiles.remove(position);
            this.update(child.getPosition());
        }
        this.removeFromGrids(tile);
        if (tile instanceof IEnergySource) {
            this.sources.remove(tile.getPosition().m_121878_());
        }
    }

    private void removeSimpleTile(IEnergyTile tile) {
        long position = tile.getPosition().m_121878_();
        if (!this.registeredTiles.containsKey(position)) {
            return;
        }
        IEnergyTile stored = ((IEnergyTile[])this.registeredTiles.remove(position))[0];
        this.update(stored.getPosition());
        this.removeFromGrids(stored);
        if (tile instanceof IEnergySource) {
            this.sources.remove(position);
        }
    }

    private void removeFromGrids(IEnergyTile tile) {
        Connectivity connection = this.connectivity.remove(tile);
        connection.destroy(this);
        if (tile instanceof IEnergyConductor) {
            EnergyNetGrid grid = (EnergyNetGrid)this.grids.get(this.cableToGrids.removeInt((Object)tile));
            if (tile instanceof IEnergySource) {
                IEnergySource source = (IEnergySource)tile;
                grid.removeSource(source);
                this.energySourcePaths.removeSource(source);
            }
            if (tile instanceof IEnergySink) {
                IEnergySink sink = (IEnergySink)tile;
                grid.removeSink(sink);
                this.energySourcePaths.removeSink(sink);
            }
            ObjectSortedSet wires = CollectionUtils.createLinkedSet();
            ObjectList toDelete = CollectionUtils.createList();
            for (EnergyLink link : connection.getAll()) {
                IEnergyTile target = link.target;
                if (target instanceof IEnergyConductor) {
                    wires.add((IEnergyConductor)((IEnergyConductor)target));
                }
                if (target instanceof IEnergySink && !this.hasMoreConnections(target, grid.getGridID())) {
                    grid.removeSink((IEnergySink)target);
                    toDelete.add((IEnergyTile)target);
                }
                if (!(target instanceof IEnergySource) || this.hasMoreConnections(target, grid.getGridID())) continue;
                grid.removeSource((IEnergySource)target);
                toDelete.add((IEnergyTile)target);
            }
            if (wires.size() == 0) {
                grid.removeConductor((IEnergyConductor)tile);
                if (grid.cables.isEmpty()) {
                    this.grids.remove(grid.getGridID());
                }
            } else if (wires.size() == 1) {
                grid.removeConductor((IEnergyConductor)tile);
            } else {
                grid.removeConductor((IEnergyConductor)tile);
                grid.markSplit();
            }
            toDelete.forEach(this::updateTile);
        } else {
            IEnergyTile target;
            if (tile instanceof IEnergySource) {
                for (EnergyLink entry : connection.getReceivers()) {
                    target = entry.target;
                    if (!(target instanceof IEnergyConductor)) continue;
                    ((EnergyNetGrid)this.grids.get(this.cableToGrids.getInt((Object)target))).removeSource((IEnergySource)tile);
                }
                this.energySourcePaths.removeSource((IEnergySource)tile);
            }
            if (tile instanceof IEnergySink) {
                for (EnergyLink entry : connection.getEmitters()) {
                    target = entry.target;
                    if (!(target instanceof IEnergyConductor)) continue;
                    ((EnergyNetGrid)this.grids.get(this.cableToGrids.getInt((Object)target))).removeSink((IEnergySink)tile);
                }
                this.energySourcePaths.removeSink((IEnergySink)tile);
            }
        }
        connection.clear();
    }

    IEnergyTile[] get(BlockPos pos) {
        return (IEnergyTile[])this.registeredTiles.get(pos.m_121878_());
    }

    void update(BlockPos pos) {
        for (Direction facing : DirectionList.ALL) {
            BlockPos sidedPos = pos.m_121945_(facing);
            if (!this.world.m_46749_(sidedPos)) continue;
            this.world.m_46586_(sidedPos, Blocks.f_50016_, pos);
        }
        if (this.world.m_46749_(pos)) {
            this.world.m_46586_(pos, Blocks.f_50016_, pos);
        }
    }

    boolean hasMoreConnections(IEnergyTile tile, int id) {
        Connectivity con = this.connectivity.get(tile);
        if (con == null) {
            return false;
        }
        for (EnergyLink link : con.getAll()) {
            if (!(link.target instanceof IEnergyConductor) || this.cableToGrids.getInt((Object)link.target) != id) continue;
            return true;
        }
        return false;
    }

    List<EnergyLink> getConnectedTiles(IEnergyTile tile, boolean reverse) {
        ArrayList<EnergyLink> links = new ArrayList<EnergyLink>();
        List<IEnergyTile> tiles = this.cachedMultiTiles.getOrDefault(tile, (List<IEnergyTile>)ObjectLists.singleton((Object)tile));
        for (Direction dir : DirectionList.ALL) {
            for (IEnergyTile other : tiles) {
                IEnergyAcceptor acceptor;
                IEnergyEmitter sender;
                IEnergyTile[] connection = this.get(other.getPosition().m_121945_(dir));
                if (connection == null || connection[0] == tile) continue;
                if (reverse) {
                    sender = this.getEmitter(tile, other);
                    acceptor = this.getAcceptor(connection[0], connection[1]);
                    if (sender == null || acceptor == null || !sender.canEmitEnergy(acceptor, dir) || !acceptor.canAcceptEnergy(sender, dir.m_122424_())) continue;
                    links.add(new EnergyLink(tile, other.getPosition(), connection[0], connection[1].getPosition(), dir.m_122424_()));
                    continue;
                }
                sender = this.getEmitter(connection[0], connection[1]);
                acceptor = this.getAcceptor(tile, other);
                if (sender == null || acceptor == null || !sender.canEmitEnergy(acceptor, dir.m_122424_()) || !acceptor.canAcceptEnergy(sender, dir)) continue;
                links.add(new EnergyLink(tile, other.getPosition(), connection[0], connection[1].getPosition(), dir.m_122424_()));
            }
        }
        return links;
    }

    private Set<EnergyLink> getConnectivity(IEnergyTile source, IEnergyTile actually, boolean reverse) {
        try {
            return this.connectivity.get(actually).get(reverse);
        }
        catch (Exception e) {
            if (IC2.CONFIG.disableEnergyNetCrash.get()) {
                return ObjectSets.emptySet();
            }
            throw e;
        }
    }

    public List<EnergyPath> discoverPaths(IEnergyTile source, Set<IEnergyTile> targets, boolean reverse, int lossLimit) {
        Object2ObjectSortedMap<IEnergyTile, EnergyBlock> reachedLinks = CollectionUtils.createLinkedMap();
        LinkedList<IEnergyTile> toCheck = new LinkedList<IEnergyTile>();
        toCheck.add(source);
        ObjectArrayList reached = new ObjectArrayList();
        while (!toCheck.isEmpty()) {
            IEnergyTile tile = (IEnergyTile)toCheck.pollFirst();
            EnergyBlock block = (EnergyBlock)reachedLinks.get(tile);
            double loss = block == null ? 0.0 : block.loss;
            for (EnergyLink link : this.getConnectivity(source, tile, reverse)) {
                IEnergyTile possible = link.target;
                if (possible == source) continue;
                double extraLoss = 0.0;
                if (possible instanceof IEnergyConductor && loss + (extraLoss = Math.max(((IEnergyConductor)possible).getConductionLoss(), 0.001)) >= (double)lossLimit || (block = (EnergyBlock)reachedLinks.get(possible)) != null && block.loss <= loss + extraLoss) continue;
                if (!reverse && possible instanceof IEnergySink) {
                    if (targets.size() > 0 && !targets.contains(possible)) continue;
                    reached.add(possible);
                } else if (reverse && possible instanceof IEnergySource) {
                    if (targets.size() > 0 && !targets.contains(possible)) continue;
                    reached.add(possible);
                }
                reachedLinks.put(possible, new EnergyBlock(link, loss + extraLoss));
                if (!(possible instanceof IEnergyConductor)) continue;
                toCheck.remove(possible);
                toCheck.add(possible);
            }
        }
        ObjectArrayList energyPaths = new ObjectArrayList(reached.size());
        for (IEnergyTile tile : reached) {
            EnergyBlock energyBlockLink;
            if ((!reverse || !(tile instanceof IEnergySource)) && (reverse || !(tile instanceof IEnergySink)) || (energyBlockLink = (EnergyBlock)reachedLinks.get(tile)) == null) continue;
            EnergyPath path = new EnergyPath();
            path.loss = Math.max(0.1, energyBlockLink.loss);
            if (reverse) {
                if (path.loss > (double)((IEnergySource)tile).getMaxEnergyOutput()) continue;
                path.source = (IEnergySource)tile;
                path.sourceDirection = energyBlockLink.direction;
            } else {
                path.target = (IEnergySink)tile;
                path.targetDirection = energyBlockLink.direction.m_122424_();
            }
            path.pathWalked.add(tile.getPosition().m_121878_());
            path.pathWalked.add(energyBlockLink.targetPos.m_121878_());
            while (true) {
                if ((tile = energyBlockLink.source) == source) {
                    if (!reverse && source instanceof IEnergySource) {
                        path.source = (IEnergySource)source;
                        path.sourceDirection = energyBlockLink.direction.m_122424_();
                        path.pathWalked.add(energyBlockLink.sourcePos.m_121878_());
                        path.pathWalked.add(energyBlockLink.source.getPosition().m_121878_());
                        Collections.reverse(path.cables);
                        Collections.reverse(path.pathWalked);
                        break;
                    }
                    if (!reverse || !(source instanceof IEnergySink)) break;
                    path.target = (IEnergySink)source;
                    path.targetDirection = energyBlockLink.direction;
                    path.pathWalked.add(energyBlockLink.sourcePos.m_121878_());
                    path.pathWalked.add(energyBlockLink.source.getPosition().m_121878_());
                    break;
                }
                if (!(tile instanceof IEnergyConductor)) break;
                IEnergyConductor energyConductor = (IEnergyConductor)tile;
                BlockPos pos = energyBlockLink.sourcePos;
                path.pathWalked.add(pos.m_121878_());
                path.minX = Math.min(path.minX, pos.m_123341_());
                path.minY = Math.min(path.minY, pos.m_123342_());
                path.minZ = Math.min(path.minZ, pos.m_123343_());
                path.maxX = Math.max(path.maxX, pos.m_123341_());
                path.maxY = Math.max(path.maxY, pos.m_123342_());
                path.maxZ = Math.max(path.maxZ, pos.m_123343_());
                path.cables.add(energyConductor);
                path.cableCheck.add(energyConductor);
                path.minInsulationEnergyAbsorption = Math.min(path.minInsulationEnergyAbsorption, energyConductor.getInsulationEnergyAbsorption());
                path.minInsulationBreakdownEnergy = Math.min(path.minInsulationBreakdownEnergy, energyConductor.getInsulationBreakdownEnergy());
                path.minConductorBreakdownEnergy = Math.min(path.minConductorBreakdownEnergy, energyConductor.getConductorBreakdownEnergy());
                energyBlockLink = (EnergyBlock)reachedLinks.get(tile);
                if (energyBlockLink != null) continue;
                IC2.PLATFORM.displayError("An energy network pathfinding entry is corrupted.\nThis could happen due to incorrect Minecraft behavior or a bug.\n\n(Technical information: energyBlockLink, tile entities below)\nE: " + source + " (" + source.getPosition() + ")\nC: " + tile + " (" + pos + ")\nR: " + path.target + " (" + path.target.getPosition() + ")");
            }
            if (path.target == null || path.source == null) continue;
            energyPaths.add(path);
        }
        return energyPaths;
    }

    IEnergyEmitter getEmitter(IEnergyTile main, IEnergyTile sub) {
        return (IEnergyEmitter)(sub instanceof IEnergyEmitter ? sub : (main instanceof IEnergyEmitter ? main : null));
    }

    IEnergyAcceptor getAcceptor(IEnergyTile main, IEnergyTile sub) {
        return (IEnergyAcceptor)(sub instanceof IEnergyAcceptor ? sub : (main instanceof IEnergyAcceptor ? main : null));
    }

    void markGridDirty(int id) {
        this.dirtyGrids.add(id);
    }

    void addConductorToGrid(IEnergyConductor conduct, int gridID) {
        this.cableToGrids.put((Object)conduct, gridID);
    }

    void addPacket(IEnergyTile packet, int energy, boolean accept) {
        int index = accept ? 1 : 0;
        this.tileStats[index].addTo((Object)packet, (long)energy);
        this.tileStats[index + 2].addTo((Object)packet, 1L);
    }

    void explodeTiles(IEnergyTile tile) {
        ArrayList<BlockPos> positionsList = new ArrayList<BlockPos>();
        for (IEnergyTile entry : this.cachedMultiTiles.getOrDefault(tile, (List<IEnergyTile>)ObjectLists.singleton((Object)tile))) {
            positionsList.add(entry.getPosition());
        }
        this.removeTile(tile);
        for (BlockPos pos : positionsList) {
            this.world.m_7471_(pos, false);
        }
        for (BlockPos pos : positionsList) {
            IC2Explosion explosion = new IC2Explosion(this.world, null, Vec3.m_82528_((Vec3i)pos).m_82520_(0.5, 0.5, 0.5), 2.5f, 0.75f, IC2DamageSource.ELECTRICITY);
            explosion.doExplosion();
        }
    }

    public TransferStats getStats(IEnergyTile tile) {
        EnergyNetGrid grid;
        long in = 0L;
        long out = 0L;
        long lossIn = 0L;
        long lossOut = 0L;
        if (tile instanceof IEnergySource) {
            for (EnergyPath path : this.energySourcePaths.get((IEnergySource)tile)) {
                out += path.totalEnergyConducted;
                lossOut += (long)((int)path.loss);
            }
        }
        if (tile instanceof IEnergySink) {
            for (EnergyPath path : this.energySourcePaths.getReverse((IEnergySink)tile)) {
                in += path.totalEnergyConducted;
                lossIn += (long)((int)path.loss);
            }
        }
        if (tile instanceof IEnergyConductor && (grid = (EnergyNetGrid)this.grids.get(this.cableToGrids.getInt((Object)tile))) != null) {
            long cableIn = 0L;
            double count = 0.0;
            for (EnergyPath path : grid.subPaths.getPathsForConductor((IEnergyConductor)tile)) {
                in += path.totalEnergyConducted;
                out += path.totalEnergyConducted;
                cableIn += (long)((int)path.loss);
                count += 1.0;
            }
            lossIn = (long)((double)lossIn + Math.ceil((double)cableIn / count));
        }
        return new TransferStats(in, out, lossIn, lossOut);
    }

    public List<PacketStats> getPackets(IEnergyTile tile) {
        EnergyNetGrid grid;
        ObjectSortedSet sources = CollectionUtils.createLinkedSet();
        ObjectSortedSet sinks = CollectionUtils.createLinkedSet();
        if (tile instanceof IEnergySource) {
            sources.add((IEnergySource)((IEnergySource)tile));
        }
        if (tile instanceof IEnergySink) {
            sinks.add((IEnergySink)((IEnergySink)tile));
        }
        if (tile instanceof IEnergyConductor && (grid = (EnergyNetGrid)this.grids.get(this.cableToGrids.getInt((Object)tile))) != null) {
            for (EnergyPath path : grid.subPaths.getPathsForConductor((IEnergyConductor)tile)) {
                sources.add((IEnergySource)path.source);
                sinks.add((IEnergySink)path.target);
            }
        }
        ArrayList<PacketStats> stats = new ArrayList<PacketStats>();
        for (IEnergySource source : sources) {
            stats.add(new PacketStats(source, this.tileStats[2].getLong((Object)source), this.tileStats[0].getLong((Object)source), false));
        }
        for (IEnergySink sink : sinks) {
            stats.add(new PacketStats(sink, this.tileStats[3].getLong((Object)sink), this.tileStats[1].getLong((Object)sink), true));
        }
        return stats;
    }

    public static class EnergyPath {
        IEnergySource source = null;
        Direction sourceDirection = null;
        IEnergySink target = null;
        Direction targetDirection = null;
        List<IEnergyConductor> cables = new ArrayList<IEnergyConductor>();
        Set<IEnergyConductor> cableCheck = CollectionUtils.createSet();
        LongList pathWalked = new LongArrayList();
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        int minInsulationEnergyAbsorption = Integer.MAX_VALUE;
        int minInsulationBreakdownEnergy = Integer.MAX_VALUE;
        int minConductorBreakdownEnergy = Integer.MAX_VALUE;
        long totalEnergyConducted = 0L;
        double loss = 0.0;

        EnergyPath() {
        }

        public int hashCode() {
            return this.source.hashCode() + this.target.hashCode() * 31;
        }

        public boolean equals(Object obj) {
            if (obj instanceof EnergyPath) {
                EnergyPath path = (EnergyPath)obj;
                return path.source == this.source && path.target == this.target;
            }
            return false;
        }
    }

    static class Connectivity {
        IEnergyTile owner;
        Set<EnergyLink> receivers = CollectionUtils.createLinkedSet();
        Set<EnergyLink> emitters = CollectionUtils.createLinkedSet();
        Set<EnergyLink> all = CollectionUtils.createLinkedSet();

        public Connectivity(IEnergyTile tile, EnergyNetLocal local) {
            EnergyLink inv;
            Connectivity other;
            this.owner = tile;
            for (EnergyLink target : local.getConnectedTiles(tile, false)) {
                this.emitters.add(target);
                this.all.add(target);
                other = local.connectivity.get(target.target);
                if (other == null) {
                    IC2.LOGGER.info("Found a Non Connectivity Set Tile: " + target.target);
                    continue;
                }
                inv = target.invert();
                other.receivers.add(inv);
                other.all.add(inv);
            }
            for (EnergyLink target : local.getConnectedTiles(tile, true)) {
                this.receivers.add(target);
                this.all.add(target);
                other = local.connectivity.get(target.target);
                if (other == null) {
                    IC2.LOGGER.info("Found a Non Connectivity Set Tile: " + target.target);
                    continue;
                }
                inv = target.invert();
                other.emitters.add(inv);
                other.all.add(inv);
            }
        }

        public Set<EnergyLink> get(boolean reverse) {
            return reverse ? this.emitters : this.receivers;
        }

        public void destroy(EnergyNetLocal local) {
            for (EnergyLink link : this.all) {
                Connectivity other = local.connectivity.get(link.target);
                if (other == null) continue;
                EnergyLink otherLink = link.invert();
                other.all.remove(otherLink);
                other.receivers.remove(otherLink);
                other.emitters.remove(otherLink);
            }
        }

        public void clear() {
            this.emitters.clear();
            this.receivers.clear();
            this.all.clear();
        }

        public Set<EnergyLink> getAll() {
            return this.all;
        }

        public Set<EnergyLink> getEmitters() {
            return this.emitters;
        }

        public Set<EnergyLink> getReceivers() {
            return this.receivers;
        }

        public String toString() {
            return "Connections: " + this.all.toString();
        }
    }

    static class EnergyLink {
        IEnergyTile source;
        BlockPos sourcePos;
        IEnergyTile target;
        BlockPos targetPos;
        Direction direction;
        EnergyLink inverted;

        private EnergyLink() {
        }

        public EnergyLink(IEnergyTile source, BlockPos sourcePos, IEnergyTile target, BlockPos targetPos, Direction direction) {
            this.source = source;
            this.sourcePos = sourcePos;
            this.target = target;
            this.targetPos = targetPos;
            this.direction = direction;
            this.inverted = new EnergyLink();
            this.inverted.source = target;
            this.inverted.sourcePos = targetPos;
            this.inverted.target = source;
            this.inverted.targetPos = sourcePos;
            this.inverted.direction = direction.m_122424_();
            this.inverted.inverted = this;
        }

        public EnergyLink invert() {
            return this.inverted;
        }

        public int hashCode() {
            return Objects.hash(this.direction, this.targetPos);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof EnergyLink) {
                EnergyLink other = (EnergyLink)obj;
                return other.direction == this.direction && other.targetPos.equals((Object)this.targetPos);
            }
            return false;
        }

        public String toString() {
            return "Link: Source[" + this.source + "(" + this.sourcePos + ")], Target[" + this.target + "(" + this.targetPos + ")]";
        }
    }

    static class EnergyBlock {
        IEnergyTile source;
        BlockPos sourcePos;
        IEnergyTile target;
        BlockPos targetPos;
        Direction direction;
        double loss;

        public EnergyBlock(EnergyLink link, double loss) {
            this.source = link.source;
            this.sourcePos = link.sourcePos;
            this.target = link.target;
            this.targetPos = link.targetPos;
            this.direction = link.direction;
            this.loss = loss;
        }
    }
}

