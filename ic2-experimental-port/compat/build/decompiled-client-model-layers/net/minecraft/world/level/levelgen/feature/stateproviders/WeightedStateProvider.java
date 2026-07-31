/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class WeightedStateProvider
extends BlockStateProvider {
    public static final Codec<WeightedStateProvider> f_68808_ = SimpleWeightedRandomList.m_146264_(BlockState.f_61039_).comapFlatMap(WeightedStateProvider::m_161597_, p_161600_ -> p_161600_.f_68809_).fieldOf("entries").codec();
    private final SimpleWeightedRandomList<BlockState> f_68809_;

    private static DataResult<WeightedStateProvider> m_161597_(SimpleWeightedRandomList<BlockState> p_161598_) {
        if (p_161598_.m_146337_()) {
            return DataResult.error((String)"WeightedStateProvider with no states");
        }
        return DataResult.success((Object)new WeightedStateProvider(p_161598_));
    }

    public WeightedStateProvider(SimpleWeightedRandomList<BlockState> p_161596_) {
        this.f_68809_ = p_161596_;
    }

    public WeightedStateProvider(SimpleWeightedRandomList.Builder<BlockState> p_161594_) {
        this(p_161594_.m_146270_());
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_68753_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225966_, BlockPos p_225967_) {
        return this.f_68809_.m_216820_(p_225966_).orElseThrow(IllegalStateException::new);
    }
}

