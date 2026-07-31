/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 */
package net.minecraft.world.level.levelgen.blockpredicates;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

class TrueBlockPredicate
implements BlockPredicate {
    public static TrueBlockPredicate f_190553_ = new TrueBlockPredicate();
    public static final Codec<TrueBlockPredicate> f_190554_ = Codec.unit(() -> f_190553_);

    private TrueBlockPredicate() {
    }

    @Override
    public boolean test(WorldGenLevel p_190559_, BlockPos p_190560_) {
        return true;
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190445_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

