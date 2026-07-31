/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeatheringCopperSlabBlock
extends SlabBlock
implements WeatheringCopper {
    private final WeatheringCopper.WeatherState f_154936_;

    public WeatheringCopperSlabBlock(WeatheringCopper.WeatherState p_154938_, BlockBehaviour.Properties p_154939_) {
        super(p_154939_);
        this.f_154936_ = p_154938_;
    }

    @Override
    public void m_213898_(BlockState p_222670_, ServerLevel p_222671_, BlockPos p_222672_, RandomSource p_222673_) {
        this.m_220947_(p_222670_, p_222671_, p_222672_, p_222673_);
    }

    @Override
    public boolean m_6724_(BlockState p_154947_) {
        return WeatheringCopper.m_154904_(p_154947_.m_60734_()).isPresent();
    }

    @Override
    public WeatheringCopper.WeatherState m_142297_() {
        return this.f_154936_;
    }

    @Override
    public /* synthetic */ Enum m_142297_() {
        return this.m_142297_();
    }
}

