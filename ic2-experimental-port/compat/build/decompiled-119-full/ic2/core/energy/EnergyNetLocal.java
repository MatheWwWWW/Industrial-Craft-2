/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.energy;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMetaDelegate;
import ic2.core.IC2;
import ic2.core.energy.Change;
import ic2.core.energy.EnergyNetGlobal;
import ic2.core.energy.Grid;
import ic2.core.energy.Tile;
import ic2.core.energy.grid.GridInfo;
import ic2.core.energy.grid.IEnergyCalculator;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeType;
import ic2.core.event.TickHandler;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class EnergyNetLocal
implements IEnergyCalculator {
    public static final boolean useLinearTransferModel = ConfigUtil.getBool(MainConfig.get(), "misc/useLinearTransferModel");
    public static final double nonConductorResistance = 0.2;
    public static final double sourceResistanceFactor = 0.0625;
    public static final double sinkResistanceFactor = 1.0;
    public static final double sourceCurrent = 17.0;
    public static final boolean enableCache = true;
    private static int nextGridUid = 0;
    private static int nextNodeUid = 0;
    private final Level world = null;
    protected final Set<Grid> grids = new HashSet<Grid>();
    protected List<Change> changes = new ArrayList<Change>();
    private final Map<BlockPos, Tile> registeredTiles = new HashMap<BlockPos, Tile>();
    private final Map<IEnergyTile, Integer> pendingAdds = new WeakHashMap<IEnergyTile, Integer>();
    private final Set<Tile> removedTiles = new HashSet<Tile>();
    private boolean locked = false;
    private static final long logSuppressionTimeout = 300000000000L;
    private final Map<String, Long> recentLogs = new HashMap<String, Long>();

    public EnergyNetLocal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void handleGridChange(ic2.core.energy.grid.Grid grid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean runSyncStep(ic2.core.energy.grid.EnergyNetLocal energyNetLocal) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean runSyncStep(ic2.core.energy.grid.Grid grid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void runAsyncStep(ic2.core.energy.grid.Grid grid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeStats getNodeStats(ic2.core.energy.grid.Tile tile) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void dumpNodeInfo(Node node, String string, PrintStream printStream, PrintStream printStream2) {
        throw new UnsupportedOperationException();
    }

    protected void addTile(IEnergyTile iEnergyTile) {
        this.addTile(iEnergyTile, 0);
    }

    protected void addTile(IEnergyTile iEnergyTile, int n) {
        Iterator<ic2.core.energy.Node> iterator;
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, "EnergyNet.addTile(%s, %d), world=%s, chunk=%s, this=%s", iEnergyTile, n, EnergyNet.instance.getWorld(iEnergyTile), EnergyNet.instance.getWorld(iEnergyTile).m_46745_(EnergyNet.instance.getPos(iEnergyTile)), this);
        }
        if (EnergyNetGlobal.checkApi && !Util.checkInterfaces(iEnergyTile.getClass())) {
            IC2.log.warn(LogCategory.EnergyNet, "EnergyNet.addTile: %s doesn't implement its advertised interfaces completely.", iEnergyTile);
        }
        if (iEnergyTile instanceof BlockEntity && ((BlockEntity)iEnergyTile).m_58901_()) {
            this.logWarn("EnergyNet.addTile: " + iEnergyTile + " is invalid (TileEntity.isInvalid()), aborting");
            return;
        }
        if (this.world != IC2.sideProxy.getWorld(this.world.m_7654_(), Util.getDimId(this.world))) {
            this.logDebug("EnergyNet.addTile: " + iEnergyTile + " is in an unloaded world, aborting");
            return;
        }
        if (this.locked) {
            this.logDebug("EnergyNet.addTileEntity: adding " + iEnergyTile + " while locked, postponing.");
            this.pendingAdds.put(iEnergyTile, n);
            return;
        }
        Tile tile = new Tile(this, iEnergyTile);
        if (EnergyNetGlobal.debugTileManagement) {
            iterator = new ArrayList(tile.subTiles.size());
            for (IEnergyTile iEnergyTile2 : tile.subTiles) {
                iterator.add((ic2.core.energy.Node)((Object)String.format("%s (%s)", iEnergyTile2, EnergyNet.instance.getPos(iEnergyTile2))));
            }
            IC2.log.debug(LogCategory.EnergyNet, "positions: %s", iterator);
        }
        iterator = tile.subTiles.listIterator();
        while (iterator.hasNext()) {
            IEnergyTile iEnergyTile3 = (IEnergyTile)iterator.next();
            BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile3).m_7949_();
            Tile tile2 = this.registeredTiles.get(blockPos);
            boolean bl = false;
            if (tile2 != null) {
                if (iEnergyTile == tile2.mainTile) {
                    this.logDebug("EnergyNet.addTileEntity: " + iEnergyTile3 + " (" + iEnergyTile + ") is already added using the same position, aborting");
                } else if (n < 2) {
                    this.pendingAdds.put(iEnergyTile, n + 1);
                } else if (tile2.mainTile instanceof BlockEntity && ((BlockEntity)iEnergyTile).m_58901_() || EnergyNetGlobal.replaceConflicting) {
                    this.logDebug("EnergyNet.addTileEntity: " + iEnergyTile3 + " (" + iEnergyTile + ") is conflicting with " + tile2.mainTile + " (invalid=" + (tile2.mainTile instanceof BlockEntity && ((BlockEntity)tile2.mainTile).m_58901_()) + ") using the same position, which is abandoned (prev. te not removed), replacing");
                    this.removeTile(tile2.mainTile);
                    tile2 = null;
                } else {
                    this.logWarn("EnergyNet.addTileEntity: " + iEnergyTile3 + " (" + iEnergyTile + ") is still conflicting with " + tile2.mainTile + " using the same position (overlapping), aborting");
                }
                if (tile2 != null) {
                    bl = true;
                }
            }
            if (!bl && !this.world.m_46749_(blockPos)) {
                if (n < 1) {
                    this.logWarn("EnergyNet.addTileEntity: " + iEnergyTile3 + " (" + iEnergyTile + ") was added too early, postponing");
                    this.pendingAdds.put(iEnergyTile, n + 1);
                } else {
                    this.logWarn("EnergyNet.addTileEntity: " + iEnergyTile3 + " (" + iEnergyTile + ") unloaded, aborting");
                }
                bl = true;
            }
            if (bl) {
                iterator.previous();
                while (iterator.hasPrevious()) {
                    IEnergyTile iEnergyTile4 = (IEnergyTile)iterator.previous();
                    this.registeredTiles.remove(EnergyNet.instance.getPos(iEnergyTile4));
                }
                return;
            }
            this.registeredTiles.put(blockPos, tile);
            this.notifyLoadedNeighbors(blockPos, tile.subTiles);
        }
        this.addTileToGrids(tile);
        if (EnergyNetGlobal.verifyGrid()) {
            for (ic2.core.energy.Node node : tile.nodes) {
                assert (node.getGrid() != null);
            }
        }
    }

    private void notifyLoadedNeighbors(BlockPos blockPos, List<IEnergyTile> list) {
        HashSet<BlockPos> hashSet = new HashSet<BlockPos>(list.size());
        for (IEnergyTile iEnergyTile : list) {
            hashSet.add(EnergyNet.instance.getPos(iEnergyTile).m_7949_());
        }
        Block block = this.world.m_8055_(blockPos).m_60734_();
        int n = blockPos.m_123341_() >> 4;
        int n2 = blockPos.m_123343_() >> 4;
        for (Direction direction : Util.ALL_DIRS) {
            BlockPos blockPos2 = blockPos.m_121945_(direction);
            if (hashSet.contains(blockPos2)) continue;
            int n3 = blockPos2.m_123341_() >> 4;
            int n4 = blockPos2.m_123343_() >> 4;
            if (!direction.m_122434_().m_122478_() && (n3 != n || n4 != n2) && !this.world.m_46749_(blockPos2)) continue;
            this.world.m_8055_(blockPos2).m_60690_(this.world, blockPos2, block, blockPos, false);
        }
    }

    protected void removeTile(IEnergyTile iEnergyTile) {
        boolean bl;
        if (this.locked) {
            throw new IllegalStateException("removeTile isn't allowed from this context");
        }
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, "EnergyNet.removeTile(%s), world=%s, chunk=%s, this=%s", iEnergyTile, EnergyNet.instance.getWorld(iEnergyTile), EnergyNet.instance.getWorld(iEnergyTile).m_46745_(EnergyNet.instance.getPos(iEnergyTile)), this);
        }
        List<IEnergyTile> list = iEnergyTile instanceof IMetaDelegate ? ((IMetaDelegate)iEnergyTile).getSubTiles() : Arrays.asList(iEnergyTile);
        boolean bl2 = bl = this.pendingAdds.remove(iEnergyTile) != null;
        if (EnergyNetGlobal.debugTileManagement) {
            ArrayList<String> arrayList = new ArrayList<String>(list.size());
            for (IEnergyTile iEnergyTile2 : list) {
                arrayList.add(String.format("%s (%s)", iEnergyTile2, EnergyNet.instance.getPos(iEnergyTile2)));
            }
            IC2.log.debug(LogCategory.EnergyNet, "positions: %s", arrayList);
        }
        boolean bl3 = false;
        for (IEnergyTile iEnergyTile2 : list) {
            BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile2);
            Tile tile = this.registeredTiles.get(blockPos);
            if (tile == null) {
                if (bl) continue;
                this.logDebug("EnergyNet.removeTileEntity: " + iEnergyTile2 + " (" + iEnergyTile + ") wasn't found (added), skipping");
                continue;
            }
            if (tile.mainTile != iEnergyTile) {
                this.logWarn("EnergyNet.removeTileEntity: " + iEnergyTile2 + " (" + iEnergyTile + ") doesn't match the registered tile " + tile.mainTile + ", skipping");
                continue;
            }
            if (!bl3) {
                assert (new HashSet<IEnergyTile>(list).equals(new HashSet<IEnergyTile>(tile.subTiles)));
                this.removeTileFromGrids(tile);
                bl3 = true;
                this.removedTiles.add(tile);
            }
            this.registeredTiles.remove(blockPos);
            if (!this.world.m_46749_(blockPos)) continue;
            this.notifyLoadedNeighbors(blockPos, tile.subTiles);
        }
    }

    protected double getTotalEnergyEmitted(BlockEntity blockEntity) {
        BlockPos blockPos = new BlockPos((Vec3i)blockEntity.m_58899_());
        Tile tile = this.registeredTiles.get(blockPos);
        if (tile == null) {
            this.logWarn("EnergyNet.getTotalEnergyEmitted: " + blockEntity + " is not added to the enet, aborting");
            return 0.0;
        }
        double d = 0.0;
        Iterable<NodeStats> iterable = tile.getStats();
        for (NodeStats nodeStats : iterable) {
            d += nodeStats.getEnergyOut();
        }
        return d;
    }

    protected double getTotalEnergySunken(BlockEntity blockEntity) {
        BlockPos blockPos = new BlockPos((Vec3i)blockEntity.m_58899_());
        Tile tile = this.registeredTiles.get(blockPos);
        if (tile == null) {
            this.logWarn("EnergyNet.getTotalEnergySunken: " + blockEntity + " is not added to the enet, aborting");
            return 0.0;
        }
        double d = 0.0;
        Iterable<NodeStats> iterable = tile.getStats();
        for (NodeStats nodeStats : iterable) {
            d += nodeStats.getEnergyIn();
        }
        return d;
    }

    protected NodeStats getNodeStats(IEnergyTile iEnergyTile) {
        BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
        Tile tile = this.registeredTiles.get(blockPos);
        if (tile == null) {
            this.logWarn("EnergyNet.getTotalEnergySunken: " + iEnergyTile + " is not added to the enet");
            return new NodeStats(0.0, 0.0, 0.0);
        }
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        Iterable<NodeStats> iterable = tile.getStats();
        for (NodeStats nodeStats : iterable) {
            d += nodeStats.getEnergyIn();
            d2 += nodeStats.getEnergyOut();
            d3 = Math.max(d3, nodeStats.getVoltage());
        }
        return new NodeStats(d, d2, d3);
    }

    protected Tile getTile(BlockPos blockPos) {
        return this.registeredTiles.get(blockPos);
    }

    public boolean dumpDebugInfo(PrintStream printStream, PrintStream printStream2, BlockPos blockPos) {
        Tile tile = this.registeredTiles.get(blockPos);
        if (tile == null) {
            return false;
        }
        printStream2.println("Tile " + tile + " info:");
        printStream2.println(" main: " + tile.mainTile);
        printStream2.println(" sub: " + tile.subTiles);
        printStream2.println(" nodes: " + tile.nodes.size());
        HashSet<Grid> hashSet = new HashSet<Grid>();
        for (ic2.core.energy.Node node : tile.nodes) {
            Grid grid = node.getGrid();
            if (!hashSet.add(grid)) continue;
            grid.dumpNodeInfo(printStream2, true, node);
            grid.dumpStats(printStream2, true);
            grid.dumpMatrix(printStream, true, true, true);
            printStream.println("dumping graph for " + grid);
            grid.dumpGraph(true);
        }
        return true;
    }

    public List<GridInfo> getGridInfos() {
        ArrayList<GridInfo> arrayList = new ArrayList<GridInfo>();
        for (Grid grid : this.grids) {
            arrayList.add(grid.getInfo());
        }
        return arrayList;
    }

    protected void onTickEnd() {
        Object object;
        if (!IC2.sideProxy.isSimulating()) {
            return;
        }
        this.locked = true;
        for (Grid iterator : this.grids) {
            iterator.finishCalculation();
            iterator.updateStats();
        }
        this.locked = false;
        this.processChanges();
        if (!this.pendingAdds.isEmpty()) {
            object = new ArrayList<Map.Entry<IEnergyTile, Integer>>(this.pendingAdds.entrySet());
            this.pendingAdds.clear();
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                this.addTile((IEnergyTile)entry.getKey(), (Integer)entry.getValue());
            }
        }
        this.locked = true;
        for (Grid grid : this.grids) {
            grid.prepareCalculation();
        }
        object = new ArrayList();
        for (Grid grid : this.grids) {
            Runnable runnable = grid.startCalculation();
            if (runnable == null) continue;
            object.add(runnable);
        }
        IC2.threadPool.executeAll((List<? extends Runnable>)object);
        this.locked = false;
    }

    protected void addChange(ic2.core.energy.Node node, Direction direction, double d, double d2) {
        this.changes.add(new Change(node, direction, d, d2));
    }

    protected static int getNextGridUid() {
        return nextGridUid++;
    }

    protected static int getNextNodeUid() {
        return nextNodeUid++;
    }

    /*
     * Could not resolve type clashes
     */
    private void addTileToGrids(Tile tile) {
        ArrayList<ic2.core.energy.Node> arrayList = new ArrayList<ic2.core.energy.Node>();
        block4: for (ic2.core.energy.Node node : tile.nodes) {
            Object object;
            Object object2;
            Object object32;
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "Adding node %s.", node);
            }
            ArrayList<ic2.core.energy.Node> arrayList2 = new ArrayList<ic2.core.energy.Node>();
            for (Object object32 : tile.subTiles) {
                for (Object object4 : Util.ALL_DIRS) {
                    object2 = EnergyNet.instance.getPos((IEnergyTile)object32).m_121945_((Direction)object4);
                    Tile tile2 = this.registeredTiles.get(object2);
                    if (tile2 == null || tile2 == node.tile) continue;
                    for (ic2.core.energy.Node node2 : tile2.nodes) {
                        IEnergyTile iEnergyTile;
                        IEnergyTile iEnergyTile2;
                        if (node2.isExtraNode()) continue;
                        boolean bl = false;
                        if ((node.nodeType == NodeType.Source || node.nodeType == NodeType.Conductor) && node2.nodeType != NodeType.Source) {
                            IEnergyEmitter iEnergyEmitter = (IEnergyEmitter)(object32 instanceof IEnergyEmitter ? object32 : node.tile.mainTile);
                            iEnergyTile2 = tile2.getSubTileAt((BlockPos)object2);
                            iEnergyTile = (IEnergyAcceptor)(iEnergyTile2 instanceof IEnergyAcceptor ? iEnergyTile2 : node2.tile.mainTile);
                            bl = iEnergyEmitter.emitsEnergyTo((IEnergyAcceptor)node2.tile.mainTile, (Direction)object4) && iEnergyTile.acceptsEnergyFrom((IEnergyEmitter)node.tile.mainTile, object4.m_122424_());
                        }
                        boolean bl2 = false;
                        if (!(bl || node.nodeType != NodeType.Sink && node.nodeType != NodeType.Conductor || node2.nodeType == NodeType.Sink)) {
                            iEnergyTile2 = (IEnergyAcceptor)(object32 instanceof IEnergyAcceptor ? object32 : node.tile.mainTile);
                            iEnergyTile = tile2.getSubTileAt((BlockPos)object2);
                            IEnergyEmitter iEnergyEmitter = (IEnergyEmitter)(iEnergyTile instanceof IEnergyEmitter ? iEnergyTile : node2.tile.mainTile);
                            boolean bl3 = bl2 = iEnergyTile2.acceptsEnergyFrom((IEnergyEmitter)node2.tile.mainTile, (Direction)object4) && iEnergyEmitter.emitsEnergyTo((IEnergyAcceptor)node.tile.mainTile, object4.m_122424_());
                        }
                        if (!bl && !bl2) continue;
                        arrayList2.add(node2);
                    }
                }
            }
            if (arrayList2.isEmpty()) {
                if (EnergyNetGlobal.debugGrid) {
                    IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s.", node);
                }
                object = new Grid(this);
                ((Grid)object).add(node, arrayList2);
                continue;
            }
            switch (node.nodeType) {
                case Conductor: {
                    Object object52;
                    object = null;
                    for (Object object52 : arrayList2) {
                        if (((ic2.core.energy.Node)object52).nodeType != NodeType.Conductor && !((ic2.core.energy.Node)object52).links.isEmpty()) continue;
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "Using %s for %s with neighbors %s.", ((ic2.core.energy.Node)object52).getGrid(), node, arrayList2);
                        }
                        object = ((ic2.core.energy.Node)object52).getGrid();
                        break;
                    }
                    if (object == null) {
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s with neighbors %s.", node, arrayList2);
                        }
                        object = new Grid(this);
                    }
                    object32 = new HashMap();
                    object52 = arrayList2.listIterator();
                    while (object52.hasNext()) {
                        ic2.core.energy.Node node3 = (ic2.core.energy.Node)object52.next();
                        if (node3.getGrid() == object) continue;
                        if (node3.nodeType != NodeType.Conductor && !node3.links.isEmpty()) {
                            int n = 0;
                            for (int i = 0; i < object52.previousIndex(); ++i) {
                                object2 = (ic2.core.energy.Node)arrayList2.get(i);
                                if (((ic2.core.energy.Node)object2).tile != node3.tile || ((ic2.core.energy.Node)object2).nodeType != node3.nodeType || ((ic2.core.energy.Node)object2).getGrid() != object) continue;
                                if (EnergyNetGlobal.debugGrid) {
                                    IC2.log.debug(LogCategory.EnergyNet, "Using neighbor node %s instead of %s.", object2, arrayList2);
                                }
                                n = 1;
                                object52.set(object2);
                                break;
                            }
                            if (n != 0) continue;
                            if (EnergyNetGlobal.debugGrid) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", node3);
                            }
                            node3 = new ic2.core.energy.Node(this, node3.tile, node3.nodeType);
                            node3.tile.addExtraNode(node3);
                            ((Grid)object).add(node3, Collections.emptyList());
                            object52.set(node3);
                            assert (node3.getGrid() != null);
                            continue;
                        }
                        ((Grid)object).merge(node3.getGrid(), (Map<ic2.core.energy.Node, ic2.core.energy.Node>)object32);
                    }
                    object52 = arrayList2.listIterator();
                    while (object52.hasNext()) {
                        ic2.core.energy.Node node4 = (ic2.core.energy.Node)object52.next();
                        ic2.core.energy.Node node5 = (ic2.core.energy.Node)object32.get(node4);
                        if (node5 != null) {
                            node4 = node5;
                            object52.set(node5);
                        }
                        assert (node4.getGrid() == object);
                    }
                    ((Grid)object).add(node, arrayList2);
                    assert (node.getGrid() != null);
                    break;
                }
                case Sink: 
                case Source: {
                    Object object52;
                    object = new ArrayList();
                    object32 = arrayList2.iterator();
                    while (object32.hasNext()) {
                        Object object6;
                        object52 = (ic2.core.energy.Node)object32.next();
                        int n = 0;
                        if (node.nodeType == NodeType.Conductor) {
                            object6 = object.iterator();
                            while (object6.hasNext()) {
                                Object object4;
                                object4 = (List)object6.next();
                                object2 = (ic2.core.energy.Node)object4.get(0);
                                if (((ic2.core.energy.Node)object2).nodeType != NodeType.Conductor || ((ic2.core.energy.Node)object2).getGrid() != ((ic2.core.energy.Node)object52).getGrid()) continue;
                                object4.add(object52);
                                n = 1;
                                break;
                            }
                        }
                        if (n != 0) continue;
                        object6 = new ArrayList();
                        object6.add(object52);
                        object.add(object6);
                    }
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "Neighbor groups detected for %s: %s.", node, object);
                    }
                    assert (!object.isEmpty());
                    for (int i = 0; i < object.size(); ++i) {
                        ic2.core.energy.Node node6;
                        object52 = (List)object.get(i);
                        ic2.core.energy.Node node7 = (ic2.core.energy.Node)object52.get(0);
                        if (node7.nodeType != NodeType.Conductor && !node7.links.isEmpty()) {
                            assert (object52.size() == 1);
                            if (EnergyNetGlobal.debugGrid) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", node7);
                            }
                            node7 = new ic2.core.energy.Node(this, node7.tile, node7.nodeType);
                            node7.tile.addExtraNode(node7);
                            new Grid(this).add(node7, Collections.emptyList());
                            object52.set(0, node7);
                            assert (node7.getGrid() != null);
                        }
                        if (i == 0) {
                            node6 = node;
                        } else {
                            if (EnergyNetGlobal.debugGrid) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for %s.", node);
                            }
                            node6 = new ic2.core.energy.Node(this, tile, node.nodeType);
                            node6.setExtraNode(true);
                            arrayList.add(node6);
                        }
                        node7.getGrid().add(node6, (Collection<ic2.core.energy.Node>)object52);
                        assert (node6.getGrid() != null);
                    }
                    continue block4;
                }
            }
        }
        for (ic2.core.energy.Node node : arrayList) {
            tile.addExtraNode(node);
        }
    }

    private void removeTileFromGrids(Tile tile) {
        for (ic2.core.energy.Node node : tile.nodes) {
            node.getGrid().remove(node);
        }
    }

    private void processChanges() {
        Object object;
        for (Tile object2 : this.removedTiles) {
            object = this.changes.iterator();
            while (object.hasNext()) {
                Change change = object.next();
                if (change.node.tile != object2) continue;
                Tile tile = this.registeredTiles.get(EnergyNet.instance.getPos(change.node.tile.mainTile));
                boolean bl = false;
                if (tile != null) {
                    for (ic2.core.energy.Node node : tile.nodes) {
                        if (node.nodeType != change.node.nodeType || node.getGrid() != change.node.getGrid()) continue;
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "Redirecting change %s to replacement node %s.", change, node);
                        }
                        change.node = node;
                        bl = true;
                        break;
                    }
                }
                if (bl) continue;
                object.remove();
                Iterator<Change> iterator = new ArrayList();
                for (Change change2 : this.changes) {
                    if (change2.node.nodeType != NodeType.Source || change.node.getGrid() != change2.node.getGrid()) continue;
                    iterator.add(change2);
                }
                if (EnergyNetGlobal.debugGrid) {
                    IC2.log.debug(LogCategory.EnergyNet, "Redistributing change %s to remaining source nodes %s.", change, iterator);
                }
                Iterator iterator2 = iterator.iterator();
                while (iterator2.hasNext()) {
                    Change change2;
                    change2 = (Change)iterator2.next();
                    change2.setAmount(change2.getAmount() - Math.abs(change.getAmount()) / (double)iterator.size());
                }
            }
        }
        this.removedTiles.clear();
        for (Change change : this.changes) {
            if (change.node.nodeType != NodeType.Sink) continue;
            assert (change.getAmount() > 0.0);
            object = (IEnergySink)change.node.tile.mainTile;
            double d = object.injectEnergy(change.dir, change.getAmount(), change.getVoltage());
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "Applied change %s, %f EU returned.", change, d);
            }
            if (!(d > 0.0)) continue;
            ArrayList<Change> arrayList = new ArrayList<Change>();
            for (Change change3 : this.changes) {
                if (change3.node.nodeType != NodeType.Source || change.node.getGrid() != change3.node.getGrid()) continue;
                arrayList.add(change3);
            }
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "Redistributing returned amount to source nodes %s.", arrayList);
            }
            for (Change change4 : arrayList) {
                change4.setAmount(change4.getAmount() - d / (double)arrayList.size());
            }
        }
        for (Change change : this.changes) {
            if (change.node.nodeType != NodeType.Source) continue;
            assert (change.getAmount() <= 0.0);
            if (change.getAmount() >= 0.0) continue;
            object = (IEnergySource)change.node.tile.mainTile;
            object.drawEnergy(change.getAmount());
            if (!EnergyNetGlobal.debugGrid) continue;
            IC2.log.debug(LogCategory.EnergyNet, "Applied change %s.", change);
        }
        this.changes.clear();
    }

    private void logDebug(String string) {
        if (!this.shouldLog(string)) {
            return;
        }
        IC2.log.debug(LogCategory.EnergyNet, string);
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable(), "stack trace");
            if (TickHandler.getLastDebugTrace() != null) {
                IC2.log.debug(LogCategory.EnergyNet, TickHandler.getLastDebugTrace(), "parent stack trace");
            }
        }
    }

    private void logWarn(String string) {
        if (!this.shouldLog(string)) {
            return;
        }
        IC2.log.warn(LogCategory.EnergyNet, string);
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable(), "stack trace");
            if (TickHandler.getLastDebugTrace() != null) {
                IC2.log.debug(LogCategory.EnergyNet, TickHandler.getLastDebugTrace(), "parent stack trace");
            }
        }
    }

    private boolean shouldLog(String string) {
        if (EnergyNetGlobal.logAll) {
            return true;
        }
        this.cleanRecentLogs();
        string = string.replaceAll("@[0-9a-f]+", "@x");
        long l = System.nanoTime();
        Long l2 = this.recentLogs.put(string, l);
        return l2 == null || l2 < l - 300000000000L;
    }

    private void cleanRecentLogs() {
        if (this.recentLogs.size() < 100) {
            return;
        }
        long l = System.nanoTime() - 300000000000L;
        Iterator<Long> iterator = this.recentLogs.values().iterator();
        while (iterator.hasNext()) {
            long l2 = iterator.next();
            if (l2 >= l) continue;
            iterator.remove();
        }
    }
}

