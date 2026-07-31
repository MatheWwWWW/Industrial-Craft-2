/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.server;

import net.minecraft.Util;

public class LanServer {
    private final String f_120072_;
    private final String f_120073_;
    private long f_120074_;

    public LanServer(String p_120076_, String p_120077_) {
        this.f_120072_ = p_120076_;
        this.f_120073_ = p_120077_;
        this.f_120074_ = Util.m_137550_();
    }

    public String m_120078_() {
        return this.f_120072_;
    }

    public String m_120079_() {
        return this.f_120073_;
    }

    public void m_120080_() {
        this.f_120074_ = Util.m_137550_();
    }
}

