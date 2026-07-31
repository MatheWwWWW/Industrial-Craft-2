/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 */
package ic2.core.energy.grid;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.IC2;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.GridInfo;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeLink;
import ic2.core.energy.grid.NodeType;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class Grid {
    private final int uid;
    private final EnergyNetLocal enet;
    private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    private boolean dirty;
    private Object data;

    Grid(EnergyNetLocal enet) {
        this.uid = enet.allocateGridId();
        this.enet = enet;
        enet.addGrid(this);
    }

    public EnergyNetLocal getEnergyNet() {
        return this.enet;
    }

    public Node getNode(int id) {
        return this.nodes.get(id);
    }

    public Collection<Node> getNodes() {
        return this.nodes.values();
    }

    public boolean clearDirty() {
        if (!this.dirty) {
            return false;
        }
        this.dirty = false;
        return true;
    }

    public <T> T getData() {
        return (T)this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toString() {
        return "Grid " + this.uid;
    }

    void add(Node node, Collection<Node> neighbors) {
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Add %s to %s neighbors: %s.", this.uid, node, this, neighbors);
        }
        this.invalidate();
        assert (!this.nodes.isEmpty() || neighbors.isEmpty());
        assert (this.nodes.isEmpty() || !neighbors.isEmpty() || node.isExtraNode());
        assert (node.links.isEmpty());
        this.add(node);
        for (Node neighbor : neighbors) {
            assert (neighbor != node);
            assert (this.nodes.containsKey(neighbor.uid));
            double loss = (node.getInnerLoss() + neighbor.getInnerLoss()) / 2.0;
            NodeLink link = new NodeLink(node, neighbor, loss);
            node.links.add(link);
            neighbor.links.add(link);
        }
    }

    void remove(Node node) {
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Remove Node %s from %s with %d nodes.", this.uid, node, this, this.nodes.size());
        }
        this.invalidate();
        Iterator<NodeLink> it = node.links.iterator();
        while (it.hasNext()) {
            NodeLink link = it.next();
            Node neighbor = link.getNeighbor(node);
            boolean found = false;
            Iterator<NodeLink> it2 = neighbor.links.iterator();
            while (it2.hasNext()) {
                if (it2.next() != link) continue;
                it2.remove();
                found = true;
                break;
            }
            assert (found);
            if (!neighbor.links.isEmpty() || !neighbor.tile.removeExtraNode(neighbor)) continue;
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "%d Removing isolated extra node %s.", this.uid, neighbor);
            }
            assert (neighbor.getType() != NodeType.Conductor);
            it.remove();
            this.nodes.remove(neighbor.uid);
            neighbor.clearGrid();
        }
        this.nodes.remove(node.uid);
        node.clearGrid();
        int linkCount = node.links.size();
        if (linkCount == 0) {
            assert (this.nodes.isEmpty());
            this.enet.removeGrid(this);
        } else if (linkCount > 1 && node.nodeType == NodeType.Conductor) {
            int i;
            Set[] nodeTable = new Set[linkCount];
            int[] mapping = new int[linkCount];
            int gridCount = 0;
            ArrayDeque<Node> nodesToCheck = new ArrayDeque<Node>();
            block2: for (i = 0; i < linkCount; ++i) {
                Node cNode;
                Node neighbor = node.links.get(i).getNeighbor(node);
                if (neighbor.getType() != NodeType.Conductor) {
                    if (neighbor.links.isEmpty()) {
                        nodeTable[i] = Collections.singleton(neighbor);
                        ++gridCount;
                        continue;
                    }
                    mapping[i] = -1;
                    continue;
                }
                for (int j = 0; j < i; ++j) {
                    Set nodes = nodeTable[j];
                    if (nodes == null || !nodes.contains(neighbor)) continue;
                    mapping[i] = j;
                    continue block2;
                }
                Set connectedNodes = Collections.newSetFromMap(new IdentityHashMap());
                nodesToCheck.add(neighbor);
                connectedNodes.add(neighbor);
                while ((cNode = (Node)nodesToCheck.poll()) != null) {
                    for (NodeLink link : cNode.links) {
                        Node nNode = link.getNeighbor(cNode);
                        if (!connectedNodes.add(nNode) || nNode.getType() != NodeType.Conductor) continue;
                        nodesToCheck.add(nNode);
                    }
                }
                assert (!connectedNodes.contains(node));
                nodeTable[i] = connectedNodes;
                ++gridCount;
            }
            assert (gridCount > 0);
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "%d Neighbor connectivity (%d links, %d new grids):", this.uid, linkCount, gridCount);
                for (i = 0; i < linkCount; ++i) {
                    Set nodes = nodeTable[i];
                    if (nodes != null) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d %d: %s: %s (%d).", this.uid, i, node.links.get(i).getNeighbor(node), nodes, nodes.size());
                        continue;
                    }
                    IC2.log.debug(LogCategory.EnergyNet, "%d %d: %s contained in %d.", this.uid, i, node.links.get(i).getNeighbor(node), mapping[i]);
                }
            }
            if (gridCount <= 1) {
                return;
            }
            for (i = 1; i < linkCount; ++i) {
                Set connectedNodes = nodeTable[i];
                if (connectedNodes == null) continue;
                Grid grid = new Grid(this.enet);
                if (EnergyNetSettings.logGridUpdatesVerbose) {
                    IC2.log.debug(LogCategory.EnergyNet, "%d Moving %d nodes from net %d to new grid %d.", this.uid, connectedNodes.size(), i, grid.uid);
                }
                for (Node cNode : connectedNodes) {
                    boolean needsExtraNode = false;
                    if (!cNode.links.isEmpty() && cNode.nodeType != NodeType.Conductor) {
                        for (int j = 0; j < i; ++j) {
                            Set nodes = nodeTable[j];
                            if (nodes == null || !nodes.contains(cNode)) continue;
                            needsExtraNode = true;
                            break;
                        }
                    }
                    if (needsExtraNode) {
                        Node extraNode = new Node(this.enet.allocateNodeId(), cNode.tile, cNode.nodeType);
                        if (EnergyNetSettings.logGridUpdatesVerbose) {
                            IC2.log.debug(LogCategory.EnergyNet, "%s Create extra Node %d for %s in grid %d.", this.uid, extraNode.uid, cNode, grid.uid);
                        }
                        cNode.tile.addExtraNode(extraNode);
                        Iterator<NodeLink> it2 = cNode.links.iterator();
                        while (it2.hasNext()) {
                            NodeLink link = it2.next();
                            Node neighbor = link.getNeighbor(cNode);
                            if (!connectedNodes.contains(neighbor)) continue;
                            assert (neighbor.nodeType == NodeType.Conductor);
                            link.replaceNode(cNode, extraNode);
                            extraNode.links.add(link);
                            it2.remove();
                        }
                        assert (!extraNode.links.isEmpty());
                        grid.add(extraNode);
                        assert (extraNode.getGrid() != null);
                        continue;
                    }
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Move Node %s to grid %d.", this.uid, cNode, grid.uid);
                    }
                    assert (this.nodes.containsKey(cNode.uid));
                    this.nodes.remove(cNode.uid);
                    cNode.clearGrid();
                    grid.add(cNode);
                    assert (cNode.getGrid() != null);
                }
            }
        }
    }

    void merge(Grid grid, Map<Node, Node> nodeReplacements) {
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Merge %s -> %s.", this.uid, grid, this);
        }
        assert (this.enet.hasGrid(grid));
        this.invalidate();
        for (Node node : grid.nodes.values()) {
            boolean found = false;
            if (node.nodeType != NodeType.Conductor) {
                for (Node node2 : node.tile.nodes) {
                    if (node2.nodeType != node.nodeType || node2.getGrid() != this) continue;
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Merge Node %s -> %s.", this.uid, node, node2);
                    }
                    found = true;
                    for (NodeLink link : node.links) {
                        link.replaceNode(node, node2);
                        node2.links.add(link);
                    }
                    node2.tile.removeExtraNode(node);
                    nodeReplacements.put(node, node2);
                    break;
                }
            }
            if (found) continue;
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "%d Add Node %s.", this.uid, node);
            }
            node.clearGrid();
            this.add(node);
            assert (node.getGrid() != null);
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Remove %s.", grid);
        }
        this.enet.removeGrid(grid);
    }

    private void add(Node node) {
        node.setGrid(this);
        Node prev = this.nodes.put(node.uid, node);
        if (prev != null) {
            throw new IllegalStateException("duplicate node uid, new " + node + ", old " + prev);
        }
    }

    private void invalidate() {
        this.dirty = true;
    }

    GridInfo getInfo() {
        int complexNodes = 0;
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        for (Node node : this.nodes.values()) {
            if (node.links.size() > 2) {
                ++complexNodes;
            }
            for (IEnergyTile tile : node.tile.subTiles) {
                BlockPos pos = EnergyNet.instance.getPos(tile);
                if (pos.func_177958_n() < minX) {
                    minX = pos.func_177958_n();
                }
                if (pos.func_177956_o() < minY) {
                    minY = pos.func_177956_o();
                }
                if (pos.func_177952_p() < minZ) {
                    minZ = pos.func_177952_p();
                }
                if (pos.func_177958_n() > maxX) {
                    maxX = pos.func_177958_n();
                }
                if (pos.func_177956_o() > maxY) {
                    maxY = pos.func_177956_o();
                }
                if (pos.func_177952_p() <= maxZ) continue;
                maxZ = pos.func_177952_p();
            }
        }
        return new GridInfo(this.uid, this.nodes.size(), complexNodes, minX, minY, minZ, maxX, maxY, maxZ);
    }

    void dumpInfo(String prefix, PrintStream console, PrintStream chat) {
        chat.printf("%sGrid %d info:%n", prefix, this.uid);
        chat.printf("%s %d nodes%n", prefix, this.nodes.size());
    }

    void dumpNodeInfo(Node node, String prefix, PrintStream console, PrintStream chat) {
        IEnergyTile ioTile = node.getTile().getMainTile();
        chat.printf("%sNode %s info:%n", prefix, node);
        chat.printf("%s pos: %s%n", prefix, Util.formatPosition((IBlockAccess)EnergyNet.instance.getWorld(ioTile), EnergyNet.instance.getPos(ioTile)));
        chat.printf("%s type: %s%n", new Object[]{prefix, node.nodeType});
        switch (node.nodeType) {
            case Conductor: {
                break;
            }
            case Sink: {
                IEnergySink sink = (IEnergySink)ioTile;
                chat.printf("%s demanded: %.2f%n", prefix, sink.getDemandedEnergy());
                chat.printf("%s tier: %d%n", prefix, sink.getSinkTier());
                break;
            }
            case Source: {
                IEnergySource source = (IEnergySource)ioTile;
                chat.printf("%s offered: %.2f%n", prefix, source.getOfferedEnergy());
                chat.printf("%s tier: %d%n", prefix, source.getSourceTier());
                break;
            }
        }
        chat.printf("%s %d neighbor links:%n", prefix, node.links.size());
        for (NodeLink link : node.links) {
            chat.printf("%s  %s %.4f %s%n", prefix, link.getNeighbor(node), link.loss, link.skippedNodes);
        }
        EnergyNetGlobal.getCalculator().dumpNodeInfo(node, prefix + " ", console, chat);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void dumpGraph() {
        OutputStreamWriter out = null;
        try {
            out = new FileWriter("graph_" + this.uid + "_raw.txt");
            out.write("graph nodes {\n  overlap=false;\n");
            Collection<Node> nodesToDump = this.nodes.values();
            HashSet<Node> dumpedConnections = new HashSet<Node>();
            for (Node node : nodesToDump) {
                out.write("  \"" + node + "\";\n");
                for (NodeLink link : node.links) {
                    Node neighbor = link.getNeighbor(node);
                    if (dumpedConnections.contains(neighbor)) continue;
                    out.write("  \"" + node + "\" -- \"" + neighbor + "\" [label=\"" + link.loss + "\"];\n");
                }
                dumpedConnections.add(node);
            }
            out.write("}\n");
        }
        catch (IOException e) {
            IC2.log.debug(LogCategory.EnergyNet, e, "Graph saving failed.");
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException iOException) {}
        }
    }
}

