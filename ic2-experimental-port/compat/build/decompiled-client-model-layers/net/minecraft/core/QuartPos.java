/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.core;

public final class QuartPos {
    public static final int f_175396_ = 2;
    public static final int f_175397_ = 4;
    public static final int f_194564_ = 3;
    private static final int f_175398_ = 2;

    private QuartPos() {
    }

    public static int m_175400_(int p_175401_) {
        return p_175401_ >> 2;
    }

    public static int m_198376_(int p_198377_) {
        return p_198377_ & 3;
    }

    public static int m_175402_(int p_175403_) {
        return p_175403_ << 2;
    }

    public static int m_175404_(int p_175405_) {
        return p_175405_ << 2;
    }

    public static int m_175406_(int p_175407_) {
        return p_175407_ >> 2;
    }
}

