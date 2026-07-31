/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class BlockStateConfiguration
implements FeatureConfiguration {
    public static final Codec<BlockStateConfiguration> f_67546_ = BlockState.f_61039_.fieldOf("state").xmap(BlockStateConfiguration::new, p_67552_ -> p_67552_.f_67547_).codec();
    public final BlockState f_67547_;

    public BlockStateConfiguration(BlockState p_67550_) {
        this.f_67547_ = p_67550_;
    }
}

