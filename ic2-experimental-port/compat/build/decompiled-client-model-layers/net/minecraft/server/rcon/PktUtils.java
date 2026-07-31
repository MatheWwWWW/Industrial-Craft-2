/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.rcon;

import java.nio.charset.StandardCharsets;

public class PktUtils {
    public static final int f_144020_ = 1460;
    public static final char[] f_11481_ = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String m_11488_(byte[] p_11489_, int p_11490_, int p_11491_) {
        int $$4;
        int $$3 = p_11491_ - 1;
        int n = $$4 = p_11490_ > $$3 ? $$3 : p_11490_;
        while (0 != p_11489_[$$4] && $$4 < $$3) {
            ++$$4;
        }
        return new String(p_11489_, p_11490_, $$4 - p_11490_, StandardCharsets.UTF_8);
    }

    public static int m_11485_(byte[] p_11486_, int p_11487_) {
        return PktUtils.m_11492_(p_11486_, p_11487_, p_11486_.length);
    }

    public static int m_11492_(byte[] p_11493_, int p_11494_, int p_11495_) {
        if (0 > p_11495_ - p_11494_ - 4) {
            return 0;
        }
        return p_11493_[p_11494_ + 3] << 24 | (p_11493_[p_11494_ + 2] & 0xFF) << 16 | (p_11493_[p_11494_ + 1] & 0xFF) << 8 | p_11493_[p_11494_] & 0xFF;
    }

    public static int m_11496_(byte[] p_11497_, int p_11498_, int p_11499_) {
        if (0 > p_11499_ - p_11498_ - 4) {
            return 0;
        }
        return p_11497_[p_11498_] << 24 | (p_11497_[p_11498_ + 1] & 0xFF) << 16 | (p_11497_[p_11498_ + 2] & 0xFF) << 8 | p_11497_[p_11498_ + 3] & 0xFF;
    }

    public static String m_11483_(byte p_11484_) {
        return "" + f_11481_[(p_11484_ & 0xF0) >>> 4] + f_11481_[p_11484_ & 0xF];
    }
}

