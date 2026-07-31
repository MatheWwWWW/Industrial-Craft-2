/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft;

import net.minecraft.CrashReport;

public class ReportedException
extends RuntimeException {
    private final CrashReport f_134758_;

    public ReportedException(CrashReport p_134760_) {
        this.f_134758_ = p_134760_;
    }

    public CrashReport m_134761_() {
        return this.f_134758_;
    }

    @Override
    public Throwable getCause() {
        return this.f_134758_.m_127524_();
    }

    @Override
    public String getMessage() {
        return this.f_134758_.m_127511_();
    }
}

