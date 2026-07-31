/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.tileentity.TileEntity
 *  net.minecraft.util.DamageSource
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.IBlockAccess
 *  net.minecraft.world.World
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
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.IC2DamageSource;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableDouble;

public class EnergyCalculatorLeg
implements IEnergyCalculator {
    @Override
    public void handleGridChange(Grid grid) {
        long startTime = 0L;
        GridData data = EnergyCalculatorLeg.getData(grid);
        EnergyCalculatorLeg.updateCache(grid, data);
    }

    @Override
    public boolean runSyncStep(EnergyNetLocal enet) {
        boolean foundAny = false;
        for (Tile tile : enet.getSources()) {
            IEnergySource source = (IEnergySource)tile.getMainTile();
            int packets = 1;
            if (!tile.isDisabled()) {
                IMultiEnergySource multiSource;
                double d;
                double amount = source.getOfferedEnergy();
                if (!(!(d > 0.0) || source instanceof IMultiEnergySource && (multiSource = (IMultiEnergySource)source).sendMultipleEnergyPackets() && (packets = multiSource.getMultipleEnergyPacketAmount()) <= 0)) {
                    int tier = source.getSourceTier();
                    if (tier < 0) {
                        if (EnergyNetSettings.logGridCalculationIssues) {
                            IC2.log.warn(LogCategory.EnergyNet, "Tile %s reported an invalid tier (%d).", Util.toString(source, (IBlockAccess)enet.getWorld(), EnergyNet.instance.getPos(source)), tier);
                        }
                        tile.setSourceData(0.0, 0);
                        continue;
                    }
                    foundAny = true;
                    double power = EnergyNet.instance.getPowerFromTier(tier);
                    amount = Math.min(amount, power * (double)packets);
                    tile.setSourceData(amount, packets);
                    continue;
                }
            }
            tile.setSourceData(0.0, 0);
        }
        return foundAny;
    }

    @Override
    public boolean runSyncStep(Grid grid) {
        long startTime = 0L;
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
        double in = 0.0;
        double out = 0.0;
        double max = 0.0;
        for (Node node : tile.getNodes()) {
            GridData data = (GridData)node.getGrid().getData();
            if (data == null || !data.active) continue;
            int calcId = data.currentCalcId;
            Collection<EnergyPath> paths = EnergyCalculatorLeg.getPaths(node, data);
            double sum = 0.0;
            for (EnergyPath path : paths) {
                if (path.lastCalcId != calcId) continue;
                sum += path.energySupplied;
                max = Math.max(path.maxPacketConducted, max);
            }
            if (node.getType() == NodeType.Source) {
                out += sum;
                continue;
            }
            if (node.getType() == NodeType.Sink) {
                in += sum;
                continue;
            }
            in += sum;
            out += sum;
        }
        return new NodeStats(in, out, EnergyNet.instance.getTierFromPower(max));
    }

    private static Collection<EnergyPath> getPaths(Node node, GridData data) {
        if (node.getType() == NodeType.Source) {
            List<EnergyPath> ret = data.energySourceToEnergyPathMap.get(node);
            if (ret == null) {
                ret = Collections.emptyList();
            }
            return ret;
        }
        List<EnergyPath> ret = data.pathCache.get(node);
        if (ret != null) {
            return ret;
        }
        ret = new ArrayList<EnergyPath>();
        for (List<EnergyPath> paths : data.energySourceToEnergyPathMap.values()) {
            for (EnergyPath path : paths) {
                if (node.getType() == NodeType.Sink) {
                    if (path.target != node) continue;
                    ret.add(path);
                    continue;
                }
                if (!path.conductors.contains(node)) continue;
                ret.add(path);
            }
        }
        data.pathCache.put(node, ret);
        return ret;
    }

    @Override
    public void dumpNodeInfo(Node node, String prefix, PrintStream console, PrintStream chat) {
        GridData data = EnergyCalculatorLeg.getData(node.getGrid());
        Collection<EnergyPath> paths = EnergyCalculatorLeg.getPaths(node, data);
        switch (node.getType()) {
            case Source: {
                chat.printf("%s%d connected sink nodes%n", prefix, paths.size());
                break;
            }
            case Sink: {
                chat.printf("%s%d connected source nodes%n", prefix, paths.size());
                break;
            }
            case Conductor: {
                chat.printf("%s%d paths across this conductor%n", prefix, paths.size());
            }
        }
        double sum = 0.0;
        double max = 0.0;
        int calcId = data.currentCalcId;
        int pathsToPrint = 8;
        int n = 0;
        for (EnergyPath path : paths) {
            boolean printPathEnergy = false;
            if (n < 8) {
                switch (node.getType()) {
                    case Source: {
                        chat.printf("%s %s", prefix, path.target);
                        break;
                    }
                    case Sink: {
                        chat.printf("%s %s", prefix, path.source);
                        break;
                    }
                    case Conductor: {
                        chat.printf("%s %s -> %s", prefix, path.source, path.target);
                    }
                }
                printPathEnergy = true;
            } else if (n == 8) {
                chat.printf("%s ... (%d more)%n", paths.size() - 8);
            }
            ++n;
            if (path.lastCalcId != calcId) {
                if (!printPathEnergy) continue;
                chat.println(" (idle)");
                continue;
            }
            if (printPathEnergy) {
                chat.printf(" (%.2f EU, max packet %.2f EU)%n", path.energySupplied, path.maxPacketConducted);
            }
            sum += path.energySupplied;
            max = Math.max(path.maxPacketConducted, max);
        }
        chat.printf("%slast tick: %.2f EU, max packet %.2f EU%n", prefix, sum, max);
    }

    private static void updateCache(Grid grid, GridData data) {
        data.active = false;
        data.energySourceToEnergyPathMap.clear();
        data.activeSources.clear();
        data.activeSinks.clear();
        data.pathCache.clear();
        data.currentCalcId = -1;
        Collection<Node> nodes = grid.getNodes();
        if (nodes.size() < 2) {
            return;
        }
        ArrayList<Node> sources = new ArrayList<Node>();
        int sinkCount = 0;
        for (Node node : nodes) {
            if (node.getType() == NodeType.Source) {
                sources.add(node);
                continue;
            }
            if (node.getType() != NodeType.Sink) continue;
            ++sinkCount;
        }
        if (sources.isEmpty() || sinkCount == 0) {
            return;
        }
        IdentityHashMap<Node, Node> path = new IdentityHashMap<Node, Node>();
        final IdentityHashMap<Node, Double> lossMap = new IdentityHashMap<Node, Double>();
        AbstractCollection queue = sources.size() <= 2048 ? new PriorityQueue<Node>(nodes.size(), new Comparator<Node>(){

            @Override
            public int compare(Node a, Node b) {
                return ((Double)lossMap.get(a)).compareTo((Double)lossMap.get(b));
            }
        }) : new ArrayDeque(nodes.size());
        LinkedHashMap<IEnergyTile, EnergyPath> paths = new LinkedHashMap<IEnergyTile, EnergyPath>();
        for (Node srcNode : sources) {
            Node node;
            lossMap.put(srcNode, 0.0);
            queue.add(srcNode);
            block2: while ((node = (Node)queue.poll()) != null) {
                double loss;
                if (node.getType() == NodeType.Sink) {
                    loss = (Double)lossMap.get(node);
                    IEnergyTile tile = node.getTile().getMainTile();
                    EnergyPath prev = (EnergyPath)paths.get(tile);
                    if (prev != null && prev.loss <= loss) continue;
                    if (EnergyNetSettings.roundLossDown) {
                        loss = Math.floor(loss);
                    }
                    paths.put(tile, new EnergyPath(srcNode, node, EnergyCalculatorLeg.reconstructPath(srcNode, node, path), loss));
                    if (paths.size() != sinkCount) continue;
                    break;
                }
                if (node.getType() != NodeType.Conductor && node != srcNode) continue;
                loss = (Double)lossMap.get(node);
                for (NodeLink link : node.getLinks()) {
                    Node neighbor = link.getNeighbor(node);
                    if (neighbor.getType() == NodeType.Source) {
                        List<EnergyPath> srcPaths = data.energySourceToEnergyPathMap.get(neighbor);
                        if (srcPaths == null) continue;
                        if (srcPaths.isEmpty()) continue block2;
                        loss -= link.getLoss();
                        List<Node> pathToHere = null;
                        for (EnergyPath cPath : srcPaths) {
                            double cLoss = loss + cPath.loss;
                            IEnergyTile tile = cPath.target.getTile().getMainTile();
                            EnergyPath prev = (EnergyPath)paths.get(tile);
                            if (prev != null && prev.loss <= cLoss) continue;
                            if (EnergyNetSettings.roundLossDown) {
                                cLoss = Math.floor(cLoss);
                            }
                            if (pathToHere == null) {
                                pathToHere = EnergyCalculatorLeg.reconstructPath(srcNode, node, path);
                            }
                            ArrayList<Node> conductors = new ArrayList<Node>(pathToHere.size() + cPath.conductors.size());
                            conductors.addAll(pathToHere);
                            conductors.addAll(cPath.conductors);
                            paths.put(tile, new EnergyPath(srcNode, cPath.target, conductors, cLoss));
                        }
                        continue block2;
                    }
                    double newLoss = loss + link.getLoss();
                    Double prevLoss = (Double)lossMap.get(neighbor);
                    if (prevLoss != null && !(prevLoss > newLoss)) continue;
                    if (prevLoss != null) {
                        queue.remove(neighbor);
                    }
                    lossMap.put(neighbor, newLoss);
                    path.put(neighbor, node);
                    queue.add(neighbor);
                }
            }
            if (!paths.isEmpty()) {
                data.energySourceToEnergyPathMap.put(srcNode, new ArrayList(paths.values()));
            }
            lossMap.clear();
            path.clear();
            paths.clear();
            queue.clear();
        }
        if (!data.energySourceToEnergyPathMap.isEmpty()) {
            data.active = true;
        }
    }

    private static List<Node> reconstructPath(Node srcNode, Node dstNode, Map<Node, Node> path) {
        ArrayList<Node> ret = new ArrayList<Node>();
        Node node = dstNode;
        while ((node = path.get(node)) != srcNode) {
            assert (node != null);
            ret.add(node);
        }
        Collections.reverse(ret);
        return ret;
    }

    private static boolean runCalculation(Grid grid, GridData data) {
        int i;
        if (!data.active) {
            return false;
        }
        List<Node> activeSources = data.activeSources;
        Map<Node, MutableDouble> activeSinks = data.activeSinks;
        activeSources.clear();
        activeSinks.clear();
        int calcId = ++data.currentCalcId;
        for (Node node : grid.getNodes()) {
            double d;
            Tile tile = node.getTile();
            if (tile.isDisabled()) continue;
            if (node.getType() == NodeType.Source && data.energySourceToEnergyPathMap.containsKey(node) && tile.getAmount() > 0.0) {
                activeSources.add(node);
                continue;
            }
            if (node.getType() != NodeType.Sink) continue;
            double amount = ((IEnergySink)tile.getMainTile()).getDemandedEnergy();
            if (!(d > 0.0)) continue;
            activeSinks.put(node, new MutableDouble(amount));
        }
        if (activeSources.isEmpty() || activeSinks.isEmpty()) {
            return false;
        }
        World world = grid.getEnergyNet().getWorld();
        Random rand = world.field_73012_v;
        boolean shufflePaths = (world.func_82737_E() & 3L) != 0L;
        int sourcesOffset = activeSources.size() > 1 ? rand.nextInt(activeSources.size()) : 0;
        for (i = sourcesOffset; i < activeSources.size() && !activeSinks.isEmpty(); ++i) {
            EnergyCalculatorLeg.distribute(activeSources.get(i), data, shufflePaths, calcId, rand);
        }
        for (i = 0; i < sourcesOffset && !activeSinks.isEmpty(); ++i) {
            EnergyCalculatorLeg.distribute(activeSources.get(i), data, shufflePaths, calcId, rand);
        }
        if (!data.eventPaths.isEmpty()) {
            EnergyCalculatorLeg.applyCableEffects(data.eventPaths, grid.getEnergyNet().getWorld());
            data.eventPaths.clear();
        }
        return true;
    }

    private static void distribute(Node srcNode, GridData data, boolean shufflePaths, int calcId, Random rand) {
        Tile tile = srcNode.getTile();
        int packetCount = tile.getPacketCount();
        assert (packetCount > 0);
        List<EnergyPath> paths = data.energySourceToEnergyPathMap.get(srcNode);
        int pathOffset = paths.size() > 1 && shufflePaths ? rand.nextInt(paths.size()) : 0;
        double totalOffer = tile.getAmount();
        assert (totalOffer > 0.0);
        double offer = packetCount == 1 ? EnergyCalculatorLeg.distributeSingle(totalOffer, tile, paths, pathOffset, data, calcId) : EnergyCalculatorLeg.distributeMultiple(totalOffer, tile, paths, pathOffset, data, calcId, packetCount);
        double used = totalOffer - Math.max(0.0, offer);
        if (used > 0.0) {
            tile.setAmount(offer);
            ((IEnergySource)tile.getMainTile()).drawEnergy(used);
        }
    }

    private static double distributeSingle(double offer, Tile tile, List<EnergyPath> paths, int pathOffset, GridData data, int calcId) {
        int i;
        for (i = pathOffset; i < paths.size() && !((offer -= EnergyCalculatorLeg.emit(paths.get(i), offer, data, calcId)) <= 0.0); ++i) {
        }
        for (i = 0; i < pathOffset && offer > 0.0; offer -= EnergyCalculatorLeg.emit(paths.get(i), offer, data, calcId), ++i) {
        }
        return offer;
    }

    private static double distributeMultiple(double offer, Tile tile, List<EnergyPath> paths, int pathOffset, GridData data, int calcId, int packetCount) {
        double cOffer;
        double used;
        IEnergySource source = (IEnergySource)tile.getMainTile();
        double power = EnergyNet.instance.getPowerFromTier(source.getSourceTier());
        while (!((used = (cOffer = Math.min(offer, power)) - EnergyCalculatorLeg.distributeSingle(cOffer, tile, paths, pathOffset, data, calcId)) <= 0.0) && --packetCount > 0 && (offer -= used) > 0.0) {
        }
        return offer;
    }

    private static double emit(EnergyPath path, double offer, GridData data, int calcId) {
        double amount;
        Tile targetTile = path.target.getTile();
        if (targetTile.isDisabled()) {
            return 0.0;
        }
        double injectAmount = offer - path.loss;
        if (injectAmount <= 0.0) {
            return 0.0;
        }
        MutableDouble sinkDemand = data.activeSinks.get(path.target);
        if (sinkDemand == null) {
            return 0.0;
        }
        IEnergySink sink = (IEnergySink)targetTile.getMainTile();
        double rejected = sink.injectEnergy(path.targetDirection, amount = Math.min(injectAmount, sinkDemand.doubleValue()), EnergyNet.instance.getTierFromPower(amount));
        if (rejected >= amount) {
            return 0.0;
        }
        double effectiveAmount = Math.max(0.0, amount - rejected + path.loss);
        if (path.lastCalcId != calcId) {
            path.lastCalcId = calcId;
            path.energySupplied = 0.0;
            path.maxPacketConducted = 0.0;
        }
        path.energySupplied += amount - rejected;
        path.maxPacketConducted = Math.max(effectiveAmount, path.maxPacketConducted);
        if (effectiveAmount > path.minEffectEnergy || amount > EnergyNet.instance.getPowerFromTier(sink.getSinkTier())) {
            data.eventPaths.add(path);
        }
        if (amount >= sinkDemand.doubleValue() || rejected > 0.0) {
            data.activeSinks.remove(path.target);
        }
        return effectiveAmount;
    }

    private static void applyCableEffects(Collection<EnergyPath> eventPaths, World world) {
        if (!MainConfig.get().get("misc/enableEnetCableMeltdown").getBool()) {
            return;
        }
        Set<Tile> cablesToRemove = Collections.newSetFromMap(new IdentityHashMap());
        Set<Tile> cablesToStrip = Collections.newSetFromMap(new IdentityHashMap());
        IdentityHashMap<Tile, MutableDouble> sinksToExplode = new IdentityHashMap<Tile, MutableDouble>();
        IdentityHashMap shockEnergyMap = new IdentityHashMap();
        for (EnergyPath energyPath : eventPaths) {
            List nearbyEntities;
            double amount = energyPath.maxPacketConducted;
            boolean conductorOverload = false;
            if (amount > energyPath.minConductorBreakdownEnergy || amount > energyPath.minInsulationBreakdownEnergy) {
                conductorOverload = true;
                for (Node node : energyPath.conductors) {
                    Tile tile = node.getTile();
                    IEnergyConductor conductor = (IEnergyConductor)tile.getMainTile();
                    if (amount > conductor.getConductorBreakdownEnergy()) {
                        cablesToRemove.add(tile);
                        continue;
                    }
                    if (!(amount > conductor.getInsulationBreakdownEnergy())) continue;
                    cablesToStrip.add(tile);
                }
            }
            if (amount > energyPath.minInsulationEnergyAbsorption && !(nearbyEntities = world.func_72872_a(EntityLivingBase.class, new AxisAlignedBB((double)(energyPath.minX - 1), (double)(energyPath.minY - 1), (double)(energyPath.minZ - 1), (double)(energyPath.maxX + 2), (double)(energyPath.maxY + 2), (double)(energyPath.maxZ + 2)))).isEmpty()) {
                IdentityHashMap<EntityLivingBase, MutableDouble> localShockEnergyMap = new IdentityHashMap<EntityLivingBase, MutableDouble>();
                for (Node node : energyPath.conductors) {
                    Tile tile = node.getTile();
                    IEnergyConductor conductor = (IEnergyConductor)tile.getMainTile();
                    if (amount <= conductor.getInsulationEnergyAbsorption()) continue;
                    int shockEnergy = (int)(amount - conductor.getInsulationEnergyAbsorption());
                    for (IEnergyTile subTile : tile.getSubTiles()) {
                        BlockPos pos = EnergyNet.instance.getPos(subTile);
                        for (EntityLivingBase entity : nearbyEntities) {
                            MutableDouble prev = (MutableDouble)localShockEnergyMap.get(entity);
                            if (prev != null && prev.doubleValue() >= (double)shockEnergy || !entity.func_174813_aQ().func_72326_a(new AxisAlignedBB((double)(pos.func_177958_n() - 1), (double)(pos.func_177956_o() - 1), (double)(pos.func_177952_p() - 1), (double)(pos.func_177958_n() + 2), (double)(pos.func_177956_o() + 2), (double)(pos.func_177952_p() + 2)))) continue;
                            if (prev == null) {
                                localShockEnergyMap.put(entity, new MutableDouble((double)shockEnergy));
                                continue;
                            }
                            prev.setValue((double)shockEnergy);
                        }
                    }
                }
                for (Map.Entry entry : localShockEnergyMap.entrySet()) {
                    MutableDouble prev = (MutableDouble)shockEnergyMap.get(entry.getKey());
                    if (prev == null) {
                        shockEnergyMap.put(entry.getKey(), entry.getValue());
                        continue;
                    }
                    prev.add(((MutableDouble)entry.getValue()).doubleValue());
                }
            }
            Tile sinkTile = energyPath.target.getTile();
            IEnergySink sink = (IEnergySink)sinkTile.getMainTile();
            if (conductorOverload || !(amount > EnergyNet.instance.getPowerFromTier(sink.getSinkTier()))) continue;
            MutableDouble prev = (MutableDouble)sinksToExplode.get(sinkTile);
            if (prev == null) {
                sinksToExplode.put(sinkTile, new MutableDouble(amount));
                continue;
            }
            if (!(prev.doubleValue() < amount)) continue;
            prev.setValue(amount);
        }
        cablesToStrip.removeAll(cablesToRemove);
        for (Tile tile : cablesToRemove) {
            ((IEnergyConductor)tile.getMainTile()).removeConductor();
        }
        for (Tile tile : cablesToStrip) {
            ((IEnergyConductor)tile.getMainTile()).removeInsulation();
        }
        for (Map.Entry entry : sinksToExplode.entrySet()) {
            EnergyCalculatorLeg.explodeTile(world, (Tile)entry.getKey(), ((MutableDouble)entry.getValue()).doubleValue());
        }
        for (Map.Entry entry : shockEnergyMap.entrySet()) {
            EntityLivingBase target = (EntityLivingBase)entry.getKey();
            int damage = (int)Math.ceil(((MutableDouble)entry.getValue()).doubleValue() / 64.0);
            if (!target.func_70089_S() || damage <= 0) continue;
            target.func_70097_a((DamageSource)IC2DamageSource.electricity, (float)damage);
        }
    }

    private static GridData getData(Grid grid) {
        GridData ret = (GridData)grid.getData();
        if (ret == null) {
            ret = new GridData();
            grid.setData(ret);
        }
        return ret;
    }

    private static void explodeTile(World world, Tile tile, double maxPower) {
        if (!MainConfig.get().get("misc/enableEnetExplosions").getBool()) {
            return;
        }
        int tier = EnergyNet.instance.getTierFromPower(maxPower);
        for (IEnergyTile subTile : tile.getSubTiles()) {
            IExplosionPowerOverride override;
            IEnergySink mainTile = (IEnergySink)tile.getMainTile();
            BlockPos pos = EnergyNet.instance.getPos(subTile);
            TileEntity realTe = world.func_175625_s(pos);
            if (mainTile instanceof IOverloadHandler && ((IOverloadHandler)((Object)mainTile)).onOverload(tier) || realTe instanceof IOverloadHandler && ((IOverloadHandler)realTe).onOverload(tier)) continue;
            float power = 2.5f;
            if (mainTile instanceof IExplosionPowerOverride) {
                override = (IExplosionPowerOverride)((Object)mainTile);
                if (!override.shouldExplode()) continue;
                power = override.getExplosionPower(tier, power);
            } else if (realTe instanceof IExplosionPowerOverride) {
                override = (IExplosionPowerOverride)realTe;
                if (!override.shouldExplode()) continue;
                power = override.getExplosionPower(tier, power);
            }
            EntityPlayer closestPlayer = world.func_184137_a((double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5, 20.0, false);
            if (closestPlayer != null) {
                IC2.achievements.issueAchievement(closestPlayer, "explodeMachine");
            }
            world.func_175698_g(pos);
            ExplosionIC2 explosion = new ExplosionIC2(world, null, (double)pos.func_177958_n() + 0.5, (double)pos.func_177956_o() + 0.5, (double)pos.func_177952_p() + 0.5, power, 0.75f, ExplosionIC2.Type.Electrical);
            explosion.doExplosion();
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

