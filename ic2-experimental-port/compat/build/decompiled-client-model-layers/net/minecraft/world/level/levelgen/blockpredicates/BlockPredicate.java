/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.AllOfPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.AnyOfPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.HasSturdyFacePredicate;
import net.minecraft.world.level.levelgen.blockpredicates.InsideWorldBoundsPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingBlockTagPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingBlocksPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.MatchingFluidsPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.NotPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.ReplaceablePredicate;
import net.minecraft.world.level.levelgen.blockpredicates.SolidPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.TrueBlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.WouldSurvivePredicate;
import net.minecraft.world.level.material.Fluid;

public interface BlockPredicate
extends BiPredicate<WorldGenLevel, BlockPos> {
    public static final Codec<BlockPredicate> f_190392_ = Registry.f_194566_.m_194605_().dispatch(BlockPredicate::m_183575_, BlockPredicateType::m_190452_);
    public static final BlockPredicate f_190393_ = BlockPredicate.m_224780_(Blocks.f_50016_);
    public static final BlockPredicate f_190394_ = BlockPredicate.m_224780_(Blocks.f_50016_, Blocks.f_49990_);

    public BlockPredicateType<?> m_183575_();

    public static BlockPredicate m_190412_(List<BlockPredicate> p_190413_) {
        return new AllOfPredicate(p_190413_);
    }

    public static BlockPredicate m_190417_(BlockPredicate ... p_190418_) {
        return BlockPredicate.m_190412_(List.of(p_190418_));
    }

    public static BlockPredicate m_190404_(BlockPredicate p_190405_, BlockPredicate p_190406_) {
        return BlockPredicate.m_190412_(List.of(p_190405_, p_190406_));
    }

    public static BlockPredicate m_190425_(List<BlockPredicate> p_190426_) {
        return new AnyOfPredicate(p_190426_);
    }

    public static BlockPredicate m_190430_(BlockPredicate ... p_190431_) {
        return BlockPredicate.m_190425_(List.of(p_190431_));
    }

    public static BlockPredicate m_190420_(BlockPredicate p_190421_, BlockPredicate p_190422_) {
        return BlockPredicate.m_190425_(List.of(p_190421_, p_190422_));
    }

    public static BlockPredicate m_224771_(Vec3i p_224772_, List<Block> p_224773_) {
        return new MatchingBlocksPredicate(p_224772_, HolderSet.m_205803_(Block::m_204297_, p_224773_));
    }

    public static BlockPredicate m_198311_(List<Block> p_198312_) {
        return BlockPredicate.m_224771_(Vec3i.f_123288_, p_198312_);
    }

    public static BlockPredicate m_224774_(Vec3i p_224775_, Block ... p_224776_) {
        return BlockPredicate.m_224771_(p_224775_, List.of(p_224776_));
    }

    public static BlockPredicate m_224780_(Block ... p_224781_) {
        return BlockPredicate.m_224774_(Vec3i.f_123288_, p_224781_);
    }

    public static BlockPredicate m_224768_(Vec3i p_224769_, TagKey<Block> p_224770_) {
        return new MatchingBlockTagPredicate(p_224769_, p_224770_);
    }

    public static BlockPredicate m_204677_(TagKey<Block> p_204678_) {
        return BlockPredicate.m_224768_(Vec3i.f_123288_, p_204678_);
    }

    public static BlockPredicate m_224784_(Vec3i p_224785_, List<Fluid> p_224786_) {
        return new MatchingFluidsPredicate(p_224785_, HolderSet.m_205803_(Fluid::m_205069_, p_224786_));
    }

    public static BlockPredicate m_224777_(Vec3i p_224778_, Fluid ... p_224779_) {
        return BlockPredicate.m_224784_(p_224778_, List.of(p_224779_));
    }

    public static BlockPredicate m_224782_(Fluid ... p_224783_) {
        return BlockPredicate.m_224777_(Vec3i.f_123288_, p_224783_);
    }

    public static BlockPredicate m_190402_(BlockPredicate p_190403_) {
        return new NotPredicate(p_190403_);
    }

    public static BlockPredicate m_190410_(Vec3i p_190411_) {
        return new ReplaceablePredicate(p_190411_);
    }

    public static BlockPredicate m_190419_() {
        return BlockPredicate.m_190410_(Vec3i.f_123288_);
    }

    public static BlockPredicate m_190399_(BlockState p_190400_, Vec3i p_190401_) {
        return new WouldSurvivePredicate(p_190401_, p_190400_);
    }

    public static BlockPredicate m_198308_(Vec3i p_198309_, Direction p_198310_) {
        return new HasSturdyFacePredicate(p_198309_, p_198310_);
    }

    public static BlockPredicate m_198913_(Direction p_198914_) {
        return BlockPredicate.m_198308_(Vec3i.f_123288_, p_198914_);
    }

    public static BlockPredicate m_190423_(Vec3i p_190424_) {
        return new SolidPredicate(p_190424_);
    }

    public static BlockPredicate m_190432_() {
        return BlockPredicate.m_190423_(Vec3i.f_123288_);
    }

    public static BlockPredicate m_190433_(Vec3i p_190434_) {
        return new InsideWorldBoundsPredicate(p_190434_);
    }

    public static BlockPredicate m_190435_() {
        return TrueBlockPredicate.f_190553_;
    }
}

