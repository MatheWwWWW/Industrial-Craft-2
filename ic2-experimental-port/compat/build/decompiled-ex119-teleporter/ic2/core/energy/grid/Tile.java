/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 */
package ic2.core.energy.grid;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class Tile {
    private final IEnergyTile mainTile;
    final List<IEnergyTile> subTiles;
    final List<Node> nodes = new ArrayList<Node>();
    private boolean disabled;
    private double amount;
    private int packetCount;

    Tile(EnergyNetLocal energyNetLocal, IEnergyTile iEnergyTile, List<IEnergyTile> list) {
        this.mainTile = iEnergyTile;
        this.subTiles = list;
        if (iEnergyTile instanceof IEnergySource) {
            this.nodes.add(new Node(energyNetLocal.allocateNodeId(), this, NodeType.Source));
        }
        if (iEnergyTile instanceof IEnergySink) {
            this.nodes.add(new Node(energyNetLocal.allocateNodeId(), this, NodeType.Sink));
        }
        if (iEnergyTile instanceof IEnergyConductor) {
            this.nodes.add(new Node(energyNetLocal.allocateNodeId(), this, NodeType.Conductor));
        }
    }

    public IEnergyTile getMainTile() {
        return this.mainTile;
    }

    public Collection<Node> getNodes() {
        return this.nodes;
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
        }
        return bl;
    }

    public Collection<IEnergyTile> getSubTiles() {
        return this.subTiles;
    }

    IEnergyTile getSubTileAt(BlockPos blockPos) {
        for (IEnergyTile iEnergyTile : this.subTiles) {
            if (!EnergyNet.instance.getPos(iEnergyTile).equals((Object)blockPos)) continue;
            return iEnergyTile;
        }
        return null;
    }

    void setDisabled() {
        this.disabled = true;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double d) {
        this.amount = d;
    }

    public int getPacketCount() {
        return this.packetCount;
    }

    public void setSourceData(double d, int n) {
        this.amount = d;
        this.packetCount = n;
    }

    public String toString() {
        BlockPos blockPos;
        Object object = Tile.getTeClassName(this.mainTile);
        Level level = EnergyNet.instance.getWorld(this.mainTile);
        MinecraftServer minecraftServer = level.m_7654_();
        if (minecraftServer != null && minecraftServer.m_18695_() && level.m_46749_(blockPos = EnergyNet.instance.getPos(this.mainTile))) {
            BlockEntity blockEntity = level.m_7702_(blockPos);
            object = blockEntity != null ? (String)object + "|" + Tile.getTeClassName(blockEntity) : (String)object + "|" + level.m_8055_(blockPos);
        }
        return object;
    }

    private static String getTeClassName(Object object) {
        return object.getClass().getSimpleName().replace("TileEntity", "");
    }
}

