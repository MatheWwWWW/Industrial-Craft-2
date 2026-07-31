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

public class NoiseBasedCountPlacement
extends RepeatingPlacement {
    public static final Codec<NoiseBasedCountPlacement> f_191722_ = RecordCodecBuilder.create(p_191736_ -> p_191736_.group((App)Codec.INT.fieldOf("noise_to_count_ratio").forGetter(p_191746_ -> p_191746_.f_191723_), (App)Codec.DOUBLE.fieldOf("noise_factor").forGetter(p_191744_ -> p_191744_.f_191724_), (App)Codec.DOUBLE.fieldOf("noise_offset").orElse((Object)0.0).forGetter(p_191738_ -> p_191738_.f_191725_)).apply((Applicative)p_191736_, NoiseBasedCountPlacement::new));
    private final int f_191723_;
    private final double f_191724_;
    private final double f_191725_;

    private NoiseBasedCountPlacement(int p_191728_, double p_191729_, double p_191730_) {
        this.f_191723_ = p_191728_;
        this.f_191724_ = p_191729_;
        this.f_191725_ = p_191730_;
    }

    public static NoiseBasedCountPlacement m_191731_(int p_191732_, double p_191733_, double p_191734_) {
        return new NoiseBasedCountPlacement(p_191732_, p_191733_, p_191734_);
    }

    @Override
    protected int m_213944_(RandomSource p_226352_, BlockPos p_226353_) {
        double $$2 = Biome.f_47433_.m_75449_((double)p_226353_.m_123341_() / this.f_191724_, (double)p_226353_.m_123343_() / this.f_191724_, false);
        return (int)Math.ceil(($$2 + this.f_191725_) * (double)this.f_191723_);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191854_;
    }
}

