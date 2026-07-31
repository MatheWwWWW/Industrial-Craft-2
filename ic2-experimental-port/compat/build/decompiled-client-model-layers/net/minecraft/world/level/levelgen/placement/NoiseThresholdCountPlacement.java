/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;

public class NoiseThresholdCountPlacement
extends RepeatingPlacement {
    public static final Codec<NoiseThresholdCountPlacement> f_191747_ = RecordCodecBuilder.create(p_191761_ -> p_191761_.group((App)Codec.DOUBLE.fieldOf("noise_level").forGetter(p_191771_ -> p_191771_.f_191748_), (App)Codec.INT.fieldOf("below_noise").forGetter(p_191769_ -> p_191769_.f_191749_), (App)Codec.INT.fieldOf("above_noise").forGetter(p_191763_ -> p_191763_.f_191750_)).apply((Applicative)p_191761_, NoiseThresholdCountPlacement::new));
    private final double f_191748_;
    private final int f_191749_;
    private final int f_191750_;

    private NoiseThresholdCountPlacement(double p_191753_, int p_191754_, int p_191755_) {
        this.f_191748_ = p_191753_;
        this.f_191749_ = p_191754_;
        this.f_191750_ = p_191755_;
    }

    public static NoiseThresholdCountPlacement m_191756_(double p_191757_, int p_191758_, int p_191759_) {
        return new NoiseThresholdCountPlacement(p_191757_, p_191758_, p_191759_);
    }

    @Override
    protected int m_213944_(RandomSource p_226355_, BlockPos p_226356_) {
        double $$2 = Biome.f_47433_.m_75449_((double)p_226356_.m_123341_() / 200.0, (double)p_226356_.m_123343_() / 200.0, false);
        return $$2 < this.f_191748_ ? this.f_191749_ : this.f_191750_;
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191855_;
    }
}

