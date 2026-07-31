/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.world.item.ItemStack
 *  org.apache.commons.lang3.mutable.MutableDouble
 */
package ic2.core.block.machines.logic.planner.newLogic;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.api.reactor.IReactorPlannerComponent;
import ic2.api.reactor.planner.ISimulatedReactor;
import ic2.api.reactor.planner.SimulatedStack;
import ic2.api.reactor.planner.Tracker;
import ic2.core.IC2;
import ic2.core.block.machines.logic.planner.IReactorProvider;
import ic2.core.block.machines.logic.planner.UserSettings;
import ic2.core.block.machines.logic.planner.newLogic.ReactorPrediction;
import ic2.core.block.machines.logic.planner.newLogic.SimulationResult;
import ic2.core.utils.collection.NBTListWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableDouble;

public class ReactorLogic
implements ISimulatedReactor,
INetworkDataBuffer {
    public IReactorProvider provider;
    public ReactorPrediction prediction = new ReactorPrediction();
    public SimulatedStack[] inventory = new SimulatedStack[54];
    public int totalTicks;
    public int heatCapacity;
    public int startingHeat;
    public int tick;
    public int ticksLeft;
    public int lastMaxHeat;
    public int maxHeat;
    public int heat;
    public float heatEffectModifier = 1.0f;
    public float lastProduction = 0.0f;
    public float production = 0.0f;
    public int lastSteamProduction = 0;
    public int steamProduction = 0;
    public boolean isProducing = false;
    public long totalProducedPower = 0L;
    public long totalWaterConsumed = 0L;
    public long totalSteamProduced = 0L;
    public boolean isExploded;
    public boolean didComponentBreak;
    public int brokenSlot = -1;
    public boolean isValid;
    public long slotsFilled = 0L;
    public boolean isSimulating = false;
    public boolean isBreeding = false;
    public int fuelPulse = 0;
    public int breedingPulse = 0;
    public Tracker averageHeat = new Tracker();

    public ReactorLogic(IReactorProvider provider) {
        this.provider = provider;
    }

    public void tick() {
        this.ticksLeft -= this.isProducing || this.isSimulating ? 1 : 0;
        this.updateTick();
        if (this.heat >= this.maxHeat) {
            this.isExploded = true;
        }
    }

    public void simulate(SimulationResult result) {
        boolean shouldCool;
        this.isProducing = true;
        int highestHeat = 0;
        int heatDuration = 0;
        while (!this.isFinished() && !this.isBroken()) {
            this.tick();
            int heat = this.getTotalHeat(null);
            if (heat > highestHeat) {
                highestHeat = heat;
                heatDuration = 0;
                continue;
            }
            ++heatDuration;
        }
        result.isStable = heatDuration >= 200;
        result.ticksSimulated = this.tick;
        result.totalSimTicks = this.totalTicks;
        this.tick = 0;
        this.ticksLeft = 15000;
        this.isProducing = false;
        this.isSimulating = true;
        int prevHeat = Integer.MAX_VALUE;
        int strikes = 0;
        int startHeat = this.heat;
        MutableDouble average = new MutableDouble();
        boolean bl = shouldCool = !result.isStable;
        while (!this.isFinished() && shouldCool) {
            boolean isCoolEnough = this.heat <= this.startingHeat;
            int totalHeat = this.getTotalHeat(average);
            if (startHeat <= 0 ? average.getValue() <= (this.isSteamReactor() ? 0.11 : 0.0) : this.heat <= 0 || isCoolEnough) break;
            if (totalHeat > prevHeat) {
                if (strikes++ > 5) {
                    break;
                }
            } else {
                prevHeat = totalHeat;
                strikes = 0;
            }
            this.tick();
        }
        this.isSimulating = false;
        result.ticksCooled = this.tick;
        result.isStable = result.isStable & strikes <= 5;
        result.tickRate = this.isSteamReactor() ? 1 : 20;
        result.calculateEff();
    }

    protected int getTotalHeat(MutableDouble average) {
        int total = this.getHeat();
        int size = 0;
        for (int y = 0; y < 6; ++y) {
            for (int x = 0; x < 9; ++x) {
                SimulatedStack stack = this.inventory[y * 9 + x];
                if (stack == null) continue;
                stack.commitState();
                if (!stack.canViewHeat(this, x, y)) continue;
                int heat = stack.getStoredHeat(this, x, y);
                total += heat;
                if (average != null) {
                    average.add((double)heat / (double)stack.getMaxStoredHeat(this, x, y));
                }
                ++size;
            }
        }
        if (average != null) {
            average.setValue(average.getValue() / (double)size);
        }
        this.commitState();
        return total;
    }

    protected void commitState() {
        this.averageHeat.commit();
    }

    protected void updateTick() {
        ++this.tick;
        this.lastMaxHeat = this.maxHeat;
        this.lastProduction = this.production;
        this.lastSteamProduction = this.steamProduction;
        this.production = 0.0f;
        this.steamProduction = 0;
        this.maxHeat = 10000;
        this.heatEffectModifier = 1.0f;
        for (int pass = 0; pass < 2; ++pass) {
            boolean heat = pass == 0;
            boolean damage = (!this.provider.isSteamReactor() || this.tick % 20 == 0) && pass == 1;
            for (int y = 0; y < 6; ++y) {
                for (int x = 0; x < 9; ++x) {
                    SimulatedStack stack = this.inventory[y * 9 + x];
                    if (stack == null) continue;
                    stack.commitState();
                    stack.simulate(this, x, y, heat, damage);
                }
            }
        }
        this.totalProducedPower = (long)((float)this.totalProducedPower + this.production * (float)this.getTickRate());
    }

    protected void resetData() {
        this.ticksLeft = 0;
        this.tick = 0;
        this.maxHeat = 10000;
        this.heat = 0;
        this.heatEffectModifier = 1.0f;
        this.lastProduction = 0.0f;
        this.production = 0.0f;
        this.lastSteamProduction = 0;
        this.steamProduction = 0;
        this.totalProducedPower = 0L;
        this.totalWaterConsumed = 0L;
        this.totalSteamProduced = 0L;
        this.breedingPulse = 0;
        this.fuelPulse = 0;
        this.isBreeding = false;
    }

    public void resetSimulation() {
        this.isProducing = true;
        this.isExploded = false;
        this.didComponentBreak = false;
        this.brokenSlot = -1;
        this.isValid = false;
        this.totalProducedPower = 0L;
        this.totalSteamProduced = 0L;
        this.totalWaterConsumed = 0L;
        this.resetData();
        this.resetComponents();
        this.totalTicks = 0;
        this.heatCapacity = 0;
        this.startingHeat = 0;
    }

    public boolean isFinished() {
        return this.ticksLeft == 0;
    }

    public boolean isBroken() {
        return this.didComponentBreak || this.isExploded;
    }

    public void validateLogic() {
        this.resetSimulation();
        this.isValid = true;
        this.isProducing = true;
        this.prediction.clear();
        int maxTick = 0;
        int maxCapacity = 0;
        for (int i = 0; i < 54; ++i) {
            SimulatedStack stack = this.inventory[i];
            if (stack == null) continue;
            IReactorPlannerComponent.ComponentType type = stack.getComponentType();
            float value = stack.getExplosionInfluence(this);
            if (value >= 0.0f && value < 1.0f) {
                this.prediction.explosionMod *= value;
            } else {
                this.prediction.explosionPower += value;
            }
            int x = i % 9;
            int y = i / 9;
            if (type == IReactorPlannerComponent.ComponentType.FUEL_ROD) {
                maxTick = Math.max(maxTick, stack.getStat(IReactorPlannerComponent.ReactorStat.MAX_COMPONENT_DURABILITY, this, x, y).m_7047_() * (21 - this.getTickRate()));
                this.prediction.heatPerTick += stack.getStat(IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION, this, x, y).m_7047_();
                this.isBreeding = true;
                this.prediction.energyPerTick += stack.getStat(IReactorPlannerComponent.ReactorStat.ENERGY_PRODUCTION, this, x, y).m_7057_();
                this.isBreeding = false;
                this.prediction.totalFuelRodPulses += this.fuelPulse;
                this.prediction.totalBreedingPulses += this.breedingPulse;
                this.resetData();
                this.prediction.totalCellCount += stack.getStat(IReactorPlannerComponent.ReactorStat.ROD_COUNT, this, x, y).m_7047_();
                this.prediction.totalInternalFuelPulses += stack.getStat(IReactorPlannerComponent.ReactorStat.PULSE_COUNT, this, x, y).m_7047_();
                this.resetData();
                continue;
            }
            if (type == IReactorPlannerComponent.ComponentType.HEAT_PACK) {
                int heat = stack.getStat(IReactorPlannerComponent.ReactorStat.HEAT_PRODUCTION, this, x, y).m_7047_();
                this.prediction.heatPerTick += heat;
                this.prediction.heatPackHeatPerTick += heat;
                continue;
            }
            if (type == IReactorPlannerComponent.ComponentType.HEAT_VENT) {
                this.prediction.coolingPerTick += stack.getStat(IReactorPlannerComponent.ReactorStat.SELF_COOLING, this, x, y).m_7047_();
                this.prediction.reactorCoolingPerTick += stack.getStat(IReactorPlannerComponent.ReactorStat.REACTOR_COOLING, this, x, y).m_7047_();
                continue;
            }
            if (type == IReactorPlannerComponent.ComponentType.HEAT_SPREAD) {
                this.prediction.coolingPerTick += stack.getStat(IReactorPlannerComponent.ReactorStat.PART_COOLING, this, x, y).m_7047_();
                this.prediction.reactorCoolingPerTick += stack.getStat(IReactorPlannerComponent.ReactorStat.REACTOR_COOLING, this, x, y).m_7047_();
                continue;
            }
            if (type != IReactorPlannerComponent.ComponentType.PLATING) continue;
            maxCapacity += stack.getStat(IReactorPlannerComponent.ReactorStat.MAX_HEAT_STORAGE, this, x, y).m_7047_();
        }
        this.heatCapacity = this.maxHeat = 10000 + maxCapacity;
        this.resetComponents();
        UserSettings settings = this.provider.getSettings();
        if (settings.maxTicks > 0) {
            maxTick = settings.maxTicks;
        }
        this.totalTicks = this.ticksLeft = maxTick;
        this.heat = this.startingHeat = settings.startingHeat;
        this.updatePrediction(maxTick);
    }

    public void resetComponents() {
        if (this.provider.getComponents() == null) {
            for (int i = 0; i < 54; ++i) {
                SimulatedStack stack = this.inventory[i];
                if (stack == null) continue;
                stack.reset();
            }
            return;
        }
        for (int i = 0; i < 54; ++i) {
            SimulatedStack stack = this.inventory[i];
            if (stack == null) continue;
            stack.reset();
            stack.syncStack(this.provider.getComponents().getStackInSlot(i));
        }
    }

    public void syncComponents() {
        for (int i = 0; i < 54; ++i) {
            SimulatedStack stack = this.inventory[i];
            if (stack == null) continue;
            stack.syncStack(this.provider.getComponents().getStackInSlot(i));
        }
    }

    public void setStackInSlot(int slot, ItemStack stack) {
        SimulatedStack simulatedStack = this.inventory[slot] = stack.m_41720_() instanceof IReactorPlannerComponent ? ((IReactorPlannerComponent)stack.m_41720_()).createSimulationComponent(stack) : null;
        this.slotsFilled = this.inventory[slot] == null ? (this.slotsFilled &= 1L << slot ^ 0xFFFFFFFFFFFFFFFFL) : (this.slotsFilled |= 1L << slot);
    }

    public void updatePrediction(int maxTick) {
        this.prediction.totalHeatProduced = (long)this.prediction.heatPerTick * (long)maxTick;
        this.prediction.totalExplosionPower = this.prediction.explosionPower * (this.heatEffectModifier * this.prediction.explosionMod);
        this.prediction.efficiency = this.prediction.energyPerTick != 0.0f ? (float)this.prediction.totalFuelRodPulses / (float)this.prediction.totalCellCount : 0.0f;
        this.prediction.totalEfficiency = this.prediction.energyPerTick != 0.0f ? (float)(this.prediction.totalFuelRodPulses + this.prediction.totalBreedingPulses) / (float)this.prediction.totalCellCount : 0.0f;
        this.prediction.breeder = this.prediction.totalBreedingPulses > 0;
        this.prediction.totalEnergyProduced = (long)(this.prediction.energyPerTick * (float)maxTick) * (long)this.getTickRate();
        this.prediction.waterPerTick = Math.min((float)this.prediction.heatPerTick / 40.0f, (float)this.prediction.coolingPerTick) * (float)IC2.CONFIG.steamReactorMultiplier.get();
        this.prediction.steamPerTick = this.prediction.waterPerTick * 160.0f;
        this.prediction.totalSteamProduced = (long)(this.prediction.steamPerTick * (float)maxTick);
        this.prediction.totalWaterConsumed = this.prediction.totalSteamProduced / 160L;
    }

    protected int getTickRate() {
        return this.provider.isSteamReactor() ? 1 : 20;
    }

    protected int getReactorSize() {
        return this.provider.getReactorSize() + 3;
    }

    @Override
    public SimulatedStack getItem(int x, int y) {
        return x < 0 || y < 0 || y >= 6 || x >= this.getReactorSize() ? null : this.inventory[y * 9 + x];
    }

    @Override
    public void markBroken(int x, int y) {
        this.didComponentBreak = true;
        this.brokenSlot = y * 9 + x;
    }

    @Override
    public int getHeat() {
        return this.heat;
    }

    @Override
    public void setHeat(int newHeat) {
        this.heat = newHeat;
    }

    @Override
    public void addHeat(int heat) {
        this.heat += heat;
        this.averageHeat.addChange(heat);
    }

    @Override
    public int getMaxHeat() {
        return this.maxHeat;
    }

    @Override
    public void setMaxHeat(int heat) {
        this.maxHeat = heat;
    }

    @Override
    public float getHeatEffectModifier() {
        return this.heatEffectModifier;
    }

    @Override
    public void setHeatEffectModifier(float hem) {
        this.heatEffectModifier = hem;
    }

    @Override
    public void addOutput(float output) {
        this.production += output;
    }

    @Override
    public void addSteam(int amount) {
        this.steamProduction += amount;
        this.totalSteamProduced += (long)amount;
    }

    @Override
    public int consumeWater(int amount) {
        this.totalWaterConsumed += (long)amount;
        return amount;
    }

    @Override
    public boolean isProducingEnergy() {
        return this.isProducing;
    }

    @Override
    public boolean isSteamReactor() {
        return this.provider.isSteamReactor();
    }

    @Override
    public void addBreedingPulse() {
        ++this.breedingPulse;
    }

    @Override
    public void addFuelPulse() {
        ++this.fuelPulse;
    }

    @Override
    public float getEnergyOutput() {
        return this.production * (float)IC2.CONFIG.reactorOutput.get();
    }

    @Override
    public boolean isSimulatingPulses() {
        return this.isBreeding;
    }

    @Override
    public long getGameTime() {
        return this.provider.getGameTime(this.tick);
    }

    public CompoundTag save(CompoundTag data) {
        data.m_128405_("total_ticks", this.totalTicks);
        data.m_128405_("heat_capacity", this.heatCapacity);
        data.m_128405_("start_heat", this.startingHeat);
        data.m_128405_("tick", this.tick);
        data.m_128405_("ticks_left", this.ticksLeft);
        data.m_128405_("max_heat", this.maxHeat);
        data.m_128405_("heat", this.heat);
        data.m_128350_("hef", this.heatEffectModifier);
        data.m_128350_("production", this.production);
        data.m_128405_("steam_production", this.steamProduction);
        data.m_128379_("is_producing", this.isProducing);
        data.m_128356_("total_produced_power", this.totalProducedPower);
        data.m_128356_("total_produced_steam", this.totalProducedPower);
        data.m_128356_("total_consumed_water", this.totalWaterConsumed);
        data.m_128379_("is_exploded", this.isExploded);
        data.m_128379_("did_break", this.didComponentBreak);
        data.m_128405_("broken_slot", this.brokenSlot);
        data.m_128379_("valid", this.isValid);
        data.m_128379_("simulating", this.isSimulating);
        data.m_128365_("prediction", (Tag)this.prediction.save(new CompoundTag()));
        ListTag list = new ListTag();
        for (int i = 0; i < 54; ++i) {
            CompoundTag nbt;
            SimulatedStack stack = this.inventory[i];
            if (stack == null || (nbt = stack.save()).m_128456_()) continue;
            nbt.m_128405_("slot", i);
            list.add((Object)nbt);
        }
        if (list.size() > 0) {
            data.m_128365_("items", (Tag)list);
        }
        return data;
    }

    public void load(CompoundTag data) {
        this.totalTicks = data.m_128451_("total_ticks");
        this.heatCapacity = data.m_128451_("heat_capacity");
        this.startingHeat = data.m_128451_("start_heat");
        this.tick = data.m_128451_("tick");
        this.ticksLeft = data.m_128451_("ticks_left");
        this.lastMaxHeat = this.maxHeat = data.m_128451_("max_heat");
        this.heat = data.m_128451_("heat");
        this.heatEffectModifier = data.m_128457_("hef");
        this.lastProduction = this.production = data.m_128457_("production");
        this.lastSteamProduction = this.steamProduction = data.m_128451_("steam_production");
        this.isProducing = data.m_128471_("is_producing");
        this.totalProducedPower = data.m_128454_("total_produced_power");
        this.totalProducedPower = data.m_128454_("total_produced_steam");
        this.totalWaterConsumed = data.m_128454_("total_consumed_water");
        this.isExploded = data.m_128471_("is_exploded");
        this.didComponentBreak = data.m_128471_("did_break");
        this.brokenSlot = data.m_128451_("broken_slot");
        this.isValid = data.m_128471_("valid");
        this.isSimulating = data.m_128471_("simulating");
        this.prediction.load(data.m_128469_("prediction"));
        for (CompoundTag nbt : NBTListWrapper.wrap(data.m_128437_("items", 10), CompoundTag.class)) {
            SimulatedStack stack = this.inventory[nbt.m_128451_("slot")];
            if (stack == null) continue;
            stack.load(nbt);
        }
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeInt(this.totalTicks);
        buffer.writeInt(this.heatCapacity);
        buffer.writeInt(this.startingHeat);
        buffer.writeInt(this.tick);
        buffer.writeInt(this.ticksLeft);
        buffer.writeInt(this.lastMaxHeat);
        buffer.writeInt(this.heat);
        buffer.writeFloat(this.heatEffectModifier);
        buffer.writeFloat(this.lastProduction);
        buffer.writeInt(this.lastSteamProduction);
        buffer.writeBoolean(this.isProducing);
        buffer.writeLong(this.totalProducedPower);
        buffer.writeLong(this.totalWaterConsumed);
        buffer.writeLong(this.totalSteamProduced);
        buffer.writeBoolean(this.isExploded);
        buffer.writeBoolean(this.didComponentBreak);
        buffer.writeByte((byte)this.brokenSlot);
        buffer.writeBoolean(this.isValid);
        buffer.writeLong(this.slotsFilled);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.totalTicks = buffer.readInt();
        this.heatCapacity = buffer.readInt();
        this.startingHeat = buffer.readInt();
        this.tick = buffer.readInt();
        this.ticksLeft = buffer.readInt();
        this.maxHeat = buffer.readInt();
        this.heat = buffer.readInt();
        this.heatEffectModifier = buffer.readFloat();
        this.production = buffer.readFloat();
        this.steamProduction = buffer.readInt();
        this.isProducing = buffer.readBoolean();
        this.totalProducedPower = buffer.readLong();
        this.totalWaterConsumed = buffer.readLong();
        this.totalSteamProduced = buffer.readLong();
        this.isExploded = buffer.readBoolean();
        this.didComponentBreak = buffer.readBoolean();
        this.brokenSlot = buffer.readByte();
        this.isValid = buffer.readBoolean();
        this.slotsFilled = buffer.readLong();
    }
}

