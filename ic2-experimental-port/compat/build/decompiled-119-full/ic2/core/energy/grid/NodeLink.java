/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.Vec3i
 */
package ic2.core.energy.grid;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.energy.grid.Node;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

public class NodeLink {
    Node nodeA;
    Node nodeB;
    Direction dirFromA;
    Direction dirFromB;
    double loss;
    List<Node> skippedNodes = new ArrayList<Node>();

    NodeLink(Node node, Node node2, double d) {
        this(node, node2, d, null, null);
        this.calculateDirections();
    }

    NodeLink(NodeLink nodeLink) {
        this(nodeLink.nodeA, nodeLink.nodeB, nodeLink.loss, nodeLink.dirFromA, nodeLink.dirFromB);
        this.skippedNodes.addAll(nodeLink.skippedNodes);
    }

    private NodeLink(Node node, Node node2, double d, Direction direction, Direction direction2) {
        assert (node != node2);
        this.nodeA = node;
        this.nodeB = node2;
        this.loss = d;
        this.dirFromA = direction;
        this.dirFromB = direction2;
    }

    public Node getNeighbor(Node node) {
        if (this.nodeA == node) {
            return this.nodeB;
        }
        return this.nodeA;
    }

    Node getNeighbor(int n) {
        if (this.nodeA.uid == n) {
            return this.nodeB;
        }
        return this.nodeA;
    }

    public double getLoss() {
        return this.loss;
    }

    void replaceNode(Node node, Node node2) {
        if (this.nodeA == node) {
            this.nodeA = node2;
        } else if (this.nodeB == node) {
            this.nodeB = node2;
        } else {
            throw new IllegalArgumentException("Node " + node + " isn't in " + this + ".");
        }
    }

    public Direction getDirFrom(Node node) {
        if (this.nodeA == node) {
            return this.dirFromA;
        }
        if (this.nodeB == node) {
            return this.dirFromB;
        }
        return null;
    }

    public String toString() {
        return "NodeLink:" + this.nodeA + "@" + this.dirFromA + "->" + this.nodeB + "@" + this.dirFromB;
    }

    private void calculateDirections() {
        for (IEnergyTile iEnergyTile : this.nodeA.tile.subTiles) {
            for (IEnergyTile iEnergyTile2 : this.nodeB.tile.subTiles) {
                BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile).m_121996_((Vec3i)EnergyNet.instance.getPos(iEnergyTile2));
                for (Direction direction : Util.ALL_DIRS) {
                    if (direction.m_122429_() != blockPos.m_123341_() || direction.m_122430_() != blockPos.m_123342_() || direction.m_122431_() != blockPos.m_123343_()) continue;
                    this.dirFromA = direction;
                    this.dirFromB = direction.m_122424_();
                    return;
                }
            }
        }
        assert (false);
        this.dirFromA = null;
        this.dirFromB = null;
    }
}

