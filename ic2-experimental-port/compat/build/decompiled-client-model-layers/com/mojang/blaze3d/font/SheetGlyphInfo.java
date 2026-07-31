/*
 * Decompiled with CFR 0.152.
 */
package com.mojang.blaze3d.font;

public interface SheetGlyphInfo {
    public int m_213962_();

    public int m_213961_();

    public void m_213958_(int var1, int var2);

    public boolean m_213965_();

    public float m_213963_();

    default public float m_231094_() {
        return this.m_213966_();
    }

    default public float m_231095_() {
        return this.m_231094_() + (float)this.m_213962_() / this.m_213963_();
    }

    default public float m_231096_() {
        return this.m_213964_();
    }

    default public float m_231097_() {
        return this.m_231096_() + (float)this.m_213961_() / this.m_213963_();
    }

    default public float m_213966_() {
        return 0.0f;
    }

    default public float m_213964_() {
        return 3.0f;
    }
}

