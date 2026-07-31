/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.util;

import javax.annotation.Nullable;

public class ExceptionCollector<T extends Throwable> {
    @Nullable
    private T f_13650_;

    public void m_13653_(T p_13654_) {
        if (this.f_13650_ == null) {
            this.f_13650_ = p_13654_;
        } else {
            ((Throwable)this.f_13650_).addSuppressed((Throwable)p_13654_);
        }
    }

    public void m_13652_() throws T {
        if (this.f_13650_ != null) {
            throw this.f_13650_;
        }
    }
}

