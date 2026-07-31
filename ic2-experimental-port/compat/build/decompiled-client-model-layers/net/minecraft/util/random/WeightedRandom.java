/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.random;

import java.util.List;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;

public class WeightedRandom {
    private WeightedRandom() {
    }

    public static int m_146312_(List<? extends WeightedEntry> p_146313_) {
        long $$1 = 0L;
        for (WeightedEntry weightedEntry : p_146313_) {
            $$1 += (long)weightedEntry.m_142631_().m_146281_();
        }
        if ($$1 > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Sum of weights must be <= 2147483647");
        }
        return (int)$$1;
    }

    public static <T extends WeightedEntry> Optional<T> m_216825_(RandomSource p_216826_, List<T> p_216827_, int p_216828_) {
        if (p_216828_ < 0) {
            throw Util.m_137570_(new IllegalArgumentException("Negative total weight in getRandomItem"));
        }
        if (p_216828_ == 0) {
            return Optional.empty();
        }
        int $$3 = p_216826_.m_188503_(p_216828_);
        return WeightedRandom.m_146314_(p_216827_, $$3);
    }

    public static <T extends WeightedEntry> Optional<T> m_146314_(List<T> p_146315_, int p_146316_) {
        for (WeightedEntry $$2 : p_146315_) {
            if ((p_146316_ -= $$2.m_142631_().m_146281_()) >= 0) continue;
            return Optional.of($$2);
        }
        return Optional.empty();
    }

    public static <T extends WeightedEntry> Optional<T> m_216822_(RandomSource p_216823_, List<T> p_216824_) {
        return WeightedRandom.m_216825_(p_216823_, p_216824_, WeightedRandom.m_146312_(p_216824_));
    }
}

