/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap$Entry
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMaps
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectSortedMap
 *  it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue
 *  it.unimi.dsi.fastutil.longs.LongBidirectionalIterator
 *  it.unimi.dsi.fastutil.longs.LongCollection
 *  it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet
 *  it.unimi.dsi.fastutil.longs.LongOpenHashSet
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  it.unimi.dsi.fastutil.objects.ObjectSortedSet
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.BlockPos$MutableBlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.item.DyeColor
 *  net.minecraft.world.level.Level
 */
package ic2.core.block.cables.mointor;

import ic2.api.util.DirectionList;
import ic2.core.block.cables.mointor.MonitorBlock;
import ic2.core.block.cables.mointor.MonitorTileEntity;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.geometry.Box;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectSortedMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSortedSet;
import java.util.Collection;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

public class MonitorGrid {
    static Map<ResourceKey<Level>, Long2ObjectMap<SubGrid>> GRIDS = CollectionUtils.createLinkedMap();

    public static void register(MonitorTileEntity tile) {
        Long2ObjectOpenHashMap grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            grid = new Long2ObjectOpenHashMap();
            GRIDS.put((ResourceKey<Level>)tile.m_58904_().m_46472_(), (Long2ObjectMap<SubGrid>)grid);
        }
        ObjectSortedSet grids = CollectionUtils.createLinkedSet();
        Direction face = tile.getFacing();
        long pos = tile.m_58899_().m_121878_();
        for (Direction dir : DirectionList.ofAxis(face.m_122434_()).invert()) {
            SubGrid subGrid = (SubGrid)grid.get(BlockPos.m_121915_((long)pos, (Direction)dir));
            if (subGrid == null || subGrid.facing != face || subGrid.gridId != tile.gridId) continue;
            grids.add((SubGrid)subGrid);
        }
        if (grids.isEmpty()) {
            grid.put(pos, (Object)new SubGrid(tile, true));
        } else if (grids.size() == 1) {
            grid.put(tile.m_58899_().m_121878_(), (Object)((SubGrid)grids.first()));
            ((SubGrid)grids.first()).add(tile, true);
        } else {
            SubGrid newGrid = new SubGrid(tile, false);
            for (SubGrid sub : grids) {
                newGrid.addAll((Collection<MonitorTileEntity>)sub.tiles.values());
            }
            newGrid.apply((Long2ObjectMap<SubGrid>)grid);
        }
    }

    public static void update(MonitorTileEntity tile) {
        Long2ObjectMap<SubGrid> grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            return;
        }
        if (grid.containsKey(tile.m_58899_().m_121878_())) {
            MonitorGrid.unregister(tile);
            MonitorGrid.register(tile);
        }
    }

    public static void validateMaster(MonitorTileEntity tile) {
        Long2ObjectMap<SubGrid> grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            return;
        }
        SubGrid removed = (SubGrid)grid.get(tile.m_58899_().m_121878_());
        if (removed == null) {
            return;
        }
        removed.currentMaster = 0L;
        removed.updateGrid();
    }

    public static void setGridId(int gridId, MonitorTileEntity tile) {
        Long2ObjectMap<SubGrid> grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            return;
        }
        SubGrid removed = (SubGrid)grid.get(tile.m_58899_().m_121878_());
        if (removed == null) {
            return;
        }
        removed.setGridId(gridId);
    }

    public static void setGridColor(DyeColor color, MonitorTileEntity tile) {
        Long2ObjectMap<SubGrid> grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            return;
        }
        SubGrid removed = (SubGrid)grid.get(tile.m_58899_().m_121878_());
        if (removed == null) {
            return;
        }
        removed.setGridColor(color);
    }

    public static void setActive(boolean active, MonitorTileEntity tile) {
        Long2ObjectMap<SubGrid> grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            return;
        }
        SubGrid subGrid = (SubGrid)grid.get(tile.m_58899_().m_121878_());
        if (subGrid == null) {
            return;
        }
        subGrid.setActive(active);
    }

    public static void unregister(MonitorTileEntity tile) {
        Long2ObjectMap<SubGrid> grid = GRIDS.get(tile.m_58904_().m_46472_());
        if (grid == null) {
            return;
        }
        SubGrid removed = (SubGrid)grid.remove(tile.m_58899_().m_121878_());
        if (removed == null) {
            return;
        }
        if (removed.remove(tile.m_58899_())) {
            removed.split(tile.m_58899_(), grid);
        } else {
            removed.updateGrid();
        }
    }

    public static void onWorldUnloaded(Level world) {
        GRIDS.remove(world.m_46472_());
    }

    public static void onServerStopped() {
        GRIDS.clear();
    }

    public static class SubGrid {
        Long2ObjectSortedMap<MonitorTileEntity> tiles = new Long2ObjectLinkedOpenHashMap();
        long currentMaster;
        int gridId;
        Box box;
        Direction facing;
        Boolean lastState = null;

        private SubGrid(Direction face, int gridId) {
            this.facing = face;
            this.gridId = gridId;
        }

        private SubGrid(MonitorTileEntity master, boolean update) {
            this.currentMaster = master.m_58899_().m_121878_();
            this.tiles.put(this.currentMaster, (Object)master);
            this.facing = master.getFacing();
            this.gridId = master.gridId;
            if (update) {
                this.updateGrid();
            }
        }

        private void setGridColor(DyeColor color) {
            if (this.currentMaster != 0L && !((MonitorTileEntity)this.tiles.get(this.currentMaster)).isMaster()) {
                this.currentMaster = 0L;
            }
            if (this.currentMaster == 0L) {
                this.findMaster();
            }
            if (this.currentMaster == 0L) {
                return;
            }
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.tiles)) {
                if (!this.box.intersectsWith((BlockPos)pos.m_122188_(entry.getLongKey()))) continue;
                ((MonitorTileEntity)entry.getValue()).setScreenColor(color);
            }
        }

        private void setGridId(int id) {
            if (this.currentMaster != 0L && !((MonitorTileEntity)this.tiles.get(this.currentMaster)).isMaster()) {
                this.currentMaster = 0L;
            }
            if (this.currentMaster == 0L) {
                this.findMaster();
            }
            if (this.currentMaster == 0L) {
                return;
            }
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            ObjectList toUpdate = CollectionUtils.createList();
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.tiles)) {
                if (!this.box.intersectsWith((BlockPos)pos.m_122188_(entry.getLongKey()))) continue;
                ((MonitorTileEntity)entry.getValue()).setGridId(id);
                toUpdate.add((MonitorTileEntity)((MonitorTileEntity)entry.getValue()));
            }
            for (MonitorTileEntity entity : toUpdate) {
                entity.updateGridState();
            }
            Long2ObjectMap<SubGrid> grid = GRIDS.get(((MonitorTileEntity)this.tiles.get(this.currentMaster)).m_58904_().m_46472_());
            for (MonitorTileEntity entity : toUpdate) {
                grid.remove(entity.m_58899_().m_121878_());
            }
            for (MonitorTileEntity value : toUpdate) {
                MonitorGrid.register(value);
            }
        }

        private void setActive(boolean active) {
            if (this.lastState != null && this.lastState == active) {
                return;
            }
            this.lastState = active;
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.tiles)) {
                ((MonitorTileEntity)entry.getValue()).setActive(this.box.intersectsWith((BlockPos)pos.m_122188_(entry.getLongKey())) && active);
            }
        }

        private void add(MonitorTileEntity tile, boolean update) {
            this.tiles.put(tile.m_58899_().m_121878_(), (Object)tile);
            if (update) {
                if (tile.isMaster()) {
                    this.currentMaster = 0L;
                }
                this.updateGrid();
                return;
            }
            this.lastState = null;
            this.currentMaster = 0L;
        }

        private void addAll(Collection<MonitorTileEntity> monitors) {
            this.currentMaster = 0L;
            this.lastState = null;
            for (MonitorTileEntity tile : monitors) {
                this.tiles.put(tile.m_58899_().m_121878_(), (Object)tile);
            }
        }

        private void split(BlockPos startPos, Long2ObjectMap<SubGrid> grids) {
            long pos = startPos.m_121878_();
            DirectionList sidesToCheck = DirectionList.ofAxis(this.facing.m_122434_()).invert();
            LongLinkedOpenHashSet left = new LongLinkedOpenHashSet();
            for (Direction dir : sidesToCheck) {
                if (!this.tiles.containsKey(BlockPos.m_121915_((long)pos, (Direction)dir))) continue;
                left.add(BlockPos.m_121915_((long)pos, (Direction)dir));
            }
            if (left.size() <= 1) {
                return;
            }
            LongArrayFIFOQueue queue = new LongArrayFIFOQueue();
            queue.enqueue(left.firstLong());
            LongOpenHashSet positionsFound = new LongOpenHashSet();
            while (!queue.isEmpty() && !left.isEmpty()) {
                long key = queue.dequeueLong();
                positionsFound.add(key);
                left.remove(key);
                for (Direction dir : sidesToCheck) {
                    long other = BlockPos.m_121915_((long)key, (Direction)dir);
                    if (positionsFound.contains(other)) continue;
                    queue.enqueue(other);
                    left.remove(other);
                }
            }
            if (left.size() <= 0 && positionsFound.size() == this.tiles.size()) {
                return;
            }
            left.addAll((LongCollection)this.tiles.keySet());
            while (left.size() > 0) {
                queue.clear();
                queue.enqueue(left.firstLong());
                SubGrid otherGrid = new SubGrid(this.facing, this.gridId);
                while (!queue.isEmpty()) {
                    long toProcess = queue.dequeueLong();
                    left.remove(toProcess);
                    otherGrid.add((MonitorTileEntity)this.tiles.get(toProcess), false);
                    for (Direction dir : sidesToCheck) {
                        long other = BlockPos.m_121915_((long)toProcess, (Direction)dir);
                        if (!left.contains(other)) continue;
                        queue.enqueue(other);
                    }
                }
                otherGrid.apply(grids);
            }
            this.tiles.clear();
        }

        private void apply(Long2ObjectMap<SubGrid> entries) {
            LongBidirectionalIterator iter = this.tiles.keySet().iterator();
            while (iter.hasNext()) {
                entries.put(iter.nextLong(), (Object)this);
            }
            this.currentMaster = 0L;
            this.updateGrid();
        }

        private boolean remove(BlockPos pos) {
            long longPos = pos.m_121878_();
            this.tiles.remove(longPos);
            if (this.currentMaster == longPos) {
                this.currentMaster = 0L;
            }
            int count = 0;
            for (Direction dir : DirectionList.ofAxis(this.facing.m_122434_()).invert()) {
                count += this.tiles.containsKey(BlockPos.m_121915_((long)longPos, (Direction)dir)) ? 1 : 0;
            }
            this.lastState = null;
            return count > 1;
        }

        private void updateGrid() {
            if (this.currentMaster != 0L && !((MonitorTileEntity)this.tiles.get(this.currentMaster)).isMaster()) {
                this.currentMaster = 0L;
            }
            if (this.currentMaster == 0L) {
                this.findMaster();
            }
            if (this.currentMaster == 0L) {
                return;
            }
            Direction mainDir = MonitorBlock.getSearchDir(this.facing, false);
            Direction subDir = MonitorBlock.getSearchDir(this.facing, true);
            this.box = this.findBounds(mainDir, subDir);
            ((MonitorTileEntity)this.tiles.get(this.currentMaster)).setBounds(this.box.get(mainDir.m_122434_()) + 1, this.box.get(subDir.m_122434_()) + 1);
            this.lastState = null;
        }

        private Box findBounds(Direction mainDir, Direction subDir) {
            int width = 0;
            int height = 0;
            long startPos = this.currentMaster;
            while (this.tiles.containsKey(startPos)) {
                startPos = BlockPos.m_121915_((long)startPos, (Direction)mainDir);
                ++width;
            }
            startPos = this.currentMaster;
            while (this.tiles.containsKey(startPos) && this.isValid(this.currentMaster, width - 1, height, mainDir, subDir)) {
                startPos = BlockPos.m_121915_((long)startPos, (Direction)subDir);
                ++height;
            }
            return Box.fromPos(BlockPos.m_122022_((long)this.currentMaster), true).expand(mainDir, width - 1).expand(subDir, height - 1);
        }

        private boolean isValid(long start, int width, int height, Direction mainDir, Direction subDir) {
            for (BlockPos pos : Box.fromPos(BlockPos.m_122022_((long)start), true).expand(mainDir, width).expand(subDir, height)) {
                if (this.tiles.containsKey(pos.m_121878_())) continue;
                return false;
            }
            return true;
        }

        private void findMaster() {
            if (this.currentMaster != 0L) {
                return;
            }
            for (Long2ObjectMap.Entry entry : Long2ObjectMaps.fastIterable(this.tiles)) {
                if (!((MonitorTileEntity)entry.getValue()).isMaster()) continue;
                this.currentMaster = entry.getLongKey();
                break;
            }
        }
    }
}

