/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperStairBlock
extends StairBlock
implements WeatheringCopper {
    private final WeatheringCopper.WeatherState f_154949_;

    public WeatheringCopperStairBlock(WeatheringCopper.WeatherState p_154951_, BlockState p_154952_, BlockBehaviour.Properties p_154953_) {
        super(p_154952_, p_154953_);
        this.f_154949_ = p_154951_;
    }

    @Override
    public void m_213898_(BlockState p_222675_, ServerLevel p_222676_, BlockPos p_222677_, RandomSource p_222678_) {
        this.m_220947_(p_222675_, p_222676_, p_222677_, p_222678_);
    }

    @Override
    public boolean m_6724_(BlockState p_154961_) {
        return WeatheringCopper.m_154904_(p_154961_.m_60734_()).isPresent();
    }

    @Override
    public WeatheringCopper.WeatherState m_142297_() {
        return this.f_154949_;
    }

    @Override
    public /* synthetic */ Enum m_142297_() {
        return this.m_142297_();
    }
}

