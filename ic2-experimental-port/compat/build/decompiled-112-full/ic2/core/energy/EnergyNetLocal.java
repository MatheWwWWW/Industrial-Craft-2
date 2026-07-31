/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.Vec3i
 *  net.minecraft.world.World
 *  net.minecraftforge.common.DimensionManager
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
import ic2.core.TickHandler;
import ic2.core.energy.Change;
import ic2.core.energy.EnergyNetGlobal;
import ic2.core.energy.Grid;
import ic2.core.energy.Tile;
import ic2.core.energy.grid.GridInfo;
import ic2.core.energy.grid.IEnergyCalculator;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeType;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

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
    private final World world = null;
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
    public boolean runSyncStep(ic2.core.energy.grid.EnergyNetLocal enet) {
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
    public void dumpNodeInfo(Node node, String prefix, PrintStream console, PrintStream chat) {
        throw new UnsupportedOperationException();
    }

    protected void addTile(IEnergyTile mainTile) {
        this.addTile(mainTile, 0);
    }

    protected void addTile(IEnergyTile mainTile, int retry) {
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, "EnergyNet.addTile(%s, %d), world=%s, chunk=%s, this=%s", mainTile, retry, EnergyNet.instance.getWorld(mainTile), EnergyNet.instance.getWorld(mainTile).func_175726_f(EnergyNet.instance.getPos(mainTile)), this);
        }
        if (EnergyNetGlobal.checkApi && !Util.checkInterfaces(mainTile.getClass())) {
            IC2.log.warn(LogCategory.EnergyNet, "EnergyNet.addTile: %s doesn't implement its advertised interfaces completely.", mainTile);
        }
        if (mainTile instanceof TileEntity && ((TileEntity)mainTile).func_145837_r()) {
            this.logWarn("EnergyNet.addTile: " + mainTile + " is invalid (TileEntity.isInvalid()), aborting");
            return;
        }
        if (this.world != DimensionManager.getWorld((int)this.world.field_73011_w.getDimension())) {
            this.logDebug("EnergyNet.addTile: " + mainTile + " is in an unloaded world, aborting");
            return;
        }
        if (this.locked) {
            this.logDebug("EnergyNet.addTileEntity: adding " + mainTile + " while locked, postponing.");
            this.pendingAdds.put(mainTile, retry);
            return;
        }
        Tile tile = new Tile(this, mainTile);
        if (EnergyNetGlobal.debugTileManagement) {
            ArrayList<String> posStrings = new ArrayList<String>(tile.subTiles.size());
            for (IEnergyTile subTile : tile.subTiles) {
                posStrings.add(String.format("%s (%s)", subTile, EnergyNet.instance.getPos(subTile)));
            }
            IC2.log.debug(LogCategory.EnergyNet, "positions: %s", posStrings);
        }
        ListIterator<IEnergyTile> it = tile.subTiles.listIterator();
        while (it.hasNext()) {
            IEnergyTile subTile = it.next();
            BlockPos pos = EnergyNet.instance.getPos(subTile).func_185334_h();
            Tile conflicting = this.registeredTiles.get(pos);
            boolean abort = false;
            if (conflicting != null) {
                if (mainTile == conflicting.mainTile) {
                    this.logDebug("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") is already added using the same position, aborting");
                } else if (retry < 2) {
                    this.pendingAdds.put(mainTile, retry + 1);
                } else if (conflicting.mainTile instanceof TileEntity && ((TileEntity)mainTile).func_145837_r() || EnergyNetGlobal.replaceConflicting) {
                    this.logDebug("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") is conflicting with " + conflicting.mainTile + " (invalid=" + (conflicting.mainTile instanceof TileEntity && ((TileEntity)conflicting.mainTile).func_145837_r()) + ") using the same position, which is abandoned (prev. te not removed), replacing");
                    this.removeTile(conflicting.mainTile);
                    conflicting = null;
                } else {
                    this.logWarn("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") is still conflicting with " + conflicting.mainTile + " using the same position (overlapping), aborting");
                }
                if (conflicting != null) {
                    abort = true;
                }
            }
            if (!abort && !this.world.func_175667_e(pos)) {
                if (retry < 1) {
                    this.logWarn("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") was added too early, postponing");
                    this.pendingAdds.put(mainTile, retry + 1);
                } else {
                    this.logWarn("EnergyNet.addTileEntity: " + subTile + " (" + mainTile + ") unloaded, aborting");
                }
                abort = true;
            }
            if (abort) {
                it.previous();
                while (it.hasPrevious()) {
                    subTile = it.previous();
                    this.registeredTiles.remove(EnergyNet.instance.getPos(subTile));
                }
                return;
            }
            this.registeredTiles.put(pos, tile);
            this.notifyLoadedNeighbors(pos, tile.subTiles);
        }
        this.addTileToGrids(tile);
        if (EnergyNetGlobal.verifyGrid()) {
            for (ic2.core.energy.Node node : tile.nodes) {
                assert (node.getGrid() != null);
            }
        }
    }

    private void notifyLoadedNeighbors(BlockPos pos, List<IEnergyTile> excluded) {
        HashSet<BlockPos> excludedPositions = new HashSet<BlockPos>(excluded.size());
        for (IEnergyTile subTile : excluded) {
            excludedPositions.add(EnergyNet.instance.getPos(subTile).func_185334_h());
        }
        Block block = this.world.func_180495_p(pos).func_177230_c();
        int ocx = pos.func_177958_n() >> 4;
        int ocz = pos.func_177952_p() >> 4;
        for (EnumFacing dir : EnumFacing.field_82609_l) {
            BlockPos cPos = pos.func_177972_a(dir);
            if (excludedPositions.contains(cPos)) continue;
            int ccx = cPos.func_177958_n() >> 4;
            int ccz = cPos.func_177952_p() >> 4;
            if (!dir.func_176740_k().func_176720_b() && (ccx != ocx || ccz != ocz) && !this.world.func_175667_e(cPos)) continue;
            this.world.func_180495_p(cPos).func_189546_a(this.world, cPos, block, pos);
        }
    }

    protected void removeTile(IEnergyTile mainTile) {
        boolean wasPending;
        if (this.locked) {
            throw new IllegalStateException("removeTile isn't allowed from this context");
        }
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, "EnergyNet.removeTile(%s), world=%s, chunk=%s, this=%s", mainTile, EnergyNet.instance.getWorld(mainTile), EnergyNet.instance.getWorld(mainTile).func_175726_f(EnergyNet.instance.getPos(mainTile)), this);
        }
        List<IEnergyTile> subTiles = mainTile instanceof IMetaDelegate ? ((IMetaDelegate)mainTile).getSubTiles() : Arrays.asList(mainTile);
        boolean bl = wasPending = this.pendingAdds.remove(mainTile) != null;
        if (EnergyNetGlobal.debugTileManagement) {
            ArrayList<String> posStrings = new ArrayList<String>(subTiles.size());
            for (IEnergyTile subTile : subTiles) {
                posStrings.add(String.format("%s (%s)", subTile, EnergyNet.instance.getPos(subTile)));
            }
            IC2.log.debug(LogCategory.EnergyNet, "positions: %s", posStrings);
        }
        boolean removed = false;
        for (IEnergyTile subTile : subTiles) {
            BlockPos pos = EnergyNet.instance.getPos(subTile);
            Tile tile = this.registeredTiles.get(pos);
            if (tile == null) {
                if (wasPending) continue;
                this.logDebug("EnergyNet.removeTileEntity: " + subTile + " (" + mainTile + ") wasn't found (added), skipping");
                continue;
            }
            if (tile.mainTile != mainTile) {
                this.logWarn("EnergyNet.removeTileEntity: " + subTile + " (" + mainTile + ") doesn't match the registered tile " + tile.mainTile + ", skipping");
                continue;
            }
            if (!removed) {
                assert (new HashSet<IEnergyTile>(subTiles).equals(new HashSet<IEnergyTile>(tile.subTiles)));
                this.removeTileFromGrids(tile);
                removed = true;
                this.removedTiles.add(tile);
            }
            this.registeredTiles.remove(pos);
            if (!this.world.func_175667_e(pos)) continue;
            this.notifyLoadedNeighbors(pos, tile.subTiles);
        }
    }

    protected double getTotalEnergyEmitted(TileEntity tileEntity) {
        BlockPos coords = new BlockPos((Vec3i)tileEntity.func_174877_v());
        Tile tile = this.registeredTiles.get(coords);
        if (tile == null) {
            this.logWarn("EnergyNet.getTotalEnergyEmitted: " + tileEntity + " is not added to the enet, aborting");
            return 0.0;
        }
        double ret = 0.0;
        Iterable<NodeStats> stats = tile.getStats();
        for (NodeStats stat : stats) {
            ret += stat.getEnergyOut();
        }
        return ret;
    }

    protected double getTotalEnergySunken(TileEntity tileEntity) {
        BlockPos coords = new BlockPos((Vec3i)tileEntity.func_174877_v());
        Tile tile = this.registeredTiles.get(coords);
        if (tile == null) {
            this.logWarn("EnergyNet.getTotalEnergySunken: " + tileEntity + " is not added to the enet, aborting");
            return 0.0;
        }
        double ret = 0.0;
        Iterable<NodeStats> stats = tile.getStats();
        for (NodeStats stat : stats) {
            ret += stat.getEnergyIn();
        }
        return ret;
    }

    protected NodeStats getNodeStats(IEnergyTile energyTile) {
        BlockPos coords = EnergyNet.instance.getPos(energyTile);
        Tile tile = this.registeredTiles.get(coords);
        if (tile == null) {
            this.logWarn("EnergyNet.getTotalEnergySunken: " + energyTile + " is not added to the enet");
            return new NodeStats(0.0, 0.0, 0.0);
        }
        double in = 0.0;
        double out = 0.0;
        double voltage = 0.0;
        Iterable<NodeStats> stats = tile.getStats();
        for (NodeStats stat : stats) {
            in += stat.getEnergyIn();
            out += stat.getEnergyOut();
            voltage = Math.max(voltage, stat.getVoltage());
        }
        return new NodeStats(in, out, voltage);
    }

    protected Tile getTile(BlockPos pos) {
        return this.registeredTiles.get(pos);
    }

    public boolean dumpDebugInfo(PrintStream console, PrintStream chat, BlockPos pos) {
        Tile tile = this.registeredTiles.get(pos);
        if (tile == null) {
            return false;
        }
        chat.println("Tile " + tile + " info:");
        chat.println(" main: " + tile.mainTile);
        chat.println(" sub: " + tile.subTiles);
        chat.println(" nodes: " + tile.nodes.size());
        HashSet<Grid> processedGrids = new HashSet<Grid>();
        for (ic2.core.energy.Node node : tile.nodes) {
            Grid grid = node.getGrid();
            if (!processedGrids.add(grid)) continue;
            grid.dumpNodeInfo(chat, true, node);
            grid.dumpStats(chat, true);
            grid.dumpMatrix(console, true, true, true);
            console.println("dumping graph for " + grid);
            grid.dumpGraph(true);
        }
        return true;
    }

    public List<GridInfo> getGridInfos() {
        ArrayList<GridInfo> ret = new ArrayList<GridInfo>();
        for (Grid grid : this.grids) {
            ret.add(grid.getInfo());
        }
        return ret;
    }

    protected void onTickEnd() {
        if (!IC2.platform.isSimulating()) {
            return;
        }
        this.locked = true;
        for (Grid grid : this.grids) {
            grid.finishCalculation();
            grid.updateStats();
        }
        this.locked = false;
        this.processChanges();
        if (!this.pendingAdds.isEmpty()) {
            ArrayList<Map.Entry<IEnergyTile, Integer>> pending = new ArrayList<Map.Entry<IEnergyTile, Integer>>(this.pendingAdds.entrySet());
            this.pendingAdds.clear();
            Iterator iterator = pending.iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator.next();
                this.addTile((IEnergyTile)entry.getKey(), (Integer)entry.getValue());
            }
        }
        this.locked = true;
        for (Grid grid : this.grids) {
            grid.prepareCalculation();
        }
        ArrayList<Runnable> tasks = new ArrayList<Runnable>();
        for (Grid grid : this.grids) {
            Runnable task = grid.startCalculation();
            if (task == null) continue;
            tasks.add(task);
        }
        IC2.getInstance().threadPool.executeAll(tasks);
        this.locked = false;
    }

    protected void addChange(ic2.core.energy.Node node, EnumFacing dir, double amount, double voltage) {
        this.changes.add(new Change(node, dir, amount, voltage));
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
        ArrayList<ic2.core.energy.Node> extraNodes = new ArrayList<ic2.core.energy.Node>();
        block4: for (ic2.core.energy.Node node : tile.nodes) {
            Grid grid;
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "Adding node %s.", node);
            }
            ArrayList<ic2.core.energy.Node> neighbors = new ArrayList<ic2.core.energy.Node>();
            for (Object subTile : tile.subTiles) {
                for (EnumFacing dir : EnumFacing.field_82609_l) {
                    BlockPos coords = EnergyNet.instance.getPos((IEnergyTile)subTile).func_177972_a(dir);
                    Tile neighborTile = this.registeredTiles.get(coords);
                    if (neighborTile == null || neighborTile == node.tile) continue;
                    for (ic2.core.energy.Node neighbor : neighborTile.nodes) {
                        if (neighbor.isExtraNode()) continue;
                        boolean canEmit = false;
                        if ((node.nodeType == NodeType.Source || node.nodeType == NodeType.Conductor) && neighbor.nodeType != NodeType.Source) {
                            IEnergyEmitter emitter = (IEnergyEmitter)(subTile instanceof IEnergyEmitter ? subTile : node.tile.mainTile);
                            IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
                            IEnergyAcceptor acceptor = (IEnergyAcceptor)(neighborSubTe instanceof IEnergyAcceptor ? neighborSubTe : neighbor.tile.mainTile);
                            canEmit = emitter.emitsEnergyTo((IEnergyAcceptor)neighbor.tile.mainTile, dir) && acceptor.acceptsEnergyFrom((IEnergyEmitter)node.tile.mainTile, dir.func_176734_d());
                        }
                        boolean canAccept = false;
                        if (!(canEmit || node.nodeType != NodeType.Sink && node.nodeType != NodeType.Conductor || neighbor.nodeType == NodeType.Sink)) {
                            IEnergyAcceptor acceptor = (IEnergyAcceptor)(subTile instanceof IEnergyAcceptor ? subTile : node.tile.mainTile);
                            IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
                            IEnergyEmitter emitter = (IEnergyEmitter)(neighborSubTe instanceof IEnergyEmitter ? neighborSubTe : neighbor.tile.mainTile);
                            boolean bl = canAccept = acceptor.acceptsEnergyFrom((IEnergyEmitter)neighbor.tile.mainTile, dir) && emitter.emitsEnergyTo((IEnergyAcceptor)node.tile.mainTile, dir.func_176734_d());
                        }
                        if (!canEmit && !canAccept) continue;
                        neighbors.add(neighbor);
                    }
                }
            }
            if (neighbors.isEmpty()) {
                if (EnergyNetGlobal.debugGrid) {
                    IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s.", node);
                }
                grid = new Grid(this);
                grid.add(node, neighbors);
                continue;
            }
            switch (node.nodeType) {
                case Conductor: {
                    ic2.core.energy.Node neighbor2;
                    Object subTile;
                    grid = null;
                    subTile = neighbors.iterator();
                    while (subTile.hasNext()) {
                        ic2.core.energy.Node neighbor = (ic2.core.energy.Node)subTile.next();
                        if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) continue;
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "Using %s for %s with neighbors %s.", neighbor.getGrid(), node, neighbors);
                        }
                        grid = neighbor.getGrid();
                        break;
                    }
                    if (grid == null) {
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s with neighbors %s.", node, neighbors);
                        }
                        grid = new Grid(this);
                    }
                    HashMap neighborReplacements = new HashMap();
                    ListIterator<ic2.core.energy.Node> it = neighbors.listIterator();
                    while (it.hasNext()) {
                        ic2.core.energy.Node neighbor = (ic2.core.energy.Node)it.next();
                        if (neighbor.getGrid() == grid) continue;
                        if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
                            boolean found = false;
                            for (int i = 0; i < it.previousIndex(); ++i) {
                                neighbor2 = (ic2.core.energy.Node)neighbors.get(i);
                                if (neighbor2.tile != neighbor.tile || neighbor2.nodeType != neighbor.nodeType || neighbor2.getGrid() != grid) continue;
                                if (EnergyNetGlobal.debugGrid) {
                                    IC2.log.debug(LogCategory.EnergyNet, "Using neighbor node %s instead of %s.", neighbor2, neighbors);
                                }
                                found = true;
                                it.set(neighbor2);
                                break;
                            }
                            if (found) continue;
                            if (EnergyNetGlobal.debugGrid) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", neighbor);
                            }
                            neighbor = new ic2.core.energy.Node(this, neighbor.tile, neighbor.nodeType);
                            neighbor.tile.addExtraNode(neighbor);
                            grid.add(neighbor, Collections.emptyList());
                            it.set(neighbor);
                            assert (neighbor.getGrid() != null);
                            continue;
                        }
                        grid.merge(neighbor.getGrid(), neighborReplacements);
                    }
                    it = neighbors.listIterator();
                    while (it.hasNext()) {
                        ic2.core.energy.Node neighbor = (ic2.core.energy.Node)it.next();
                        ic2.core.energy.Node replacement = (ic2.core.energy.Node)neighborReplacements.get(neighbor);
                        if (replacement != null) {
                            neighbor = replacement;
                            it.set(replacement);
                        }
                        assert (neighbor.getGrid() == grid);
                    }
                    grid.add(node, neighbors);
                    assert (node.getGrid() != null);
                    break;
                }
                case Sink: 
                case Source: {
                    ic2.core.energy.Node neighbor2;
                    ArrayList neighborGroups = new ArrayList();
                    for (ic2.core.energy.Node neighbor : neighbors) {
                        boolean found = false;
                        if (node.nodeType == NodeType.Conductor) {
                            for (List nodeList : neighborGroups) {
                                neighbor2 = (ic2.core.energy.Node)nodeList.get(0);
                                if (neighbor2.nodeType != NodeType.Conductor || neighbor2.getGrid() != neighbor.getGrid()) continue;
                                nodeList.add(neighbor);
                                found = true;
                                break;
                            }
                        }
                        if (found) continue;
                        ArrayList<ic2.core.energy.Node> nodeList = new ArrayList<ic2.core.energy.Node>();
                        nodeList.add(neighbor);
                        neighborGroups.add(nodeList);
                    }
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "Neighbor groups detected for %s: %s.", node, neighborGroups);
                    }
                    assert (!neighborGroups.isEmpty());
                    for (int i = 0; i < neighborGroups.size(); ++i) {
                        ic2.core.energy.Node currentNode;
                        List nodeList = (List)neighborGroups.get(i);
                        ic2.core.energy.Node neighbor = (ic2.core.energy.Node)nodeList.get(0);
                        if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
                            assert (nodeList.size() == 1);
                            if (EnergyNetGlobal.debugGrid) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", neighbor);
                            }
                            neighbor = new ic2.core.energy.Node(this, neighbor.tile, neighbor.nodeType);
                            neighbor.tile.addExtraNode(neighbor);
                            new Grid(this).add(neighbor, Collections.emptyList());
                            nodeList.set(0, neighbor);
                            assert (neighbor.getGrid() != null);
                        }
                        if (i == 0) {
                            currentNode = node;
                        } else {
                            if (EnergyNetGlobal.debugGrid) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for %s.", node);
                            }
                            currentNode = new ic2.core.energy.Node(this, tile, node.nodeType);
                            currentNode.setExtraNode(true);
                            extraNodes.add(currentNode);
                        }
                        neighbor.getGrid().add(currentNode, nodeList);
                        assert (currentNode.getGrid() != null);
                    }
                    continue block4;
                }
            }
        }
        for (ic2.core.energy.Node node : extraNodes) {
            tile.addExtraNode(node);
        }
    }

    private void removeTileFromGrids(Tile tile) {
        for (ic2.core.energy.Node node : tile.nodes) {
            node.getGrid().remove(node);
        }
    }

    private void processChanges() {
        for (Tile tile : this.removedTiles) {
            Iterator<Change> it = this.changes.iterator();
            while (it.hasNext()) {
                Change change = it.next();
                if (change.node.tile != tile) continue;
                Tile replacement = this.registeredTiles.get(EnergyNet.instance.getPos(change.node.tile.mainTile));
                boolean validReplacement = false;
                if (replacement != null) {
                    for (ic2.core.energy.Node node : replacement.nodes) {
                        if (node.nodeType != change.node.nodeType || node.getGrid() != change.node.getGrid()) continue;
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "Redirecting change %s to replacement node %s.", change, node);
                        }
                        change.node = node;
                        validReplacement = true;
                        break;
                    }
                }
                if (validReplacement) continue;
                it.remove();
                ArrayList<Change> sameGridSourceChanges = new ArrayList<Change>();
                for (Change change2 : this.changes) {
                    if (change2.node.nodeType != NodeType.Source || change.node.getGrid() != change2.node.getGrid()) continue;
                    sameGridSourceChanges.add(change2);
                }
                if (EnergyNetGlobal.debugGrid) {
                    IC2.log.debug(LogCategory.EnergyNet, "Redistributing change %s to remaining source nodes %s.", change, sameGridSourceChanges);
                }
                for (Change change2 : sameGridSourceChanges) {
                    change2.setAmount(change2.getAmount() - Math.abs(change.getAmount()) / (double)sameGridSourceChanges.size());
                }
            }
        }
        this.removedTiles.clear();
        for (Change change : this.changes) {
            if (change.node.nodeType != NodeType.Sink) continue;
            assert (change.getAmount() > 0.0);
            IEnergySink sink = (IEnergySink)change.node.tile.mainTile;
            double returned = sink.injectEnergy(change.dir, change.getAmount(), change.getVoltage());
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "Applied change %s, %f EU returned.", change, returned);
            }
            if (!(returned > 0.0)) continue;
            ArrayList<Change> sameGridSourceChanges = new ArrayList<Change>();
            for (Change change2 : this.changes) {
                if (change2.node.nodeType != NodeType.Source || change.node.getGrid() != change2.node.getGrid()) continue;
                sameGridSourceChanges.add(change2);
            }
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "Redistributing returned amount to source nodes %s.", sameGridSourceChanges);
            }
            for (Change change3 : sameGridSourceChanges) {
                change3.setAmount(change3.getAmount() - returned / (double)sameGridSourceChanges.size());
            }
        }
        for (Change change : this.changes) {
            if (change.node.nodeType != NodeType.Source) continue;
            assert (change.getAmount() <= 0.0);
            if (change.getAmount() >= 0.0) continue;
            IEnergySource source = (IEnergySource)change.node.tile.mainTile;
            source.drawEnergy(change.getAmount());
            if (!EnergyNetGlobal.debugGrid) continue;
            IC2.log.debug(LogCategory.EnergyNet, "Applied change %s.", change);
        }
        this.changes.clear();
    }

    private void logDebug(String msg) {
        if (!this.shouldLog(msg)) {
            return;
        }
        IC2.log.debug(LogCategory.EnergyNet, msg);
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable(), "stack trace");
            if (TickHandler.getLastDebugTrace() != null) {
                IC2.log.debug(LogCategory.EnergyNet, TickHandler.getLastDebugTrace(), "parent stack trace");
            }
        }
    }

    private void logWarn(String msg) {
        if (!this.shouldLog(msg)) {
            return;
        }
        IC2.log.warn(LogCategory.EnergyNet, msg);
        if (EnergyNetGlobal.debugTileManagement) {
            IC2.log.debug(LogCategory.EnergyNet, new Throwable(), "stack trace");
            if (TickHandler.getLastDebugTrace() != null) {
                IC2.log.debug(LogCategory.EnergyNet, TickHandler.getLastDebugTrace(), "parent stack trace");
            }
        }
    }

    private boolean shouldLog(String msg) {
        if (EnergyNetGlobal.logAll) {
            return true;
        }
        this.cleanRecentLogs();
        msg = msg.replaceAll("@[0-9a-f]+", "@x");
        long time = System.nanoTime();
        Long lastLog = this.recentLogs.put(msg, time);
        return lastLog == null || lastLog < time - 300000000000L;
    }

    private void cleanRecentLogs() {
        if (this.recentLogs.size() < 100) {
            return;
        }
        long minTime = System.nanoTime() - 300000000000L;
        Iterator<Long> it = this.recentLogs.values().iterator();
        while (it.hasNext()) {
            long recTime = it.next();
            if (recTime >= minTime) continue;
            it.remove();
        }
    }
}

