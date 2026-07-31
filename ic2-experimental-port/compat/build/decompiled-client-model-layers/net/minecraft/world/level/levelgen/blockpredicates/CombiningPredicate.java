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
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;

abstract class CombiningPredicate
implements BlockPredicate {
    protected final List<BlockPredicate> f_190453_;

    protected CombiningPredicate(List<BlockPredicate> p_190455_) {
        this.f_190453_ = p_190455_;
    }

    public static <T extends CombiningPredicate> Codec<T> m_190458_(Function<List<BlockPredicate>, T> p_190459_) {
        return RecordCodecBuilder.create(p_190462_ -> p_190462_.group((App)BlockPredicate.f_190392_.listOf().fieldOf("predicates").forGetter(p_190457_ -> p_190457_.f_190453_)).apply((Applicative)p_190462_, p_190459_));
    }
}

