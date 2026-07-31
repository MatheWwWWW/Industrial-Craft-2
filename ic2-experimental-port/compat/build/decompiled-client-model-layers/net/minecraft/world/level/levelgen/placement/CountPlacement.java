/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public class CountPlacement
extends RepeatingPlacement {
    public static final Codec<CountPlacement> f_191623_ = IntProvider.m_146545_(0, 256).fieldOf("count").xmap(CountPlacement::new, p_191633_ -> p_191633_.f_191624_).codec();
    private final IntProvider f_191624_;

    private CountPlacement(IntProvider p_191627_) {
        this.f_191624_ = p_191627_;
    }

    public static CountPlacement m_191630_(IntProvider p_191631_) {
        return new CountPlacement(p_191631_);
    }

    public static CountPlacement m_191628_(int p_191629_) {
        return CountPlacement.m_191630_(ConstantInt.m_146483_(p_191629_));
    }

    @Override
    protected int m_213944_(RandomSource p_226333_, BlockPos p_226334_) {
        return this.f_191624_.m_214085_(p_226333_);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191853_;
    }
}

