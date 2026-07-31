/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongIterator
 *  it.unimi.dsi.fastutil.longs.LongList
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.entity.MobSpawnType
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelAccessor
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraftforge.event.entity.living.LivingSpawnEvent$CheckSpawn
 *  net.minecraftforge.event.level.BlockEvent$NeighborNotifyEvent
 *  net.minecraftforge.event.level.LevelEvent$Unload
 *  net.minecraftforge.eventbus.api.Event$Result
 *  net.minecraftforge.eventbus.api.EventPriority
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package ic2.core.platform.events;

import ic2.core.block.base.features.multiblock.IStructureListener;
import ic2.core.utils.collection.CollectionUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StructureManager {
    public static final StructureManager INSTANCE = new StructureManager();
    Map<LevelAccessor, StructureListeners> listeners = CollectionUtils.createMap();

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onBlockUpdate(BlockEvent.NeighborNotifyEvent event) {
        this.getListeners(event.getLevel()).onTrigger(event.getPos(), event.getState());
    }

    @SubscribeEvent
    public void onWorldUnload(LevelEvent.Unload event) {
        StructureListeners listener = this.listeners.remove(event.getLevel());
        if (listener != null) {
            listener.clear();
        }
    }

    @SubscribeEvent
    public void canSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        if (event.getSpawnReason() == MobSpawnType.NATURAL && this.getListeners(event.getLevel()).isBlocked(new BlockPos(event.getX(), event.getY(), event.getZ()).m_7495_())) {
            event.setResult(Event.Result.DENY);
        }
    }

    public IStructureListener getListener(Level world, BlockPos pos) {
        return this.getListeners((LevelAccessor)world).getListener(pos);
    }

    public void onServerStopped() {
        this.listeners.clear();
    }

    public void addListeners(Level world, LongCollection positions, IStructureListener listener) {
        if (listener == null || positions.isEmpty()) {
            return;
        }
        this.getListeners((LevelAccessor)world).add(positions, listener);
    }

    public boolean isBlocked(Level world, LongList positions) {
        return this.getListeners((LevelAccessor)world).isBlocked(positions);
    }

    public void clearListeners(Level world, LongCollection positions) {
        this.getListeners((LevelAccessor)world).removeListeners(positions);
    }

    private StructureListeners getListeners(LevelAccessor world) {
        StructureListeners listener = this.listeners.get(world);
        if (listener == null) {
            listener = new StructureListeners();
            this.listeners.put(world, listener);
        }
        return listener;
    }

    public static class StructureListeners {
        Long2ObjectMap<IStructureListener> listeners = new Long2ObjectOpenHashMap();

        public void onTrigger(BlockPos pos, BlockState newState) {
            IStructureListener listener = (IStructureListener)this.listeners.get(pos.m_121878_());
            if (listener != null) {
                listener.markStructureDirty(pos, newState);
            }
        }

        public void add(LongCollection positions, IStructureListener listener) {
            LongIterator longIterator = positions.iterator();
            while (longIterator.hasNext()) {
                long entry = (Long)longIterator.next();
                this.listeners.putIfAbsent(entry, (Object)listener);
            }
        }

        public boolean isBlocked(LongList positions) {
            return positions.removeAll((LongCollection)this.listeners.keySet());
        }

        public boolean isBlocked(BlockPos pos) {
            return this.listeners.containsKey(pos.m_121878_());
        }

        public IStructureListener getListener(BlockPos pos) {
            return (IStructureListener)this.listeners.get(pos.m_121878_());
        }

        public void removeListeners(LongCollection positions) {
            this.listeners.keySet().removeAll(positions);
        }

        public void clear() {
            this.listeners.clear();
            this.listeners = null;
        }
    }
}

