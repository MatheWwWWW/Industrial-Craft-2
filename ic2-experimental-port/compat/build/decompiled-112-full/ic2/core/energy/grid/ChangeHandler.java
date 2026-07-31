/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
 */
package ic2.core.energy.grid;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.IEnergyNetEventReceiver;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IMetaDelegate;
import ic2.core.IC2;
import ic2.core.energy.grid.EnergyNetGlobal;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.Grid;
import ic2.core.energy.grid.GridChange;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeType;
import ic2.core.energy.grid.Tile;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

class ChangeHandler {
    ChangeHandler() {
    }

    static boolean prepareSync(EnergyNetLocal enet, GridChange change) {
        World world = enet.getWorld();
        GridChange.Type type = change.type;
        IEnergyTile ioTile = change.ioTile;
        BlockPos pos = change.pos;
        if (EnergyNet.instance.getWorld(ioTile) != world) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s had the wrong world in grid update (%s)", new Object[]{Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type});
            }
            return false;
        }
        if (type != GridChange.Type.REMOVAL && !EnergyNet.instance.getPos(ioTile).equals((Object)pos)) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s has the wrong position in grid update (%s)", new Object[]{Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type});
            }
            return false;
        }
        if (type != GridChange.Type.REMOVAL && !world.func_175667_e(pos)) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s was unloaded in grid update (%s)", new Object[]{Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type});
            }
            return false;
        }
        if (type != GridChange.Type.REMOVAL && ioTile instanceof TileEntity && ((TileEntity)ioTile).func_145837_r()) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s was invalidated in grid update (%s)", new Object[]{Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type});
            }
            return false;
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Considering tile %s for grid update (%s)", new Object[]{Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), type});
        }
        if (type == GridChange.Type.ADDITION) {
            if (ioTile instanceof IMetaDelegate) {
                change.subTiles = new ArrayList<IEnergyTile>(((IMetaDelegate)ioTile).getSubTiles());
                if (change.subTiles.isEmpty()) {
                    throw new RuntimeException(String.format("Tile %s must return at least 1 sub tile for IMetaDelegate.getSubTiles().", Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos)));
                }
            } else {
                change.subTiles = Arrays.asList(ioTile);
            }
        }
        return true;
    }

    static void applyAddition(EnergyNetLocal enet, IEnergyTile ioTile, BlockPos pos, List<IEnergyTile> subTiles, Collection<GridChange> pendingChanges) {
        if (enet.registeredIoTiles.containsKey(ioTile)) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s is already registered", Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos));
            }
            return;
        }
        for (IEnergyTile subTile : subTiles) {
            BlockPos subPos = EnergyNet.instance.getPos(subTile);
            Tile prev = enet.registeredTiles.get(subPos);
            if (prev == null) continue;
            IEnergyTile prevIoTile = prev.getMainTile();
            boolean found = false;
            Iterator<GridChange> it = pendingChanges.iterator();
            while (it.hasNext()) {
                GridChange change = it.next();
                if (change.type == GridChange.Type.REMOVAL && change.ioTile == prevIoTile) {
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "Expediting pending removal of %s due to addition conflict.", Util.toString(change.ioTile, (IBlockAccess)enet.getWorld(), change.pos));
                    }
                    found = true;
                    it.remove();
                    ChangeHandler.applyRemoval(enet, change.ioTile, change.pos);
                    assert (!enet.registeredTiles.containsKey(subPos));
                    break;
                }
                if (change.type != GridChange.Type.ADDITION || change.ioTile != prevIoTile) continue;
                break;
            }
            if (found) continue;
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s, sub tile %s addition is conflicting with a previous registration at the same location: %s.", Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos), Util.toString(subTile, (IBlockAccess)enet.getWorld(), subPos), prevIoTile);
            }
            return;
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Adding tile %s.", Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos));
        }
        Tile tile = new Tile(enet, ioTile, subTiles);
        enet.registeredIoTiles.put(ioTile, tile);
        if (ioTile instanceof IEnergySource) {
            enet.sources.add(tile);
        }
        for (IEnergyTile subTile : subTiles) {
            BlockPos subPos = EnergyNet.instance.getPos(subTile);
            enet.registeredTiles.put(subPos, tile);
            enet.addPositionToNotify(subPos);
        }
        ChangeHandler.addTileToGrids(enet, tile);
        for (IEnergyNetEventReceiver receiver : EnergyNetGlobal.getEventReceivers()) {
            receiver.onAdd(ioTile);
        }
    }

    /*
     * Could not resolve type clashes
     */
    private static void addTileToGrids(EnergyNetLocal enet, Tile tile) {
        ArrayList<Node> extraNodes = new ArrayList<Node>();
        IEnergyTile ioTile = tile.getMainTile();
        block4: for (Node node : tile.nodes) {
            Grid grid;
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "Adding node %s.", node);
            }
            ArrayList<Node> neighbors = new ArrayList<Node>();
            for (Object subTile : tile.subTiles) {
                for (EnumFacing dir : EnumFacing.field_82609_l) {
                    BlockPos coords = EnergyNet.instance.getPos((IEnergyTile)subTile).func_177972_a(dir);
                    Tile neighborTile = enet.registeredTiles.get(coords);
                    if (neighborTile == null || neighborTile == node.tile) continue;
                    for (Node neighbor : neighborTile.nodes) {
                        if (neighbor.isExtraNode()) continue;
                        IEnergyTile neighborIoTile = neighbor.tile.getMainTile();
                        boolean canEmit = false;
                        if ((node.nodeType == NodeType.Source || node.nodeType == NodeType.Conductor) && neighbor.nodeType != NodeType.Source) {
                            IEnergyEmitter emitter = (IEnergyEmitter)(subTile instanceof IEnergyEmitter ? subTile : ioTile);
                            IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
                            IEnergyAcceptor acceptor = (IEnergyAcceptor)(neighborSubTe instanceof IEnergyAcceptor ? neighborSubTe : neighborIoTile);
                            canEmit = emitter.emitsEnergyTo((IEnergyAcceptor)neighborIoTile, dir) && acceptor.acceptsEnergyFrom((IEnergyEmitter)ioTile, dir.func_176734_d());
                        }
                        boolean canAccept = false;
                        if (!(canEmit || node.nodeType != NodeType.Sink && node.nodeType != NodeType.Conductor || neighbor.nodeType == NodeType.Sink)) {
                            IEnergyAcceptor acceptor = (IEnergyAcceptor)(subTile instanceof IEnergyAcceptor ? subTile : ioTile);
                            IEnergyTile neighborSubTe = neighborTile.getSubTileAt(coords);
                            IEnergyEmitter emitter = (IEnergyEmitter)(neighborSubTe instanceof IEnergyEmitter ? neighborSubTe : neighborIoTile);
                            boolean bl = canAccept = acceptor.acceptsEnergyFrom((IEnergyEmitter)neighborIoTile, dir) && emitter.emitsEnergyTo((IEnergyAcceptor)ioTile, dir.func_176734_d());
                        }
                        if (!canEmit && !canAccept) continue;
                        neighbors.add(neighbor);
                    }
                }
            }
            if (neighbors.isEmpty()) {
                if (EnergyNetSettings.logGridUpdatesVerbose) {
                    IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s.", node);
                }
                grid = new Grid(enet);
                grid.add(node, neighbors);
                continue;
            }
            switch (node.nodeType) {
                case Conductor: {
                    Node neighbor2;
                    Object subTile;
                    grid = null;
                    subTile = neighbors.iterator();
                    while (subTile.hasNext()) {
                        Node neighbor = (Node)subTile.next();
                        if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) continue;
                        if (EnergyNetSettings.logGridUpdatesVerbose) {
                            IC2.log.debug(LogCategory.EnergyNet, "Using %s for %s with neighbors %s.", neighbor.getGrid(), node, neighbors);
                        }
                        grid = neighbor.getGrid();
                        break;
                    }
                    if (grid == null) {
                        if (EnergyNetSettings.logGridUpdatesVerbose) {
                            IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s with neighbors %s.", node, neighbors);
                        }
                        grid = new Grid(enet);
                    }
                    HashMap neighborReplacements = new HashMap();
                    ListIterator<Node> it = neighbors.listIterator();
                    while (it.hasNext()) {
                        Node neighbor = (Node)it.next();
                        if (neighbor.getGrid() == grid) continue;
                        if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
                            boolean found = false;
                            for (int i = 0; i < it.previousIndex(); ++i) {
                                neighbor2 = (Node)neighbors.get(i);
                                if (neighbor2.tile != neighbor.tile || neighbor2.nodeType != neighbor.nodeType || neighbor2.getGrid() != grid) continue;
                                if (EnergyNetSettings.logGridUpdatesVerbose) {
                                    IC2.log.debug(LogCategory.EnergyNet, "Using neighbor node %s instead of %s.", neighbor2, neighbors);
                                }
                                found = true;
                                it.set(neighbor2);
                                break;
                            }
                            if (found) continue;
                            if (EnergyNetSettings.logGridUpdatesVerbose) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", neighbor);
                            }
                            neighbor = new Node(enet.allocateNodeId(), neighbor.tile, neighbor.nodeType);
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
                        Node neighbor = (Node)it.next();
                        Node replacement = (Node)neighborReplacements.get(neighbor);
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
                    Node neighbor2;
                    ArrayList neighborGroups = new ArrayList();
                    for (Node neighbor : neighbors) {
                        boolean found = false;
                        if (node.nodeType == NodeType.Conductor) {
                            for (List nodeList : neighborGroups) {
                                neighbor2 = (Node)nodeList.get(0);
                                if (neighbor2.nodeType != NodeType.Conductor || neighbor2.getGrid() != neighbor.getGrid()) continue;
                                nodeList.add(neighbor);
                                found = true;
                                break;
                            }
                        }
                        if (found) continue;
                        ArrayList<Node> nodeList = new ArrayList<Node>();
                        nodeList.add(neighbor);
                        neighborGroups.add(nodeList);
                    }
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "Neighbor groups detected for %s: %s.", node, neighborGroups);
                    }
                    assert (!neighborGroups.isEmpty());
                    for (int i = 0; i < neighborGroups.size(); ++i) {
                        Node currentNode;
                        List nodeList = (List)neighborGroups.get(i);
                        Node neighbor = (Node)nodeList.get(0);
                        if (neighbor.nodeType != NodeType.Conductor && !neighbor.links.isEmpty()) {
                            assert (nodeList.size() == 1);
                            if (EnergyNetSettings.logGridUpdatesVerbose) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", neighbor);
                            }
                            neighbor = new Node(enet.allocateNodeId(), neighbor.tile, neighbor.nodeType);
                            neighbor.tile.addExtraNode(neighbor);
                            new Grid(enet).add(neighbor, Collections.emptyList());
                            nodeList.set(0, neighbor);
                            assert (neighbor.getGrid() != null);
                        }
                        if (i == 0) {
                            currentNode = node;
                        } else {
                            if (EnergyNetSettings.logGridUpdatesVerbose) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for %s.", node);
                            }
                            currentNode = new Node(enet.allocateNodeId(), tile, node.nodeType);
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
        for (Node node : extraNodes) {
            tile.addExtraNode(node);
        }
    }

    static void applyRemoval(EnergyNetLocal enet, IEnergyTile ioTile, BlockPos pos) {
        Tile tile = enet.registeredIoTiles.remove(ioTile);
        if (tile == null) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s removal without registration", Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos));
            }
            return;
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Removing tile %s.", Util.toString(ioTile, (IBlockAccess)enet.getWorld(), pos));
        }
        assert (tile.getMainTile() == ioTile);
        if (ioTile instanceof IEnergySource) {
            enet.sources.remove(tile);
        }
        for (IEnergyTile subTile : tile.subTiles) {
            BlockPos subPos = EnergyNet.instance.getPos(subTile);
            enet.registeredTiles.remove(subPos);
            enet.addPositionToNotify(subPos);
        }
        ChangeHandler.removeTileFromGrids(tile);
        for (IEnergyNetEventReceiver receiver : EnergyNetGlobal.getEventReceivers()) {
            receiver.onRemove(ioTile);
        }
    }

    private static void removeTileFromGrids(Tile tile) {
        for (Node node : tile.nodes) {
            node.getGrid().remove(node);
        }
    }
}

