package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import ic2.core.block.invslot.InvSlotCharge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** Original Advanced Solars day/night output and four-slot charging behavior. */
public abstract class LegacyAdvancedSolarPanelBlockEntity extends TileEntityBaseGenerator {
    public final InvSlotCharge chargeSlot2;
    public final InvSlotCharge chargeSlot3;
    public final InvSlotCharge chargeSlot4;
    private final int dayProduction;
    private final int lowerProduction;
    private final int maxOutput;
    private int ticker = 127;
    private boolean day = true;

    protected LegacyAdvancedSolarPanelBlockEntity(
            BlockEntityType<? extends LegacyAdvancedSolarPanelBlockEntity> type,
            BlockPos position,
            BlockState state,
            int dayProduction,
            int lowerProduction,
            int tier,
            int capacity,
            int maxOutput) {
        super(type, position, state, dayProduction, tier, capacity);
        this.dayProduction = dayProduction;
        this.lowerProduction = lowerProduction;
        this.maxOutput = maxOutput;
        this.chargeSlot2 = new InvSlotCharge(this, 1);
        this.chargeSlot3 = new InvSlotCharge(this, 1);
        this.chargeSlot4 = new InvSlotCharge(this, 1);
        this.energy.addManagedSlot(chargeSlot2)
                .addManagedSlot(chargeSlot3)
                .addManagedSlot(chargeSlot4);
    }

    @Override
    public boolean gainEnergy() {
        if (++ticker % 64 == 0) {
            day = isSunVisible();
        }
        if (!skyBlockCheck()) {
            return false;
        }
        int generated = isSunVisible() ? dayProduction : lowerProduction;
        if (energy.getFreeEnergy() < generated) {
            return false;
        }
        energy.addEnergy(generated);
        return true;
    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

    public boolean skyBlockCheck() {
        Level level = m_58904_();
        return level != null
                && level.m_6042_().f_223549_()
                && level.m_46861_(f_58858_.m_7494_());
    }

    public boolean isSunVisible() {
        Level level = m_58904_();
        return level != null && isSunVisible(level, f_58858_.m_7494_());
    }

    public static boolean isSunVisible(Level level, BlockPos position) {
        if (!level.m_46461_()) {
            return false;
        }
        Biome biome = level.m_204166_(position).m_203334_();
        return biome.m_47530_() == Biome.Precipitation.NONE
                || (!level.m_46471_() && !level.m_46470_());
    }

    public int getDayProduction() {
        return dayProduction;
    }

    public int getLowerProduction() {
        return lowerProduction;
    }

    public int getMaxOutput() {
        return maxOutput;
    }

    public int getChargeSlotCount() {
        return 4;
    }

    public double getCapacity() {
        return energy.getCapacity();
    }

    public double getStoredEnergy() {
        return energy.getEnergy();
    }

    public int getSourceTier() {
        return energy.getSourceTier();
    }

    public boolean isDayCached() {
        return day;
    }

    public static final class Advanced extends LegacyAdvancedSolarPanelBlockEntity {
        public Advanced(BlockPos position, BlockState state) {
            super(LegacyAdvancedSolarsContent.ADVANCED_SOLAR_BLOCK_ENTITY.get(),
                    position, state, 16, 2, 1, 32_000, 32);
        }
    }

    public static final class Hybrid extends LegacyAdvancedSolarPanelBlockEntity {
        public Hybrid(BlockPos position, BlockState state) {
            super(LegacyAdvancedSolarsContent.HYBRID_SOLAR_BLOCK_ENTITY.get(),
                    position, state, 128, 16, 2, 100_000, 128);
        }
    }

    public static final class UltimateHybrid extends LegacyAdvancedSolarPanelBlockEntity {
        public UltimateHybrid(BlockPos position, BlockState state) {
            super(LegacyAdvancedSolarsContent.ULTIMATE_HYBRID_SOLAR_BLOCK_ENTITY.get(),
                    position, state, 1_024, 128, 4, 1_000_000, 2_048);
        }
    }
}
