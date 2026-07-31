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
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

class NotPredicate
implements BlockPredicate {
    public static final Codec<NotPredicate> f_190505_ = RecordCodecBuilder.create(p_190515_ -> p_190515_.group((App)BlockPredicate.f_190392_.fieldOf("predicate").forGetter(p_190517_ -> p_190517_.f_190506_)).apply((Applicative)p_190515_, NotPredicate::new));
    private final BlockPredicate f_190506_;

    public NotPredicate(BlockPredicate p_190509_) {
        this.f_190506_ = p_190509_;
    }

    @Override
    public boolean test(WorldGenLevel p_190512_, BlockPos p_190513_) {
        return !this.f_190506_.test(p_190512_, p_190513_);
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190444_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

