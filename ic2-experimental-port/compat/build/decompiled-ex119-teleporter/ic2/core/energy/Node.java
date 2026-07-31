/*
 * Decompiled with CFR 0.152.
 */
package ic2.core.energy;

import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.core.IC2;
import ic2.core.energy.EnergyNetGlobal;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.energy.Grid;
import ic2.core.energy.MutableNodeStats;
import ic2.core.energy.NodeLink;
import ic2.core.energy.Tile;
import ic2.core.energy.grid.NodeType;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.List;

class Node {
    final int uid;
    final Tile tile;
    final NodeType nodeType;
    private final Node parent;
    private boolean isExtraNode = false;
    private int tier;
    private double amount;
    private double resistance;
    private double voltage;
    private double currentIn;
    private double currentOut;
    private Grid grid;
    List<NodeLink> links = new ArrayList<NodeLink>();
    private final MutableNodeStats lastNodeStats = new MutableNodeStats();

    Node(EnergyNetLocal energyNetLocal, Tile tile, NodeType nodeType) {
        if (energyNetLocal == null) {
            throw new NullPointerException("The energyNet parameter must not be null.");
        }
        if (tile == null) {
            throw new NullPointerException("The tile parameter must not be null.");
        }
        assert (nodeType != NodeType.Conductor || tile.mainTile instanceof IEnergyConductor);
        assert (nodeType != NodeType.Sink || tile.mainTile instanceof IEnergySink);
        assert (nodeType != NodeType.Source || tile.mainTile instanceof IEnergySource);
        this.uid = EnergyNetLocal.getNextNodeUid();
        this.tile = tile;
        this.nodeType = nodeType;
        this.parent = null;
    }

    Node(Node node) {
        this.uid = node.uid;
        this.tile = node.tile;
        this.nodeType = node.nodeType;
        this.parent = node;
        assert (this.nodeType != NodeType.Conductor || this.tile.mainTile instanceof IEnergyConductor);
        assert (this.nodeType != NodeType.Sink || this.tile.mainTile instanceof IEnergySink);
        assert (this.nodeType != NodeType.Source || this.tile.mainTile instanceof IEnergySource);
        for (NodeLink nodeLink : node.links) {
            assert (nodeLink.getNeighbor((Node)node).links.contains(nodeLink));
            this.links.add(new NodeLink(nodeLink));
        }
    }

    double getInnerLoss() {
        switch (this.nodeType) {
            case Source: {
                return 0.4;
            }
            case Sink: {
                return 0.4;
            }
            case Conductor: {
                return ((IEnergyConductor)this.tile.mainTile).getConductionLoss();
            }
        }
        throw new RuntimeException("invalid nodetype: " + this.nodeType);
    }

    boolean isExtraNode() {
        return this.getTop().isExtraNode;
    }

    void setExtraNode(boolean bl) {
        if (this.nodeType == NodeType.Conductor) {
            throw new IllegalStateException("A conductor can't be an extra node.");
        }
        this.getTop().isExtraNode = bl;
    }

    int getTier() {
        return this.getTop().tier;
    }

    void setTier(int n) {
        if (n < 0 || Double.isNaN(n)) {
            assert (false);
            if (EnergyNetGlobal.debugGrid) {
                IC2.log.warn(LogCategory.EnergyNet, "Node %s / te %s is using the invalid tier %d.", this, this.tile.mainTile, n);
            }
            n = 0;
        } else if (n > 20 && (n != Integer.MAX_VALUE || this.nodeType != NodeType.Sink)) {
            if (Util.inDev()) {
                IC2.log.debug(LogCategory.EnergyNet, "Restricting node %s to tier 20, requested %d.", this, n);
            }
            n = 20;
        }
        this.getTop().tier = n;
    }

    double getAmount() {
        return this.getTop().amount;
    }

    void setAmount(double d) {
        this.getTop().amount = d;
    }

    double getResistance() {
        return this.getTop().resistance;
    }

    void setResistance(double d) {
        this.getTop().resistance = d;
    }

    double getVoltage() {
        return this.getTop().voltage;
    }

    void setVoltage(double d) {
        this.getTop().voltage = d;
    }

    double getMaxCurrent() {
        return this.tile.maxCurrent;
    }

    void resetCurrents() {
        this.getTop().currentIn = 0.0;
        this.getTop().currentOut = 0.0;
    }

    void addCurrent(double d) {
        if (d >= 0.0) {
            this.getTop().currentIn += d;
        } else {
            this.getTop().currentOut += -d;
        }
    }

    public String toString() {
        String string = null;
        switch (this.nodeType) {
            case Conductor: {
                string = "C";
                break;
            }
            case Sink: {
                string = "A";
                break;
            }
            case Source: {
                string = "E";
            }
        }
        return this.tile.mainTile.getClass().getSimpleName().replace("TileEntity", "") + "|" + string + "|" + this.tier + "|" + this.uid;
    }

    Node getTop() {
        if (this.parent != null) {
            return this.parent.getTop();
        }
        return this;
    }

    NodeLink getConnectionTo(Node node) {
        for (NodeLink nodeLink : this.links) {
            if (nodeLink.getNeighbor(this) != node) continue;
            return nodeLink;
        }
        return null;
    }

    NodeStats getStats() {
        return this.lastNodeStats;
    }

    void updateStats() {
        if (EnergyNetLocal.useLinearTransferModel) {
            this.lastNodeStats.set(this.currentIn * this.voltage, this.currentOut * this.voltage, this.voltage);
        } else {
            this.lastNodeStats.set(this.currentIn, this.currentOut, this.voltage);
        }
    }

    Grid getGrid() {
        return this.getTop().grid;
    }

    void setGrid(Grid grid) {
        if (grid == null) {
            throw new NullPointerException("null grid");
        }
        assert (this.getTop().grid == null);
        this.getTop().grid = grid;
    }

    void clearGrid() {
        assert (this.getTop().grid != null);
        this.getTop().grid = null;
    }
}

