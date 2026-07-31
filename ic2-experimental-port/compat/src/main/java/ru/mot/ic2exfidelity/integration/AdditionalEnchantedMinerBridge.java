package ru.mot.ic2exfidelity.integration;

import com.yogpc.qp.QuarryPlus;
import com.yogpc.qp.machines.PowerTile;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.info.ILocatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Adapts Additional Enchanted Miner's IC2 Classic energy sink to the IC2
 * Experimental energy API. The conversion math mirrors the addon's original
 * bridge, including its user-configurable EU-to-FE ratio.
 */
public final class AdditionalEnchantedMinerBridge {
    private static final Map<PowerTile, ExperimentalSink> SINKS = new IdentityHashMap<>();

    private AdditionalEnchantedMinerBridge() {
    }

    public static synchronized void register(PowerTile tile) {
        if (EnergyNet.instance == null || SINKS.containsKey(tile)) {
            return;
        }

        ExperimentalSink sink = new ExperimentalSink(tile);
        EnergyNet.instance.addLocatableTile(sink);
        SINKS.put(tile, sink);
    }

    public static synchronized void unregister(PowerTile tile) {
        ExperimentalSink sink = SINKS.remove(tile);
        if (sink != null && EnergyNet.instance != null) {
            EnergyNet.instance.removeTile(sink);
        }
    }

    private static long conversionRate() {
        if (QuarryPlus.config == null || QuarryPlus.config.powerMap == null) {
            return 1L;
        }

        Long configured = QuarryPlus.config.powerMap.ic2ConversionRate.get();
        return Math.max(1L, configured == null ? 1L : configured);
    }

    private record ExperimentalSink(PowerTile tile) implements IEnergySink, ILocatable {
        @Override
        public Level getWorldObj() {
            return tile.m_58904_();
        }

        @Override
        public BlockPos getPosition() {
            return tile.m_58899_();
        }

        @Override
        public int getSinkTier() {
            return 6;
        }

        @Override
        public double getDemandedEnergy() {
            long missingFe = Math.max(0L, tile.getMaxEnergy() - tile.getEnergy());
            return missingFe / (double) conversionRate();
        }

        @Override
        public double injectEnergy(Direction direction, double amount, double voltage) {
            if (amount <= 0.0D) {
                return 0.0D;
            }

            long rate = conversionRate();
            double requestedEu = Math.min(amount, getDemandedEnergy());
            double requestedFeAsDouble = requestedEu * rate;
            long requestedFe = requestedFeAsDouble >= Long.MAX_VALUE
                ? Long.MAX_VALUE
                : Math.max(0L, (long) Math.floor(requestedFeAsDouble));
            long acceptedFe = tile.addEnergy(requestedFe, false);
            double acceptedEu = acceptedFe / (double) rate;
            return Math.max(0.0D, amount - acceptedEu);
        }

        @Override
        public boolean acceptsEnergyFrom(IEnergyEmitter emitter, Direction direction) {
            return true;
        }
    }
}

