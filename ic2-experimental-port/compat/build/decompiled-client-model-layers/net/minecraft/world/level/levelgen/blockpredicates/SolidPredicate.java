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

public class SolidPredicate
extends StateTestingPredicate {
    public static final Codec<SolidPredicate> f_190530_ = RecordCodecBuilder.create(p_190538_ -> SolidPredicate.m_190546_(p_190538_).apply((Applicative)p_190538_, SolidPredicate::new));

    public SolidPredicate(Vec3i p_190533_) {
        super(p_190533_);
    }

    @Override
    protected boolean m_183454_(BlockState p_190536_) {
        return p_190536_.m_60767_().m_76333_();
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190438_;
    }
}

