/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level;

public class FoliageColor {
    private static int[] f_46104_ = new int[65536];

    public static void m_46110_(int[] p_46111_) {
        f_46104_ = p_46111_;
    }

    public static int m_46107_(double p_46108_, double p_46109_) {
        int $$3 = (int)((1.0 - (p_46109_ *= p_46108_)) * 255.0);
        int $$2 = (int)((1.0 - p_46108_) * 255.0);
        int $$4 = $$3 << 8 | $$2;
        if ($$4 >= f_46104_.length) {
            return FoliageColor.m_46113_();
        }
        return f_46104_[$$4];
    }

    public static int m_46106_() {
        return 0x619961;
    }

    public static int m_46112_() {
        return 8431445;
    }

    public static int m_46113_() {
        return 4764952;
    }

    public static int m_220346_() {
        return 9619016;
    }
}

