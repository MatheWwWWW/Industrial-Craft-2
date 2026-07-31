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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class WouldSurvivePredicate
implements BlockPredicate {
    public static final Codec<WouldSurvivePredicate> f_190565_ = RecordCodecBuilder.create(p_190577_ -> p_190577_.group((App)Vec3i.m_194650_(16).optionalFieldOf("offset", (Object)Vec3i.f_123288_).forGetter(p_190581_ -> p_190581_.f_190566_), (App)BlockState.f_61039_.fieldOf("state").forGetter(p_190579_ -> p_190579_.f_190567_)).apply((Applicative)p_190577_, WouldSurvivePredicate::new));
    private final Vec3i f_190566_;
    private final BlockState f_190567_;

    protected WouldSurvivePredicate(Vec3i p_190570_, BlockState p_190571_) {
        this.f_190566_ = p_190570_;
        this.f_190567_ = p_190571_;
    }

    @Override
    public boolean test(WorldGenLevel p_190574_, BlockPos p_190575_) {
        return this.f_190567_.m_60710_(p_190574_, p_190575_.m_121955_(this.f_190566_));
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190440_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

