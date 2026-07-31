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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class SurfaceRelativeThresholdFilter
extends PlacementFilter {
    public static final Codec<SurfaceRelativeThresholdFilter> f_191919_ = RecordCodecBuilder.create(p_191929_ -> p_191929_.group((App)Heightmap.Types.f_64274_.fieldOf("heightmap").forGetter(p_191944_ -> p_191944_.f_191920_), (App)Codec.INT.optionalFieldOf("min_inclusive", (Object)Integer.MIN_VALUE).forGetter(p_191942_ -> p_191942_.f_191921_), (App)Codec.INT.optionalFieldOf("max_inclusive", (Object)Integer.MAX_VALUE).forGetter(p_191939_ -> p_191939_.f_191922_)).apply((Applicative)p_191929_, SurfaceRelativeThresholdFilter::new));
    private final Heightmap.Types f_191920_;
    private final int f_191921_;
    private final int f_191922_;

    private SurfaceRelativeThresholdFilter(Heightmap.Types p_191925_, int p_191926_, int p_191927_) {
        this.f_191920_ = p_191925_;
        this.f_191921_ = p_191926_;
        this.f_191922_ = p_191927_;
    }

    public static SurfaceRelativeThresholdFilter m_191930_(Heightmap.Types p_191931_, int p_191932_, int p_191933_) {
        return new SurfaceRelativeThresholdFilter(p_191931_, p_191932_, p_191933_);
    }

    @Override
    protected boolean m_213917_(PlacementContext p_226407_, RandomSource p_226408_, BlockPos p_226409_) {
        long $$3 = p_226407_.m_191824_(this.f_191920_, p_226409_.m_123341_(), p_226409_.m_123343_());
        long $$4 = $$3 + (long)this.f_191921_;
        long $$5 = $$3 + (long)this.f_191922_;
        return $$4 <= (long)p_226409_.m_123342_() && (long)p_226409_.m_123342_() <= $$5;
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191850_;
    }
}

