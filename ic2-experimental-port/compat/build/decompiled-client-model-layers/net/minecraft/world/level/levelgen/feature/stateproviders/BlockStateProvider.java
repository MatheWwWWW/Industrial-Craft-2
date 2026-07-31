/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public abstract class BlockStateProvider {
    public static final Codec<BlockStateProvider> f_68747_ = Registry.f_122856_.m_194605_().dispatch(BlockStateProvider::m_5923_, BlockStateProviderType::m_68761_);

    public static SimpleStateProvider m_191384_(BlockState p_191385_) {
        return new SimpleStateProvider(p_191385_);
    }

    public static SimpleStateProvider m_191382_(Block p_191383_) {
        return new SimpleStateProvider(p_191383_.m_49966_());
    }

    protected abstract BlockStateProviderType<?> m_5923_();

    public abstract BlockState m_213972_(RandomSource var1, BlockPos var2);
}

