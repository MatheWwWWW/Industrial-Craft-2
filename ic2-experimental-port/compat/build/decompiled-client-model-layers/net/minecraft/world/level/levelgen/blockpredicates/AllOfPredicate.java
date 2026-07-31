/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.minecraft.world.level.levelgen.blockpredicates.CombiningPredicate;

class AllOfPredicate
extends CombiningPredicate {
    public static final Codec<AllOfPredicate> f_190370_ = AllOfPredicate.m_190458_(AllOfPredicate::new);

    public AllOfPredicate(List<BlockPredicate> p_190373_) {
        super(p_190373_);
    }

    @Override
    public boolean test(WorldGenLevel p_190376_, BlockPos p_190377_) {
        for (BlockPredicate $$2 : this.f_190453_) {
            if ($$2.test(p_190376_, p_190377_)) continue;
            return false;
        }
        return true;
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190443_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

