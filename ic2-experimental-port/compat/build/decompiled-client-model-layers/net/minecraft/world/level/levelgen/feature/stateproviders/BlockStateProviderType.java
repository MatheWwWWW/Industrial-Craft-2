/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RotatedBlockProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class BlockStateProviderType<P extends BlockStateProvider> {
    public static final BlockStateProviderType<SimpleStateProvider> f_68752_ = BlockStateProviderType.m_68762_("simple_state_provider", SimpleStateProvider.f_68797_);
    public static final BlockStateProviderType<WeightedStateProvider> f_68753_ = BlockStateProviderType.m_68762_("weighted_state_provider", WeightedStateProvider.f_68808_);
    public static final BlockStateProviderType<NoiseThresholdProvider> f_191386_ = BlockStateProviderType.m_68762_("noise_threshold_provider", NoiseThresholdProvider.f_191463_);
    public static final BlockStateProviderType<NoiseProvider> f_191387_ = BlockStateProviderType.m_68762_("noise_provider", NoiseProvider.f_191438_);
    public static final BlockStateProviderType<DualNoiseProvider> f_191388_ = BlockStateProviderType.m_68762_("dual_noise_provider", DualNoiseProvider.f_191389_);
    public static final BlockStateProviderType<RotatedBlockProvider> f_68756_ = BlockStateProviderType.m_68762_("rotated_block_provider", RotatedBlockProvider.f_68786_);
    public static final BlockStateProviderType<RandomizedIntStateProvider> f_161554_ = BlockStateProviderType.m_68762_("randomized_int_state_provider", RandomizedIntStateProvider.f_161555_);
    private final Codec<P> f_68757_;

    private static <P extends BlockStateProvider> BlockStateProviderType<P> m_68762_(String p_68763_, Codec<P> p_68764_) {
        return Registry.m_122961_(Registry.f_122856_, p_68763_, new BlockStateProviderType<P>(p_68764_));
    }

    private BlockStateProviderType(Codec<P> p_68760_) {
        this.f_68757_ = p_68760_;
    }

    public Codec<P> m_68761_() {
        return this.f_68757_;
    }
}

