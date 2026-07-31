/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class CubicSampler {
    private static final int f_177979_ = 2;
    private static final int f_177980_ = 6;
    private static final double[] f_130036_ = new double[]{0.0, 1.0, 4.0, 6.0, 4.0, 1.0, 0.0};

    private CubicSampler() {
    }

    public static Vec3 m_130038_(Vec3 p_130039_, Vec3Fetcher p_130040_) {
        int $$2 = Mth.m_14107_(p_130039_.m_7096_());
        int $$3 = Mth.m_14107_(p_130039_.m_7098_());
        int $$4 = Mth.m_14107_(p_130039_.m_7094_());
        double $$5 = p_130039_.m_7096_() - (double)$$2;
        double $$6 = p_130039_.m_7098_() - (double)$$3;
        double $$7 = p_130039_.m_7094_() - (double)$$4;
        double $$8 = 0.0;
        Vec3 $$9 = Vec3.f_82478_;
        for (int $$10 = 0; $$10 < 6; ++$$10) {
            double $$11 = Mth.m_14139_($$5, f_130036_[$$10 + 1], f_130036_[$$10]);
            int $$12 = $$2 - 2 + $$10;
            for (int $$13 = 0; $$13 < 6; ++$$13) {
                double $$14 = Mth.m_14139_($$6, f_130036_[$$13 + 1], f_130036_[$$13]);
                int $$15 = $$3 - 2 + $$13;
                for (int $$16 = 0; $$16 < 6; ++$$16) {
                    double $$17 = Mth.m_14139_($$7, f_130036_[$$16 + 1], f_130036_[$$16]);
                    int $$18 = $$4 - 2 + $$16;
                    double $$19 = $$11 * $$14 * $$17;
                    $$8 += $$19;
                    $$9 = $$9.m_82549_(p_130040_.m_130041_($$12, $$15, $$18).m_82490_($$19));
                }
            }
        }
        $$9 = $$9.m_82490_(1.0 / $$8);
        return $$9;
    }

    @FunctionalInterface
    public static interface Vec3Fetcher {
        public Vec3 m_130041_(int var1, int var2, int var3);
    }
}

