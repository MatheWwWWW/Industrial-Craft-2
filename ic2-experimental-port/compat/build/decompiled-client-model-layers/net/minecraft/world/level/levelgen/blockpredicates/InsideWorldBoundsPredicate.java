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
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class InsideWorldBoundsPredicate
implements BlockPredicate {
    public static final Codec<InsideWorldBoundsPredicate> f_190463_ = RecordCodecBuilder.create(p_190473_ -> p_190473_.group((App)Vec3i.m_194650_(16).optionalFieldOf("offset", (Object)BlockPos.f_121853_).forGetter(p_190475_ -> p_190475_.f_190464_)).apply((Applicative)p_190473_, InsideWorldBoundsPredicate::new));
    private final Vec3i f_190464_;

    public InsideWorldBoundsPredicate(Vec3i p_190467_) {
        this.f_190464_ = p_190467_;
    }

    @Override
    public boolean test(WorldGenLevel p_190470_, BlockPos p_190471_) {
        return !p_190470_.m_151570_(p_190471_.m_121955_(this.f_190464_));
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_190441_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

