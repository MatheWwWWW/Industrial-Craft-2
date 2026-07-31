/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.level.storage;

public class DataVersion {
    private final int f_192994_;
    private final String f_192995_;
    public static String f_192993_ = "main";

    public DataVersion(int p_192998_) {
        this(p_192998_, f_192993_);
    }

    public DataVersion(int p_193000_, String p_193001_) {
        this.f_192994_ = p_193000_;
        this.f_192995_ = p_193001_;
    }

    public boolean m_193002_() {
        return !this.f_192995_.equals(f_192993_);
    }

    public String m_193005_() {
        return this.f_192995_;
    }

    public int m_193006_() {
        return this.f_192994_;
    }

    public boolean m_193003_(DataVersion p_193004_) {
        return this.m_193005_().equals(p_193004_.m_193005_());
    }
}

