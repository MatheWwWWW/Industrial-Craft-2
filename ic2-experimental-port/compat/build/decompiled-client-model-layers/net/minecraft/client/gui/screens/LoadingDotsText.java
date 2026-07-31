/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screens;

public class LoadingDotsText {
    private static final String[] f_232740_ = new String[]{"O o o", "o O o", "o o O", "o O o"};
    private static final long f_232741_ = 300L;

    public static String m_232744_(long p_232745_) {
        int $$1 = (int)(p_232745_ / 300L % (long)f_232740_.length);
        return f_232740_[$$1];
    }
}

