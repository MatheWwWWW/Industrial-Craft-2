/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  com.mojang.serialization.DataResult
 */
package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.invoke.MethodHandle;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.minecraft.util.ExtraCodecs;

public record InclusiveRange<T extends Comparable<T>>(T f_184563_, T f_184564_) {
    public static final Codec<InclusiveRange<Integer>> f_184562_ = InclusiveRange.m_184572_(Codec.INT);

    public InclusiveRange {
        if (f_184563_.compareTo(f_184564_) > 0) {
            throw new IllegalArgumentException("min_inclusive must be less than or equal to max_inclusive");
        }
    }

    public static <T extends Comparable<T>> Codec<InclusiveRange<T>> m_184572_(Codec<T> p_184573_) {
        return ExtraCodecs.m_184361_(p_184573_, "min_inclusive", "max_inclusive", InclusiveRange::m_184580_, InclusiveRange::f_184563_, InclusiveRange::f_184564_);
    }

    public static <T extends Comparable<T>> Codec<InclusiveRange<T>> m_184574_(Codec<T> p_184575_, T p_184576_, T p_184577_) {
        Function<InclusiveRange, DataResult> $$3 = p_184586_ -> {
            if (p_184586_.f_184563_().compareTo(p_184576_) < 0) {
                return DataResult.error((String)("Range limit too low, expected at least " + p_184576_ + " [" + p_184586_.f_184563_() + "-" + p_184586_.f_184564_() + "]"));
            }
            if (p_184586_.f_184564_().compareTo(p_184577_) > 0) {
                return DataResult.error((String)("Range limit too high, expected at most " + p_184577_ + " [" + p_184586_.f_184563_() + "-" + p_184586_.f_184564_() + "]"));
            }
            return DataResult.success((Object)p_184586_);
        };
        return InclusiveRange.m_184572_(p_184575_).flatXmap($$3, $$3);
    }

    public static <T extends Comparable<T>> DataResult<InclusiveRange<T>> m_184580_(T p_184581_, T p_184582_) {
        if (p_184581_.compareTo(p_184582_) <= 0) {
            return DataResult.success(new InclusiveRange<T>(p_184581_, p_184582_));
        }
        return DataResult.error((String)"min_inclusive must be less than or equal to max_inclusive");
    }

    public boolean m_184578_(T p_184579_) {
        return p_184579_.compareTo(this.f_184563_) >= 0 && p_184579_.compareTo(this.f_184564_) <= 0;
    }

    public boolean m_184570_(InclusiveRange<T> p_184571_) {
        return p_184571_.f_184563_().compareTo(this.f_184563_) >= 0 && p_184571_.f_184564_.compareTo(this.f_184564_) <= 0;
    }

    @Override
    public String toString() {
        return "[" + this.f_184563_ + ", " + this.f_184564_ + "]";
    }

    @Override
    public final int hashCode() {
        return (int)ObjectMethods.bootstrap("hashCode", new MethodHandle[]{InclusiveRange.class, "minInclusive;maxInclusive", "f_184563_", "f_184564_"}, this);
    }

    @Override
    public final boolean equals(Object p_184589_) {
        return (boolean)ObjectMethods.bootstrap("equals", new MethodHandle[]{InclusiveRange.class, "minInclusive;maxInclusive", "f_184563_", "f_184564_"}, this, p_184589_);
    }
}

