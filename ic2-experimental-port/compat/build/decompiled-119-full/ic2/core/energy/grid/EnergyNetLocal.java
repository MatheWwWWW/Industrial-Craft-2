/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.SectionPos
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.chunk.ChunkSource
 */
package ic2.core.energy.grid;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.IC2;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.Grid;
import ic2.core.energy.grid.GridChange;
import ic2.core.energy.grid.GridInfo;
import ic2.core.energy.grid.GridUpdater;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeLink;
import ic2.core.energy.grid.Tile;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkSource;

public class EnergyNetLocal {
    static final GridChange QUEUE_DELAY_CHANGE = new GridChange(null, null, null);
    private final Level world;
    private final Queue<GridChange> gridChangesQueue = new ArrayDeque<GridChange>();
    private final Map<IEnergyTile, GridChange> gridAdditionsMap = new IdentityHashMap<IEnergyTile, GridChange>();
    private final Set<IEnergyTile> ioTilesToNotify = Collections.newSetFromMap(new IdentityHashMap());
    private final GridUpdater updater = new GridUpdater(this);
    int nextNodeId;
    int nextGridId;
    final Map<IEnergyTile, Tile> registeredIoTiles = new IdentityHashMap<IEnergyTile, Tile>();
    final Map<BlockPos, Tile> registeredTiles = new HashMap<BlockPos, Tile>();
    final Set<Tile> sources = Collections.newSetFromMap(new IdentityHashMap());
    private final List<Grid> grids = new ArrayList<Grid>();

    public static EnergyNetLocal create(Level level) {
        return new EnergyNetLocal(level);
    }

    private EnergyNetLocal(Level level) {
        this.world = level;
        for (int i = 0; i < 1; ++i) {
            this.gridChangesQueue.add(QUEUE_DELAY_CHANGE);
        }
    }

    IEnergyTile getIoTile(BlockPos blockPos) {
        Tile tile = this.getTile(blockPos);
        if (tile != null) {
            return tile.getMainTile();
        }
        IEnergyTile iEnergyTile = null;
        for (GridChange gridChange : this.gridChangesQueue) {
            if (gridChange == QUEUE_DELAY_CHANGE || !gridChange.pos.equals((Object)blockPos)) continue;
            iEnergyTile = gridChange.type == GridChange.Type.REMOVAL ? null : gridChange.ioTile;
        }
        return iEnergyTile;
    }

    IEnergyTile getSubTile(BlockPos blockPos) {
        Tile tile = this.getTile(blockPos);
        if (tile != null) {
            return tile.getSubTileAt(blockPos);
        }
        IEnergyTile iEnergyTile = null;
        block0: for (GridChange gridChange : this.gridChangesQueue) {
            if (gridChange == QUEUE_DELAY_CHANGE) continue;
            List<IEnergyTile> list = gridChange.subTiles != null ? gridChange.subTiles : Collections.singletonList(gridChange.ioTile);
            for (IEnergyTile iEnergyTile2 : list) {
                if (!EnergyNet.instance.getPos(iEnergyTile2).equals((Object)blockPos)) continue;
                iEnergyTile = gridChange.type == GridChange.Type.REMOVAL ? null : gridChange.ioTile;
                continue block0;
            }
        }
        return iEnergyTile;
    }

    public Tile getTile(BlockPos blockPos) {
        if (this.updater.isInChangeStep()) {
            this.updater.awaitCompletion();
        }
        return this.registeredTiles.get(blockPos);
    }

    void addTile(IEnergyTile iEnergyTile, BlockPos blockPos) {
        GridChange gridChange = new GridChange(GridChange.Type.ADDITION, blockPos, iEnergyTile);
        GridChange gridChange2 = this.gridAdditionsMap.put(iEnergyTile, gridChange);
        if (gridChange2 != null) {
            this.gridAdditionsMap.put(iEnergyTile, gridChange2);
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s was attempted to be queued twice for addition.", Util.toString(iEnergyTile, (BlockGetter)this.getWorld(), blockPos));
            }
        } else {
            this.gridChangesQueue.add(gridChange);
        }
    }

    void removeTile(IEnergyTile iEnergyTile, BlockPos blockPos) {
        GridChange gridChange = this.gridAdditionsMap.remove(iEnergyTile);
        if (gridChange != null) {
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "Removing tile %s by cancelling a pending addition.", Util.toString(iEnergyTile, (BlockGetter)this.getWorld(), blockPos));
            }
            this.gridChangesQueue.remove(gridChange);
        } else {
            this.gridChangesQueue.add(new GridChange(GridChange.Type.REMOVAL, blockPos, iEnergyTile));
            Tile tile = this.registeredIoTiles.get(iEnergyTile);
            if (tile != null) {
                tile.setDisabled();
                if (EnergyNetSettings.logGridUpdatesVerbose) {
                    IC2.log.debug(LogCategory.EnergyNet, "Disabled tile %s.", Util.toString(iEnergyTile, (BlockGetter)this.getWorld(), blockPos));
                }
            } else if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.warn(LogCategory.EnergyNet, "Missing tile %s.", Util.toString(iEnergyTile, (BlockGetter)this.getWorld(), blockPos));
            }
        }
    }

    public Collection<Tile> getSources() {
        return this.sources;
    }

    NodeStats getNodeStats(IEnergyTile iEnergyTile) {
        this.updater.awaitCompletion();
        Tile tile = this.registeredIoTiles.get(iEnergyTile);
        if (tile == null) {
            return null;
        }
        return EnergyNetGlobal.getCalculator().getNodeStats(tile);
    }

    int getAdjacentConnections(IEnergyTile iEnergyTile) {
        Tile tile = this.registeredIoTiles.get(iEnergyTile);
        if (tile == null) {
            return 0;
        }
        BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
        int n = 0;
        for (Node node : tile.getNodes()) {
            for (NodeLink nodeLink : node.getLinks()) {
                Node node2 = nodeLink.getNeighbor(node);
                for (IEnergyTile iEnergyTile2 : node2.getTile().getSubTiles()) {
                    BlockPos blockPos2 = EnergyNet.instance.getPos(iEnergyTile2);
                    Direction direction = EnergyNetLocal.getDirBetween(blockPos, blockPos2);
                    if (direction == null) continue;
                    n |= 1 << direction.ordinal();
                }
            }
        }
        return n;
    }

    private static Direction getDirBetween(BlockPos blockPos, BlockPos blockPos2) {
        int n = blockPos2.m_123341_() - blockPos.m_123341_();
        int n2 = blockPos2.m_123342_() - blockPos.m_123342_();
        int n3 = blockPos2.m_123343_() - blockPos.m_123343_();
        int n4 = Math.abs(n) + Math.abs(n2) + Math.abs(n3);
        if (n4 != 1) {
            return null;
        }
        if (n != 0) {
            return n > 0 ? Direction.EAST : Direction.WEST;
        }
        if (n2 != 0) {
            return n2 > 0 ? Direction.UP : Direction.DOWN;
        }
        return n3 > 0 ? Direction.SOUTH : Direction.NORTH;
    }

    public Collection<GridInfo> getGridInfos() {
        if (this.updater.isInChangeStep()) {
            this.updater.awaitCompletion();
        }
        ArrayList<GridInfo> arrayList = new ArrayList<GridInfo>();
        for (Grid grid : this.grids) {
            arrayList.add(grid.getInfo());
        }
        return arrayList;
    }

    boolean dumpDebugInfo(BlockPos blockPos, PrintStream printStream, PrintStream printStream2) {
        this.updater.awaitCompletion();
        Tile tile = this.registeredTiles.get(blockPos);
        if (tile == null) {
            return false;
        }
        printStream2.println("Tile " + tile + " info:");
        printStream2.println(" disabled: " + tile.isDisabled());
        printStream2.println(" main: " + tile.getMainTile());
        printStream2.println(" sub: " + tile.subTiles);
        printStream2.println(" nodes: " + tile.nodes.size());
        HashSet<Grid> hashSet = new HashSet<Grid>();
        for (Node node : tile.nodes) {
            Grid grid = node.getGrid();
            if (!hashSet.add(grid)) continue;
            grid.dumpNodeInfo(node, " ", printStream, printStream2);
            grid.dumpInfo(" ", printStream, printStream2);
            grid.dumpGraph();
        }
        return true;
    }

    public void onTickStart() {
        if (this.updater.isInChangeStep()) {
            this.updater.awaitCompletion();
            if (!this.ioTilesToNotify.isEmpty()) {
                ChunkSource chunkSource = this.world.m_7726_();
                int n = Integer.MIN_VALUE;
                int n2 = Integer.MIN_VALUE;
                boolean bl = false;
                for (IEnergyTile iEnergyTile : this.ioTilesToNotify) {
                    BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
                    int n3 = SectionPos.m_123171_((int)blockPos.m_123341_());
                    int n4 = SectionPos.m_123171_((int)blockPos.m_123343_());
                    if (n3 != n || n4 != n2) {
                        bl = chunkSource.m_5563_(n3, n4);
                        n = n3;
                        n2 = n4;
                    }
                    if (!bl) continue;
                    iEnergyTile.onConnectionChange();
                }
                this.ioTilesToNotify.clear();
            }
            this.updater.startTransferCalc();
        }
    }

    public void onTickEnd() {
        this.updater.awaitCompletion();
        if (!this.gridChangesQueue.isEmpty() && this.gridChangesQueue.peek() != QUEUE_DELAY_CHANGE) {
            this.updater.startChangeCalc(this.gridChangesQueue, this.gridAdditionsMap);
        } else {
            this.gridChangesQueue.poll();
            this.updater.startTransferCalc();
        }
        this.gridChangesQueue.add(QUEUE_DELAY_CHANGE);
        assert (this.gridChangesQueue.size() >= 1);
    }

    public Level getWorld() {
        return this.world;
    }

    int allocateNodeId() {
        return this.nextNodeId++;
    }

    int allocateGridId() {
        return this.nextGridId++;
    }

    void addTileToNotify(IEnergyTile iEnergyTile) {
        this.ioTilesToNotify.add(iEnergyTile);
    }

    void removeTileToNotify(IEnergyTile iEnergyTile) {
        this.ioTilesToNotify.remove(iEnergyTile);
    }

    boolean hasGrid(Grid grid) {
        return this.grids.contains(grid);
    }

    boolean hasGrids() {
        return !this.grids.isEmpty();
    }

    Collection<Grid> getGrids() {
        return this.grids;
    }

    void addGrid(Grid grid) {
        assert (!this.hasGrid(grid));
        this.grids.add(grid);
    }

    void removeGrid(Grid grid) {
        boolean bl = this.grids.remove(grid);
        assert (bl);
    }

    void shuffleGrids() {
        Collections.shuffle(this.grids);
    }
}

