/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.world.level.BlockGetter
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
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;

public class Grid {
    private final int uid;
    private final EnergyNetLocal enet;
    private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    private boolean dirty;
    private Object data;

    Grid(EnergyNetLocal energyNetLocal) {
        this.uid = energyNetLocal.allocateGridId();
        this.enet = energyNetLocal;
        energyNetLocal.addGrid(this);
    }

    public EnergyNetLocal getEnergyNet() {
        return this.enet;
    }

    public Node getNode(int n) {
        return this.nodes.get(n);
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

    public void setData(Object object) {
        this.data = object;
    }

    public String toString() {
        return "Grid " + this.uid;
    }

    void add(Node node, Collection<Node> collection) {
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Add %s to %s neighbors: %s.", this.uid, node, this, collection);
        }
        this.invalidate();
        assert (!this.nodes.isEmpty() || collection.isEmpty());
        assert (this.nodes.isEmpty() || !collection.isEmpty() || node.isExtraNode());
        assert (node.links.isEmpty());
        this.add(node);
        for (Node node2 : collection) {
            assert (node2 != node);
            assert (this.nodes.containsKey(node2.uid));
            double d = (node.getInnerLoss() + node2.getInnerLoss()) / 2.0;
            NodeLink nodeLink = new NodeLink(node, node2, d);
            node.links.add(nodeLink);
            node2.links.add(nodeLink);
        }
    }

    void remove(Node node) {
        Object object;
        int n;
        Object object2;
        Set[] setArray;
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Remove Node %s from %s with %d nodes.", this.uid, node, this, this.nodes.size());
        }
        this.invalidate();
        Iterator<NodeLink> iterator = node.links.iterator();
        while (iterator.hasNext()) {
            setArray = iterator.next();
            object2 = setArray.getNeighbor(node);
            n = 0;
            object = ((Node)object2).links.iterator();
            while (object.hasNext()) {
                if (object.next() != setArray) continue;
                object.remove();
                n = 1;
                break;
            }
            assert (n != 0);
            this.enet.addTileToNotify(((Node)object2).getTile().getMainTile());
            if (!((Node)object2).links.isEmpty() || !((Node)object2).tile.removeExtraNode((Node)object2)) continue;
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "%d Removing isolated extra node %s.", this.uid, object2);
            }
            assert (((Node)object2).getType() != NodeType.Conductor);
            iterator.remove();
            this.nodes.remove(((Node)object2).uid);
            ((Node)object2).clearGrid();
        }
        this.nodes.remove(node.uid);
        node.clearGrid();
        int n2 = node.links.size();
        if (n2 == 0) {
            assert (this.nodes.isEmpty());
            this.enet.removeGrid(this);
        } else if (n2 > 1 && node.nodeType == NodeType.Conductor) {
            Object object3;
            Object object4;
            int n3;
            setArray = new Set[n2];
            object2 = new int[n2];
            n = 0;
            object = new ArrayDeque();
            block2: for (n3 = 0; n3 < n2; ++n3) {
                object4 = node.links.get(n3).getNeighbor(node);
                if (((Node)object4).getType() != NodeType.Conductor) {
                    if (((Node)object4).links.isEmpty()) {
                        setArray[n3] = Collections.singleton(object4);
                        ++n;
                        continue;
                    }
                    object2[n3] = -1;
                    continue;
                }
                for (int i = 0; i < n3; ++i) {
                    object3 = setArray[i];
                    if (object3 == null || !object3.contains(object4)) continue;
                    object2[n3] = i;
                    continue block2;
                }
                Set set = Collections.newSetFromMap(new IdentityHashMap());
                object.add(object4);
                set.add(object4);
                while ((object3 = (Node)object.poll()) != null) {
                    for (NodeLink nodeLink : ((Node)object3).links) {
                        Node node2 = nodeLink.getNeighbor((Node)object3);
                        if (!set.add(node2) || node2.getType() != NodeType.Conductor) continue;
                        object.add(node2);
                    }
                }
                assert (!set.contains(node));
                setArray[n3] = set;
                ++n;
            }
            assert (n > 0);
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "%d Neighbor connectivity (%d links, %d new grids):", this.uid, n2, n);
                for (n3 = 0; n3 < n2; ++n3) {
                    object4 = setArray[n3];
                    if (object4 != null) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d %d: %s: %s (%d).", this.uid, n3, node.links.get(n3).getNeighbor(node), object4, object4.size());
                        continue;
                    }
                    IC2.log.debug(LogCategory.EnergyNet, "%d %d: %s contained in %d.", this.uid, n3, node.links.get(n3).getNeighbor(node), (int)object2[n3]);
                }
            }
            if (n <= 1) {
                return;
            }
            for (n3 = 1; n3 < n2; ++n3) {
                object4 = setArray[n3];
                if (object4 == null) continue;
                Grid grid = new Grid(this.enet);
                if (EnergyNetSettings.logGridUpdatesVerbose) {
                    IC2.log.debug(LogCategory.EnergyNet, "%d Moving %d nodes from net %d to new grid %d.", this.uid, object4.size(), n3, grid.uid);
                }
                object3 = object4.iterator();
                while (object3.hasNext()) {
                    Object object5;
                    Node node3 = (Node)object3.next();
                    boolean bl = false;
                    if (!node3.links.isEmpty() && node3.nodeType != NodeType.Conductor) {
                        for (int i = 0; i < n3; ++i) {
                            object5 = setArray[i];
                            if (object5 == null || !object5.contains(node3)) continue;
                            bl = true;
                            break;
                        }
                    }
                    if (bl) {
                        Node node4 = new Node(this.enet.allocateNodeId(), node3.tile, node3.nodeType);
                        if (EnergyNetSettings.logGridUpdatesVerbose) {
                            IC2.log.debug(LogCategory.EnergyNet, "%s Create extra Node %d for %s in grid %d.", this.uid, node4.uid, node3, grid.uid);
                        }
                        node3.tile.addExtraNode(node4);
                        object5 = node3.links.iterator();
                        while (object5.hasNext()) {
                            NodeLink nodeLink = (NodeLink)object5.next();
                            Node node5 = nodeLink.getNeighbor(node3);
                            if (!object4.contains(node5)) continue;
                            assert (node5.nodeType == NodeType.Conductor);
                            nodeLink.replaceNode(node3, node4);
                            node4.links.add(nodeLink);
                            object5.remove();
                        }
                        assert (!node4.links.isEmpty());
                        grid.add(node4);
                        assert (node4.getGrid() != null);
                        continue;
                    }
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Move Node %s to grid %d.", this.uid, node3, grid.uid);
                    }
                    assert (this.nodes.containsKey(node3.uid));
                    this.nodes.remove(node3.uid);
                    node3.clearGrid();
                    grid.add(node3);
                    assert (node3.getGrid() != null);
                }
            }
        }
    }

    void merge(Grid grid, Map<Node, Node> map) {
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Merge %s -> %s.", this.uid, grid, this);
        }
        assert (this.enet.hasGrid(grid));
        this.invalidate();
        for (Node node : grid.nodes.values()) {
            boolean bl = false;
            if (node.nodeType != NodeType.Conductor) {
                for (Node node2 : node.tile.nodes) {
                    if (node2.nodeType != node.nodeType || node2.getGrid() != this) continue;
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Merge Node %s -> %s.", this.uid, node, node2);
                    }
                    bl = true;
                    for (NodeLink nodeLink : node.links) {
                        nodeLink.replaceNode(node, node2);
                        node2.links.add(nodeLink);
                    }
                    node2.tile.removeExtraNode(node);
                    map.put(node, node2);
                    break;
                }
            }
            if (bl) continue;
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
        Node node2 = this.nodes.put(node.uid, node);
        if (node2 != null) {
            throw new IllegalStateException("duplicate node uid, new " + node + ", old " + node2);
        }
    }

    private void invalidate() {
        this.dirty = true;
    }

    GridInfo getInfo() {
        int n = 0;
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MAX_VALUE;
        int n5 = Integer.MIN_VALUE;
        int n6 = Integer.MIN_VALUE;
        int n7 = Integer.MIN_VALUE;
        for (Node node : this.nodes.values()) {
            if (node.links.size() > 2) {
                ++n;
            }
            for (IEnergyTile iEnergyTile : node.tile.subTiles) {
                BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
                if (blockPos.m_123341_() < n2) {
                    n2 = blockPos.m_123341_();
                }
                if (blockPos.m_123342_() < n3) {
                    n3 = blockPos.m_123342_();
                }
                if (blockPos.m_123343_() < n4) {
                    n4 = blockPos.m_123343_();
                }
                if (blockPos.m_123341_() > n5) {
                    n5 = blockPos.m_123341_();
                }
                if (blockPos.m_123342_() > n6) {
                    n6 = blockPos.m_123342_();
                }
                if (blockPos.m_123343_() <= n7) continue;
                n7 = blockPos.m_123343_();
            }
        }
        return new GridInfo(this.uid, this.nodes.size(), n, n2, n3, n4, n5, n6, n7);
    }

    void dumpInfo(String string, PrintStream printStream, PrintStream printStream2) {
        printStream2.printf("%sGrid %d info:%n", string, this.uid);
        printStream2.printf("%s %d nodes%n", string, this.nodes.size());
    }

    void dumpNodeInfo(Node node, String string, PrintStream printStream, PrintStream printStream2) {
        IEnergyTile iEnergyTile = node.getTile().getMainTile();
        printStream2.printf("%sNode %s info:%n", string, node);
        printStream2.printf("%s pos: %s%n", string, Util.formatPosition((BlockGetter)EnergyNet.instance.getWorld(iEnergyTile), EnergyNet.instance.getPos(iEnergyTile)));
        printStream2.printf("%s type: %s%n", new Object[]{string, node.nodeType});
        switch (node.nodeType) {
            case Conductor: {
                break;
            }
            case Sink: {
                Object object = (IEnergySink)iEnergyTile;
                printStream2.printf("%s demanded: %.2f%n", string, object.getDemandedEnergy());
                printStream2.printf("%s tier: %d%n", string, object.getSinkTier());
                break;
            }
            case Source: {
                Object object = (IEnergySource)iEnergyTile;
                printStream2.printf("%s offered: %.2f%n", string, object.getOfferedEnergy());
                printStream2.printf("%s tier: %d%n", string, object.getSourceTier());
                break;
            }
        }
        printStream2.printf("%s %d neighbor links:%n", string, node.links.size());
        for (NodeLink nodeLink : node.links) {
            printStream2.printf("%s  %s %.4f %s%n", string, nodeLink.getNeighbor(node), nodeLink.loss, nodeLink.skippedNodes);
        }
        EnergyNetGlobal.getCalculator().dumpNodeInfo(node, string + " ", printStream, printStream2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void dumpGraph() {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("graph_" + this.uid + "_raw.txt");
            fileWriter.write("graph nodes {\n  overlap=false;\n");
            Collection<Node> collection = this.nodes.values();
            HashSet<Node> hashSet = new HashSet<Node>();
            for (Node node : collection) {
                fileWriter.write("  \"" + node + "\";\n");
                for (NodeLink nodeLink : node.links) {
                    Node node2 = nodeLink.getNeighbor(node);
                    if (hashSet.contains(node2)) continue;
                    fileWriter.write("  \"" + node + "\" -- \"" + node2 + "\" [label=\"" + nodeLink.loss + "\"];\n");
                }
                hashSet.add(node);
            }
            fileWriter.write("}\n");
        }
        catch (IOException iOException) {
            IC2.log.debug(LogCategory.EnergyNet, iOException, "Graph saving failed.");
        }
        finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            }
            catch (IOException iOException) {}
        }
    }
}

