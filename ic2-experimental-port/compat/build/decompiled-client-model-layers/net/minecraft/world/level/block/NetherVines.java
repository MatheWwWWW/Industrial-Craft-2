/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.block;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public class NetherVines {
    private static final double f_153987_ = 0.826;
    public static final double f_153986_ = 0.1;

    public static boolean m_54963_(BlockState p_54964_) {
        return p_54964_.m_60795_();
    }

    public static int m_221803_(RandomSource p_221804_) {
        double $$1 = 1.0;
        int $$2 = 0;
        while (p_221804_.m_188500_() < $$1) {
            $$1 *= 0.826;
            ++$$2;
        }
        return $$2;
    }
}

