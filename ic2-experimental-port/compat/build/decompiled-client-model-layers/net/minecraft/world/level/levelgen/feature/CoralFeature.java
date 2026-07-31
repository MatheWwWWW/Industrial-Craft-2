/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class CoralFeature
extends Feature<NoneFeatureConfiguration> {
    public CoralFeature(Codec<NoneFeatureConfiguration> p_65429_) {
        super(p_65429_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159536_) {
        RandomSource $$1 = p_159536_.m_225041_();
        WorldGenLevel $$2 = p_159536_.m_159774_();
        BlockPos $$3 = p_159536_.m_159777_();
        Optional<Block> $$4 = Registry.f_122824_.m_203431_(BlockTags.f_13051_).flatMap(p_224980_ -> p_224980_.m_213653_($$1)).map(Holder::m_203334_);
        if ($$4.isEmpty()) {
            return false;
        }
        return this.m_214196_($$2, $$1, $$3, $$4.get().m_49966_());
    }

    protected abstract boolean m_214196_(LevelAccessor var1, RandomSource var2, BlockPos var3, BlockState var4);

    protected boolean m_224973_(LevelAccessor p_224974_, RandomSource p_224975_, BlockPos p_224976_, BlockState p_224977_) {
        BlockPos $$4 = p_224976_.m_7494_();
        BlockState $$5 = p_224974_.m_8055_(p_224976_);
        if (!$$5.m_60713_(Blocks.f_49990_) && !$$5.m_204336_(BlockTags.f_13064_) || !p_224974_.m_8055_($$4).m_60713_(Blocks.f_49990_)) {
            return false;
        }
        p_224974_.m_7731_(p_224976_, p_224977_, 3);
        if (p_224975_.m_188501_() < 0.25f) {
            Registry.f_122824_.m_203431_(BlockTags.f_13064_).flatMap(p_224972_ -> p_224972_.m_213653_(p_224975_)).map(Holder::m_203334_).ifPresent(p_204720_ -> p_224974_.m_7731_($$4, p_204720_.m_49966_(), 2));
        } else if (p_224975_.m_188501_() < 0.05f) {
            p_224974_.m_7731_($$4, (BlockState)Blocks.f_50567_.m_49966_().m_61124_(SeaPickleBlock.f_56074_, p_224975_.m_188503_(4) + 1), 2);
        }
        for (Direction $$6 : Direction.Plane.HORIZONTAL) {
            BlockPos $$7;
            if (!(p_224975_.m_188501_() < 0.2f) || !p_224974_.m_8055_($$7 = p_224976_.m_121945_($$6)).m_60713_(Blocks.f_49990_)) continue;
            Registry.f_122824_.m_203431_(BlockTags.f_13052_).flatMap(p_224965_ -> p_224965_.m_213653_(p_224975_)).map(Holder::m_203334_).ifPresent(p_204725_ -> {
                BlockState $$4 = p_204725_.m_49966_();
                if ($$4.m_61138_(BaseCoralWallFanBlock.f_49192_)) {
                    $$4 = (BlockState)$$4.m_61124_(BaseCoralWallFanBlock.f_49192_, $$6);
                }
                p_224974_.m_7731_($$7, $$4, 2);
            });
        }
        return true;
    }
}

