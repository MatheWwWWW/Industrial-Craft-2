/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.core.BlockPos
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.damagesource.DamageSource
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.phys.AABB
 *  org.apache.commons.lang3.mutable.MutableDouble
 */
package ic2.core.energy.leg;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.NodeStats;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.energy.tile.IExplosionPowerOverride;
import ic2.api.energy.tile.IMultiEnergySource;
import ic2.api.energy.tile.IOverloadHandler;
import ic2.core.IC2;
import ic2.core.Ic2DamageSource;
import ic2.core.Ic2Explosion;
import ic2.core.energy.grid.EnergyNetLocal;
import ic2.core.energy.grid.EnergyNetSettings;
import ic2.core.energy.grid.Grid;
import ic2.core.energy.grid.IEnergyCalculator;
import ic2.core.energy.grid.Node;
import ic2.core.energy.grid.NodeLink;
import ic2.core.energy.grid.NodeType;
import ic2.core.energy.grid.Tile;
import ic2.core.energy.leg.EnergyPath;
import ic2.core.init.MainConfig;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import java.io.PrintStream;
import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.mutable.MutableDouble;

public class EnergyCalculatorLeg
implements IEnergyCalculator {
    @Override
    public void handleGridChange(Grid grid) {
        long l = 0L;
        GridData gridData = EnergyCalculatorLeg.getData(grid);
        EnergyCalculatorLeg.updateCache(grid, gridData);
    }

    @Override
    public boolean runSyncStep(EnergyNetLocal energyNetLocal) {
        boolean bl = false;
        for (Tile tile : energyNetLocal.getSources()) {
            IEnergySource iEnergySource = (IEnergySource)tile.getMainTile();
            int n = 1;
            if (!tile.isDisabled()) {
                IMultiEnergySource iMultiEnergySource;
                double d;
                double d2 = iEnergySource.getOfferedEnergy();
                if (!(!(d > 0.0) || iEnergySource instanceof IMultiEnergySource && (iMultiEnergySource = (IMultiEnergySource)iEnergySource).sendMultipleEnergyPackets() && (n = iMultiEnergySource.getMultipleEnergyPacketAmount()) <= 0)) {
                    int n2 = iEnergySource.getSourceTier();
                    if (n2 < 0) {
                        if (EnergyNetSettings.logGridCalculationIssues) {
                            IC2.log.warn(LogCategory.EnergyNet, "Tile %s reported an invalid tier (%d).", Util.toString(iEnergySource, (BlockGetter)energyNetLocal.getWorld(), EnergyNet.instance.getPos(iEnergySource)), n2);
                        }
                        tile.setSourceData(0.0, 0);
                        continue;
                    }
                    bl = true;
                    double d3 = EnergyNet.instance.getPowerFromTier(n2);
                    d2 = Math.min(d2, d3 * (double)n);
                    tile.setSourceData(d2, n);
                    continue;
                }
            }
            tile.setSourceData(0.0, 0);
        }
        return bl;
    }

    @Override
    public boolean runSyncStep(Grid grid) {
        long l = 0L;
        if (EnergyCalculatorLeg.runCalculation(grid, EnergyCalculatorLeg.getData(grid))) {
            // empty if block
        }
        return false;
    }

    @Override
    public void runAsyncStep(Grid grid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeStats getNodeStats(Tile tile) {
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        for (Node node : tile.getNodes()) {
            GridData gridData = (GridData)node.getGrid().getData();
            if (gridData == null || !gridData.active) continue;
            int n = gridData.currentCalcId;
            Collection<EnergyPath> collection = EnergyCalculatorLeg.getPaths(node, gridData);
            double d4 = 0.0;
            for (EnergyPath energyPath : collection) {
                if (energyPath.lastCalcId != n) continue;
                d4 += energyPath.energySupplied;
                d3 = Math.max(energyPath.maxPacketConducted, d3);
            }
            if (node.getType() == NodeType.Source) {
                d2 += d4;
                continue;
            }
            if (node.getType() == NodeType.Sink) {
                d += d4;
                continue;
            }
            d += d4;
            d2 += d4;
        }
        return new NodeStats(d, d2, EnergyNet.instance.getTierFromPower(d3));
    }

    private static Collection<EnergyPath> getPaths(Node node, GridData gridData) {
        if (node.getType() == NodeType.Source) {
            List<EnergyPath> list = gridData.energySourceToEnergyPathMap.get(node);
            if (list == null) {
                list = Collections.emptyList();
            }
            return list;
        }
        List<EnergyPath> list = gridData.pathCache.get(node);
        if (list != null) {
            return list;
        }
        list = new ArrayList<EnergyPath>();
        for (List<EnergyPath> list2 : gridData.energySourceToEnergyPathMap.values()) {
            for (EnergyPath energyPath : list2) {
                if (node.getType() == NodeType.Sink) {
                    if (energyPath.target != node) continue;
                    list.add(energyPath);
                    continue;
                }
                if (!energyPath.conductors.contains(node)) continue;
                list.add(energyPath);
            }
        }
        gridData.pathCache.put(node, list);
        return list;
    }

    @Override
    public void dumpNodeInfo(Node node, String string, PrintStream printStream, PrintStream printStream2) {
        GridData gridData = EnergyCalculatorLeg.getData(node.getGrid());
        Collection<EnergyPath> collection = EnergyCalculatorLeg.getPaths(node, gridData);
        switch (node.getType()) {
            case Source: {
                printStream2.printf("%s%d connected sink nodes%n", string, collection.size());
                break;
            }
            case Sink: {
                printStream2.printf("%s%d connected source nodes%n", string, collection.size());
                break;
            }
            case Conductor: {
                printStream2.printf("%s%d paths across this conductor%n", string, collection.size());
            }
        }
        double d = 0.0;
        double d2 = 0.0;
        int n = gridData.currentCalcId;
        int n2 = 0;
        for (EnergyPath energyPath : collection) {
            boolean bl = false;
            if (n2 < 8) {
                switch (node.getType()) {
                    case Source: {
                        printStream2.printf("%s %s", string, energyPath.target);
                        break;
                    }
                    case Sink: {
                        printStream2.printf("%s %s", string, energyPath.source);
                        break;
                    }
                    case Conductor: {
                        printStream2.printf("%s %s -> %s", string, energyPath.source, energyPath.target);
                    }
                }
                bl = true;
            } else if (n2 == 8) {
                printStream2.printf("%s ... (%d more)%n", collection.size() - 8);
            }
            ++n2;
            if (energyPath.lastCalcId != n) {
                if (!bl) continue;
                printStream2.println(" (idle)");
                continue;
            }
            if (bl) {
                printStream2.printf(" (%.2f EU, max packet %.2f EU)%n", energyPath.energySupplied, energyPath.maxPacketConducted);
            }
            d += energyPath.energySupplied;
            d2 = Math.max(energyPath.maxPacketConducted, d2);
        }
        printStream2.printf("%slast tick: %.2f EU, max packet %.2f EU%n", string, d, d2);
    }

    private static void updateCache(Grid grid, GridData gridData) {
        gridData.active = false;
        gridData.energySourceToEnergyPathMap.clear();
        gridData.activeSources.clear();
        gridData.activeSinks.clear();
        gridData.pathCache.clear();
        gridData.currentCalcId = -1;
        Collection<Node> collection = grid.getNodes();
        if (collection.size() < 2) {
            return;
        }
        ArrayList<Node> arrayList = new ArrayList<Node>();
        int n = 0;
        for (Node object2 : collection) {
            if (object2.getType() == NodeType.Source) {
                arrayList.add(object2);
                continue;
            }
            if (object2.getType() != NodeType.Sink) continue;
            ++n;
        }
        if (arrayList.isEmpty() || n == 0) {
            return;
        }
        IdentityHashMap identityHashMap = new IdentityHashMap();
        final IdentityHashMap<Node, Double> identityHashMap2 = new IdentityHashMap<Node, Double>();
        AbstractCollection abstractCollection = arrayList.size() <= 2048 ? new PriorityQueue<Node>(collection.size(), new Comparator<Node>(){

            @Override
            public int compare(Node node, Node node2) {
                return ((Double)identityHashMap2.get(node)).compareTo((Double)identityHashMap2.get(node2));
            }
        }) : new ArrayDeque(collection.size());
        LinkedHashMap<Object, EnergyPath> linkedHashMap = new LinkedHashMap<Object, EnergyPath>();
        for (Node node : arrayList) {
            Node node2;
            identityHashMap2.put(node, 0.0);
            abstractCollection.add(node);
            block2: while ((node2 = (Node)abstractCollection.poll()) != null) {
                double d;
                if (node2.getType() == NodeType.Sink) {
                    d = (Double)identityHashMap2.get(node2);
                    IEnergyTile iEnergyTile = node2.getTile().getMainTile();
                    EnergyPath energyPath = (EnergyPath)linkedHashMap.get(iEnergyTile);
                    if (energyPath != null && energyPath.loss <= d) continue;
                    if (EnergyNetSettings.roundLossDown) {
                        d = Math.floor(d);
                    }
                    linkedHashMap.put(iEnergyTile, new EnergyPath(node, node2, EnergyCalculatorLeg.reconstructPath(node, node2, identityHashMap), d));
                    if (linkedHashMap.size() != n) continue;
                    break;
                }
                if (node2.getType() != NodeType.Conductor && node2 != node) continue;
                d = (Double)identityHashMap2.get(node2);
                for (NodeLink nodeLink : node2.getLinks()) {
                    Node node3 = nodeLink.getNeighbor(node2);
                    if (node3.getType() == NodeType.Source) {
                        List<EnergyPath> list = gridData.energySourceToEnergyPathMap.get(node3);
                        if (list == null) continue;
                        if (list.isEmpty()) continue block2;
                        d -= nodeLink.getLoss();
                        List<Node> list2 = null;
                        for (EnergyPath energyPath : list) {
                            double d2 = d + energyPath.loss;
                            IEnergyTile iEnergyTile = energyPath.target.getTile().getMainTile();
                            EnergyPath energyPath2 = (EnergyPath)linkedHashMap.get(iEnergyTile);
                            if (energyPath2 != null && energyPath2.loss <= d2) continue;
                            if (EnergyNetSettings.roundLossDown) {
                                d2 = Math.floor(d2);
                            }
                            if (list2 == null) {
                                list2 = EnergyCalculatorLeg.reconstructPath(node, node2, identityHashMap);
                            }
                            ArrayList<Node> arrayList2 = new ArrayList<Node>(list2.size() + energyPath.conductors.size());
                            arrayList2.addAll(list2);
                            arrayList2.addAll(energyPath.conductors);
                            linkedHashMap.put(iEnergyTile, new EnergyPath(node, energyPath.target, arrayList2, d2));
                        }
                        continue block2;
                    }
                    double d3 = d + nodeLink.getLoss();
                    Double d4 = (Double)identityHashMap2.get(node3);
                    if (d4 != null && !(d4 > d3)) continue;
                    if (d4 != null) {
                        abstractCollection.remove(node3);
                    }
                    identityHashMap2.put(node3, d3);
                    identityHashMap.put(node3, node2);
                    abstractCollection.add(node3);
                }
            }
            if (!linkedHashMap.isEmpty()) {
                gridData.energySourceToEnergyPathMap.put(node, new ArrayList(linkedHashMap.values()));
            }
            identityHashMap2.clear();
            identityHashMap.clear();
            linkedHashMap.clear();
            abstractCollection.clear();
        }
        if (!gridData.energySourceToEnergyPathMap.isEmpty()) {
            gridData.active = true;
        }
    }

    private static List<Node> reconstructPath(Node node, Node node2, Map<Node, Node> map) {
        ArrayList<Node> arrayList = new ArrayList<Node>();
        Node node3 = node2;
        while ((node3 = map.get(node3)) != node) {
            assert (node3 != null);
            arrayList.add(node3);
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    private static boolean runCalculation(Grid grid, GridData gridData) {
        int n;
        Node node2;
        if (!gridData.active) {
            return false;
        }
        List<Node> list = gridData.activeSources;
        Map<Node, MutableDouble> map = gridData.activeSinks;
        list.clear();
        map.clear();
        int n2 = ++gridData.currentCalcId;
        for (Node node2 : grid.getNodes()) {
            double d;
            Tile tile = node2.getTile();
            if (tile.isDisabled()) continue;
            if (node2.getType() == NodeType.Source && gridData.energySourceToEnergyPathMap.containsKey(node2) && tile.getAmount() > 0.0) {
                list.add(node2);
                continue;
            }
            if (node2.getType() != NodeType.Sink) continue;
            double d2 = ((IEnergySink)tile.getMainTile()).getDemandedEnergy();
            if (!(d > 0.0)) continue;
            map.put(node2, new MutableDouble(d2));
        }
        if (list.isEmpty() || map.isEmpty()) {
            return false;
        }
        Level level = grid.getEnergyNet().getWorld();
        node2 = level.f_46441_;
        boolean bl = (level.m_46467_() & 3L) != 0L;
        int n3 = list.size() > 1 ? node2.m_188503_(list.size()) : 0;
        for (n = n3; n < list.size() && !map.isEmpty(); ++n) {
            EnergyCalculatorLeg.distribute(list.get(n), gridData, bl, n2, (RandomSource)node2);
        }
        for (n = 0; n < n3 && !map.isEmpty(); ++n) {
            EnergyCalculatorLeg.distribute(list.get(n), gridData, bl, n2, (RandomSource)node2);
        }
        if (!gridData.eventPaths.isEmpty()) {
            EnergyCalculatorLeg.applyCableEffects(gridData.eventPaths, grid.getEnergyNet().getWorld());
            gridData.eventPaths.clear();
        }
        return true;
    }

    private static void distribute(Node node, GridData gridData, boolean bl, int n, RandomSource randomSource) {
        Tile tile = node.getTile();
        int n2 = tile.getPacketCount();
        assert (n2 > 0);
        List<EnergyPath> list = gridData.energySourceToEnergyPathMap.get(node);
        int n3 = list.size() > 1 && bl ? randomSource.m_188503_(list.size()) : 0;
        double d = tile.getAmount();
        assert (d > 0.0);
        double d2 = n2 == 1 ? EnergyCalculatorLeg.distributeSingle(d, tile, list, n3, gridData, n) : EnergyCalculatorLeg.distributeMultiple(d, tile, list, n3, gridData, n, n2);
        double d3 = d - Math.max(0.0, d2);
        if (d3 > 0.0) {
            tile.setAmount(d2);
            ((IEnergySource)tile.getMainTile()).drawEnergy(d3);
        }
    }

    private static double distributeSingle(double d, Tile tile, List<EnergyPath> list, int n, GridData gridData, int n2) {
        int n3;
        for (n3 = n; n3 < list.size() && !((d -= EnergyCalculatorLeg.emit(list.get(n3), d, gridData, n2)) <= 0.0); ++n3) {
        }
        for (n3 = 0; n3 < n && d > 0.0; d -= EnergyCalculatorLeg.emit(list.get(n3), d, gridData, n2), ++n3) {
        }
        return d;
    }

    private static double distributeMultiple(double d, Tile tile, List<EnergyPath> list, int n, GridData gridData, int n2, int n3) {
        double d2;
        double d3;
        IEnergySource iEnergySource = (IEnergySource)tile.getMainTile();
        double d4 = EnergyNet.instance.getPowerFromTier(iEnergySource.getSourceTier());
        while (!((d3 = (d2 = Math.min(d, d4)) - EnergyCalculatorLeg.distributeSingle(d2, tile, list, n, gridData, n2)) <= 0.0) && --n3 > 0 && (d -= d3) > 0.0) {
        }
        return d;
    }

    private static double emit(EnergyPath energyPath, double d, GridData gridData, int n) {
        double d2;
        Tile tile = energyPath.target.getTile();
        if (tile.isDisabled()) {
            return 0.0;
        }
        double d3 = d - energyPath.loss;
        if (d3 <= 0.0) {
            return 0.0;
        }
        MutableDouble mutableDouble = gridData.activeSinks.get(energyPath.target);
        if (mutableDouble == null) {
            return 0.0;
        }
        IEnergySink iEnergySink = (IEnergySink)tile.getMainTile();
        double d4 = iEnergySink.injectEnergy(energyPath.targetDirection, d2 = Math.min(d3, mutableDouble.doubleValue()), EnergyNet.instance.getTierFromPower(d2));
        if (d4 >= d2) {
            return 0.0;
        }
        double d5 = Math.max(0.0, d2 - d4 + energyPath.loss);
        if (energyPath.lastCalcId != n) {
            energyPath.lastCalcId = n;
            energyPath.energySupplied = 0.0;
            energyPath.maxPacketConducted = 0.0;
        }
        energyPath.energySupplied += d2 - d4;
        energyPath.maxPacketConducted = Math.max(d5, energyPath.maxPacketConducted);
        if (d5 > energyPath.minEffectEnergy || d2 > EnergyNet.instance.getPowerFromTier(iEnergySink.getSinkTier())) {
            gridData.eventPaths.add(energyPath);
        }
        if (d2 >= mutableDouble.doubleValue() || d4 > 0.0) {
            gridData.activeSinks.remove(energyPath.target);
        }
        return d5;
    }

    private static void applyCableEffects(Collection<EnergyPath> collection, Level level) {
        if (!MainConfig.get().get("misc/enableEnetCableMeltdown").getBool()) {
            return;
        }
        Set<Object> set = Collections.newSetFromMap(new IdentityHashMap());
        Set<Object> set2 = Collections.newSetFromMap(new IdentityHashMap());
        IdentityHashMap<Object, MutableDouble> identityHashMap = new IdentityHashMap<Object, MutableDouble>();
        IdentityHashMap<LivingEntity, MutableDouble> identityHashMap2 = new IdentityHashMap<LivingEntity, MutableDouble>();
        for (EnergyPath object : collection) {
            Object object2;
            Object object3;
            Object object4;
            double d = object.maxPacketConducted;
            boolean bl = false;
            if (d > object.minConductorBreakdownEnergy || d > object.minInsulationBreakdownEnergy) {
                bl = true;
                object4 = object.conductors.iterator();
                while (object4.hasNext()) {
                    object3 = object4.next();
                    object2 = ((Node)object3).getTile();
                    IEnergyConductor iEnergyConductor = (IEnergyConductor)((Tile)object2).getMainTile();
                    if (d > iEnergyConductor.getConductorBreakdownEnergy()) {
                        set.add(object2);
                        continue;
                    }
                    if (!(d > iEnergyConductor.getInsulationBreakdownEnergy())) continue;
                    set2.add(object2);
                }
            }
            if (d > object.minInsulationEnergyAbsorption && !(object4 = level.m_6443_(LivingEntity.class, new AABB((double)(object.minX - 1), (double)(object.minY - 1), (double)(object.minZ - 1), (double)(object.maxX + 2), (double)(object.maxY + 2), (double)(object.maxZ + 2)), null)).isEmpty()) {
                Tile tile;
                object3 = new IdentityHashMap<LivingEntity, MutableDouble>();
                for (Node node : object.conductors) {
                    tile = node.getTile();
                    IEnergyConductor iEnergyConductor = (IEnergyConductor)tile.getMainTile();
                    if (d <= iEnergyConductor.getInsulationEnergyAbsorption()) continue;
                    int n = (int)(d - iEnergyConductor.getInsulationEnergyAbsorption());
                    for (IEnergyTile iEnergyTile : tile.getSubTiles()) {
                        BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
                        Iterator iterator = object4.iterator();
                        while (iterator.hasNext()) {
                            LivingEntity livingEntity = (LivingEntity)iterator.next();
                            MutableDouble mutableDouble = (MutableDouble)object3.get(livingEntity);
                            if (mutableDouble != null && mutableDouble.doubleValue() >= (double)n || !livingEntity.m_20191_().m_82381_(new AABB((double)(blockPos.m_123341_() - 1), (double)(blockPos.m_123342_() - 1), (double)(blockPos.m_123343_() - 1), (double)(blockPos.m_123341_() + 2), (double)(blockPos.m_123342_() + 2), (double)(blockPos.m_123343_() + 2)))) continue;
                            if (mutableDouble == null) {
                                object3.put(livingEntity, new MutableDouble((double)n));
                                continue;
                            }
                            mutableDouble.setValue((double)n);
                        }
                    }
                }
                for (Map.Entry entry : object3.entrySet()) {
                    tile = (MutableDouble)identityHashMap2.get(entry.getKey());
                    if (tile == null) {
                        identityHashMap2.put((LivingEntity)entry.getKey(), (MutableDouble)entry.getValue());
                        continue;
                    }
                    tile.add(((MutableDouble)entry.getValue()).doubleValue());
                }
            }
            object4 = object.target.getTile();
            object3 = (IEnergySink)((Tile)object4).getMainTile();
            if (bl || !(d > EnergyNet.instance.getPowerFromTier(object3.getSinkTier()))) continue;
            object2 = (MutableDouble)identityHashMap.get(object4);
            if (object2 == null) {
                identityHashMap.put(object4, new MutableDouble(d));
                continue;
            }
            if (!(object2.doubleValue() < d)) continue;
            object2.setValue(d);
        }
        set2.removeAll(set);
        for (Tile tile : set) {
            ((IEnergyConductor)tile.getMainTile()).removeConductor();
        }
        for (Tile tile : set2) {
            ((IEnergyConductor)tile.getMainTile()).removeInsulation();
        }
        for (Map.Entry entry : identityHashMap.entrySet()) {
            EnergyCalculatorLeg.explodeTile(level, (Tile)entry.getKey(), ((MutableDouble)entry.getValue()).doubleValue());
        }
        for (Map.Entry entry : identityHashMap2.entrySet()) {
            LivingEntity livingEntity = (LivingEntity)entry.getKey();
            int n = (int)Math.ceil(((MutableDouble)entry.getValue()).doubleValue() / 64.0);
            if (!livingEntity.m_6084_() || n <= 0) continue;
            livingEntity.m_6469_((DamageSource)Ic2DamageSource.electricity, (float)n);
        }
    }

    private static GridData getData(Grid grid) {
        GridData gridData = (GridData)grid.getData();
        if (gridData == null) {
            gridData = new GridData();
            grid.setData(gridData);
        }
        return gridData;
    }

    private static void explodeTile(Level level, Tile tile, double d) {
        if (!MainConfig.get().get("misc/enableEnetExplosions").getBool()) {
            return;
        }
        int n = EnergyNet.instance.getTierFromPower(d);
        for (IEnergyTile iEnergyTile : tile.getSubTiles()) {
            IExplosionPowerOverride iExplosionPowerOverride;
            IEnergySink iEnergySink = (IEnergySink)tile.getMainTile();
            BlockPos blockPos = EnergyNet.instance.getPos(iEnergyTile);
            BlockEntity blockEntity = level.m_7702_(blockPos);
            if (iEnergySink instanceof IOverloadHandler && ((IOverloadHandler)((Object)iEnergySink)).onOverload(n) || blockEntity instanceof IOverloadHandler && ((IOverloadHandler)blockEntity).onOverload(n)) continue;
            float f = 2.5f;
            if (iEnergySink instanceof IExplosionPowerOverride) {
                iExplosionPowerOverride = (IExplosionPowerOverride)((Object)iEnergySink);
                if (!iExplosionPowerOverride.shouldExplode()) continue;
                f = iExplosionPowerOverride.getExplosionPower(n, f);
            } else if (blockEntity instanceof IExplosionPowerOverride) {
                iExplosionPowerOverride = (IExplosionPowerOverride)blockEntity;
                if (!iExplosionPowerOverride.shouldExplode()) continue;
                f = iExplosionPowerOverride.getExplosionPower(n, f);
            }
            iExplosionPowerOverride = level.m_45924_((double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_() + 0.5, (double)blockPos.m_123343_() + 0.5, 20.0, false);
            level.m_7471_(blockPos, false);
            Ic2Explosion ic2Explosion = new Ic2Explosion(level, null, (double)blockPos.m_123341_() + 0.5, (double)blockPos.m_123342_() + 0.5, (double)blockPos.m_123343_() + 0.5, f, 0.75f, Ic2Explosion.Type.Electrical);
            ic2Explosion.doExplosion();
        }
    }

    private static class GridData {
        boolean active;
        final Map<Node, List<EnergyPath>> energySourceToEnergyPathMap = new IdentityHashMap<Node, List<EnergyPath>>();
        final List<Node> activeSources = new ArrayList<Node>();
        final Map<Node, MutableDouble> activeSinks = new IdentityHashMap<Node, MutableDouble>();
        final Set<EnergyPath> eventPaths = Collections.newSetFromMap(new IdentityHashMap());
        final Map<Node, List<EnergyPath>> pathCache = new IdentityHashMap<Node, List<EnergyPath>>();
        int currentCalcId = -1;

        private GridData() {
        }
    }
}

