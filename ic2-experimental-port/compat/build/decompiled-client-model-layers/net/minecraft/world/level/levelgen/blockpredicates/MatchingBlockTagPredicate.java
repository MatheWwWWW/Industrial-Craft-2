/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.App
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.datafixers.kinds.App;
import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.StateTestingPredicate;

public class MatchingBlockTagPredicate
extends StateTestingPredicate {
    final TagKey<Block> f_198335_;
    public static final Codec<MatchingBlockTagPredicate> f_198336_ = RecordCodecBuilder.create(p_204688_ -> MatchingBlockTagPredicate.m_190546_(p_204688_).and((App)TagKey.m_203877_(Registry.f_122901_).fieldOf("tag").forGetter(p_204686_ -> p_204686_.f_198335_)).apply((Applicative)p_204688_, MatchingBlockTagPredicate::new));

    protected MatchingBlockTagPredicate(Vec3i p_204683_, TagKey<Block> p_204684_) {
        super(p_204683_);
        this.f_198335_ = p_204684_;
    }

    @Override
    protected boolean m_183454_(BlockState p_198343_) {
        return p_198343_.m_204336_(this.f_198335_);
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_198313_;
    }
}

