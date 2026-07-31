/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class FastColor {

    public static class ARGB32 {
        public static int m_13655_(int p_13656_) {
            return p_13656_ >>> 24;
        }

        public static int m_13665_(int p_13666_) {
            return p_13666_ >> 16 & 0xFF;
        }

        public static int m_13667_(int p_13668_) {
            return p_13668_ >> 8 & 0xFF;
        }

        public static int m_13669_(int p_13670_) {
            return p_13670_ & 0xFF;
        }

        public static int m_13660_(int p_13661_, int p_13662_, int p_13663_, int p_13664_) {
            return p_13661_ << 24 | p_13662_ << 16 | p_13663_ << 8 | p_13664_;
        }

        public static int m_13657_(int p_13658_, int p_13659_) {
            return ARGB32.m_13660_(ARGB32.m_13655_(p_13658_) * ARGB32.m_13655_(p_13659_) / 255, ARGB32.m_13665_(p_13658_) * ARGB32.m_13665_(p_13659_) / 255, ARGB32.m_13667_(p_13658_) * ARGB32.m_13667_(p_13659_) / 255, ARGB32.m_13669_(p_13658_) * ARGB32.m_13669_(p_13659_) / 255);
        }
    }
}

