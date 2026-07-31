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
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class HasSturdyFacePredicate
implements BlockPredicate {
    private final Vec3i f_198316_;
    private final Direction f_198317_;
    public static final Codec<HasSturdyFacePredicate> f_198315_ = RecordCodecBuilder.create(p_198327_ -> p_198327_.group((App)Vec3i.m_194650_(16).optionalFieldOf("offset", (Object)Vec3i.f_123288_).forGetter(p_198331_ -> p_198331_.f_198316_), (App)Direction.f_175356_.fieldOf("direction").forGetter(p_198329_ -> p_198329_.f_198317_)).apply((Applicative)p_198327_, HasSturdyFacePredicate::new));

    public HasSturdyFacePredicate(Vec3i p_198320_, Direction p_198321_) {
        this.f_198316_ = p_198320_;
        this.f_198317_ = p_198321_;
    }

    @Override
    public boolean test(WorldGenLevel p_198324_, BlockPos p_198325_) {
        BlockPos $$2 = p_198325_.m_121955_(this.f_198316_);
        return p_198324_.m_8055_($$2).m_60783_(p_198324_, $$2, this.f_198317_);
    }

    @Override
    public BlockPredicateType<?> m_183575_() {
        return BlockPredicateType.f_198314_;
    }

    @Override
    public /* synthetic */ boolean test(Object object, Object object2) {
        return this.test((WorldGenLevel)object, (BlockPos)object2);
    }
}

