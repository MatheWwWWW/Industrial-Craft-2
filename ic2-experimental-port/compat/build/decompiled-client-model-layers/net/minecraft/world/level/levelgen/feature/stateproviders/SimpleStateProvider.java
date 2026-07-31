/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class SimpleStateProvider
extends BlockStateProvider {
    public static final Codec<SimpleStateProvider> f_68797_ = BlockState.f_61039_.fieldOf("state").xmap(SimpleStateProvider::new, p_68804_ -> p_68804_.f_68798_).codec();
    private final BlockState f_68798_;

    protected SimpleStateProvider(BlockState p_68801_) {
        this.f_68798_ = p_68801_;
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_68752_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225963_, BlockPos p_225964_) {
        return this.f_68798_;
    }
}

