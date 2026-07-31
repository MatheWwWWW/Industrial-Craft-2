/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.nbt.CompoundTag
 */
package ic2.core.block.machines.logic.planner.encoder;

import ic2.core.block.machines.logic.planner.encoder.ReactorSetup;
import ic2.core.block.machines.logic.planner.newLogic.ReactorPrediction;
import ic2.core.block.machines.tiles.mv.ReactorPlannerTileEntity;
import net.minecraft.nbt.CompoundTag;

public interface IEncoder {
    public CompoundTag createDecodedData(String var1);

    public void processData(CompoundTag var1, ReactorPlannerTileEntity var2);

    public String createEncodedData(ReactorPlannerTileEntity var1);

    public ReactorSetup createSetup(String var1, boolean var2);

    public String getName();

    public boolean hasBitLimit();

    public int getBitLimit();

    default public ReactorSetup finishSetup(ReactorPrediction prediction, boolean isSteam, int chambers, int startingHeat) {
        if (isSteam) {
            ReactorSetup setup = new ReactorSetup();
            setup.breeder = prediction.breeder;
            setup.isStable = prediction.heatPerTick <= prediction.coolingPerTick;
            setup.chambersRequired = chambers;
            setup.isSteam = true;
            setup.efficiency = prediction.efficiency;
            setup.totalEfficiency = prediction.totalEfficiency;
            setup.duration = (int)Math.max(1.0f, (float)prediction.totalSteamProduced / Math.max(1.0f, prediction.steamPerTick));
            setup.output = prediction.steamPerTick;
            setup.totalOutput = prediction.totalSteamProduced;
            setup.input = prediction.totalWaterConsumed / (long)setup.duration;
            setup.totalInput = prediction.totalWaterConsumed;
            setup.startingHeat = startingHeat;
            return setup;
        }
        ReactorSetup setup = new ReactorSetup();
        setup.breeder = prediction.breeder;
        setup.isStable = prediction.heatPerTick <= prediction.coolingPerTick;
        setup.chambersRequired = chambers;
        setup.isSteam = false;
        setup.efficiency = prediction.efficiency;
        setup.totalEfficiency = prediction.totalEfficiency;
        setup.duration = (int)Math.max(1.0f, (float)prediction.totalEnergyProduced / Math.max(1.0f, prediction.energyPerTick));
        setup.output = prediction.energyPerTick;
        setup.totalOutput = prediction.totalEnergyProduced;
        setup.startingHeat = startingHeat;
        return setup;
    }
}

