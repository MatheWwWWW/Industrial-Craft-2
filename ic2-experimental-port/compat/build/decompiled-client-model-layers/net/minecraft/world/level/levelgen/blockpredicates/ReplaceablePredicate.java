/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.kinds.Applicative
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.codecs.RecordCodecBuilder
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.StateTestingPredicate;

class ReplaceablePredicate
extends StateTestingPredicate {
    public static final Codec<ReplaceablePredicate> f_190521_ = RecordCodecBuilder.create(p_190529_ -> ReplaceablePredicate.m_190546_(p_190529_).apply((Applicative)p_190529_, ReplaceablePredicate::new));

    public ReplaceablePredicate(Vec3i p_190524_) {
        super(p_190524_);
    }

    @Override
    protected boolean m_183454_(BlockState p_190527_) {
        return p_190527_.m_60767_().m_76336_();
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190439_;
    }
}

