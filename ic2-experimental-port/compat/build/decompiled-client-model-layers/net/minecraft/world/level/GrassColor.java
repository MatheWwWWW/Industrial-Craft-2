/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

public class GrassColor {
    private static int[] f_46413_ = new int[65536];

    public static void m_46418_(int[] p_46419_) {
        f_46413_ = p_46419_;
    }

    public static int m_46415_(double p_46416_, double p_46417_) {
        int $$3 = (int)((1.0 - (p_46417_ *= p_46416_)) * 255.0);
        int $$2 = (int)((1.0 - p_46416_) * 255.0);
        int $$4 = $$3 << 8 | $$2;
        if ($$4 >= f_46413_.length) {
            return -65281;
        }
        return f_46413_[$$4];
    }
}

