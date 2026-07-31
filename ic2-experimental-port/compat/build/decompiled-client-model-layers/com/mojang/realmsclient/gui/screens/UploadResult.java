/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package com.mojang.realmsclient.gui.screens;

import javax.annotation.Nullable;

public class UploadResult {
    public final int f_90133_;
    @Nullable
    public final String f_90134_;

    UploadResult(int p_90136_, String p_90137_) {
        this.f_90133_ = p_90136_;
        this.f_90134_ = p_90137_;
    }

    public static class Builder {
        private int f_90142_ = -1;
        private String f_90143_;

        public Builder m_90146_(int p_90147_) {
            this.f_90142_ = p_90147_;
            return this;
        }

        public Builder m_90148_(@Nullable String p_90149_) {
            this.f_90143_ = p_90149_;
            return this;
        }

        public UploadResult m_90145_() {
            return new UploadResult(this.f_90142_, this.f_90143_);
        }
    }
}

