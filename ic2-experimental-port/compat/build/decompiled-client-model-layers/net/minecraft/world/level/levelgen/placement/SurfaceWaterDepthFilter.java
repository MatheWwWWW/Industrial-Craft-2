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

public class SurfaceWaterDepthFilter
extends PlacementFilter {
    public static final Codec<SurfaceWaterDepthFilter> f_191945_ = RecordCodecBuilder.create(p_191953_ -> p_191953_.group((App)Codec.INT.fieldOf("max_water_depth").forGetter(p_191959_ -> p_191959_.f_191946_)).apply((Applicative)p_191953_, SurfaceWaterDepthFilter::new));
    private final int f_191946_;

    private SurfaceWaterDepthFilter(int p_191949_) {
        this.f_191946_ = p_191949_;
    }

    public static SurfaceWaterDepthFilter m_191950_(int p_191951_) {
        return new SurfaceWaterDepthFilter(p_191951_);
    }

    @Override
    protected boolean m_213917_(PlacementContext p_226411_, RandomSource p_226412_, BlockPos p_226413_) {
        int $$3 = p_226411_.m_191824_(Heightmap.Types.OCEAN_FLOOR, p_226413_.m_123341_(), p_226413_.m_123343_());
        int $$4 = p_226411_.m_191824_(Heightmap.Types.WORLD_SURFACE, p_226413_.m_123341_(), p_226413_.m_123343_());
        return $$4 - $$3 <= this.f_191946_;
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191851_;
    }
}

