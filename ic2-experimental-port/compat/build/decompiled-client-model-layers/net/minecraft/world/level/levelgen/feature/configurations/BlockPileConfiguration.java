/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class BlockPileConfiguration
implements FeatureConfiguration {
    public static final Codec<BlockPileConfiguration> f_67539_ = BlockStateProvider.f_68747_.fieldOf("state_provider").xmap(BlockPileConfiguration::new, p_67545_ -> p_67545_.f_67540_).codec();
    public final BlockStateProvider f_67540_;

    public BlockPileConfiguration(BlockStateProvider p_67543_) {
        this.f_67540_ = p_67543_;
    }
}

