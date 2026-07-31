/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;

public class RotatedBlockProvider
extends BlockStateProvider {
    public static final Codec<RotatedBlockProvider> f_68786_ = BlockState.f_61039_.fieldOf("state").xmap(BlockBehaviour.BlockStateBase::m_60734_, Block::m_49966_).xmap(RotatedBlockProvider::new, p_68793_ -> p_68793_.f_68787_).codec();
    private final Block f_68787_;

    public RotatedBlockProvider(Block p_68790_) {
        this.f_68787_ = p_68790_;
    }

    @Override
    protected BlockStateProviderType<?> m_5923_() {
        return BlockStateProviderType.f_68756_;
    }

    @Override
    public BlockState m_213972_(RandomSource p_225922_, BlockPos p_225923_) {
        Direction.Axis $$2 = Direction.Axis.m_235688_(p_225922_);
        return (BlockState)this.f_68787_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, $$2);
    }
}

