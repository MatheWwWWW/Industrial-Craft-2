/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class LinearCongruentialGenerator {
    private static final long f_144818_ = 6364136223846793005L;
    private static final long f_144819_ = 1442695040888963407L;

    public static long m_13972_(long p_13973_, long p_13974_) {
        p_13973_ *= p_13973_ * 6364136223846793005L + 1442695040888963407L;
        return p_13973_ += p_13974_;
    }
}

