/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

class ChangeHandler {
    ChangeHandler() {
    }

    static boolean prepareSync(EnergyNetLocal energyNetLocal, GridChange gridChange) {
        Level level = energyNetLocal.getWorld();
        GridChange.Type type = gridChange.type;
        IEnergyTile iEnergyTile = gridChange.ioTile;
        BlockPos blockPos = gridChange.pos;
        if (EnergyNet.instance.getWorld(iEnergyTile) != level) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s had the wrong world in grid update (%s)", new Object[]{Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos), type});
            }
            return false;
        }
        if (type != GridChange.Type.REMOVAL && !EnergyNet.instance.getPos(iEnergyTile).equals((Object)blockPos)) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s has the wrong position in grid update (%s)", new Object[]{Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos), type});
            }
            return false;
        }
        if (type != GridChange.Type.REMOVAL && !level.m_46749_(blockPos)) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s was unloaded in grid update (%s)", new Object[]{Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos), type});
            }
            return false;
        }
        if (type != GridChange.Type.REMOVAL && iEnergyTile instanceof BlockEntity && ((BlockEntity)iEnergyTile).m_58901_()) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s was invalidated in grid update (%s)", new Object[]{Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos), type});
            }
            return false;
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Considering tile %s for grid update (%s)", new Object[]{Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos), type});
        }
        if (type == GridChange.Type.ADDITION) {
            if (iEnergyTile instanceof IMetaDelegate) {
                gridChange.subTiles = new ArrayList<IEnergyTile>(((IMetaDelegate)iEnergyTile).getSubTiles());
                if (gridChange.subTiles.isEmpty()) {
                    throw new RuntimeException(String.format("Tile %s must return at least 1 sub tile for IMetaDelegate.getSubTiles().", Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos)));
                }
            } else {
                gridChange.subTiles = Arrays.asList(iEnergyTile);
            }
        }
        return true;
    }

    static void applyAddition(EnergyNetLocal energyNetLocal, IEnergyTile iEnergyTile, BlockPos blockPos, List<IEnergyTile> list, Collection<GridChange> collection) {
        Tile tile;
        if (energyNetLocal.registeredIoTiles.containsKey(iEnergyTile)) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s is already registered", Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos));
            }
            return;
        }
        for (IEnergyTile object : list) {
            BlockPos blockPos2 = EnergyNet.instance.getPos(object);
            tile = energyNetLocal.registeredTiles.get(blockPos2);
            if (tile == null) continue;
            IEnergyTile iEnergyTile2 = tile.getMainTile();
            boolean bl = false;
            Iterator<GridChange> iterator = collection.iterator();
            while (iterator.hasNext()) {
                GridChange gridChange = iterator.next();
                if (gridChange.type == GridChange.Type.REMOVAL && gridChange.ioTile == iEnergyTile2) {
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "Expediting pending removal of %s due to addition conflict.", Util.toString(gridChange.ioTile, (BlockGetter)energyNetLocal.getWorld(), gridChange.pos));
                    }
                    bl = true;
                    iterator.remove();
                    ChangeHandler.applyRemoval(energyNetLocal, gridChange.ioTile, gridChange.pos);
                    assert (!energyNetLocal.registeredTiles.containsKey(blockPos2));
                    break;
                }
                if (gridChange.type != GridChange.Type.ADDITION || gridChange.ioTile != iEnergyTile2) continue;
                break;
            }
            if (bl) continue;
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s, sub tile %s addition is conflicting with a previous registration at the same location: %s.", Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos), Util.toString(object, (BlockGetter)energyNetLocal.getWorld(), blockPos2), iEnergyTile2);
            }
            return;
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Adding tile %s.", Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos));
        }
        Tile tile2 = new Tile(energyNetLocal, iEnergyTile, list);
        energyNetLocal.registeredIoTiles.put(iEnergyTile, tile2);
        if (iEnergyTile instanceof IEnergySource) {
            energyNetLocal.sources.add(tile2);
        }
        for (IEnergyTile iEnergyTile3 : list) {
            tile = EnergyNet.instance.getPos(iEnergyTile3);
            energyNetLocal.registeredTiles.put((BlockPos)tile, tile2);
        }
        ChangeHandler.addTileToGrids(energyNetLocal, tile2);
        for (IEnergyNetEventReceiver iEnergyNetEventReceiver : EnergyNetGlobal.getEventReceivers()) {
            iEnergyNetEventReceiver.onAdd(iEnergyTile);
        }
    }

    /*
     * WARNING - void declaration
     */
    private static void addTileToGrids(EnergyNetLocal energyNetLocal, Tile tile) {
        ArrayList<Node> arrayList = new ArrayList<Node>();
        IEnergyTile iEnergyTile = tile.getMainTile();
        for (Node node : tile.nodes) {
            Object object;
            Object object2;
            if (EnergyNetSettings.logGridUpdatesVerbose) {
                IC2.log.debug(LogCategory.EnergyNet, "Adding node %s.", node);
            }
            ArrayList<Node> arrayList2 = new ArrayList<Node>();
            for (IEnergyTile object32 : tile.subTiles) {
                for (Direction direction : Util.ALL_DIRS) {
                    object2 = EnergyNet.instance.getPos(object32).m_121945_(direction);
                    Tile tile2 = energyNetLocal.registeredTiles.get(object2);
                    if (tile2 == null || tile2 == node.tile) continue;
                    for (Node node2 : tile2.nodes) {
                        IEnergyTile iEnergyTile2;
                        IEnergyTile iEnergyTile3;
                        if (node2.isExtraNode()) continue;
                        IEnergyTile iEnergyTile4 = node2.tile.getMainTile();
                        boolean bl = false;
                        if ((node.nodeType == NodeType.Source || node.nodeType == NodeType.Conductor) && node2.nodeType != NodeType.Source) {
                            IEnergyEmitter iEnergyEmitter = (IEnergyEmitter)(object32 instanceof IEnergyEmitter ? object32 : iEnergyTile);
                            iEnergyTile3 = tile2.getSubTileAt((BlockPos)object2);
                            iEnergyTile2 = (IEnergyAcceptor)(iEnergyTile3 instanceof IEnergyAcceptor ? iEnergyTile3 : iEnergyTile4);
                            bl = iEnergyEmitter.emitsEnergyTo((IEnergyAcceptor)iEnergyTile4, direction) && iEnergyTile2.acceptsEnergyFrom((IEnergyEmitter)iEnergyTile, direction.m_122424_());
                        }
                        boolean bl2 = false;
                        if (!(bl || node.nodeType != NodeType.Sink && node.nodeType != NodeType.Conductor || node2.nodeType == NodeType.Sink)) {
                            iEnergyTile3 = (IEnergyAcceptor)(object32 instanceof IEnergyAcceptor ? object32 : iEnergyTile);
                            iEnergyTile2 = tile2.getSubTileAt((BlockPos)object2);
                            IEnergyEmitter iEnergyEmitter = (IEnergyEmitter)(iEnergyTile2 instanceof IEnergyEmitter ? iEnergyTile2 : iEnergyTile4);
                            boolean bl3 = bl2 = iEnergyTile3.acceptsEnergyFrom((IEnergyEmitter)iEnergyTile4, direction) && iEnergyEmitter.emitsEnergyTo((IEnergyAcceptor)iEnergyTile, direction.m_122424_());
                        }
                        if (!bl && !bl2) continue;
                        arrayList2.add(node2);
                    }
                }
            }
            if (arrayList2.isEmpty()) {
                if (EnergyNetSettings.logGridUpdatesVerbose) {
                    IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s.", node);
                }
                object = new Grid(energyNetLocal);
                ((Grid)object).add(node, arrayList2);
                continue;
            }
            switch (node.nodeType) {
                case Conductor: {
                    object = null;
                    for (Node node3 : arrayList2) {
                        if (node3.nodeType != NodeType.Conductor && !node3.links.isEmpty()) continue;
                        if (EnergyNetSettings.logGridUpdatesVerbose) {
                            IC2.log.debug(LogCategory.EnergyNet, "Using %s for %s with neighbors %s.", node3.getGrid(), node, arrayList2);
                        }
                        object = node3.getGrid();
                        break;
                    }
                    if (object == null) {
                        if (EnergyNetSettings.logGridUpdatesVerbose) {
                            IC2.log.debug(LogCategory.EnergyNet, "Creating new grid for %s with neighbors %s.", node, arrayList2);
                        }
                        object = new Grid(energyNetLocal);
                    }
                    HashMap<Node, Node> node8 = new HashMap<Node, Node>();
                    ListIterator<Object> listIterator = arrayList2.listIterator();
                    while (listIterator.hasNext()) {
                        Node node4 = (Node)listIterator.next();
                        if (node4.getGrid() == object) continue;
                        if (node4.nodeType != NodeType.Conductor && !node4.links.isEmpty()) {
                            int n = 0;
                            for (int i = 0; i < listIterator.previousIndex(); ++i) {
                                object2 = (Node)arrayList2.get(i);
                                if (((Node)object2).tile != node4.tile || ((Node)object2).nodeType != node4.nodeType || ((Node)object2).getGrid() != object) continue;
                                if (EnergyNetSettings.logGridUpdatesVerbose) {
                                    IC2.log.debug(LogCategory.EnergyNet, "Using neighbor node %s instead of %s.", object2, arrayList2);
                                }
                                n = 1;
                                listIterator.set(object2);
                                break;
                            }
                            if (n != 0) continue;
                            if (EnergyNetSettings.logGridUpdatesVerbose) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", node4);
                            }
                            node4 = new Node(energyNetLocal.allocateNodeId(), node4.tile, node4.nodeType);
                            node4.tile.addExtraNode(node4);
                            ((Grid)object).add(node4, Collections.emptyList());
                            listIterator.set(node4);
                            assert (node4.getGrid() != null);
                            continue;
                        }
                        ((Grid)object).merge(node4.getGrid(), node8);
                    }
                    ListIterator<Node> listIterator2 = arrayList2.listIterator();
                    while (listIterator2.hasNext()) {
                        Node node5 = (Node)listIterator2.next();
                        Node node6 = (Node)node8.get(node5);
                        if (node6 != null) {
                            node5 = node6;
                            listIterator2.set(node6);
                        }
                        assert (node5.getGrid() == object);
                    }
                    ((Grid)object).add(node, arrayList2);
                    assert (node.getGrid() != null);
                    break;
                }
                case Sink: 
                case Source: {
                    void var8_13;
                    object = new ArrayList();
                    for (Node node7 : arrayList2) {
                        Object object3;
                        int n = 0;
                        if (node.nodeType == NodeType.Conductor) {
                            object3 = object.iterator();
                            while (object3.hasNext()) {
                                List list = (List)object3.next();
                                object2 = (Node)list.get(0);
                                if (((Node)object2).nodeType != NodeType.Conductor || ((Node)object2).getGrid() != node7.getGrid()) continue;
                                list.add(node7);
                                n = 1;
                                break;
                            }
                        }
                        if (n != 0) continue;
                        object3 = new ArrayList();
                        object3.add(node7);
                        object.add(object3);
                    }
                    if (EnergyNetSettings.logGridUpdatesVerbose) {
                        IC2.log.debug(LogCategory.EnergyNet, "Neighbor groups detected for %s: %s.", node, object);
                    }
                    assert (!object.isEmpty());
                    boolean bl = false;
                    while (var8_13 < object.size()) {
                        Node node8;
                        List list = (List)object.get((int)var8_13);
                        Node node9 = (Node)list.get(0);
                        if (node9.nodeType != NodeType.Conductor && !node9.links.isEmpty()) {
                            assert (list.size() == 1);
                            if (EnergyNetSettings.logGridUpdatesVerbose) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for neighbor %s.", node9);
                            }
                            node9 = new Node(energyNetLocal.allocateNodeId(), node9.tile, node9.nodeType);
                            node9.tile.addExtraNode(node9);
                            new Grid(energyNetLocal).add(node9, Collections.emptyList());
                            list.set(0, node9);
                            assert (node9.getGrid() != null);
                        }
                        if (var8_13 == false) {
                            node8 = node;
                        } else {
                            if (EnergyNetSettings.logGridUpdatesVerbose) {
                                IC2.log.debug(LogCategory.EnergyNet, "Creating new extra node for %s.", node);
                            }
                            node8 = new Node(energyNetLocal.allocateNodeId(), tile, node.nodeType);
                            node8.setExtraNode(true);
                            arrayList.add(node8);
                        }
                        node9.getGrid().add(node8, list);
                        assert (node8.getGrid() != null);
                        ++var8_13;
                    }
                    break;
                }
            }
            energyNetLocal.addTileToNotify(iEnergyTile);
            for (Node node10 : arrayList2) {
                energyNetLocal.addTileToNotify(node10.getTile().getMainTile());
            }
        }
        for (Node node : arrayList) {
            tile.addExtraNode(node);
        }
    }

    static void applyRemoval(EnergyNetLocal energyNetLocal, IEnergyTile iEnergyTile, BlockPos blockPos) {
        Tile tile = energyNetLocal.registeredIoTiles.remove(iEnergyTile);
        if (tile == null) {
            if (EnergyNetSettings.logGridUpdateIssues) {
                IC2.log.warn(LogCategory.EnergyNet, "Tile %s removal without registration", Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos));
            }
            return;
        }
        if (EnergyNetSettings.logGridUpdatesVerbose) {
            IC2.log.debug(LogCategory.EnergyNet, "Removing tile %s.", Util.toString(iEnergyTile, (BlockGetter)energyNetLocal.getWorld(), blockPos));
        }
        assert (tile.getMainTile() == iEnergyTile);
        if (iEnergyTile instanceof IEnergySource) {
            energyNetLocal.sources.remove(tile);
        }
        for (IEnergyTile object : tile.subTiles) {
            BlockPos blockPos2 = EnergyNet.instance.getPos(object);
            energyNetLocal.registeredTiles.remove(blockPos2);
        }
        ChangeHandler.removeTileFromGrids(tile);
        energyNetLocal.removeTileToNotify(iEnergyTile);
        for (IEnergyNetEventReceiver iEnergyNetEventReceiver : EnergyNetGlobal.getEventReceivers()) {
            iEnergyNetEventReceiver.onRemove(iEnergyTile);
        }
    }

    private static void removeTileFromGrids(Tile tile) {
        for (Node node : tile.nodes) {
            node.getGrid().remove(node);
        }
    }
}

