/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.PriorityQueue
 *  it.unimi.dsi.fastutil.objects.ObjectList
 *  net.minecraft.nbt.FloatTag
 *  net.minecraft.nbt.IntTag
 *  net.minecraft.nbt.NumericTag
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.item.ItemStack
 */
package ic2.core.item.reactor;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.ISteamReactor;
import ic2.api.reactor.IUsableUranium;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.core.IC2;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.reactor.base.IUraniumRod;
import ic2.core.item.reactor.base.ItemStackCoord;
import ic2.core.item.reactor.base.ReactorComponentBase;
import ic2.core.item.reactor.planner.SimulatedUranium;
import ic2.core.item.reactor.urantypes.BlazeUranium;
import ic2.core.item.reactor.urantypes.CharcoalUranium;
import ic2.core.item.reactor.urantypes.EnderUranium;
import ic2.core.item.reactor.urantypes.NetherStarUranium;
import ic2.core.item.reactor.urantypes.RedstoneUranium;
import ic2.core.item.reactor.urantypes.StandardUranium;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.math.MathUtils;
import it.unimi.dsi.fastutil.PriorityQueue;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

public class ReactorUraniumRod
extends ReactorComponentBase
implements IUsableUranium {
    final IUraniumRod uranium;
    final int rodCount;
    final short componentId;
    public static final IUraniumRod[] TYPES = new IUraniumRod[6];
    RandomSource random = RandomSource.m_216327_();

    public ReactorUraniumRod(String itemName, String textureFolder, String textureName, IUraniumRod uranium, int rodCount, int componentId) {
        super(itemName, textureFolder, textureName, new PropertiesBuilder().maxDamage(uranium.getRodDurability()));
        this.uranium = uranium;
        this.rodCount = rodCount;
        this.componentId = (short)componentId;
    }

    @Override
    public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatCalculation, boolean damageTick) {
        if (!reactor.isProducingEnergy()) {
            return;
        }
        int pulsePerTick = this.uranium.getUraniumPulses();
        List<int[]> affectedArea = this.uranium.getPulseArea();
        if (heatCalculation) {
            List<int[]> heatArea = this.uranium.getHeatArea();
            for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                int pulses = (1 + this.rodCount / 2) * pulsePerTick;
                for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                    int m = affectedArea.size();
                    for (int i = 0; i < m; ++i) {
                        int[] offsets = affectedArea.get(i);
                        pulses += this.pulseNeighbor(reactor, x + offsets[0], y + offsets[1], stack, x, y, true, damageTick);
                    }
                }
                int heat = (int)((float)(MathUtils.sumUp(pulses) * 4) * this.uranium.getPulseHeatModifier());
                PriorityQueue queue = CollectionUtils.createInsertionQueue();
                int m = heatArea.size();
                for (int i = 0; i < m; ++i) {
                    int[] offsets = heatArea.get(i);
                    this.findNeighbor(reactor, x + offsets[0], y + offsets[1], queue);
                }
                while (queue.size() > 0 && heat > 0) {
                    int deltaHeat = heat / queue.size();
                    heat -= deltaHeat;
                    ItemStackCoord target = (ItemStackCoord)queue.dequeue();
                    heat += ((IReactorComponent)target.stack.m_41720_()).storeHeat(target.stack, reactor, target.x, target.y, deltaHeat);
                }
                if (heat <= 0) continue;
                reactor.addHeat(heat);
            }
        } else {
            int pulses = (1 + this.rodCount / 2) * pulsePerTick;
            for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                for (int i = 0; i < pulses; ++i) {
                    this.acceptUraniumPulse(stack, reactor, stack, x, y, x, y, false, damageTick);
                }
                for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                    int m = affectedArea.size();
                    for (int i = 0; i < m; ++i) {
                        int[] offsets = affectedArea.get(i);
                        this.pulseNeighbor(reactor, x + offsets[0], y + offsets[1], stack, x, y, false, damageTick);
                    }
                }
            }
        }
        if (damageTick) {
            int damage = stack.m_41773_();
            if (damage + 1 > stack.m_41776_()) {
                reactor.setStackInReactor(x, y, this.random.m_188503_(3) == 0 ? this.uranium.createNearDepletedRod(this.rodCount) : ItemStack.f_41583_);
            } else {
                stack.m_41721_(damage + 1);
            }
        }
    }

    @Override
    public void addAffectedSlots(int x, int y, BiPredicate<Integer, Integer> slots) {
        slots.test(x, y);
        for (int[] pos : this.uranium.getHeatArea()) {
            slots.test(x + pos[0], y + pos[1]);
        }
        for (int[] pos : this.uranium.getPulseArea()) {
            slots.test(x + pos[0], y + pos[1]);
        }
    }

    @Override
    public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack source, int myX, int myY, int sourceX, int sourceY, boolean heatRun, boolean damageTick) {
        if (reactor instanceof ISteamReactor) {
            return true;
        }
        if (!heatRun) {
            reactor.addOutput(this.uranium.getPulseEU());
        }
        return true;
    }

    @Override
    public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return false;
    }

    @Override
    public int getStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int getMaxStoredHeat(ItemStack stack, IReactor reactor, int x, int y) {
        return 0;
    }

    @Override
    public int storeHeat(ItemStack stack, IReactor reactor, int x, int y, int heatChange) {
        return heatChange;
    }

    @Override
    public float getExplosionInfluence(ItemStack stack, IReactor reactor) {
        return this.uranium.getExplosionModifier() * (float)this.rodCount;
    }

    protected int pulseNeighbor(IReactor reactor, int targetX, int targetY, ItemStack stack, int x, int y, boolean heatRun, boolean damageTick) {
        ItemStack other = reactor.getStackInReactor(targetX, targetY);
        return other.m_41720_() instanceof IReactorComponent && ((IReactorComponent)other.m_41720_()).acceptUraniumPulse(other, reactor, stack, targetX, targetY, x, y, heatRun, damageTick) ? this.uranium.getPulsesForConnection() : 0;
    }

    protected void findNeighbor(IReactor reactor, int x, int y, PriorityQueue<ItemStackCoord> coords) {
        ItemStack stack = reactor.getStackInReactor(x, y);
        if (stack.m_41720_() instanceof IReactorComponent && ((IReactorComponent)stack.m_41720_()).canStoreHeat(stack, reactor, x, y)) {
            coords.enqueue((Object)new ItemStackCoord(stack, x, y));
        }
    }

    @Override
    public ItemStack createDepletedUraniumRod() {
        return this.uranium.createNearDepletedRod(this.rodCount);
    }

    public IUraniumRod getUranium() {
        return this.uranium;
    }

    public int getRodCount() {
        return this.rodCount;
    }

    @Override
    public SimulatedStack createSimulationComponent(ItemStack self) {
        return new SimulatedUranium(this.componentId, this.getMaxDamage(self), this.uranium, this.rodCount);
    }

    @Override
    public short getComponentID(ItemStack stack) {
        return this.componentId;
    }

    @Override
    public IReactorPlannerComponent.ReactorType getSupportedReactor(ItemStack stack) {
        return IReactorPlannerComponent.ReactorType.UNIVERSAL;
    }

    @Override
    public IReactorPlannerComponent.ComponentType getType(ItemStack stack) {
        return IReactorPlannerComponent.ComponentType.FUEL_ROD;
    }

    @Override
    public List<IReactorPlannerComponent.ReactorStat> getStats(ItemStack stack) {
        ObjectList list = CollectionUtils.createList();
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.ROD_COUNT);
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY);
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.PULSE_COUNT);
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION);
        list.add((IReactorPlannerComponent.ReactorStat)IReactorPlannerComponent.ReactorStat.ENERGY_PRODUCTION);
        return list;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack) {
        switch (stat) {
            case ROD_COUNT: {
                return IntTag.m_128679_((int)this.rodCount);
            }
            case MAX_COMPONENT_DURABILITY: {
                return IntTag.m_128679_((int)stack.m_41776_());
            }
            case PULSE_COUNT: {
                return IntTag.m_128679_((int)((1 + this.rodCount / 2) * this.uranium.getUraniumPulses() * this.rodCount));
            }
            case HEAT_PRODUCTION: {
                return IntTag.m_128679_((int)((int)((float)(MathUtils.sumUp((1 + this.rodCount / 2) * this.uranium.getUraniumPulses()) * 4) * this.uranium.getPulseHeatModifier()) * this.rodCount));
            }
            case ENERGY_PRODUCTION: {
                return FloatTag.m_128566_((float)((float)((1 + this.rodCount / 2) * this.uranium.getUraniumPulses()) * this.uranium.getPulseEU() * (float)this.rodCount * (float)IC2.CONFIG.reactorOutput.get()));
            }
        }
        return NULL_VALUE;
    }

    @Override
    public NumericTag getReactorStat(IReactorPlannerComponent.ReactorStat stat, ItemStack stack, IReactor planner, int x, int y) {
        switch (stat) {
            case HEAT_PRODUCTION: {
                int pulsePerTick = this.uranium.getUraniumPulses();
                List<int[]> affectedArea = this.uranium.getPulseArea();
                int heat = 0;
                for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                    int pulses = (1 + this.rodCount / 2) * pulsePerTick;
                    for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                        int m = affectedArea.size();
                        for (int i = 0; i < m; ++i) {
                            int[] offsets = affectedArea.get(i);
                            pulses += this.pulseNeighbor(planner, x + offsets[0], y + offsets[1], stack, x, y, true, false);
                        }
                    }
                    heat += (int)((float)(MathUtils.sumUp(pulses) * 4) * this.uranium.getPulseHeatModifier());
                }
                return IntTag.m_128679_((int)heat);
            }
            case ENERGY_PRODUCTION: {
                int pulsePerTick = this.uranium.getUraniumPulses();
                List<int[]> affectedArea = this.uranium.getPulseArea();
                int pulses = (1 + this.rodCount / 2) * pulsePerTick;
                for (int iteration = 0; iteration < this.rodCount; ++iteration) {
                    for (int i = 0; i < pulses; ++i) {
                        this.acceptUraniumPulse(stack, planner, stack, x, y, x, y, false, false);
                    }
                    for (int pulse = 0; pulse < pulsePerTick; ++pulse) {
                        int m = affectedArea.size();
                        for (int i = 0; i < m; ++i) {
                            int[] offsets = affectedArea.get(i);
                            this.pulseNeighbor(planner, x + offsets[0], y + offsets[1], stack, x, y, false, false);
                        }
                    }
                }
                return FloatTag.m_128566_((float)((float)planner.getEnergyOutput()));
            }
        }
        return super.getReactorStat(stat, stack, planner, x, y);
    }

    static {
        ReactorUraniumRod.TYPES[0] = StandardUranium.INSTANCE;
        ReactorUraniumRod.TYPES[1] = RedstoneUranium.INSTANCE;
        ReactorUraniumRod.TYPES[2] = BlazeUranium.INSTANCE;
        ReactorUraniumRod.TYPES[3] = EnderUranium.INSTANCE;
        ReactorUraniumRod.TYPES[4] = NetherStarUranium.INSTANCE;
        ReactorUraniumRod.TYPES[5] = CharcoalUranium.INSTANCE;
    }
}

