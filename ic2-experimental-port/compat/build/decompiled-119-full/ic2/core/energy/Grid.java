/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  org.apache.logging.log4j.Level
 *  org.ejml.data.Complex_F64
 *  org.ejml.data.DMatrix
 *  org.ejml.data.DMatrixRMaj
 *  org.ejml.data.Matrix
 *  org.ejml.dense.row.factory.DecompositionFactory_DDRM
 *  org.ejml.dense.row.factory.LinearSolverFactory_DDRM
 *  org.ejml.interfaces.decomposition.EigenDecomposition_F64
 *  org.ejml.ops.MatrixIO
 */
package ic2.core.energy;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.IC2;
import ic2.core.energy.EnergyNetGlobal;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.energy.GridCalculation;
import ic2.core.energy.Node;
import ic2.core.energy.NodeLink;
import ic2.core.energy.StructureCache;
import ic2.core.energy.grid.GridInfo;
import ic2.core.energy.grid.NodeType;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.apache.logging.log4j.Level;
import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrix;
import org.ejml.data.DMatrixRMaj;
import org.ejml.data.Matrix;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.dense.row.factory.LinearSolverFactory_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.ops.MatrixIO;

class Grid {
    private final int uid;
    private final EnergyNetLocal energyNet;
    private final Map<Integer, Node> nodes = new HashMap<Integer, Node>();
    private boolean hasNonZeroVoltages = false;
    private boolean lastVoltagesNeedUpdate = false;
    private final Set<Integer> activeSources = new HashSet<Integer>();
    private final Set<Integer> activeSinks = new HashSet<Integer>();
    private final StructureCache cache = new StructureCache();
    private Future<Iterable<Node>> calculation;
    private StructureCache.Data lastData = null;
    private boolean failed;

    Grid(EnergyNetLocal energyNetLocal) {
        this.uid = EnergyNetLocal.getNextGridUid();
        this.energyNet = energyNetLocal;
        energyNetLocal.grids.add(this);
    }

    public String toString() {
        return "Grid " + this.uid;
    }

    void add(Node node, Collection<Node> collection) {
        if (EnergyNetGlobal.debugGrid) {
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
        Object object2;
        if (EnergyNetGlobal.debugGrid) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Remove Node %s from %s with %d nodes.", this.uid, node, this, this.nodes.size());
        }
        this.invalidate();
        Object object3 = node.links.iterator();
        while (object3.hasNext()) {
            NodeLink nodeLink = object3.next();
            object2 = nodeLink.getNeighbor(node);
            boolean bl = false;
            object = ((Node)object2).links.iterator();
            while (object.hasNext()) {
                if (object.next() != nodeLink) continue;
                object.remove();
                bl = true;
                break;
            }
            assert (bl);
            if (!((Node)object2).links.isEmpty() || !((Node)object2).tile.removeExtraNode((Node)object2)) continue;
            object3.remove();
            this.nodes.remove(((Node)object2).uid);
            ((Node)object2).clearGrid();
        }
        this.nodes.remove(node.uid);
        node.clearGrid();
        if (node.links.isEmpty()) {
            this.energyNet.grids.remove(this);
        } else if (node.links.size() > 1 && node.nodeType == NodeType.Conductor) {
            int n;
            object3 = new ArrayList();
            for (n = 0; n < node.links.size(); ++n) {
                Node node2;
                object2 = node.links.get(n).getNeighbor(node);
                HashSet<Node> hashSet = new HashSet<Node>();
                object = new LinkedList<Node>(Arrays.asList(object2));
                while ((node2 = (Node)object.poll()) != null) {
                    if (!hashSet.add(node2) || node2.nodeType != NodeType.Conductor) continue;
                    for (NodeLink object4 : node2.links) {
                        Node node3 = object4.getNeighbor(node2);
                        if (hashSet.contains(node3)) continue;
                        object.add(node3);
                    }
                }
                object3.add(hashSet);
            }
            assert (object3.size() == node.links.size());
            for (n = 1; n < node.links.size(); ++n) {
                Object object5;
                if (EnergyNetGlobal.debugGrid) {
                    IC2.log.debug(LogCategory.EnergyNet, "%d Checking net %d with %d nodes.", this.uid, n, ((Set)object3.get(n)).size());
                }
                object2 = (Set)object3.get(n);
                Node node4 = node.links.get(n).getNeighbor(node);
                assert (object2.contains(node4));
                boolean bl = true;
                for (int i = 0; i < n; ++i) {
                    object5 = (Set)object3.get(i);
                    if (!object5.contains(node4)) continue;
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Same as %d.", this.uid, i);
                    }
                    bl = false;
                    break;
                }
                if (!bl) continue;
                if (EnergyNetGlobal.debugGrid) {
                    IC2.log.debug(LogCategory.EnergyNet, "%d Moving nodes %s.", this.uid, object2);
                }
                Grid grid = new Grid(this.energyNet);
                object5 = object2.iterator();
                while (object5.hasNext()) {
                    Object object4;
                    Node node5 = (Node)object5.next();
                    boolean bl2 = false;
                    if (!node5.links.isEmpty() && node5.nodeType != NodeType.Conductor) {
                        for (int i = 0; i < n; ++i) {
                            object4 = (Set)object3.get(i);
                            if (!object4.contains(node5)) continue;
                            bl2 = true;
                            break;
                        }
                    }
                    if (bl2) {
                        if (EnergyNetGlobal.debugGrid) {
                            IC2.log.debug(LogCategory.EnergyNet, "%s Create extra Node for %s.", this.uid, node5);
                        }
                        Node node6 = new Node(this.energyNet, node5.tile, node5.nodeType);
                        node5.tile.addExtraNode(node6);
                        object4 = node5.links.iterator();
                        while (object4.hasNext()) {
                            NodeLink nodeLink = (NodeLink)object4.next();
                            if (!object2.contains(nodeLink.getNeighbor(node5))) continue;
                            nodeLink.replaceNode(node5, node6);
                            node6.links.add(nodeLink);
                            object4.remove();
                        }
                        assert (!node6.links.isEmpty());
                        grid.add(node6);
                        assert (node6.getGrid() != null);
                        continue;
                    }
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Move Node %s.", this.uid, node5);
                    }
                    assert (this.nodes.containsKey(node5.uid));
                    this.nodes.remove(node5.uid);
                    node5.clearGrid();
                    grid.add(node5);
                    assert (node5.getGrid() != null);
                }
            }
        }
    }

    void merge(Grid grid, Map<Node, Node> map) {
        if (EnergyNetGlobal.debugGrid) {
            IC2.log.debug(LogCategory.EnergyNet, "%d Merge %s -> %s.", this.uid, grid, this);
        }
        assert (this.energyNet.grids.contains(grid));
        this.invalidate();
        for (Node node : grid.nodes.values()) {
            boolean bl = false;
            if (node.nodeType != NodeType.Conductor) {
                for (Node node2 : this.nodes.values()) {
                    if (node2.tile != node.tile || node2.nodeType != node.nodeType) continue;
                    if (EnergyNetGlobal.debugGrid) {
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
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.debug(LogCategory.EnergyNet, "%d Add Node %s.", this.uid, node);
            }
            node.clearGrid();
            this.add(node);
            assert (node.getGrid() != null);
        }
        if (EnergyNetGlobal.debugGrid) {
            IC2.log.debug(LogCategory.EnergyNet, "Remove %s.", grid);
        }
        this.energyNet.grids.remove(grid);
    }

    void prepareCalculation() {
        assert (this.calculation == null);
        if (!this.activeSources.isEmpty()) {
            this.activeSources.clear();
        }
        if (!this.activeSinks.isEmpty()) {
            this.activeSinks.clear();
        }
        ArrayList<Node> arrayList = new ArrayList<Node>();
        int n = 0;
        for (Node node : this.nodes.values()) {
            assert (node.getGrid() == this);
            switch (node.nodeType) {
                case Source: {
                    IEnergyTile iEnergyTile = (IEnergySource)node.tile.mainTile;
                    node.setTier(iEnergyTile.getSourceTier());
                    node.setAmount(iEnergyTile.getOfferedEnergy());
                    if (node.getAmount() > 0.0) {
                        this.activeSources.add(node.uid);
                        n = Math.max(node.getTier(), n);
                        break;
                    }
                    node.setAmount(0.0);
                    break;
                }
                case Sink: {
                    IEnergyTile iEnergyTile = (IEnergySink)node.tile.mainTile;
                    node.setTier(iEnergyTile.getSinkTier());
                    node.setAmount(iEnergyTile.getDemandedEnergy());
                    if (node.getAmount() > 0.0) {
                        this.activeSinks.add(node.uid);
                        if (node.getTier() != Integer.MAX_VALUE) break;
                        arrayList.add(node);
                        break;
                    }
                    node.setAmount(0.0);
                    break;
                }
                case Conductor: {
                    node.setAmount(0.0);
                }
            }
            assert (node.getAmount() >= 0.0);
        }
        for (Node node : arrayList) {
            node.setTier(n);
        }
    }

    Runnable startCalculation() {
        Object object;
        assert (this.calculation == null);
        if (this.failed) {
            IC2.log.warn(LogCategory.EnergyNet, "Calculation failed previously, skipping calculation.");
            return null;
        }
        boolean bl = this.hasNonZeroVoltages;
        if (!this.activeSinks.isEmpty() && !this.activeSources.isEmpty()) {
            bl = true;
            object = this.activeSources.iterator();
            while (object.hasNext()) {
                int n = (Integer)object.next();
                Node node = this.nodes.get(n);
                int n2 = 1;
                for (Node node2 : node.tile.nodes) {
                    if (node2.uid == n || node2.nodeType != NodeType.Source || node2.getGrid().activeSinks.isEmpty()) continue;
                    assert (node2.getGrid().activeSources.contains(node2.uid));
                    assert (node2.getGrid() != this);
                    ++n2;
                }
                node.setAmount(node.getAmount() / (double)n2);
                IEnergySource iEnergySource = (IEnergySource)node.tile.mainTile;
                iEnergySource.drawEnergy(node.getAmount());
                if (!EnergyNetGlobal.debugGrid) continue;
                IC2.log.debug(LogCategory.EnergyNet, "%d %s %f EU", this.uid, node, -node.getAmount());
            }
        }
        if (bl) {
            this.calculation = object = IC2.threadPool.makeTask(new GridCalculation(this));
            return object;
        }
        return null;
    }

    void finishCalculation() {
        if (this.calculation == null) {
            return;
        }
        try {
            Iterable<Node> iterable = this.calculation.get();
            for (Node node : iterable) {
                Direction direction;
                if (!node.links.isEmpty()) {
                    direction = node.links.get(0).getDirFrom(node);
                } else {
                    direction = null;
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.warn(LogCategory.EnergyNet, "Can't determine direction for %s.", node);
                        this.dumpNodeInfo(IC2.log.getPrintStream(LogCategory.EnergyNet, Level.DEBUG), false, node);
                        this.dumpGraph(false);
                    }
                }
                this.energyNet.addChange(node, direction, node.getAmount(), node.getVoltage());
            }
        }
        catch (InterruptedException interruptedException) {
            IC2.log.debug(LogCategory.EnergyNet, interruptedException, "Calculation interrupted.");
        }
        catch (ExecutionException executionException) {
            IC2.log.warn(LogCategory.EnergyNet, executionException, "Calculation failed.");
            PrintStream printStream = IC2.log.getPrintStream(LogCategory.EnergyNet, Level.WARN);
            this.dumpStats(printStream, false);
            this.dumpMatrix(printStream, false, true, true);
            this.dumpGraph(false);
            this.failed = true;
        }
        this.calculation = null;
    }

    void updateStats() {
        if (this.lastVoltagesNeedUpdate) {
            this.lastVoltagesNeedUpdate = false;
            for (Node node : this.nodes.values()) {
                node.updateStats();
            }
        }
    }

    Iterable<Node> calculate() {
        this.lastVoltagesNeedUpdate = true;
        if (this.activeSources.isEmpty() || this.activeSinks.isEmpty()) {
            for (Node node : this.nodes.values()) {
                node.setVoltage(0.0);
                node.resetCurrents();
            }
            if (!this.activeSources.isEmpty()) {
                this.activeSources.clear();
            }
            if (!this.activeSinks.isEmpty()) {
                this.activeSinks.clear();
            }
            this.hasNonZeroVoltages = false;
            return new ArrayList<Node>();
        }
        StructureCache.Data data = this.calculateDistribution();
        this.calculateEffects(data);
        this.activeSources.clear();
        this.activeSinks.clear();
        ArrayList<Node> arrayList = new ArrayList<Node>();
        for (Node node : data.activeNodes) {
            if (node.nodeType != NodeType.Sink && node.nodeType != NodeType.Source) continue;
            arrayList.add(node.getTop());
        }
        this.hasNonZeroVoltages = true;
        return arrayList;
    }

    private void add(Node node) {
        node.setGrid(this);
        Node node2 = this.nodes.put(node.uid, node);
        if (node2 != null) {
            throw new IllegalStateException("duplicate node uid, new " + node + ", old " + node2);
        }
    }

    private void invalidate() {
        this.finishCalculation();
        this.cache.clear();
    }

    private StructureCache.Data calculateDistribution() {
        StructureCache.Data data;
        long l = System.nanoTime();
        this.lastData = data = this.cache.get(this.activeSources, this.activeSinks);
        if (!data.isInitialized) {
            this.copyForOptimize(data);
            this.optimize(data);
            Grid.determineEmittingNodes(data);
            int n = data.activeNodes.size();
            data.networkMatrix = new DMatrixRMaj(n, n);
            data.sourceMatrix = new DMatrixRMaj(n, 1);
            data.resultMatrix = new DMatrixRMaj(n, 1);
            data.solver = LinearSolverFactory_DDRM.symmPosDef((int)n);
            if (!EnergyNetLocal.useLinearTransferModel) {
                Grid.populateNetworkMatrix(data);
                Grid.initializeSolver(data);
                if (!data.solver.modifiesA()) {
                    data.networkMatrix = null;
                }
            }
            data.isInitialized = true;
        }
        if (EnergyNetLocal.useLinearTransferModel) {
            Grid.populateNetworkMatrix(data);
            Grid.initializeSolver(data);
        }
        this.populateSourceMatrix(data);
        if (EnergyNetGlobal.debugGridVerbose) {
            this.dumpMatrix(IC2.log.getPrintStream(LogCategory.EnergyNet, Level.TRACE), false, true, false);
        }
        data.solver.solve((Matrix)data.sourceMatrix, (Matrix)data.resultMatrix);
        assert (!data.solver.modifiesB());
        if (EnergyNetGlobal.debugGridVerbose) {
            this.dumpMatrix(IC2.log.getPrintStream(LogCategory.EnergyNet, Level.TRACE), false, false, true);
        }
        if (EnergyNetGlobal.debugGrid) {
            l = System.nanoTime() - l;
            IC2.log.debug(LogCategory.EnergyNet, "%d The distribution calculation took %d us.", this.uid, l / 1000L);
        }
        return data;
    }

    private static void initializeSolver(StructureCache.Data data) {
        if (!data.solver.setA((Matrix)data.networkMatrix)) {
            int n = data.networkMatrix.numCols;
            if (data.solver.modifiesA()) {
                Grid.populateNetworkMatrix(data);
            }
            data.solver = LinearSolverFactory_DDRM.linear((int)n);
            if (!data.solver.setA((Matrix)data.networkMatrix)) {
                EigenDecomposition_F64 eigenDecomposition_F64;
                if (data.solver.modifiesA()) {
                    Grid.populateNetworkMatrix(data);
                }
                if ((eigenDecomposition_F64 = DecompositionFactory_DDRM.eig((int)n, (boolean)false)).decompose((Matrix)data.networkMatrix)) {
                    int n2 = n;
                    int n3 = n;
                    StringBuilder stringBuilder = new StringBuilder("Eigen values: ");
                    for (int i = 0; i < n; ++i) {
                        Complex_F64 complex_F64 = eigenDecomposition_F64.getEigenvalue(i);
                        if (complex_F64.isReal()) {
                            --n2;
                        }
                        if (complex_F64.real > 0.0) {
                            --n3;
                        }
                        if (i != 0) {
                            stringBuilder.append(", ");
                        }
                        stringBuilder.append(complex_F64);
                    }
                    IC2.log.info(LogCategory.EnergyNet, stringBuilder.toString());
                    IC2.log.info(LogCategory.EnergyNet, "Total: %d, complex: %d, non positive: %d", n, n2, n3);
                } else {
                    IC2.log.info(LogCategory.EnergyNet, "Unable to compute the eigen values.");
                }
                if (eigenDecomposition_F64.inputModified()) {
                    Grid.populateNetworkMatrix(data);
                }
                throw new RuntimeException("Can't decompose network matrix.");
            }
        }
    }

    private void calculateEffects(StructureCache.Data data) {
        long l = System.nanoTime();
        for (Node object : this.nodes.values()) {
            object.setVoltage(Double.NaN);
            object.resetCurrents();
        }
        block5: for (int i = 0; i < data.activeNodes.size(); ++i) {
            Node node = data.activeNodes.get(i);
            node.setVoltage(data.resultMatrix.get(i));
            switch (node.nodeType) {
                case Source: {
                    double d;
                    if (EnergyNetLocal.useLinearTransferModel) {
                        d = data.sourceMatrix.get(i) - node.getVoltage() / node.getResistance();
                        double d2 = d * node.getVoltage();
                        assert (d2 >= 0.0) : d2 + " (u=" + node.getVoltage() + ")";
                        assert (d2 <= node.getAmount()) : d2 + " <= " + node.getAmount() + " (u=" + node.getVoltage() + ")";
                        node.setAmount(d2 - node.getAmount());
                    } else {
                        d = node.getAmount();
                        node.setAmount(0.0);
                    }
                    assert (node.getAmount() <= 0.0);
                    if (!EnergyNetGlobal.debugGrid) continue block5;
                    IC2.log.debug(LogCategory.EnergyNet, "%d %s %f EU, %f V, %f A.", this.uid, node, -node.getAmount(), node.getVoltage(), -d);
                    continue block5;
                }
                case Sink: {
                    double d;
                    if (EnergyNetLocal.useLinearTransferModel) {
                        d = node.getVoltage() / node.getResistance();
                        node.setAmount(node.getVoltage() * d);
                    } else {
                        d = node.getVoltage();
                        node.setAmount(d);
                    }
                    assert (node.getAmount() >= 0.0);
                    if (!EnergyNetGlobal.debugGrid) continue block5;
                    IC2.log.debug(LogCategory.EnergyNet, "%d %s %f EU, %f V, %f A.", this.uid, node, node.getAmount(), node.getVoltage(), d);
                    continue block5;
                }
            }
        }
        HashSet<NodeLink> hashSet = EnergyNetGlobal.verifyGrid() ? new HashSet<NodeLink>() : null;
        for (Node node : data.activeNodes) {
            for (NodeLink nodeLink : node.links) {
                if (nodeLink.nodeA != node) continue;
                Node node2 = nodeLink.nodeA.getTop();
                Node node3 = nodeLink.nodeB.getTop();
                double d = nodeLink.loss;
                for (Node node4 : nodeLink.skippedNodes) {
                    assert (node4.nodeType == NodeType.Conductor);
                    if (!Double.isNaN((node4 = node4.getTop()).getVoltage())) {
                        assert (false);
                        break;
                    }
                    NodeLink nodeLink2 = node2.getConnectionTo(node4);
                    assert (nodeLink2 != null);
                    if (EnergyNetGlobal.verifyGrid()) assert (hashSet.add(nodeLink2));
                    node4.setVoltage(Util.lerp(node2.getVoltage(), node3.getVoltage(), nodeLink2.loss / d));
                    nodeLink2.updateCurrent();
                    node2 = node4;
                    d -= nodeLink2.loss;
                }
                node2.getConnectionTo(node3).updateCurrent();
            }
        }
        l = System.nanoTime() - l;
        if (EnergyNetGlobal.debugGrid) {
            IC2.log.debug(LogCategory.EnergyNet, "%d The effect calculation took %d us.", this.uid, l / 1000L);
        }
    }

    private void copyForOptimize(StructureCache.Data data) {
        data.optimizedNodes = new HashMap<Integer, Node>();
        for (Node node : this.nodes.values()) {
            assert (!node.links.isEmpty());
            if (!(node.getAmount() > 0.0) && node.nodeType != NodeType.Conductor) continue;
            assert (node.nodeType != NodeType.Sink || this.activeSinks.contains(node.uid));
            assert (node.nodeType != NodeType.Source || this.activeSources.contains(node.uid));
            assert (node.getGrid() != null);
            data.optimizedNodes.put(node.uid, new Node(node));
        }
        for (Node node : data.optimizedNodes.values()) {
            assert (!node.links.isEmpty());
            assert (node.getGrid() == this);
            Iterator<NodeLink> iterator = node.links.listIterator();
            while (iterator.hasNext()) {
                NodeLink nodeLink = iterator.next();
                Node node2 = nodeLink.getNeighbor(node.uid);
                assert (node2.getGrid() == this);
                if ((node2.nodeType == NodeType.Sink || node2.nodeType == NodeType.Source) && node2.getAmount() <= 0.0) {
                    iterator.remove();
                    continue;
                }
                if (nodeLink.nodeA.uid == node.uid) {
                    nodeLink.nodeA = data.optimizedNodes.get(nodeLink.nodeA.uid);
                    nodeLink.nodeB = data.optimizedNodes.get(nodeLink.nodeB.uid);
                    assert (nodeLink.nodeA != null && nodeLink.nodeB != null);
                    ArrayList<Node> arrayList = new ArrayList<Node>();
                    for (Node node3 : nodeLink.skippedNodes) {
                        arrayList.add(data.optimizedNodes.get(node3.uid));
                    }
                    nodeLink.skippedNodes = arrayList;
                    continue;
                }
                assert (nodeLink.nodeB.uid == node.uid);
                boolean bl = false;
                for (NodeLink nodeLink2 : data.optimizedNodes.get((Object)Integer.valueOf((int)nodeLink.nodeA.uid)).links) {
                    assert (nodeLink2.nodeA.uid != node.uid);
                    if (nodeLink2.nodeB.uid != node.uid || node.links.contains(nodeLink2)) continue;
                    assert (nodeLink2.nodeA.uid == nodeLink.nodeA.uid);
                    bl = true;
                    iterator.set(nodeLink2);
                    break;
                }
                assert (bl);
            }
        }
        if (EnergyNetGlobal.verifyGrid()) {
            for (Node node : data.optimizedNodes.values()) {
                assert (!node.links.isEmpty());
                for (NodeLink nodeLink : node.links) {
                    if (!data.optimizedNodes.containsValue(nodeLink.nodeA)) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Link %s is broken.", this.uid, nodeLink);
                    }
                    assert (data.optimizedNodes.containsValue(nodeLink.nodeA));
                    assert (data.optimizedNodes.containsValue(nodeLink.nodeB));
                    assert (nodeLink.nodeA != nodeLink.nodeB);
                    assert (nodeLink.getNeighbor((Node)node).links.contains(nodeLink));
                }
            }
            Iterator<Object> iterator = this.activeSources.iterator();
            while (iterator.hasNext()) {
                int n = (Integer)iterator.next();
                assert (data.optimizedNodes.containsKey(n));
            }
            iterator = this.activeSinks.iterator();
            while (iterator.hasNext()) {
                int n = (Integer)iterator.next();
                assert (data.optimizedNodes.containsKey(n));
            }
        }
    }

    private void optimize(StructureCache.Data data) {
        List<Node> list;
        Iterator<Object> iterator;
        int n;
        do {
            n = 0;
            iterator = data.optimizedNodes.values().iterator();
            while (iterator.hasNext()) {
                NodeLink nodeLink22;
                Node node = iterator.next();
                if (node.nodeType != NodeType.Conductor) continue;
                if (node.links.size() < 2) {
                    iterator.remove();
                    ++n;
                    for (NodeLink nodeLink22 : node.links) {
                        boolean bl = false;
                        list = nodeLink22.getNeighbor((Node)node).links.iterator();
                        while (list.hasNext()) {
                            if (list.next() != nodeLink22) continue;
                            bl = true;
                            list.remove();
                            break;
                        }
                        assert (bl);
                    }
                    continue;
                }
                if (node.links.size() != 2) continue;
                iterator.remove();
                ++n;
                NodeLink nodeLink3 = node.links.get(0);
                nodeLink22 = node.links.get(1);
                Node node2 = nodeLink3.getNeighbor(node);
                list = nodeLink22.getNeighbor(node);
                if (node2 == list) {
                    node2.links.remove(nodeLink3);
                    ((Node)((Object)list)).links.remove(nodeLink22);
                    continue;
                }
                nodeLink3.loss += nodeLink22.loss;
                if (nodeLink3.nodeA == node) {
                    nodeLink3.nodeA = list;
                    nodeLink3.dirFromA = nodeLink22.getDirFrom((Node)((Object)list));
                    if (nodeLink22.nodeA == node) {
                        assert (nodeLink22.nodeB == list);
                        Collections.reverse(nodeLink22.skippedNodes);
                    } else assert (nodeLink22.nodeB == node && nodeLink22.nodeA == list);
                    nodeLink22.skippedNodes.add(node);
                    nodeLink22.skippedNodes.addAll(nodeLink3.skippedNodes);
                    nodeLink3.skippedNodes = nodeLink22.skippedNodes;
                } else {
                    nodeLink3.nodeB = list;
                    nodeLink3.dirFromB = nodeLink22.getDirFrom((Node)((Object)list));
                    if (nodeLink22.nodeB == node) {
                        assert (nodeLink22.nodeA == list);
                        Collections.reverse(nodeLink22.skippedNodes);
                    } else assert (nodeLink22.nodeA == node && nodeLink22.nodeB == list);
                    nodeLink3.skippedNodes.add(node);
                    nodeLink3.skippedNodes.addAll(nodeLink22.skippedNodes);
                }
                assert (nodeLink3.nodeA != nodeLink3.nodeB);
                assert (nodeLink3.nodeA == node2 || nodeLink3.nodeB == node2);
                assert (nodeLink3.nodeA == list || nodeLink3.nodeB == list);
                boolean bl = false;
                ListIterator<NodeLink> object = ((Node)((Object)list)).links.listIterator();
                while (object.hasNext()) {
                    if (object.next() != nodeLink22) continue;
                    bl = true;
                    object.set(nodeLink3);
                    break;
                }
                assert (bl);
            }
        } while (n > 0);
        if (EnergyNetGlobal.verifyGrid()) {
            for (Node node : data.optimizedNodes.values()) {
                assert (!node.links.isEmpty());
                for (NodeLink nodeLink22 : node.links) {
                    if (!data.optimizedNodes.containsValue(nodeLink22.nodeA)) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d Link %s is broken.", this.uid, nodeLink22);
                    }
                    assert (data.optimizedNodes.containsValue(nodeLink22.nodeA));
                    assert (data.optimizedNodes.containsValue(nodeLink22.nodeB));
                    assert (!this.nodes.containsValue(nodeLink22.nodeA));
                    assert (!this.nodes.containsValue(nodeLink22.nodeB));
                    assert (this.nodes.containsValue(nodeLink22.nodeA.getTop()));
                    assert (this.nodes.containsValue(nodeLink22.nodeB.getTop()));
                    assert (nodeLink22.nodeA != nodeLink22.nodeB);
                    assert (nodeLink22.nodeA == node || nodeLink22.nodeB == node);
                    assert (nodeLink22.getNeighbor((Node)node).links.contains(nodeLink22));
                    assert (!nodeLink22.skippedNodes.contains(nodeLink22.nodeA));
                    assert (!nodeLink22.skippedNodes.contains(nodeLink22.nodeB));
                    assert (Collections.disjoint(nodeLink22.skippedNodes, data.optimizedNodes.values()));
                    assert (Collections.disjoint(nodeLink22.skippedNodes, this.nodes.values()));
                    assert (new HashSet<Node>(nodeLink22.skippedNodes).size() == nodeLink22.skippedNodes.size());
                    Node node3 = node.getTop();
                    if (nodeLink22.nodeA == node) {
                        list = nodeLink22.skippedNodes;
                    } else {
                        list = new ArrayList<Node>(nodeLink22.skippedNodes);
                        Collections.reverse(list);
                    }
                    for (Node node2 : list) {
                        assert (node3.getConnectionTo(node2.getTop()) != null) : node3 + " -> " + node2.getTop() + " not in " + node3.links + " (skipped " + list + ")";
                        node3 = node2.getTop();
                    }
                    assert (node3.getConnectionTo(nodeLink22.getNeighbor(node).getTop()) != null) : node3 + " -> " + nodeLink22.getNeighbor(node).getTop() + " not in " + node3.links;
                }
            }
            iterator = this.activeSources.iterator();
            while (iterator.hasNext()) {
                int n2 = (Integer)iterator.next();
                assert (data.optimizedNodes.containsKey(n2));
            }
            iterator = this.activeSinks.iterator();
            while (iterator.hasNext()) {
                int n3 = (Integer)iterator.next();
                assert (data.optimizedNodes.containsKey(n3));
            }
        }
    }

    private static void determineEmittingNodes(StructureCache.Data data) {
        data.activeNodes = new ArrayList<Node>();
        int n = 0;
        for (Node node : data.optimizedNodes.values()) {
            switch (node.nodeType) {
                case Source: {
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d %d %s.", node.getGrid().uid, n++, node);
                    }
                    data.activeNodes.add(node);
                    break;
                }
                case Sink: {
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d %d %s.", node.getGrid().uid, n++, node);
                    }
                    data.activeNodes.add(node);
                    break;
                }
                case Conductor: {
                    if (EnergyNetGlobal.debugGrid) {
                        IC2.log.debug(LogCategory.EnergyNet, "%d %d %s.", node.getGrid().uid, n++, node);
                    }
                    data.activeNodes.add(node);
                }
            }
        }
    }

    private static void populateNetworkMatrix(StructureCache.Data data) {
        for (int i = 0; i < data.activeNodes.size(); ++i) {
            Node node = data.activeNodes.get(i);
            for (int j = 0; j < data.activeNodes.size(); ++j) {
                double d;
                block11: {
                    block8: {
                        block9: {
                            block10: {
                                d = 0.0;
                                if (i != j) break block8;
                                for (NodeLink nodeLink : node.links) {
                                    if (nodeLink.getNeighbor(node) == node) continue;
                                    d += 1.0 / nodeLink.loss;
                                    assert (nodeLink.loss >= 0.0);
                                }
                                if (!EnergyNetLocal.useLinearTransferModel) break block9;
                                if (node.nodeType != NodeType.Source) break block10;
                                double d2 = EnergyNet.instance.getPowerFromTier(node.getTier());
                                double d3 = Util.square(d2) / (node.getAmount() * 4.0);
                                assert (d3 > 0.0);
                                d += 1.0 / d3;
                                node.setResistance(d3);
                                break block11;
                            }
                            if (node.nodeType != NodeType.Sink) break block11;
                            double d4 = EnergyNet.instance.getPowerFromTier(node.getTier());
                            assert (d4 > 0.0);
                            d += 1.0 / d4;
                            node.setResistance(d4);
                            break block11;
                        }
                        if (node.nodeType != NodeType.Sink) break block11;
                        d += 1.0;
                        break block11;
                    }
                    Node node2 = data.activeNodes.get(j);
                    for (NodeLink nodeLink : node.links) {
                        Node node3 = nodeLink.getNeighbor(node);
                        if (node3 == node || node3 != node2) continue;
                        d -= 1.0 / nodeLink.loss;
                        assert (nodeLink.loss >= 0.0);
                    }
                }
                data.networkMatrix.set(i, j, d);
            }
        }
    }

    private void populateSourceMatrix(StructureCache.Data data) {
        for (int i = 0; i < data.activeNodes.size(); ++i) {
            Node node = data.activeNodes.get(i);
            double d = 0.0;
            if (node.nodeType == NodeType.Source) {
                if (EnergyNetLocal.useLinearTransferModel) {
                    double d2 = EnergyNet.instance.getPowerFromTier(node.getTier());
                    d = d2 / node.getResistance();
                } else {
                    d = node.getAmount();
                }
                assert (d > 0.0);
            }
            data.sourceMatrix.set(i, 0, d);
        }
    }

    void dumpNodeInfo(PrintStream printStream, boolean bl, Node node) {
        Object object;
        if (bl) {
            this.finishCalculation();
        }
        printStream.println("Node " + node + " info:");
        printStream.println(" type: " + node.nodeType);
        switch (node.nodeType) {
            case Conductor: {
                break;
            }
            case Sink: {
                object = (IEnergySink)node.tile.mainTile;
                printStream.println(" demanded: " + object.getDemandedEnergy());
                printStream.println(" tier: " + object.getSinkTier());
                break;
            }
            case Source: {
                object = (IEnergySource)node.tile.mainTile;
                printStream.println(" offered: " + object.getOfferedEnergy());
                printStream.println(" tier: " + object.getSourceTier());
                break;
            }
        }
        printStream.println(node.links.size() + " neighbor links:");
        for (NodeLink object2 : node.links) {
            printStream.println(" " + object2.getNeighbor(node) + " " + object2.loss + " " + object2.skippedNodes);
        }
        object = this.lastData;
        if (object == null || !((StructureCache.Data)object).isInitialized || ((StructureCache.Data)object).optimizedNodes == null) {
            printStream.println("No optimized data");
        } else if (!((StructureCache.Data)object).optimizedNodes.containsKey(node.uid)) {
            printStream.println("Optimized away");
        } else {
            Node node2 = ((StructureCache.Data)object).optimizedNodes.get(node.uid);
            printStream.println(node2.links.size() + " optimized neighbor links:");
            for (NodeLink nodeLink : node2.links) {
                printStream.println(" " + nodeLink.getNeighbor(node2) + " " + nodeLink.loss + " " + nodeLink.skippedNodes);
            }
        }
    }

    void dumpMatrix(PrintStream printStream, boolean bl, boolean bl2, boolean bl3) {
        StructureCache.Data data;
        if (bl) {
            this.finishCalculation();
        }
        if (bl2) {
            printStream.println("Dumping matrices for " + this + ".");
        }
        if ((data = this.lastData) == null) {
            printStream.println("Matrices unavailable");
        } else if (bl2 || bl3) {
            if (!data.isInitialized) {
                printStream.println("Matrices potentially outdated");
            }
            if (bl2) {
                printStream.println("Emitting node indizes:");
                for (int i = 0; i < data.activeNodes.size(); ++i) {
                    Node node = data.activeNodes.get(i);
                    printStream.println(i + " " + node + " (amount=" + node.getAmount() + ", tier=" + node.getTier() + ")");
                }
                printStream.println("Network matrix:");
                Grid.printMatrix(data.networkMatrix, printStream);
                printStream.println("Source matrix:");
                Grid.printMatrix(data.sourceMatrix, printStream);
            }
            if (bl3) {
                printStream.println("Result matrix:");
                Grid.printMatrix(data.resultMatrix, printStream);
            }
        }
    }

    private static void printMatrix(DMatrixRMaj dMatrixRMaj, PrintStream printStream) {
        if (dMatrixRMaj == null) {
            printStream.println("null");
            return;
        }
        boolean bl = true;
        block0: for (int i = 0; i < dMatrixRMaj.numRows; ++i) {
            for (int j = 0; j < dMatrixRMaj.numCols; ++j) {
                if (dMatrixRMaj.get(i, j) == 0.0) continue;
                bl = false;
                continue block0;
            }
        }
        if (bl) {
            printStream.println(dMatrixRMaj.numRows + "x" + dMatrixRMaj.numCols + ", all zero");
        } else {
            MatrixIO.print((PrintStream)printStream, (DMatrix)dMatrixRMaj, (String)"%.6f");
        }
    }

    void dumpStats(PrintStream printStream, boolean bl) {
        if (bl) {
            this.finishCalculation();
        }
        printStream.println("Grid " + this.uid + " info:");
        printStream.println(this.nodes.size() + " nodes");
        StructureCache.Data data = this.lastData;
        if (data != null && data.isInitialized) {
            if (data.activeNodes != null) {
                int n = 0;
                int n2 = 0;
                for (Node node : data.activeNodes) {
                    if (node.nodeType == NodeType.Source) {
                        ++n;
                        continue;
                    }
                    if (node.nodeType != NodeType.Sink) continue;
                    ++n2;
                }
                printStream.println("Active: " + n + " sources -> " + n2 + " sinks");
            }
            if (data.optimizedNodes != null) {
                printStream.println(data.optimizedNodes.size() + " nodes after optimization");
            }
            if (data.activeNodes != null) {
                printStream.println(data.activeNodes.size() + " emitting nodes");
            }
        }
        printStream.printf("%d entries in cache, hitrate %.2f%%", this.cache.size(), 100.0 * (double)this.cache.hits / (double)(this.cache.hits + this.cache.misses));
        printStream.println();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void dumpGraph(boolean bl) {
        if (bl) {
            this.finishCalculation();
        }
        StructureCache.Data data = this.lastData;
        for (int i = 0; i < 2 && (i != 1 || data != null && data.isInitialized && data.optimizedNodes != null); ++i) {
            OutputStreamWriter outputStreamWriter = null;
            try {
                outputStreamWriter = new FileWriter("graph_" + this.uid + "_" + (i == 0 ? "raw" : "optimized") + ".txt");
                outputStreamWriter.write("graph nodes {\n  overlap=false;\n");
                Collection<Node> collection = (i == 0 ? this.nodes : data.optimizedNodes).values();
                HashSet<Node> hashSet = new HashSet<Node>();
                for (Node node : collection) {
                    outputStreamWriter.write("  \"" + node + "\";\n");
                    for (NodeLink nodeLink : node.links) {
                        Node node2 = nodeLink.getNeighbor(node);
                        if (hashSet.contains(node2)) continue;
                        outputStreamWriter.write("  \"" + node + "\" -- \"" + node2 + "\" [label=\"" + nodeLink.loss + "\"];\n");
                    }
                    hashSet.add(node);
                }
                outputStreamWriter.write("}\n");
                continue;
            }
            catch (IOException iOException) {
                IC2.log.debug(LogCategory.EnergyNet, iOException, "Graph saving failed.");
                continue;
            }
            finally {
                try {
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                }
                catch (IOException iOException) {}
            }
        }
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
}

