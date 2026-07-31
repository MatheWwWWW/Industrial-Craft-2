/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 */
package ic2.core.energy.leg;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeLink;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

class EnergyPath {
    final Node source;
    final Node target;
    final List<Node> conductors;
    final double loss;
    final Direction targetDirection;
    final double minEffectEnergy;
    final double minInsulationEnergyAbsorption;
    final double minInsulationBreakdownEnergy;
    final double minConductorBreakdownEnergy;
    final int minX;
    final int minY;
    final int minZ;
    final int maxX;
    final int maxY;
    final int maxZ;
    int lastCalcId = -1;
    double energySupplied;
    double maxPacketConducted;

    EnergyPath(Node node, Node node2, List<Node> list, double d) {
        this.source = node;
        this.target = node2;
        this.conductors = list;
        this.loss = d;
        NodeLink nodeLink = list.isEmpty() ? node.getLinkTo(node2) : node2.getLinkTo(list.get(list.size() - 1));
        this.targetDirection = nodeLink.getDirFrom(node2);
        int n = Integer.MAX_VALUE;
        int n2 = Integer.MAX_VALUE;
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MIN_VALUE;
        int n5 = Integer.MIN_VALUE;
        int n6 = Integer.MIN_VALUE;
        double d2 = Double.POSITIVE_INFINITY;
        double d3 = Double.POSITIVE_INFINITY;
        double d4 = Double.POSITIVE_INFINITY;
        for (Node node3 : list) {
            IEnergyConductor iEnergyConductor = (IEnergyConductor)node3.getTile().getMainTile();
            d2 = Math.min(d2, iEnergyConductor.getInsulationEnergyAbsorption());
            d3 = Math.min(d3, iEnergyConductor.getInsulationBreakdownEnergy());
            d4 = Math.min(d4, iEnergyConductor.getConductorBreakdownEnergy());
            for (IEnergyTile iEnergyTile : node3.getTile().getSubTiles()) {
                BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
                n = Math.min(n, blockPos.m_123341_());
                n2 = Math.min(n2, blockPos.m_123342_());
                n3 = Math.min(n3, blockPos.m_123343_());
                n4 = Math.max(n4, blockPos.m_123341_());
                n5 = Math.max(n5, blockPos.m_123342_());
                n6 = Math.max(n6, blockPos.m_123343_());
            }
        }
        this.minEffectEnergy = Math.min(Math.min(d2, d3), d4);
        this.minInsulationEnergyAbsorption = d2;
        this.minInsulationBreakdownEnergy = d3;
        this.minConductorBreakdownEnergy = d4;
        this.minX = n;
        this.minY = n2;
        this.minZ = n3;
        this.maxX = n4;
        this.maxY = n5;
        this.maxZ = n6;
    }
}

