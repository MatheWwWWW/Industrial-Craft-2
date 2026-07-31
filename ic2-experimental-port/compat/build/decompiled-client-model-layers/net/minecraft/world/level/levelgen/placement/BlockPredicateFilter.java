/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.placement;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class BlockPredicateFilter
extends PlacementFilter {
    public static final Codec<BlockPredicateFilter> f_191569_ = RecordCodecBuilder.create(p_191575_ -> p_191575_.group((App)BlockPredicate.f_190392_.fieldOf("predicate").forGetter(p_191579_ -> p_191579_.f_191570_)).apply((Applicative)p_191575_, BlockPredicateFilter::new));
    private final BlockPredicate f_191570_;

    private BlockPredicateFilter(BlockPredicate p_191573_) {
        this.f_191570_ = p_191573_;
    }

    public static BlockPredicateFilter m_191576_(BlockPredicate p_191577_) {
        return new BlockPredicateFilter(p_191577_);
    }

    @Override
    protected boolean m_213917_(PlacementContext p_226321_, RandomSource p_226322_, BlockPos p_226323_) {
        return this.f_191570_.test(p_226321_.m_191831_(), p_226323_);
    }

    @Override
    public PlacementModifierType<?> m_183327_() {
        return PlacementModifierType.f_191848_;
    }
}

