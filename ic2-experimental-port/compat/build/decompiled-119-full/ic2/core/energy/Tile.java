/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 */
package ic2.core.energy;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMetaDelegate;
import ic2.core.energy.EnergyNetLocal;
import ic2.core.energy.Node;
import ic2.core.energy.grid.NodeType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.BlockPos;

class Tile {
    final IEnergyTile mainTile;
    final List<IEnergyTile> subTiles;
    final List<Node> nodes = new ArrayList<Node>();
    final double maxCurrent;

    Tile(EnergyNetLocal energyNetLocal, IEnergyTile iEnergyTile) {
        this.mainTile = iEnergyTile;
        if (iEnergyTile instanceof IMetaDelegate) {
            this.subTiles = new ArrayList<IEnergyTile>(((IMetaDelegate)iEnergyTile).getSubTiles());
            if (this.subTiles.isEmpty()) {
                throw new RuntimeException("Tile " + iEnergyTile + " must return at least 1 sub tile for IMetaDelegate.getSubTiles().");
            }
        } else {
            this.subTiles = Arrays.asList(iEnergyTile);
        }
        if (iEnergyTile instanceof IEnergySource) {
            this.nodes.add(new Node(energyNetLocal, this, NodeType.Source));
        }
        if (iEnergyTile instanceof IEnergySink) {
            this.nodes.add(new Node(energyNetLocal, this, NodeType.Sink));
        }
        if (iEnergyTile instanceof IEnergyConductor) {
            this.nodes.add(new Node(energyNetLocal, this, NodeType.Conductor));
            this.maxCurrent = ((IEnergyConductor)iEnergyTile).getConductorBreakdownEnergy();
        } else {
            this.maxCurrent = Double.MAX_VALUE;
        }
    }

    void addExtraNode(Node node) {
        node.setExtraNode(true);
        this.nodes.add(node);
    }

    boolean removeExtraNode(Node node) {
        boolean bl = false;
        if (node.isExtraNode()) {
            bl = true;
        } else {
            for (Node node2 : this.nodes) {
                if (node2 == node || node2.nodeType != node.nodeType || !node2.isExtraNode()) continue;
                node2.setExtraNode(false);
                bl = true;
                break;
            }
        }
        if (bl) {
            this.nodes.remove(node);
            return true;
        }
        return false;
    }

    IEnergyTile getSubTileAt(BlockPos blockPos) {
        for (IEnergyTile iEnergyTile : this.subTiles) {
            if (!EnergyNet.instance.getPos(iEnergyTile).equals((Object)blockPos)) continue;
            return iEnergyTile;
        }
        return null;
    }

    Iterable<NodeStats> getStats() {
        ArrayList<NodeStats> arrayList = new ArrayList<NodeStats>(this.nodes.size());
        for (Node node : this.nodes) {
            arrayList.add(node.getStats());
        }
        return arrayList;
    }
}

