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

class AnyOfPredicate
extends CombiningPredicate {
    public static final Codec<AnyOfPredicate> f_190381_ = AnyOfPredicate.m_190458_(AnyOfPredicate::new);

    public AnyOfPredicate(List<BlockPredicate> p_190384_) {
        super(p_190384_);
    }

    @Override
    public boolean test(WorldGenLevel p_190387_, BlockPos p_190388_) {
        for (BlockPredicate $$2 : this.f_190453_) {
            if (!$$2.test(p_190387_, p_190388_)) continue;
            return true;
        }
        return false;
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190442_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

