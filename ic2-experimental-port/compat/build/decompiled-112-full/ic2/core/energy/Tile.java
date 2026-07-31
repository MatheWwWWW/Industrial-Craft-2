/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
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
import net.minecraft.util.math.BlockPos;

class Tile {
    final IEnergyTile mainTile;
    final List<IEnergyTile> subTiles;
    final List<Node> nodes = new ArrayList<Node>();
    final double maxCurrent;

    Tile(EnergyNetLocal energyNet, IEnergyTile mainTile) {
        this.mainTile = mainTile;
        if (mainTile instanceof IMetaDelegate) {
            this.subTiles = new ArrayList<IEnergyTile>(((IMetaDelegate)mainTile).getSubTiles());
            if (this.subTiles.isEmpty()) {
                throw new RuntimeException("Tile " + mainTile + " must return at least 1 sub tile for IMetaDelegate.getSubTiles().");
            }
        } else {
            this.subTiles = Arrays.asList(mainTile);
        }
        if (mainTile instanceof IEnergySource) {
            this.nodes.add(new Node(energyNet, this, NodeType.Source));
        }
        if (mainTile instanceof IEnergySink) {
            this.nodes.add(new Node(energyNet, this, NodeType.Sink));
        }
        if (mainTile instanceof IEnergyConductor) {
            this.nodes.add(new Node(energyNet, this, NodeType.Conductor));
            this.maxCurrent = ((IEnergyConductor)mainTile).getConductorBreakdownEnergy();
        } else {
            this.maxCurrent = Double.MAX_VALUE;
        }
    }

    void addExtraNode(Node node) {
        node.setExtraNode(true);
        this.nodes.add(node);
    }

    boolean removeExtraNode(Node node) {
        boolean canBeRemoved = false;
        if (node.isExtraNode()) {
            canBeRemoved = true;
        } else {
            for (Node otherNode : this.nodes) {
                if (otherNode == node || otherNode.nodeType != node.nodeType || !otherNode.isExtraNode()) continue;
                otherNode.setExtraNode(false);
                canBeRemoved = true;
                break;
            }
        }
        if (canBeRemoved) {
            this.nodes.remove(node);
            return true;
        }
        return false;
    }

    IEnergyTile getSubTileAt(BlockPos pos) {
        for (IEnergyTile subTile : this.subTiles) {
            if (!EnergyNet.instance.getPos(subTile).equals((Object)pos)) continue;
            return subTile;
        }
        return null;
    }

    Iterable<NodeStats> getStats() {
        ArrayList<NodeStats> ret = new ArrayList<NodeStats>(this.nodes.size());
        for (Node node : this.nodes) {
            ret.add(node.getStats());
        }
        return ret;
    }
}

