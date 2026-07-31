/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class RarityFilter
extends PlacementFilter {
    public static final Codec<RarityFilter> f_191895_ = ExtraCodecs.f_144629_.fieldOf("chance").xmap(RarityFilter::new, p_191907_ -> p_191907_.f_191896_).codec();
    private final int f_191896_;

    private RarityFilter(int p_191899_) {
        this.f_191896_ = p_191899_;
    }

    public static RarityFilter m_191900_(int p_191901_) {
        return new RarityFilter(p_191901_);
    }

    @Override
    protected boolean m_213917_(PlacementContext p_226397_, RandomSource p_226398_, BlockPos p_226399_) {
        return p_226398_.m_188501_() < 1.0f / (float)this.f_191896_;
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191849_;
    }
}

