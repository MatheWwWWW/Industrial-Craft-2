/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.UnderwaterMagmaConfiguration;
import net.minecraft.world.phys.AABB;

public class UnderwaterMagmaFeature
extends Feature<UnderwaterMagmaConfiguration> {
    public UnderwaterMagmaFeature(Codec<UnderwaterMagmaConfiguration> p_160560_) {
        super(p_160560_);
    }

    @Override
    public boolean m_142674_(FeaturePlaceContext<UnderwaterMagmaConfiguration> p_160569_) {
        Vec3i $$7;
        WorldGenLevel $$1 = p_160569_.m_159774_();
        BlockPos $$2 = p_160569_.m_159777_();
        UnderwaterMagmaConfiguration $$3 = p_160569_.m_159778_();
        RandomSource $$4 = p_160569_.m_225041_();
        OptionalInt $$5 = UnderwaterMagmaFeature.m_160564_($$1, $$2, $$3);
        if (!$$5.isPresent()) {
            return false;
        }
        BlockPos $$6 = $$2.m_175288_($$5.getAsInt());
        AABB $$8 = new AABB($$6.m_121996_($$7 = new Vec3i($$3.f_161265_, $$3.f_161265_, $$3.f_161265_)), $$6.m_121955_($$7));
        return BlockPos.m_121921_($$8).filter(p_225310_ -> $$4.m_188501_() < p_225309_.f_161266_).filter(p_160584_ -> this.m_160574_($$1, (BlockPos)p_160584_)).mapToInt(p_160579_ -> {
            $$1.m_7731_((BlockPos)p_160579_, Blocks.f_50450_.m_49966_(), 2);
            return 1;
        }).sum() > 0;
    }

    private static OptionalInt m_160564_(WorldGenLevel p_160565_, BlockPos p_160566_, UnderwaterMagmaConfiguration p_160567_) {
        Predicate<BlockState> $$3 = p_160586_ -> p_160586_.m_60713_(Blocks.f_49990_);
        Predicate<BlockState> $$4 = p_160581_ -> !p_160581_.m_60713_(Blocks.f_49990_);
        Optional<Column> $$5 = Column.m_158175_(p_160565_, p_160566_, p_160567_.f_161264_, $$3, $$4);
        return $$5.map(Column::m_142009_).orElseGet(OptionalInt::empty);
    }

    private boolean m_160574_(WorldGenLevel p_160575_, BlockPos p_160576_) {
        if (this.m_160561_(p_160575_, p_160576_) || this.m_160561_(p_160575_, p_160576_.m_7495_())) {
            return false;
        }
        for (Direction $$2 : Direction.Plane.HORIZONTAL) {
            if (!this.m_160561_(p_160575_, p_160576_.m_121945_($$2))) continue;
            return false;
        }
        return true;
    }

    private boolean m_160561_(LevelAccessor p_160562_, BlockPos p_160563_) {
        BlockState $$2 = p_160562_.m_8055_(p_160563_);
        return $$2.m_60713_(Blocks.f_49990_) || $$2.m_60795_();
    }
}

