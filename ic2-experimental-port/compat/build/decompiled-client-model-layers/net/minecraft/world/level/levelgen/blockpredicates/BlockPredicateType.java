/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.blockpredicates.AllOfPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.AnyOfPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
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

public interface BlockPredicateType<P extends BlockPredicate> {
    public static final BlockPredicateType<MatchingBlocksPredicate> f_190436_ = BlockPredicateType.m_190449_("matching_blocks", MatchingBlocksPredicate.f_190479_);
    public static final BlockPredicateType<MatchingBlockTagPredicate> f_198313_ = BlockPredicateType.m_190449_("matching_block_tag", MatchingBlockTagPredicate.f_198336_);
    public static final BlockPredicateType<MatchingFluidsPredicate> f_190437_ = BlockPredicateType.m_190449_("matching_fluids", MatchingFluidsPredicate.f_190492_);
    public static final BlockPredicateType<HasSturdyFacePredicate> f_198314_ = BlockPredicateType.m_190449_("has_sturdy_face", HasSturdyFacePredicate.f_198315_);
    public static final BlockPredicateType<SolidPredicate> f_190438_ = BlockPredicateType.m_190449_("solid", SolidPredicate.f_190530_);
    public static final BlockPredicateType<ReplaceablePredicate> f_190439_ = BlockPredicateType.m_190449_("replaceable", ReplaceablePredicate.f_190521_);
    public static final BlockPredicateType<WouldSurvivePredicate> f_190440_ = BlockPredicateType.m_190449_("would_survive", WouldSurvivePredicate.f_190565_);
    public static final BlockPredicateType<InsideWorldBoundsPredicate> f_190441_ = BlockPredicateType.m_190449_("inside_world_bounds", InsideWorldBoundsPredicate.f_190463_);
    public static final BlockPredicateType<AnyOfPredicate> f_190442_ = BlockPredicateType.m_190449_("any_of", AnyOfPredicate.f_190381_);
    public static final BlockPredicateType<AllOfPredicate> f_190443_ = BlockPredicateType.m_190449_("all_of", AllOfPredicate.f_190370_);
    public static final BlockPredicateType<NotPredicate> f_190444_ = BlockPredicateType.m_190449_("not", NotPredicate.f_190505_);
    public static final BlockPredicateType<TrueBlockPredicate> f_190445_ = BlockPredicateType.m_190449_("true", TrueBlockPredicate.f_190554_);

    public Codec<P> m_190452_();

    private static <P extends BlockPredicate> BlockPredicateType<P> m_190449_(String p_190450_, Codec<P> p_190451_) {
        return Registry.m_122961_(Registry.f_194566_, p_190450_, () -> p_190451_);
    }
}

