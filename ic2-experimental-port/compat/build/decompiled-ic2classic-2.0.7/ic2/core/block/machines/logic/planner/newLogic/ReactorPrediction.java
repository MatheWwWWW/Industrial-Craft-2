/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.block.machines.logic.planner.newLogic;

import ic2.api.network.buffer.IInputBuffer;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.api.network.buffer.IOutputBuffer;
import ic2.core.inventory.base.INBTSavable;
import net.minecraft.nbt.CompoundTag;

public class ReactorPrediction
implements INetworkDataBuffer,
INBTSavable {
    public long totalHeatProduced;
    public int heatPerTick;
    public int heatPackHeatPerTick;
    public int coolingPerTick;
    public int reactorCoolingPerTick;
    public float efficiency;
    public float totalEfficiency;
    public boolean breeder;
    public int totalFuelRodPulses;
    public int totalInternalFuelPulses;
    public int totalCellCount;
    public int totalBreedingPulses;
    public float totalExplosionPower;
    float explosionPower = 10.0f;
    float explosionMod = 1.0f;
    public long totalEnergyProduced;
    public float energyPerTick;
    public long totalWaterConsumed;
    public long totalSteamProduced;
    public float steamPerTick;
    public float waterPerTick;

    public void clear() {
        this.totalHeatProduced = 0L;
        this.heatPerTick = 0;
        this.heatPackHeatPerTick = 0;
        this.coolingPerTick = 0;
        this.reactorCoolingPerTick = 0;
        this.efficiency = 0.0f;
        this.totalEfficiency = 0.0f;
        this.breeder = false;
        this.totalFuelRodPulses = 0;
        this.totalInternalFuelPulses = 0;
        this.totalCellCount = 0;
        this.totalBreedingPulses = 0;
        this.totalExplosionPower = 0.0f;
        this.explosionPower = 10.0f;
        this.explosionMod = 1.0f;
        this.totalEnergyProduced = 0L;
        this.energyPerTick = 0.0f;
        this.totalWaterConsumed = 0L;
        this.totalSteamProduced = 0L;
        this.steamPerTick = 0.0f;
        this.waterPerTick = 0.0f;
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        nbt.m_128356_("total_heat", this.totalHeatProduced);
        nbt.m_128405_("heat_production", this.heatPerTick);
        nbt.m_128405_("heat_pack", this.heatPackHeatPerTick);
        nbt.m_128405_("cooling", this.coolingPerTick);
        nbt.m_128405_("reactor_cooling", this.reactorCoolingPerTick);
        nbt.m_128350_("explosion_power", this.totalExplosionPower);
        nbt.m_128350_("efficiency", this.efficiency);
        nbt.m_128350_("total_efficiency", this.totalEfficiency);
        nbt.m_128379_("breeder", this.breeder);
        nbt.m_128356_("total_energy", this.totalEnergyProduced);
        nbt.m_128350_("energy_production", this.energyPerTick);
        nbt.m_128356_("total_water", this.totalWaterConsumed);
        nbt.m_128356_("total_steam", this.totalSteamProduced);
        nbt.m_128350_("steam", this.steamPerTick);
        nbt.m_128350_("water", this.waterPerTick);
        return nbt;
    }

    @Override
    public void load(CompoundTag nbt) {
        this.totalHeatProduced = nbt.m_128454_("total_heat");
        this.heatPerTick = nbt.m_128451_("heat_production");
        this.heatPackHeatPerTick = nbt.m_128451_("heat_pack");
        this.coolingPerTick = nbt.m_128451_("cooling");
        this.reactorCoolingPerTick = nbt.m_128451_("reactor_cooling");
        this.totalExplosionPower = nbt.m_128457_("explosion_power");
        this.efficiency = nbt.m_128457_("efficiency");
        this.totalEfficiency = nbt.m_128457_("total_efficiency");
        this.breeder = nbt.m_128471_("breeder");
        this.energyPerTick = nbt.m_128457_("energy_production");
        this.totalEnergyProduced = nbt.m_128454_("total_energy");
        this.totalWaterConsumed = nbt.m_128454_("total_water");
        this.totalSteamProduced = nbt.m_128454_("total_steam");
        this.steamPerTick = nbt.m_128457_("steam");
        this.waterPerTick = nbt.m_128457_("water");
    }

    @Override
    public void write(IOutputBuffer buffer) {
        buffer.writeLong(this.totalHeatProduced);
        buffer.writeInt(this.heatPerTick);
        buffer.writeInt(this.heatPackHeatPerTick);
        buffer.writeInt(this.coolingPerTick);
        buffer.writeInt(this.reactorCoolingPerTick);
        buffer.writeFloat(this.totalExplosionPower);
        buffer.writeFloat(this.efficiency);
        buffer.writeFloat(this.totalEfficiency);
        buffer.writeBoolean(this.breeder);
        buffer.writeLong(this.totalEnergyProduced);
        buffer.writeFloat(this.energyPerTick);
        buffer.writeLong(this.totalWaterConsumed);
        buffer.writeLong(this.totalSteamProduced);
        buffer.writeFloat(this.steamPerTick);
        buffer.writeFloat(this.waterPerTick);
    }

    @Override
    public void read(IInputBuffer buffer) {
        this.totalHeatProduced = buffer.readLong();
        this.heatPerTick = buffer.readInt();
        this.heatPackHeatPerTick = buffer.readInt();
        this.coolingPerTick = buffer.readInt();
        this.reactorCoolingPerTick = buffer.readInt();
        this.totalExplosionPower = buffer.readFloat();
        this.efficiency = buffer.readFloat();
        this.totalEfficiency = buffer.readFloat();
        this.breeder = buffer.readBoolean();
        this.totalEnergyProduced = buffer.readLong();
        this.energyPerTick = buffer.readFloat();
        this.totalWaterConsumed = buffer.readLong();
        this.totalSteamProduced = buffer.readLong();
        this.steamPerTick = buffer.readFloat();
        this.waterPerTick = buffer.readFloat();
    }
}

