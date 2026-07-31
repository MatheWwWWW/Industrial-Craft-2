/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.ImmutableList$Builder
 *  com.mojang.serialization.Codec
 */
package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;

public class SimpleWeightedRandomList<E>
extends WeightedRandomList<WeightedEntry.Wrapper<E>> {
    public static <E> Codec<SimpleWeightedRandomList<E>> m_185860_(Codec<E> p_185861_) {
        return WeightedEntry.Wrapper.m_146305_(p_185861_).listOf().xmap(SimpleWeightedRandomList::new, WeightedRandomList::m_146338_);
    }

    public static <E> Codec<SimpleWeightedRandomList<E>> m_146264_(Codec<E> p_146265_) {
        return ExtraCodecs.m_144637_(WeightedEntry.Wrapper.m_146305_(p_146265_).listOf()).xmap(SimpleWeightedRandomList::new, WeightedRandomList::m_146338_);
    }

    SimpleWeightedRandomList(List<? extends WeightedEntry.Wrapper<E>> p_146262_) {
        super(p_146262_);
    }

    public static <E> Builder<E> m_146263_() {
        return new Builder();
    }

    public static <E> SimpleWeightedRandomList<E> m_185864_() {
        return new SimpleWeightedRandomList<E>(List.of());
    }

    public static <E> SimpleWeightedRandomList<E> m_185862_(E p_185863_) {
        return new SimpleWeightedRandomList<E>(List.of(WeightedEntry.m_146290_(p_185863_, 1)));
    }

    public Optional<E> m_216820_(RandomSource p_216821_) {
        return this.m_216829_(p_216821_).map(WeightedEntry.Wrapper::m_146310_);
    }

    public static class Builder<E> {
        private final ImmutableList.Builder<WeightedEntry.Wrapper<E>> f_146268_ = ImmutableList.builder();

        public Builder<E> m_146271_(E p_146272_, int p_146273_) {
            this.f_146268_.add(WeightedEntry.m_146290_(p_146272_, p_146273_));
            return this;
        }

        public SimpleWeightedRandomList<E> m_146270_() {
            return new SimpleWeightedRandomList(this.f_146268_.build());
        }
    }
}

