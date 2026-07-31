package ru.mot.ic2exfidelity.advancedsolars;

import ic2.core.block.generator.tileentity.TileEntityBaseGenerator;
import java.util.Collections;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** IC2 Classic MV/HV compact solar panels required by Advanced Solars. */
public abstract class LegacyClassicSolarPanelBlockEntity extends TileEntityBaseGenerator {
    private final int output;
    private int ticker;
    private boolean sunVisible;

    protected LegacyClassicSolarPanelBlockEntity(
            BlockEntityType<? extends LegacyClassicSolarPanelBlockEntity> type,
            BlockPos position,
            BlockState state,
            int output,
            int tier) {
        super(type, position, state, output, tier, output);
        this.output = output;
        this.energy.setDirections(
                Collections.emptySet(),
                EnumSet.complementOf(EnumSet.of(Direction.UP)));
    }

    @Override
    public boolean gainEnergy() {
        if (++ticker % 128 == 0) {
            sunVisible = isSunVisible();
        }
        if (!sunVisible) {
            return false;
        }
        energy.addEnergy(output);
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

    public boolean isSunVisible() {
        Level level = m_58904_();
        if (level == null
                || !level.m_6042_().f_223549_()
                || !level.m_46461_()
                || !level.m_46861_(f_58858_.m_7494_())) {
            return false;
        }
        Biome biome = level.m_204166_(f_58858_.m_7494_()).m_203334_();
        return biome.m_47530_() == Biome.Precipitation.NONE
                || (!level.m_46471_() && !level.m_46470_());
    }

    public int getOutput() {
        return output;
    }

    public int getSourceTier() {
        return energy.getSourceTier();
    }

    public double getCapacity() {
        return energy.getCapacity();
    }

    public java.util.Set<Direction> getOutputDirections() {
        return energy.getSourceDirs();
    }

    public static final class MV extends LegacyClassicSolarPanelBlockEntity {
        public MV(BlockPos position, BlockState state) {
            super(LegacyAdvancedSolarsPrerequisites.MV_SOLAR_PANEL_BLOCK_ENTITY.get(),
                    position, state, 64, 2);
        }
    }

    public static final class HV extends LegacyClassicSolarPanelBlockEntity {
        public HV(BlockPos position, BlockState state) {
            super(LegacyAdvancedSolarsPrerequisites.HV_SOLAR_PANEL_BLOCK_ENTITY.get(),
                    position, state, 512, 3);
        }
    }
}
