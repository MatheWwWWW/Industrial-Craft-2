/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class BiomeFilter
extends PlacementFilter {
    private static final BiomeFilter f_191558_ = new BiomeFilter();
    public static Codec<BiomeFilter> f_191557_ = Codec.unit(() -> f_191558_);

    private BiomeFilter() {
    }

    public static BiomeFilter m_191561_() {
        return f_191558_;
    }

    @Override
    protected boolean m_213917_(PlacementContext p_226317_, RandomSource p_226318_, BlockPos p_226319_) {
        PlacedFeature $$3 = p_226317_.m_191832_().orElseThrow(() -> new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome"));
        Holder<Biome> $$4 = p_226317_.m_191831_().m_204166_(p_226319_);
        return p_226317_.m_191833_().m_223131_($$4).m_186658_($$3);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191852_;
    }
}

