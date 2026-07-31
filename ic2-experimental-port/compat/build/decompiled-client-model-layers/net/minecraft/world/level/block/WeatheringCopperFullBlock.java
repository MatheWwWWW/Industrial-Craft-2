/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperFullBlock
extends Block
implements WeatheringCopper {
    private final WeatheringCopper.WeatherState f_154923_;

    public WeatheringCopperFullBlock(WeatheringCopper.WeatherState p_154925_, BlockBehaviour.Properties p_154926_) {
        super(p_154926_);
        this.f_154923_ = p_154925_;
    }

    @Override
    public void m_213898_(BlockState p_222665_, ServerLevel p_222666_, BlockPos p_222667_, RandomSource p_222668_) {
        this.m_220947_(p_222665_, p_222666_, p_222667_, p_222668_);
    }

    @Override
    public boolean m_6724_(BlockState p_154935_) {
        return WeatheringCopper.m_154904_(p_154935_.m_60734_()).isPresent();
    }

    @Override
    public WeatheringCopper.WeatherState m_142297_() {
        return this.f_154923_;
    }

    @Override
    public /* synthetic */ Enum m_142297_() {
        return this.m_142297_();
    }
}

