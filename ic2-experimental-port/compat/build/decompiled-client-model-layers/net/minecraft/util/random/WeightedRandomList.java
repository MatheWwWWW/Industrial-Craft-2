/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.serialization.Codec
 */
package net.minecraft.util.random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;

public class WeightedRandomList<E extends WeightedEntry> {
    private final int f_146324_;
    private final ImmutableList<E> f_146325_;

    WeightedRandomList(List<? extends E> p_146327_) {
        this.f_146325_ = ImmutableList.copyOf(p_146327_);
        this.f_146324_ = WeightedRandom.m_146312_(p_146327_);
    }

    public static <E extends WeightedEntry> WeightedRandomList<E> m_146332_() {
        return new WeightedRandomList<E>(ImmutableList.of());
    }

    @SafeVarargs
    public static <E extends WeightedEntry> WeightedRandomList<E> m_146330_(E ... p_146331_) {
        return new WeightedRandomList<E>(ImmutableList.copyOf((Object[])p_146331_));
    }

    public static <E extends WeightedEntry> WeightedRandomList<E> m_146328_(List<E> p_146329_) {
        return new WeightedRandomList<E>(p_146329_);
    }

    public boolean m_146337_() {
        return this.f_146325_.isEmpty();
    }

    public Optional<E> m_216829_(RandomSource p_216830_) {
        if (this.f_146324_ == 0) {
            return Optional.empty();
        }
        int $$1 = p_216830_.m_188503_(this.f_146324_);
        return WeightedRandom.m_146314_(this.f_146325_, $$1);
    }

    public List<E> m_146338_() {
        return this.f_146325_;
    }

    public static <E extends WeightedEntry> Codec<WeightedRandomList<E>> m_146333_(Codec<E> p_146334_) {
        return p_146334_.listOf().xmap(WeightedRandomList::m_146328_, WeightedRandomList::m_146338_);
    }
}

